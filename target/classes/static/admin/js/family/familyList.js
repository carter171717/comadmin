layui.use(['layer','form','table'], function() {
    var layer = layui.layer,
        $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        t;                  //表格数据变量

    t = {
        elem: '#familyTable',
        url:'/admin/family/list',
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
            {field:'name', title: '姓名', width:'15%'},
            {field:'sex',  title: '性别',    width:'10%'},
            {field:'animal',     title: '生肖',    width:'10%' },
            {field:'birthDayNew',     title: '新历生日',    width:'15%' },
            {field:'birthDayLunar',    title: '农历生日',width:'15%'},
            {field:'remark', title: '备注', width:'20%'},
            {title: '操作',fixed: 'right', align: 'center', toolbar: '#familyBar'}
        ]]
    };
    table.render(t);

    //监听工具条
    table.on('tool(familyList)', function(obj){
        var data = obj.data;

        if(obj.event === 'edit'){
            var editIndex = layer.open({
                title : "编辑家庭成员信息",
                type : 2,
                content : "/admin/family/edit?id="+data.id,
                success : function(layero, index){
                    setTimeout(function(){
                        layer.tips('点击此处返回家庭成员列表', '.layui-layer-setwin .layui-layer-close', {
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
                    $.post("/admin/family/delete",{"id":data.id},function (res){
                        if(res.success){
                            layer.msg("删除成功",{time: 1000},function(){
                                table.reload('familyTable', t);
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
        addCard : function(){
            var addIndex = layer.open({
                title : "添加家庭成员",
                type : 2,
                content : "/admin/family/add",
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
            var checkStatus = table.checkStatus('familyTable'),
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
                            url:"/admin/family/deleteSome",
                            dataType:"json",
                            contentType:"application/json",
                            data:JSON.stringify(data),
                            success:function(res){
                                layer.close(deleteindex);
                                if(res.success){
                                    layer.msg("删除成功",{time: 1000},function(){
                                        table.reload('familyTable', t);
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
        table.reload('familyTable', t);
        return false;
    });

});