request = [[447512.57616212807, 3566063.2553188796]
            ,[447534.8453397465, 3566064.0506526656]
            ,[447557.11451281403, 3566066.536045716]
            ,[447576.99771182967, 3566068.2261231844]
            ,[447594.3954995912, 3566058.334233178]];
//request = [[447512.57616212807, 3566063.2553188796]
//            ,[447534.8453397465, 3566064.0506526656]];
// POST
//封装成函数，方便传入request参数
axios.post('http://localhost/DrillVisual/ChartPlot', request)
    .then(function (response) {
        // console.log("response from server:"+response)
        // 这里获取返回数据
        document.getElementById("output").innerHTML = JSON.stringify(response.data);
        })
    .catch(function (error) {
        console.log(error);
    });
