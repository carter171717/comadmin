/**
 * Summernote编辑器统一设置
 */
$(function () {
    //初始化summernote
    initSummernote();
});

var $summernote;
var previewFlag = true;
/**
 * 编辑或者预览
 */
var editOrPreview = function (target) {
    if (previewFlag) {
        var markup = $('.summernote').summernote('code');
        $('.summernote').summernote('destroy');
        $(target).html("<i class=\"fa fa-pencil\"></i>" + "编辑");
        previewFlag = false;
    } else {
        // $('.summernote').summernote({focus: true});
        initSummernote();
        $(target).html("<i class=\"fa fa-book\"></i>" + "预览");
        previewFlag = true;
    }

};

//调用富文本编辑
function initSummernote() {
    var height = $(window).height() / 1.8;
    $summernote = $('#content').summernote({
        height: 430,
        width:970,
        minHeight: null,
        lang: 'zh-CN',
        maxHeight: null,
        focus: true,
        addclass: {
            debug: false,
            classTags: [{
                title: "Button",
                "value": "btn btn-success"
            }, "jumbotron", "lead", "img-rounded", "img-circle",
                "img-responsive", "btn", "btn btn-success", "btn btn-danger",
                "text-muted", "text-primary", "text-warning", "text-danger",
                "text-success", "table-bordered", "table-responsive", "alert",
                "alert alert-success", "alert alert-info", "alert alert-warning",
                "alert alert-danger", "visible-sm", "hidden-xs", "hidden-md",
                "hidden-lg", "hidden-print"]
        },
        toolbar: [
            ['style', ['style', 'addclass', 'clear']],
            ['font', ['bold', 'underline', 'clear']],
            ['highlight', ['highlight']],
            ['fontname', ['fontname']],
            ['color', ['color']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['table', ['table']],
            ['insert', ['link', 'picture', 'video']],
            ['view', ['fullscreen', 'codeview', 'help']]
        ],
        //调用图片上传
        callbacks:
            {
                onImageUpload: function (files) {
                    sendFile($summernote, files[0]);
                },
                onMediaDelete: function (target) {
                    var imgSrc = target.context.currentSrc;
                    $.ajax({
                        data: {
                            fileUrl: imgSrc
                        },
                        type: "DELETE",
                        url: "/qiniu/image/remove",
                        dataType: "json",
                        success: function (data) {
                        }
                    });
                }
            }
    });
}

//ajax上传图片
function sendFile($summernote, file) {
    var formData = new FormData();
    formData.append("file", file);
    $.ajax({
       // url: "/upload/uploadPic",  //qiniu/images
        url: "/upload/qiniu/images",
        data: formData,
        cache: false,
        contentType: false,
        processData: false,
        type: 'POST',
        success: function (result) {
            console.log(result.data.name);
            console.log(result.data.url);
            $summernote.summernote('insertImage', result.data.url, function ($image) {

                 $image.attr('src', result.data.url);
            });

        }
    });
}
