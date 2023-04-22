// 这里输入模拟JSON数组
// request = ["35-1001","35-1002","35-1003","35-1004","35-1005"];
request = [[447512.57616212807, 3566063.2553188796]
            ,[447534.8453397465, 3566064.0506526656]
            ,[447557.11451281403, 3566066.536045716]
            ,[447576.99771182967, 3566068.2261231844]
            ,[447594.3954995912, 3566058.334233178]];
// POST
function plotcht(request){
    //封装成函数，方便传入request参数
    axios.post('http://localhost/DrillVisual/ChartPlot', request)
        .then(function (response) {
            console.log("response from server:"+response)
            // 这里获取返回数据
            // document.getElementById("output").innerHTML = JSON.stringify(response.data);
            var result = response.data;
            document.querySelector("#dataDiv").removeAttribute('_echarts_instance_')
            // 获取图表元素
            var myChart = echarts.init(document.getElementById("dataDiv"));
            // 指定数据xArray, seriesArray, legendArray
            var xArray = [];
            var seriesArray = [];
            var legendArray = [];

            // 计算xArray
            var idx, sum;
            sum = 0;
            for (idx = 0; idx < result.drillDistance.length; idx++) {
                xArray[idx] = sum;
                sum += result.drillDistance[idx];
            }
            xArray[idx] = sum;
            // alert(xArray);

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
                    type: 'line',
                    name: arcList[idx].stratumId,
                    // areaStyle: {}
                };
                // alert(line.data);
                seriesArray.push(line);
                if (!legendArray.includes(arcList[idx].stratumId))
                    legendArray.push(arcList[idx].stratumId);
            }
            // 连接钻孔线
            for (idx = 0; idx < colNum; idx++) {
                var height = result.drillPointList[idx].drillHeight;
                var depth = result.drillPointList[idx].drillDepth;
                var line = {
                    data:[
                        [xArray[idx], height],
                        [xArray[idx], height - depth]
                    ],
                    type: 'line',
                    name: 'Drill'
                };
                seriesArray.push(line);
            }

            // 指定图表的配置项
            option = {
                legend: {
                    // Try 'horizontal'
                    data: legendArray,
                    orient: 'horizontal',
                    right: 0,
                    top: 'bottom'
                },
                xAxis: {
                    // show:false,//不显示坐标轴线、坐标轴刻度线和坐标轴上的文字
                    axisTick:{
                        show:false//不显示坐标轴刻度线
                    },
                    axisLine: {
                        show: false,//不显示坐标轴线
                    },
                },
                yAxis: {},
                series: seriesArray
            };
            // 使用刚指定的配置项和数据显示图表
            myChart.setOption(option);
        })
        .catch(function (error) {
            console.log(error);
        });
}
