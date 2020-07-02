layui.use(['layer','form','table','jquery'], function() {
    var layer = layui.layer,
        $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        t;                  //表格数据变量

    t = {
        elem: '#carIncomeTable',
        url:'/admin/carIncome/list',
        method:'post',
        page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
            layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'], //自定义分页布局
            //,curr: 5 //设定初始在第 5 页
            groups: 6, //只显示 1 个连续页码
            first: "首页", //显示首页
            last: "尾页", //显示尾页
            limits:[3,10, 20, 30]
        },
        width: $(parent.window).width()-223,
        cols: [[
            {type:'checkbox'},
            {field:'rideLine', title: '乘车路线', width:'8%'},
            {field:'passengerName', title: '乘客姓名', width:'10%'},
            {field:'sex',  title: '性别',    width:'6%'},
            {field:'rideDay',     title: '乘车日期',    width:'8%' },
            {field:'fee',     title: '乘车费用',    width:'8%' },
            {field:'startLocation',     title: '乘车起点',    width:'15%' },
            {field:'endLocation',    title: '乘车终点',width:'15%'},
            {field:'remark', title: '备注', width:'12%'},
            {title: '操作',fixed: 'right', align: 'center', toolbar: '#carIncomeBar'}
        ]]
    };
    table.render(t);

    //监听工具条
    table.on('tool(carIncomeList)', function(obj){
        var data = obj.data;

        if(obj.event === 'edit'){
            var editIndex = layer.open({
                title : "编辑消费信息",
                type : 2,
                content : "/admin/carIncome/edit?id="+data.id,
                success : function(layero, index){
                    setTimeout(function(){
                        layer.tips('点击此处返回卡列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    },500);
                }
            });
            //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
            $(window).resize(function(){
                layer.full(editIndex);
            });
            layer.full(editIndex);
        }

        if(obj.event === "del"){
            layer.confirm("你确定要删除这条记录么？",{btn:['是的,我确定','我再想想']},
                function(){
                    $.post("/admin/carIncome/delete",{"id":data.id},function (res){
                        if(res.success){
                            layer.msg("删除成功",{time: 1000},function(){
                                table.reload('carIncomeTable', t);
                            });
                        }else{
                            layer.msg(res.message);
                        }

                    });
                }
            );
        }
    });

    //功能按钮
    var active={
        addCarIncome : function(){
            var addIndex = layer.open({
                title : "添加消费记录",
                type : 2,
                content : "/admin/carIncome/add",
                success : function(layero, addIndex){
                    setTimeout(function(){
                        layer.tips('点击此处返回卡列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    },500);
                }
            });
            //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
            $(window).resize(function(){
                layer.full(addIndex);
            });
            layer.full(addIndex);
        },
        deleteSome : function(){                        //批量删除
            var checkStatus = table.checkStatus('carIncomeTable'),
                data = checkStatus.data;
            if(data.length > 0){
                for(var i=0;i<data.length;i++){
                    var d = data[i];
                    if(d.adminUser){
                        layer.msg("不能删除超级管理员");
                        return false;
                    }
                }
                layer.confirm("你确定要删除这些卡片么？",{btn:['是的,我确定','我再想想']},
                    function(){
                        var deleteindex = layer.msg('删除中，请稍候',{icon: 16,time:false,shade:0.8});
                        $.ajax({
                            type:"POST",
                            url:"/admin/carIncome/deleteSome",
                            dataType:"json",
                            contentType:"application/json",
                            data:JSON.stringify(data),
                            success:function(res){
                                layer.close(deleteindex);
                                if(res.success){
                                    layer.msg("删除成功",{time: 1000},function(){
                                        table.reload('carIncomeTable', t);
                                    });
                                }else{
                                    layer.msg(res.message);
                                }
                            }
                        });
                    }
                )
            }else{
                layer.msg("请选择需要删除的卡片",{time:1000});
            }
        }
    };

    $('.layui-inline .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    //搜索
    form.on("submit(searchForm)",function(data){
        t.where = data.field;
        table.reload('carIncomeTable', t);
        return false;
    });

});


function getData(){
    $.ajax({
        url : "/admin/carIncome/countIncomeTotal",
        type : "POST",
        dataType : 'json',
        async : false,
        data : {
        },
        success : function(data) {
            //alert(data.total);
            var billTotal = data.total;
            $("#billTotal").val(billTotal +" 元");

        }
    });
}


$(document).ready(function(){
    getData();
});