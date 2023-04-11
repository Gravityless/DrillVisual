// 这里输入模拟JSON数组
request = ["35-1001","35-1002","35-1003"];
// POST
axios.post('http://localhost/DrillVisual/FieldTestServlet', request)
  .then(function (response) {
    // 这里获取返回数据
    // document.getElementById("output").innerHTML = JSON.stringify(response.data);
    var result = response.data;
    // 获取图表元素
    var myChart = echarts.init(document.getElementById("dataDiv"));
    // 指定数据xArray和seriesArray
    var xArray = [];
    var seriesArray = [];

    // 计算xArray
    var idx, sum;
    sum = 0;
    for (idx = 0; idx < result.drillDistance.length; idx++) {
        xArray[idx] = sum;
        sum += result.drillDistance[idx];
    }
    xArray[idx] = sum;
    alert(xArray);

    // 计算seriesArray
    var colNum = result.drillDistance.length + 1;
    var arcNum = result.layerLineList.length;
    var arcList = result.layerLineList;
    // 连接地层线
    for (idx = 0; idx < arcNum; idx++) {
        var line = {
            data:[
                [xArray[arcList[idx].columnIndex], [arcList[idx].depthLeft]],
                [xArray[arcList[idx].columnIndex + 1], [arcList[idx].depthRight]]
            ],
            type: 'line'
        };
        // alert(line.data);
        seriesArray.push(line);
    }
    // 连接钻孔线
    for (idx = 0; idx < colNum; idx++) {
        var height = result.drillLine.drillPointList[idx].drillHeight;
        var depth = result.drillLine.drillPointList[idx].drillDepth;
        var line = {
            data:[
                [xArray[idx], height],
                [xArray[idx], height - depth]
            ],
            type: 'line'
        };
        seriesArray.push(line);
    }

    // 指定图表的配置项
    option = {
      xAxis: {},
      yAxis: {},
      series: seriesArray
    };
    // 使用刚指定的配置项和数据显示图表
    myChart.setOption(option);
  })
  .catch(function (error) {
    console.log(error);
  });