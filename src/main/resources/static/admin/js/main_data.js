
$(document).ready(function(){
    getData();
});

function calc(){

    var drived = $("#drived").val();
    var candrive = $("#candrive").val();
    //判断这两个数字不能为空
    var oilBill = $("#oilMoney").val();
    var runTotal = parseInt(drived)+parseInt(candrive);
    var oilTotal = parseInt(oilBill);
    var yh = (oilTotal / runTotal).toFixed(2);

    $("#yh").text(yh+" 元");
}

function getData(){
    $.ajax({
        url : "/admin/statistics/mainData",
        type : "POST",
        dataType : 'json',
        async : false,
        data : {
        },
        success : function(data) {
            var listTypeBill = data.listTypeBill;
            var incomeBill = data.incomeByMonth;
            var listOilBill = data.listOilBill;
            var listAllBill = data.listAllBill;
            var newDate = data.dateMap.nowDate;
            var lunarDate = data.dateMap.lunarDate;
            var birthdayMembers = data.birthNames;
            var incomeTotal = data.incomeTotal;
            var billTotal = data.billTotal;
            var creditBill = data.creditBill;
            var oilbillTotal = data.oilbillTotal;
            var onroadBill = data.listOnRodeBill;
            $("#creditBill").text(creditBill +" 元");
            $("#incomeTotal").text(incomeTotal +" 元");
            $("#billTotal").text(billTotal +" 元");
            $("#oilTotal").text(oilbillTotal +" 元");
            $("#oilMoney").val(oilbillTotal);
            $("#birthdayMembers").text(birthdayMembers);
            $("#newDate").text(newDate);
            $("#lunarDate").text(lunarDate);
            init1(listTypeBill);
            init2(incomeBill,onroadBill);
            //init3(listOilBill,listAllBill);

        }
    });
}
// 每月加油支出 ，总支出
function init3(listOilBill,listAllBill){

    var xlist =[];
    var ylist1 =[];
    var ylist2 =[];

    for(var i =0 ;i< listAllBill.length;i++){
        xlist.push(listOilBill[i].month);
        ylist1.push(listOilBill[i].total);
        ylist2.push(listAllBill[i].total);
    }

    Highcharts.chart('container3', {
        title: {
            text: '每月支出账单统计'
        },
        yAxis: {
            title: {
                text: '金额'
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle'
        },

        xAxis: {
            categories: xlist,
            crosshair: true
        },/*
    plotOptions: {
        series: {
            label: {
                connectorAllowed: false
            },
            pointStart: 2010
        }
    },*/
        series: [{
                name:"加油支出",
                color:"#FFC125",
                data: ylist1
            },
            {
                name:"全部支出",
                color:"#1E90FF",
                data: ylist2
            }],
        responsive: {
            rules: [{
                condition: {
                    maxWidth: 500
                },
                chartOptions: {
                    legend: {
                        layout: 'horizontal',
                        align: 'center',
                        verticalAlign: 'bottom'
                    }
                }
            }]
        }
    });
}


//顺风车收入柱状图初始化数据

function init2(data,onroadBill){

    var xlist =[];
    var ylist =[];
    var ylist2=[];

    for(var i =0 ;i< data.length;i++){
        xlist.push(data[i].month);
        ylist.push(data[i].total);
        ylist2.push(onroadBill[i].total);
    }
    Highcharts.chart('container2',{
        chart: {
            type: 'column'
        },
        title: {
            text: '每月顺风车收入与支出统计'
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
                    name: '收入金额',
                    data: ylist
                },
                {
                    name: '支出金额',
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




function initPie(sexList) {

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
            name: '性别比例',
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