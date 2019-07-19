layui.use(['form','jquery','layer','laydate'],function(){
    var form = layui.form,
        $    = layui.jquery,
        layer = layui.layer,
        laydate = layui.laydate;//默认启用用户

    form.on("submit(addCarbill)",function(data){
        var loadIndex = layer.load(2, {
            shade: [0.3, '#333']
        });
        //给角色赋值
        delete data.field["roles"];
        var selectRole = [];
        $('input[name="roles"]:checked').each(function(){
            selectRole.push({"id":$(this).val()});
        });
        data.field.roleLists = selectRole;


        $.ajax({
            type:"POST",
            url:"/admin/carbill/add",
            dataType:"json",
            contentType:"application/json",
            data:JSON.stringify(data.field),
            success:function(res){
                layer.close(loadIndex);
                if(res.success){
                    parent.layer.msg("添加成功!",{time:1500},function(){
                        //刷新父页面
                        parent.location.reload();
                    });
                }else{
                    layer.msg(res.message);
                }
            }
        });
        return false;
    });

    form.on('switch(adminUser)', function(data){
        $("#adminUser").val(data.elem.checked);
    });

    form.on('switch(locked)', function(data){
        $("#locked").val(data.elem.checked);
    });

    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例

    });
    laydate.render({
        elem: '#billDay' //指定元素
    });

});