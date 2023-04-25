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
            //比较两个点是否重合
            function comparepoint(x,y){
                return Math.abs(x[0]-y[0])<1e-6 && Math.abs(x[1]-y[1])<1e-6
            }
            function havesamex(pts,x){
                return Math.abs(pts[0]-x)<1e-6
            }
            function findymax(x,ymin){
                ymax=undefined
                for (var pts of points){
                    if (havesamex(pts,x) && pts[1]>ymin ){
                        if (ymax==undefined){
                            ymax=pts[1]
                        }else{
                            ymax=Math.min(pts[1],ymax);
                        }
                    }
                }
                return ymax;
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
            points=[]//储存所有点
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
                points.push(line.data[0])
                points.push(line.data[1])
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
            for (var ii=0;ii<drawdat.length;++ii){
                var line=drawdat[ii]
                var n=line.data.length
                for (var i=0;i<n;++i){
                    var xx=line.data[i][0];
                    var ymin=line.data[i][1];
                    xx=xx.toString();
                    line.data[i].push(findymax(xx,ymin))
                }
            }
            for (var ii=0;ii<drawdat.length;++ii){//后处理，去掉未被填充的线
                if (drawdat[ii].data[0][2]===undefined){
                    drawdat.splice(ii,1);
                    --ii;
                }
            }
            for (var ii=0;ii<drawdat.length;++ii){//后处理，去掉重复的图例
                for (var jj=ii+1;jj<drawdat.length;++jj){
                    if (drawdat[jj].name===drawdat[ii].name){
                        drawdat[ii].data.push('-')
                        for (var kk of drawdat[jj].data){
                            drawdat[ii].data.push(kk)
                        }
                        drawdat.splice(jj,1);
                        --jj;
                    }

                }
            }
            console.log('drawdat='+drawdat)
            // 连接钻孔线

            // 指定图表的配置项
            var cht= {
                chart:{
                    type:'arearange',
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
                series: drawdat
            };
            console.log(cht['series'])
            $('#dataDiv').highcharts(cht);
        })
        .catch(function (error) {
            console.log(error);
        });
}
