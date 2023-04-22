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
            var dat={};//制作highcharts所需的数据
            for (idx = 0; idx < arcNum; idx++) {
                var line = {
                    data:[
                        [xArray[arcList[idx].columnIndex], arcList[idx].depthLeft],
                        [xArray[arcList[idx].columnIndex + 1], arcList[idx].depthRight]
                    ],
                    type: 'line',
                    name: arcList[idx].stratumId,
                    // areaStyle: {}
                };
                if (dat[line.name]===undefined){
                    dat[line.name]=[];//push 第一段弧的左端点
                }
                dat[line.name].push(line['data'][0]);//push 每段弧的右端点
                dat[line.name].push(line['data'][1]);//push 每段弧的右端点
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

            var drawdat=[];
            for (var nm in dat) {
                //console.log(nm + "=" + dat[nm]);
                drawdat.push({name:nm,data:dat[nm]})
            }
            // 指定图表的配置项
            var cht= {
                chart: {
                    type: 'line'
                },
                title: {
                    text: '地层连线剖面图',
                    align: 'left'
                },
                yAxis: {
                    title: {
                        useHTML: true,
                        text: '地层深度',
                    }
                },
                plotOptions: {
                    area: {
                        stacking: 'normal',
                        lineColor: '#666666',
                        lineWidth: 1,
                        marker: {
                            lineWidth: 1,
                            lineColor: '#666666'
                        }
                    }
                },
                series: drawdat,
            };
            console.log(cht['series'])
            $('#dataDiv').highcharts(cht);
        })
        .catch(function (error) {
            console.log(error);
        });
}
