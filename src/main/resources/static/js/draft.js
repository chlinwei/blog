$(function () {
    //此用户没有该草稿
    if(!flag){
        $("body").text("抱歉,您没有该草稿");
    }else{
        //有该草稿
        //获取文章分类
        getCustomTypes(getCustomTypesDone);

        //获取草稿
        getDraft(draftId,getDraftDone);

        autoSaveDraft();
    }
});

//给草稿按钮绑定事件
$(".draftBtn").click(function () {
    updateDraft();
});


//新建文章
$(".publishBtn").click(function () {
    pubArticle(getArticle());
});


function getDraft(draftId,callback) {
    $.ajax({
        type:'get',
        url:contextPath+"/getDraft",
        data:{
            draftId:draftId
        },
        success:function (data) {
            if(callback){
                callback(data);
            }
        },
        error:function () {
            alert("获取草稿失败");
        }
    })
}



function getDraftDone(data) {
    if(data.code===0){
        var article = data.data;
        var articleType = article.articleType;
        //id
        $(".draftId").val(article.id);

        //文章类型
        if(articleType==="原创"||articleType==="转载"){
            $("#articleType").text(articleType);
        }
        //文章标题
        $("#articleTitle").val(article.articleTitle);

        //文章内容
        $("#my-editormd-markdown-doc").val(article.articleContent);
        initEditor();

        //个人分类
        $(".customType").attr("data-id",article.customTypeId);
        $(".customType").text(article.customTypeName);

        //文章标签
        if(article.articleTags!==""&& article.articleTags!==null){
            var arr = article.articleTags.split(";");
            putInTags(arr);
        }

        //文章摘要
        $("#summary").val(article.summary);
    }else{
        alert("获取草稿失败");
    }
}
