// POST
function plotcht(request){
    //封装成函数，方便传入request参数
    axios.post('http://localhost/DrillVisual/ChartPlot', request)
        .then(function (response) {
            console.log("response from server:"+JSON.stringify(response))
            // 这里获取返回数据
            var result = response.data;
            // 若干地层面组成的数组，有name和data两个属性
            var drawdat = [];
            var polygonList = result.layerPolygonList;
            var colNum = result.drillDistance.length + 1;
            var xArray=[];
            var colarr=[];
            // 计算xArray
            var idx, sum;
            sum = 0;
            for (idx = 0; idx < result.drillDistance.length; idx++) {
                xArray[idx] = sum;
                sum += result.drillDistance[idx];
            }
            xArray[idx] = sum;
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
                //加入钻孔点
                colarr.push(line.data[0]);
                colarr.push(line.data[1]);
                colarr.push('-');
            }
            // 不合并图例
            // for (i = 0; i < polygonList.length; i++) {
            //     var polygon = {
            //         data: polygonList[i].data,
            //         name: polygonList[i].stratumId
            //     };
            //     drawdat.push(polygon);
            // }
            // 合并图例
             for (i = 0; i < polygonList.length; i++) {
                 var added = 0;
                 for (j = 0; j < drawdat.length; j++) {
                     if (drawdat[j].name == polygonList[i].stratumId) {
                         drawdat[j].data.push('-');
                         for (var data of polygonList[i].data)
                            drawdat[j].data.push(data);
                         added = 1;
                     }
                 }
                 if (added == 0) {
                     var polygon = {
                         data: polygonList[i].data,
                         name: polygonList[i].stratumId
                     };
                     drawdat.push(polygon);
                 }
             }
             var patts=Highcharts.patterns;//默认仅有10个pattern，再生成10个加入patts
             for (var i=0;i<10;++i){
                 var tmppatts=Object.assign({},patts[i])
                 tmppatts.color=patts[(i+1)%10].color; //取第i个的符号与第i+1个的颜色
                 patts.push(tmppatts);
             }
             for (var i=0;i<drawdat.length;++i){
                 drawdat[i].color={pattern:patts[i]}
                 drawdat[i].type="arearange"
             }
            // 提取钻孔编号
            var drillCodes = "";
            for (var i=0;i<result.drillPointList.length;i++) {
                drillCodes += result.drillPointList[i].drillCode;
                drillCodes += "-";
            }
            drillCodes = drillCodes.substring(0, drillCodes.length-1);
            var series=[];
            for (var i=0;i<drawdat.length;++i){
                series.push(drawdat[i]);
            }
            //加入钻孔线
            series.push({
                type: "line",
                name: "钻孔线",
                data:colarr,
                lineColor:'black',
            })

            // 指定图表的配置项
            var cht= {
                chart:{
                    style:{
                        fontFamily: "宋体",
                    }
                },
                title: {
                    text: '地质剖面图<br>(钻孔编号：' + drillCodes +')',
                    align: 'center'
                },
                xAxis:{
                  title:{
                      text:'水平距离(m)'
                  }
                },
                yAxis: {
                    max:10.0,
                    min:-60.0,
                    gridLineWidth: 0,
                    minorGridLineWidth: 0,
                    tickInterval: 10,
                    title: {
                        text: '地层深度(m)',
                    }
                },
                tooltip:{
                    headerFormat:"",
                    pointFormat: '地层编号:' +
                        '{series.name}<br>'+
                        '水平距离(m): ' +
                        '{point.x:.2f}<br>'+
                        '深度(m): ' +
                        '{point.y:.2f}',
                    valueDecimals: 2,
                },
                plotOptions: {
                    area: {
                        stacking: 'normal',
                        lineColor: '#666666',
                        lineWidth: 1,
                        marker: {
                            lineWidth: 1,
                            lineColor: '#666666',
                        }
                    },
                    series: {//设置点样式
                        marker:{
                            radius:0,
                            lineWidth: 5,
                            states:{
                                hover:{
                                    enabled:false,
                                }
                            }
                        },
                    }
                },
                series: series,
            };
            console.log(cht.tooltip.pointFormat);
            $('#dataDiv').highcharts(cht);
        })
        .catch(function (error) {
            console.log(error);
        });
}
