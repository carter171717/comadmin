layui.use(['form','jquery','layer','laydate'],function(){
    var form = layui.form,
        $    = layui.jquery,
        layer = layui.layer,
        laydate = layui.laydate;//默认启用用户

    //搜索
    form.on("submit(searchForm)",function(data){
        t.where = data.field;
       // table.reload('carbillTable', t);
       // return false;
    });

});

function getData(){


    $.ajax({
        url : "/admin/statistics/yearCarData",
        type : "POST",
        dataType : 'json',
        async : false,
        data : {
        },
        success : function(data) {

            var listBillTotal = data.listBillTotal;
            var listIncomeTotal = data.listIncomeTotal;
            init2(listBillTotal,listIncomeTotal);

        }
    });


}

$(document).ready(function(){

    getData();
    getPieData();
});


//初始化饼图
function  pie(data,yearTotal) {

    var pieChar = Highcharts.chart('container1', {
        chart: {
            type: 'pie',
            options3d: {
                enabled: true,
                alpha: 45
            }
        },
        title: {
            text: '年度用车费用类别汇总'
        },
        subtitle: {
            text: '用车费用总额：'+yearTotal+" 元",
            style:{
                color: '#8B4513',
            }
        },

        plotOptions: {
            pie: {
                innerSize: 100,
                depth: 45,
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                },
            }
        },
        series: [{
            name: '金额',
            data: data
        }]
    });
}

function getPieData() {

    var year = $("#year").val();
    $.ajax({
        url : "/admin/statistics/yearDetailData",
        type : "POST",
        dataType : 'json',
        async : false,
        data : {
            year:year
        },
        success : function(data) {
            var data1 = [];
            var yearTotal = 0;
            for(var i = 0 ;i< data.length;i++){
                //alert(data[i].billType);
                //data1.push([key, dict[key]]);
                data1.push([data[i].billType,data[i].billNum]);
                yearTotal = yearTotal + data[i].billNum ;
            }
            pie(data1,yearTotal);
           // $('#container1').highcharts().series[0].setData(data);
        }
    });

}





//顺风车收入柱状图初始化数据

function init2(data,listIncomeTotal){

    var xlist =[];
    var ylist =[];
    var ylist2=[];

    for(var i =0 ;i< data.length;i++){
        xlist.push(data[i].yearName+"年");
        ylist.push(data[i].total);
        ylist2.push(listIncomeTotal[i].total);
    }
    Highcharts.chart('container2',{
        chart: {
            type: 'column'
        },
        title: {
            text: '年度用车收入与支出汇总'
        },
        xAxis: {
            categories: xlist,
            crosshair: true
        },
        yAxis: {
            min: 0,
            title: {
                text: '金额 (元)'
            }
        },
        tooltip: {
            // head + 每个 point + footer 拼接成完整的 table
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y} 元</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                borderWidth: 0,
                dataLabels: {
                    enabled: true //设置显示对应y的值
                }
            }
        },
        series: [{
            name: '支出总金额',
            data: ylist
        },
            {
                name: '收入总金额',
                color:"#FFC125",
                data: ylist2
            }]
    });



}





//柱状图初始化数据
function init1(data){

    var xlist =[];
    var ylist =[];

    for(var i =0 ;i< data.length;i++){
        xlist.push(data[i].billType);
        ylist.push(data[i].total);
    }

    Highcharts.chart('container1', {
        chart: {
            type: 'cylinder',//column cylinder
            options3d: {
                enabled: true,
                alpha: 15,
                beta: 5,
                depth: 25,
                viewDistance: 25
            }
        },
        title: {
            text: '消费类别统计'
        },
        xAxis: {
            categories:xlist,
            crosshair: true
        },
        yAxis: {
            title: {
                text: '金额'
            }
        },
        tooltip: {
            // head + 每个 point + footer 拼接成完整的 table
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y} 元</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            cylinder: {
                dataLabels: {
                    enabled: true //设置显示对应y的值
                    // inside: true
                }
            },
            series: {
                depth: 25,
                colorByPoint: true
            }
        },
        series: [{
            data: ylist,
            name: '消费金额',
            showInLegend: false
        }]
    });

}




function initPie(detailList) {


    var namelist =[];
    var valuelist =[];

    for(var i =0 ;i< detailList.length;i++){
        namelist.push(detailList[i].billType);
        valuelist.push(detailList[i].billNum);
    }


    Highcharts.chart('container1', {
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: '性别比例'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'red'
                    }
                }
            }
        },
        series: [{
            name: '年度用车消费汇总',
            colorByPoint: true,
            data: [{
                name: sexList[0].sex,
                y: sexList[0].num,
                sliced: true,
                selected: true
            }, {
                name: sexList[1].sex,
                y:sexList[1].num
            }]
        }]
    });

}