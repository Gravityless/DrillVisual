# 钻孔数据可视化项目
## 使用方法：
    1. 默认进入index.html，选择可视化平台进入主页面，选择模拟输入输出进入测试页面
    2. 建议本地安装Tomcat8，以避免旧版本的乱码错误，但仍可以按照3使用集成Tomcat7
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
