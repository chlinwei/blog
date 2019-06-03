/**
 * 文章类型相关操作
 */
$(".title_wrapper .drop_down>a").click(function () {
    $("#articleType").text($(this).text());
    $(".title_wrapper .drop_head").removeClass("on");

});


/**
 * 文章分类相关操作
 */
$(".customType_wrapper .drop_down").on("click","a",function () {
    var customType = $("#customType");
    var a = $(this);
    customType.text(a.text());
    customType.attr("data-id",a.attr("data-id"));
    var drop_head = $(this).parent().prev();
    drop_head.removeClass("on");
});


/**
 * 删除指定的元素
 */
function delTag(ele) {
    ele.detach();
}


/**
 * 获取标签
 */
function getTags() {
    var spans = $(".tagDvi>div>span");
    var arr =  new Array();
    var tags = $("#tags");
    for(var i=0;i<spans.length;i++){
        var span = $(spans[i]);
        arr.push(span.text().trim());
    }
    return arr;
}


/**
 * 添加标签
 */
$(".addTagBtn").click(function () {
    var tagDiv = $($(".tagDvi")[0]);
    var lastTag = $(".tagDvi>div:last-of-type");
    if(tagDiv.children().length===5){
        return;
    }
    if(lastTag.length===0||lastTag.children("span").text().trim()!==""){
        var div = "<div><span contenteditable='true' style='display: inline-block;padding: 0 12px;'></span><a onclick='delTag($(this).parent())' href='javascript:void(0)'><img src="+contextPath+'/image/cross.png'+" style='width:13px;height:13px;vertical-align: middle'></a></div>"
        tagDiv.append(div);
    }
});
/**
 * 给已经存在的标签绑定删除方法
 */
$(".tagDvi>div>a").click(function () {
    delTag($(this).parent().detach());
});



/**
 * 检查是否合适
 */
function checkArticle() {
    var flag = true;
    //草稿id
    var id = $(".draftId").val();
    if(id===""){
        id = null;
    }
    //标题
    var articleTitle = $("#articleTitle").val().trim();
    //类型
    var articleType = $("#articleType").text();
    //内容
    var articleContent = $("#my-editormd>textarea").val();
    //个人分类的id
    var customTypeId = $("#customType").attr("data-id");
    //文章标签
    var articleTags = getTags().join(";");
    //文章摘要
    var summary = $("#summary").val();

    //空白字符,包括空字符串
    var pattern = /^[\s]*$/;
    var errorBox = $("#errorBox");
    var errorMsg = $("#errorMsg");
    if(pattern.test(articleTitle)){
        $("#articleTitle").val("");
        editorErrorShow("文章标题不能为空")
        flag = false;
    }else if(articleType==="请选择"){
        editorErrorShow("请选择是否是原创");
        flag = false;
    }else if(articleContent===""){
        editorErrorShow("文章内容不能为空");
        flag = false;
    }else if(customTypeId===""){
        editorErrorShow("个人分类不能为空");
        flag = false;
    }else if(articleTags==="") {
        editorErrorShow("文章标签至少填一个");
        flag = false;
    }
    return flag;
}

/**
 * 返回Article对象
 */
function getArticle() {
    return {
        id:$(".draftId").val(),
        articleTitle: $("#articleTitle").val().trim(),
        articleType: $("#articleType").text(),
        articleContent: $("#my-editormd>textarea").val(),
        customTypeId: $("#customType").attr("data-id"),
        articleTags: getTags().join(";"),
        summary: $("#summary").val()
    };
}


function editorErrorShow(text) {
    var errorBox = $("#errorBox");
    var errorMsg = $("#errorMsg");
    errorMsg.text(text);
    errorBox.show();
    setTimeout(function () {
        errorBox.hide()
    },3000);
}

/**
 * 获取文章分类列表
 */
function getCustomTypes(callback) {
    $.ajax({
        type:'get',
        url:contextPath+'/getAllCustomTypes',
        data:{},
        success:function (data) {
            if(callback){
                callback(data);
            }
        },
        error:function () {
            console.log("连接服务器失败");
        }
    })
}

function getCustomTypesDone(data) {
    if(data.code===0){
        var customTypes = data.data;
        var drop_down = $($(".customType_wrapper .drop_down")[0]);
        for(var i=0;i<customTypes.length;i++){
            drop_down.append("<a href='javascript:void(0)' data-id="+customTypes[i].id+">"+customTypes[i].name+"</a>")
        }
    }
}


/**
 * 删除一个文章分类
 */
function delCustomType(customTypeId,callback) {
    $.ajax({
        type:'post',
        url:contextPath+"/delCustomType",
        data:{
            id:customTypeId
        },
        success:function (data) {
            if(data.code===0){
                if(callback){
                    data.data = customTypeId;
                    callback(data);
                }
            }
        },
        error:function () {
            alert("删除文章分类失败")
        }
    });
}


/**
 * 新建文章
 */
function pubArticle(article) {
    if(checkArticle()) {
        $.ajax({
            type: 'post',
            url: contextPath + "/pubDraft",
            data: {
                id:article.id,
                articleTitle: article.articleTitle,
                articleType: article.articleType,
                articleContent:article.articleContent,
                customTypeId: article.customTypeId,
                articleTags: article.articleTags,
                summary:article.summary
            },
            success: function (data) {
                if(data.code===0) {
                    window.location.replace(contextPath + "/pubSuccess/" + data.data);
                }else{
                    alert("发布文章失败");
                }
            },
            error: function () {
                alert("发布文章失败");
            }
        })
    }
}

/**
 * 保存草稿
 */
