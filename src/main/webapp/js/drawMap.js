var extent=[447473.9310505188,3565849.0686830175,447643.00895286,3566068.461321368]

var tiled = [
    new ol.layer.Tile({
        source: new ol.source.TileWMS({
            url: 'http://localhost:8090/geoserver/drilldata/wms',
            params: {
                'LAYERS': 'drilldata:drillpoints',
                'TILED': false
            },
            serverType: 'geoserver'
        })
    })
];
var projection = new ol.proj.Projection({
    code: 'EPSG:4549',
    units: 'm',
    axisOrientation: 'neu',
    global: false
});
var mousePositionControl = new ol.control.MousePosition({
    className: 'custom-mouse-position',
    target: document.querySelector("#wrapper #location"),
    coordinateFormat: ol.coordinate.createStringXY(5),
    undefinedHTML: '&nbsp;'
});
var map = new ol.Map({
    layers: tiled,
    target: 'mapDiv',
    view: new ol.View({
        projection: projection
    }),
    controls: ol.control.defaults().extend([mousePositionControl]),
});
map.getView().fit(extent, map.getSize());
map.getView().on('change:resolution', function(evt) {
    var resolution = evt.target.get('resolution');
    var units = map.getView().getProjection().getUnits();
    var dpi = 25.4 / 0.28;
    var mpu = ol.proj.METERS_PER_UNIT[units];
    var scale = resolution * mpu * 39.37 * dpi;
    if (scale >= 9500 && scale <= 950000) {
        scale = Math.round(scale / 1000) + "K";
    } else if (scale >= 950000) {
        scale = Math.round(scale / 1000000) + "M";
    } else {
        scale = Math.round(scale);
    }
    document.getElementById('scale').innerHTML = "比例尺：1:" + scale;
});
map.on('singleclick', function(evt) {
    document.getElementById('pointtable').innerHTML = "Loading... please wait...";
    var view = map.getView();
    var viewResolution = view.getResolution();
    var source = tiled[0].getSource();
    var url = source.getGetFeatureInfoUrl(
        evt.coordinate, viewResolution, view.getProjection(),
        {'INFO_FORMAT': 'text/html', 'FEATURE_COUNT': 50});
    if (url) {
        document.getElementById('pointtable').innerHTML = '<iframe seamless src="' + url + '"></iframe>';
    }
    if (url) {
        fetch(url)
            .then(function (response) { return response.text(); })
            .then(function (html) {
                console.log(html);
                //document.getElementById('info').innerHTML = html;
            });
    }
});