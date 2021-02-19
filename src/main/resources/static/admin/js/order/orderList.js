layui.use(['layer','form','table','laydate'], function() {
    var layer = layui.layer,
        $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        laydate = layui.laydate,
        t;                  //表格数据变量

    laydate.render({
        elem: '#orderDate' //指定元素
    });

    t = {
        elem: '#orderTable',
        url:'/admin/order/list',
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
            {field:'passengerName', title: '乘客昵称', width:'8%'},
            {field:'phone',  title: '手机号码',    width:'10%'},
            {field:'routeName',     title: '出行路线',    width:'8%' },
            {field:'orderDate',     title: '出行日期',    width:'8%' },
            {field:'orderTime',     title: '出行时间',    width:'6%' },
            {field:'startAddress',     title: '上车地址',    width:'8%' },
            {field:'endAddress',     title: '下车地址',    width:'8%' },
            {field:'remark', title: '乘客备注', width:'8%'},
            {field:'status',     title: '订单状态',    width:'8%',templet:'#orderStatus'},
            {field:'driverName',     title: '车主姓名',    width:'8%' },
            {field:'driverPhone',     title: '车主电话',    width:'8%' },
            {title: '操作',fixed: 'right', align: 'center', toolbar: '#orderBar'}
        ]]
    };
    table.render(t);

    //监听工具条
    table.on('tool(orderList)', function(obj){
        var data = obj.data;

        if(obj.event === 'edit'){
            var editIndex = layer.open({
                title : "编辑博客",
                type : 2,
                content : "/admin/blog/edit?id="+data.id,
                success : function(layero, index){
                    setTimeout(function(){
                        layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
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
                    $.post("/admin/order/delete",{"id":data.id},function (res){
                        if(res.success){
                            layer.msg("删除成功",{time: 1000},function(){
                                table.reload('orderTable', t);
                            });
                        }else{
                            layer.msg(res.message);
                        }

                    });
                }
            );
        }

        if(obj.event === "takeOrder"){
            layer.confirm("你确定要接单么？",{btn:['是的,我确定','我再想想']},
                function(){
                    $.post("/admin/order/takeOrder",{"id":data.id},function (res){
                        if(res.success){
                            layer.msg("接单成功，接单后请联系乘客哦！",{time: 2000},function(){
                                table.reload('orderTable', t);
                            });
                        }else{
                            layer.msg(res.message);
                        }
                    });
                }
            );
        }

        if(obj.event === "fallBack"){
            layer.confirm("你确定要取消接单吗？",{btn:['是的,我确定','我再想想']},
                function(){
                    $.post("/admin/order/fallBack",{"id":data.id},function (res){
                        if(res.success){
                            layer.msg("取消成功，请及时告知乘客",{time: 2000},function(){
                                table.reload('orderTable', t);
                            });
                        }else{
                            layer.msg(res.message);
                        }
                    });
                }
            );
        }

        if(obj.event === "read"){
            var id = data.id;
            var title = "博客预览";
            var url = "/web/article/" + id+'.html';
            var width = $(window).width() / 1.3;
            var height = $(window).height() / 1.2;
            //如果是移动端，就使用自适应大小弹窗
            if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {
                width = 'auto';
                height = 'auto';
            }

            layer.open({
                type: 2,
                area: [width + 'px', height + 'px'],
                fix: false,
                //不固定
                maxmin: true,
                shade: 0.3,
                title:  "详细",
                content: url,
                btn: '关闭',
                // 弹层外区域关闭
                shadeClose: true,
                success: function (layer) {
                    layer[0].childNodes[3].childNodes[0].attributes[0].value = 'layui-layer-btn1';
                },
                btn1: function (index) {
                    layer.close(index);
                }
            });
        }

    });

    //功能按钮
    var active={
        addBlog : function(){
            var addIndex = layer.open({
                title : "创建博客",
                type : 2,
                content : "/admin/blog/add",
                success : function(layero, addIndex){
                    setTimeout(function(){
                        layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
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
            var checkStatus = table.checkStatus('blogTable'),
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
                            url:"/admin/carbill/deleteSome",
                            dataType:"json",
                            contentType:"application/json",
                            data:JSON.stringify(data),
                            success:function(res){
                                layer.close(deleteindex);
                                if(res.success){
                                    layer.msg("删除成功",{time: 1000},function(){
                                        table.reload('carbillTable', t);
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
        table.reload('orderTable', t);
        return false;
    });

});