
var leafletMap = new L.Map("mapDiv", {
    zoom: 18,
    center: [32.216311,119.443884],
    boxZoom: true,
});
var wmsLayer= L.tileLayer.wms("http://localhost:8090/geoserver/drilldata/wms", { // 链接对应工作空间
    layers: 'drilldata:drillpoints', //需要加载的图层,对应工作空间和图层名称
});
leafletMap.addLayer(wmsLayer);


var popup = L.popup();
function onMapClick(e) {
    popup
    .setLatLng(e.latlng)
    .setContent("You clicked the map at " + e.latlng.toString())
    .openOn(leafletMap);
}
leafletMap.on('click', onMapClick);
