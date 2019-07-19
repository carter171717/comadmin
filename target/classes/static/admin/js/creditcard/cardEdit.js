var index = parent.layer.getFrameIndex(window.name); //当前窗口索引
layui.use(['form','jquery','layer'],function(){
    var form = layui.form,
        $    = layui.jquery,
        layer = layui.layer;

    form.on("submit(editCard)",function(data){
        if(data.field.id == null){
            layer.msg("卡ID不存在");
            return false;
        }
        //给角色赋值
        delete data.field["roles"];
        var selectRole = [];
        $('input[name="roles"]:checked').each(function(){
            selectRole.push({"id":$(this).val()});
        });
        data.field.roleLists = selectRole;

        var loadIndex = layer.load(2, {
            shade: [0.3, '#333']
        });
        $.ajax({
            type:"POST",
            url:"/admin/creditCard/edit",
            dataType:"json",
            contentType:"application/json",
            data:JSON.stringify(data.field),
            success:function(res){
                layer.close(loadIndex);
                if(res.success){
                    parent.layer.msg("卡信息编辑成功！",{time:1500},function(){
                        parent.location.reload();
                    });
                }else{
                    layer.msg(res.message);
                }
            }
        });
        return false;
    });



});