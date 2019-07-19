layui.use(['form','jquery','layer','laydate'],function(){
    var form = layui.form,
        $    = layui.jquery,
        layer = layui.layer,
        laydate = layui.laydate;

    form.on("submit(editBlog)",function(data){
        if(data.field.id == null){
            layer.msg("ID不存在");
            return false;
        }

        var formData = getData();

        $.ajax({
            type:"POST",
            url:"/admin/blog/edit",
            dataType:"json",
            contentType:"application/json",
            data:JSON.stringify(formData),
            success:function(res){
                //layer.close(loadIndex);
                if(res.success){
                    parent.layer.msg("编辑成功！",{time:1500},function(){
                        parent.location.reload();
                    });
                }else{
                    layer.msg(res.message);
                }
            }
        });
        return false;
    });

//获取表单数据
    function getData() {

        var title = $("input[name='title']").val();
        var summary = $("input[name='summary']").val();
        var createBy = $("input[name='createBy']").val();
        var id = $("input[name='id']").val();
        var remark = $("#remark").val();
        var content = $("#content").summernote("code");

        var data = {
            id:id,
            title: title,
            summary: summary,
            createBy: createBy,
            remark: remark,
            content: content
        }
        return data;
    }

});