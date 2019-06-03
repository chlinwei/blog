function doLikeDone(data) {
    if(data.code===0){
        var zan = $(".content-list .item[data-id="+data.typeId+"] .zan");
        var span = zan.find(".likes");
        zan.addClass("on");
        span.text(parseInt(span.text())+1);
        //获取以前的点赞次数
    }else if(data.code===403){
        window.location.replace(contextPath+"/login");
    }else if(data.code===402){
        alert("已经点过赞了");
    }else{
        alert("点赞失败");
    }
}

function undoLikeDone(data) {
    if(data.code===0){
        var zan = $(".content-list .item[data-id="+data.typeId+"] .zan");
        var span = zan.find(".likes");
        zan.removeClass("on");
        span.text(parseInt(span.text())-1);
    }else if(data.code===403){
        window.location.replace(contextPath+"/login");
    }else{
        alert("取消点赞失败");
    }
}

/**
 * 绑定事件
 */
$(".content-list").on("click",".item .zan",function () {
    var articleId = parseInt($(this).parent().parent().parent().attr("data-id"));
    var authorId = parseInt($(this).parent().find(".author").attr("data-id"));
    if($(this).hasClass("on")){
        //取消点赞
        undoLike(articleId,1,undoLikeDone);
    }else{
        //进行点赞
        doLike(articleId,authorId,1,doLikeDone);
    }
});
