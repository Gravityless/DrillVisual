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