function saveDraft() {
    var articleContent= $("#my-editormd-markdown-doc").val().trim();
    if(articleContent===""){
        return;
    }
    $(".noticeMessage").val("保存中");
    $(".noticeMessage").show();
    var articleType = $("#articleType").text();
    if(articleType==="请选择"){
        articleType=null;
    }
    var articleTitle = $("#articleTitle").val().trim();
    if(articleTitle===""){
        articleTitle = "无标题文章";
    }
    var customTypeId = $("#customType").attr("data-id");
    if(customTypeId===""){
        customTypeId = null;
    }else{
        customTypeId = parseInt(customTypeId);
    }
    var articleTags = getTags();
    if(articleTags.length===0){
        articleTags=null;
    }else{
        articleTags = articleTags.join(";");
    }
    var summary = $("#summary").val().trim();
    if(summary===""){
        summary = null;
    }
    $.ajax({
        type:'post',
        url:contextPath+"/saveDraft",
        data:{
            articleType:articleType,
            articleTitle:articleTitle,
            articleContent:articleContent,
            customTypeId:customTypeId,
            articleTags:articleTags,
            summary:summary
        },
        success:function (data) {
            if(data.code===0){
                $(".noticeMessage").val("已保存");
                $(".draftId").val(data.data);
            }else{
                alert("保存草稿失败");
                $(".noticeMessage").val("");
                $(".noticeMessage").hide();
            }
        },
        error:function () {
            alert("保存草稿失败");
            $(".noticeMessage").val("");
            $(".noticeMessage").hide();
        }
    })
}

/**
 * 修改草稿
 */
function updateDraft() {
    var articleContent= $("#my-editormd-markdown-doc").val().trim();
    if(articleContent===""){
        return;
    }
    $(".noticeMessage").val("保存中");
    $(".noticeMessage").show();
    var articleType = $("#articleType").text();
    if(articleType==="请选择"){
        articleType=null;
    }
    var articleTitle = $("#articleTitle").text().trim();
    if(articleTitle===""){
        articleTitle = "无标题文章";
    }
    var customTypeId = $("#customType").attr("data-id");
    if(customTypeId===""){
        customTypeId = null;
    }else{
        customTypeId = parseInt(customTypeId);
    }
    var articleTags = getTags();
    if(articleTags.length===0){
        articleTags=null;
    }else{
        articleTags = articleTags.join(";");
    }
    var summary = $("#summary").val().trim();
    if(summary===""){
        summary = null;
    }
    $.ajax({
        url:contextPath+"/updateDraft",
        type:"post",
        data:{
            id:parseInt($(".draftId").val()),
            articleType:articleType,
            articleTitle:articleTitle,
            articleContent:articleContent,
            customTypeId:customTypeId,
            articleTags:articleTags,
            summary:summary
        },
        success:function (data) {
            if(data.code===0){
                $(".noticeMessage").val("已保存");
                $(".noticeMessage").show();
            }else{
                alert("保存草稿失败");
                $(".noticeMessage").val("");
                $(".noticeMessage").hide();

            }
        },
        error:function () {
            alert("修改草稿失败");
            $(".noticeMessage").val("");
            $(".noticeMessage").hide();
        }
    })
}



/**
 * 自动保存草稿
 */
function autoSaveDraft() {
    ifvisible.setIdleDuration(120);
    //判断是否为草稿
    //已经保存的草稿
    var timerId = setInterval(function () {
        if($(".draftId").val()===""){
            //为空表示还是未保存的草稿
            saveDraft();
        }else {
            //修改草稿
            updateDraft();
        }
    }, 5000);
    ifvisible.off("idle");
    ifvisible.off("wakeup");
    ifvisible.idle(function () {
        //闲置时立马执行
        window.clearInterval(timerId);
    });
    ifvisible.wakeup(function () {
        //唤醒时立马执行
        autoSaveDraft();
    });
}


/**
 * 填充标签
 */
function putInTags(arr) {
    var tagDiv = $($(".tagDvi")[0]);
    for(var i=0;i<arr.length;i++){
        var div = "<div><span contenteditable='true' style='display: inline-block;padding: 0 12px;'>"+arr[i]+"</span><a onclick='delTag($(this).parent())' href='javascript:void(0)'><img src="+contextPath+'/image/cross.png'+" style='width:13px;height:13px;vertical-align: middle'></a></div>"
        tagDiv.append(div);
    }
}



function initEditor() {
    testEditor = editormd("my-editormd", { //注意1：这里的就是上面的DIV的id属性值
        width: "100%",
        height: 740,
        syncScrolling: true, //设置双向滚动
        path: contextPath+"/webjars/editor.md/1.5.0/lib/", //lib目录的路径
        // previewTheme : "dark", //代码块使用dark主题
        codeFold : true,
        emoji:true,
        tocm : true, // Using [TOCM]
        tex : true, // 开启科学公式TeX语言支持，默认关闭
        flowChart : true, // 开启流程图支持，默认关闭
        sequenceDiagram : true, // 开启时序/序列图支持，默认关闭,
        htmlDecode : true, //不过滤标签
        imageUpload : true, //上传图片
        imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp","JPG","JPEG","GIF","PNG","BMP","WEBP"],
        imageUploadURL : contextPath+"/uploadArticleImage",
        onload:function () {
        },
        saveHTMLToTextarea: true, //注意3：这个配置，方便post提交表单
        toolbarIcons : function () {
            return ["bold","del","italic","quote","|","h1","h2","h3","h4","h5","h6","|","list-ul","list-ol","hr","|","link","image","code","code-block","table","datetime","html-entities","emoji","|","watch","preview","fullscreen","clear","search","|","help","info"]
        }
    });
}


/**
 * 禁止标签中含有";"
 */
$(".tagDvi").on("keypress","div>span",function (e) {
    if(e.keyCode==59){
        return false;
    }
});
