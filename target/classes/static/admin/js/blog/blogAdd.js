layui.use(['form','jquery','layer','laydate'],function(){
    var form = layui.form,
        $    = layui.jquery,
        layer = layui.layer,
        laydate = layui.laydate;//默认启用用户

    form.on("submit(addBlog)",function(data){
        var loadIndex = layer.load(2, {
            shade: [0.3, '#333']
        });

        var formData = getData();

        $.ajax({
            type:"POST",
            url:"/admin/blog/add",
            dataType:"json",
            contentType:"application/json",
            data:JSON.stringify(formData),
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


    //获取表单数据
    function getData() {

        var title = $("input[name='title']").val();
        var summary = $("input[name='summary']").val();
        var createBy = $("input[name='createBy']").val();
        var remark = $("#remark").val();
        var content = $("#content").summernote("code");

        var data = {
            title: title,
            summary: summary,
            createBy: createBy,
            remark: remark,
            content: content
        }
        return data;
    }

});