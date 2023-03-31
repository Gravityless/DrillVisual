//
// var leafletMap = new L.Map("mapDiv", {
//     zoom: 25,
//     center: [32.216311,119.443884],
//     maxZoom:25,
//     maxNativeZoom:25,
// });
// var wmsLayer= L.tileLayer.wms("http://localhost:8090/geoserver/drilldata/wms", { // 链接对应工作空间
//     layers: 'drilldata:drillpoints', //需要加载的图层,对应工作空间和图层名称
// });
// leafletMap.addLayer(wmsLayer);
//
//
// var popup = L.popup();
// function onMapClick(e) {
//     popup
//     .setLatLng(e.latlng)
//     .setContent("You clicked the map at " + e.latlng.toString())
//     .openOn(leafletMap);
// }
// leafletMap.on('click', onMapClick);
function init(){

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
    var map = new ol.Map({
        layers: tiled,
        target: 'mapDiv',
        view: new ol.View({
            projection: projection
        }),
        controls: ol.control.defaults({
            attributionOptions: {
                collapsible: false
            }
        })
    });
    map.getView().fit(extent, map.getSize());
}
