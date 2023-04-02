# 钻孔数据可视化项目
## 使用方法：
    1. 更改Geoserver参数(在src/main/webapp/js/mapjs的第7 8行，改变端口号和图层)
    2. 将数据库导出为.shp文件，并转换投影为UTM投影，否则leaflet无法识别
    3. 在idea中下载Maven Helper插件，然后右键项目->Run Maven->Tomcat7:run即可，按照提示打开网站

## 命名
    1. Geoserver工作空间dilldata
    2. Geoserver存储仓库drillpoints4mssql
    3. 图层名称drillpoints
    4. 图层标题drillpoints
    5. Geoserver端口8090
    6. MSSQL端口1433
    7. Tomcat端口80

## 注意：
    geoserver需要开启跨域，解决方案https://blog.csdn.net/xinleicol/article/details/118308342
