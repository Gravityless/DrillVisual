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
            //比较两个点是否重合
            function comparepoint(x,y){
                return Math.abs(x[0]-y[0])<1e-6 && Math.abs(x[1]-y[1])<1e-6
            }
            // 计算xArray
            var idx, sum;
            sum = 0;
            for (idx = 0; idx < result.drillDistance.length; idx++) {
                xArray[idx] = sum;
                sum += result.drillDistance[idx];
            }
            xArray[idx] = sum;

            // 计算seriesArray
            var colNum = result.drillDistance.length + 1;
            var arcNum = result.layerLineList.length;
            var arcList = result.layerLineList;
            // 连接地层线
            var drawdat=[];//若干地层线组成的数组,每一项对应一条线，有name和data两个属性
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
                var found=false
                for (var it=0;it<drawdat.length;++it){//将当前线段与drawdat里的同名线匹配，若能连接，则加入
                    if (drawdat[it].name===line.name){
                        for (var left=0;left<drawdat[it].data.length;++left){
                            if (comparepoint(drawdat[it].data[left],line['data'][0])){
                                if (left===drawdat[it].data.length-1){
                                    drawdat[it].data.push(line['data'][1])
                                    found=true
                                    break
                                }else{
                                    newdat=drawdat[it];
                                    newdat.data=drawdat[it].data.slice(0,left+1)
                                    drawdat.push(newdat)
                                }
                            }
                        }
                    }
                }
                if (!found){
                    drawdat.push({name:line.name,data:line.data})
                }
            }
            // 连接钻孔线
            drawdat.push({name:'Drill',data:[]})
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
                drawdat[drawdat.length-1].data.push([xArray[idx], height]) //将钻孔点push进绘制数组中，中间用-隔开，避免不同列钻孔相连
                drawdat[drawdat.length-1].data.push([xArray[idx], height-depth])
                drawdat[drawdat.length-1].data.push('-')
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
