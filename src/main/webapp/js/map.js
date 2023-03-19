
var leafletMap = new L.Map("mapDiv", {
    zoom: 18,
    center: [32.216311,119.443884],
    boxZoom: true,
});
var wmsLayer= L.tileLayer.wms("http://localhost:5436/geoserver/lmy/wms", { // 链接要改对应的
    layers: 'lmy:drillpoints_wgs84',//需要加载的图层,就是我们刚刚新建的。map 和 图层名称
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
