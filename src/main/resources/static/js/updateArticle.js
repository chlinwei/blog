//获取文章数据,参数为匿名函数,用来填充获取的数据
function getArticle (articleId,callback) {
    $.ajax({
        url:contextPath+"/getUpdatePageArticle",
        data:{
            articleId:articleId
        },
        success:function (data) {
            console.log(data);
            if(data.code===0){
                callback(data.data);
            }
        },
        error:function () {
            alert("获取文章失败");
        }
    });
}

//填充文章
function getArticleDone(article) {
    //文章内容
    $("#my-editormd-markdown-doc").val(article.articleContent);
    //初始化编辑器,注意,这里要先把articleContent填入到html中,才能初始化编辑器
    initEditor();

    //文章类型
    $("#articleType").text(article.articleType);
    //文章标题
    $("#articleTitle").val(article.articleTitle);

    //文章分类
    $(".customType").attr("data-id",article.customTypeId);
    $(".customType").text(article.customTypeName);

    //文章标签
    var arr = article.articleTags.split(";");
    putInTags(arr);

    //文章摘要
    $("#summary").val(article.summary);
}

/**
 * 保存修改过的文章
 */
function updateArticle() {
    if(checkArticle()){
        $.ajax({
            type:'post',
            url:contextPath + "/updateArticle",
            data:{
                id:articleId,
                articleTitle: $("#articleTitle").val().trim(),
                articleType: $("#articleType").text(),
                articleContent:$("#my-editormd>textarea").val(),
                customTypeId: $("#customType").attr("data-id"),
                articleTags: getTags().join(";"),
                summary:$("#summary").val()
            },
            success:function (data) {
                if(data.code===0) {
                    window.location.replace(contextPath + "/pubSuccess/" + articleId);
                }else{
                    alert("文章保存失败！");
                }
            },
            error:function () {
                alert("文章保存失败！");
            }

        });
    }
}
$(function () {
    if(flag){
        //获取文章分类
        getCustomTypes(getCustomTypesDone);
        //获取文章
        getArticle(articleId,getArticleDone);


    }else{
        $("body").text("要修改的文章不存在");
    }
});

/**
 * 给发布按钮绑定事件
 */
$(".publishBtn").click(function () {
    updateArticle();
});

