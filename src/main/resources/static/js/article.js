//填充文章
function putInArticle(article) {
    $("#articleTitle").text(article.articleTitle);
    $("#articleTitle").attr("data-id",article.id);
    $("#articleType").text(article.articleType);
    $("#publishDate").text(article.createTime);

    $(".user_wrapper>a").attr("href",contextPath+"/userBlog/"+article.userId);

    $("#userName").text(article.userName);
    $("#userName").attr("data-id",article.userId);
    $("#avatarUrl").attr("src",article.avatarUrl);
    if(typeof(article.customTypeId)==="undefined"){
        //找不到文章分类时,应该删除a标签
        $("#customType_wrapper").empty();
    }else{
        $("#customType_wrapper>a").attr("href",contextPath+"/userBlog/"+article.userId +"?customTypeId="+article.customTypeId);
        $("#customTypeName").text(article.customTypeName);
    }
    $("#author").text(article.userName);

    /**
     * 点赞
     */
    if(article.isLiked===1){
        //表示点赞了
        $(".like_div .like_wrapper").addClass("on");
    }

    //标签
    var tags = article.articleTags.split(";");
    var div = $($(".other-left")[0]);
    for(var i=0;i<tags.length;i++){
        var a = '<a><img src='+contextPath+"/image/tag.png"+'><span>'+tags[i]+'</span></a>'
        div.append(a);
    }
    //上一篇
    if(article.lastArticleId){
        $("#last").attr("href",contextPath+"/article/"+article.lastArticleId);
        $("#last").text("上一篇："+article.lastArticleTitle);
    }
    //下一篇
    if(article.nextArticleId){
        $("#next").attr("href",contextPath+"/article/"+article.nextArticleId);
        $("#next").text("下一篇："+article.nextArticleTitle);
    }
    //喜欢
    $("#like").text(article.likes);
    if(article.isLiked===1){
        $(".like_div").addClass("on");
    }
    //删除修改按钮
    if(userInfo){
        //登录了
        if(userInfo.id===article.userId){
            var div = $("#own");
            var html = '<a id="delArticleBtn">删除</a>&nbsp;|&nbsp;<a href='+contextPath+'/update/'+articleId+'>修改</a>&nbsp;&nbsp;'
            div.append(html);
        }
    }


    //收藏
    if(article.isCollected===1){
        //已经收藏
        $(".collectBtn").addClass("on");
    }

    $("#mdText").text(article.articleContent);
    var content = "";
    content = editormd.markdownToHTML("content", {
        htmlDecode: "true", // you can filter tags decode
        emoji: true,
        taskList: true,
        tex: true,
        flowChart: true,
        sequenceDiagram: true
    });
}


//填充评论列表
function putInComment(comments) {
    var commentList = $($(".comment-list")[0]);
    commentList.empty();
    for(var i=0;i<comments.length;i++ ){
        var comment = comments[i];
        var itemDiv = "<div class='list-item' data-id="+comment.id+"><div class='fl user-face'><a target='_blank' href="+contextPath+'/userBlog/'+ ""+comment.fromUserVo.id+"><img src="+comment.fromUserVo.url+"></a></div>";
        itemDiv += '<div class="fl con">';
        itemDiv += "<div class='user'><a target='_blank' href="+contextPath+'/userBlog/'+ ""+comment.fromUserVo.id+" class='userName'  data-id="+comment.fromUserVo.id+">"+comment.fromUserVo.userName+"</a></div>";
        itemDiv += '<p class="content">'+comment.content+'</p>';

        itemDiv += '<div class="info">';
        itemDiv += '<span class="floor">#'+comment.floor+'</span>';
        itemDiv += '<span class="time">'+comment.createTime+'</span>';
        //有人点赞
        if(comment.isLiked===1){
            //本用户点赞了
            itemDiv += "<span  class='like_wrapper on'><img><span class='like'>" + comment.likes + "</span></span>";
        }else {
            itemDiv += "<span  class='like_wrapper'><img><span class='like'>" + comment.likes + "</span></span>";
        }
        itemDiv += '<span class="reply" onclick="showInputBox('+comment.id+')">回复</span>';
        //判断是否登录,且登录的用户idh和评论id是一样的
        if(userInfo){
            //登录了
            if(userInfo.id === comment.fromUserVo.id){
                itemDiv += "<span class='removeBtn'><img></span>";
            }
        }

        itemDiv += '</div><div class="replyBox">';
        //回复
        if(comment.replyMaps){
            for(var j=0;j<comment.replyMaps.length;j++){
                var reply = comment.replyMaps[j];
                itemDiv += '<div class="reply-item" data-id='+reply.id+'>';
                itemDiv += "<a target='_blank' href="+contextPath+'/userBlog/'+""+reply.replyFromUserVo.id+"   class='reply-face'><img src="+reply.replyFromUserVo.url+"></a>";

                itemDiv += '<div class="reply-con">';
                itemDiv += "<a target='_blank'  href="+contextPath+'/userBlog/'+""+reply.replyFromUserVo.id+" class='userName' data-id="+reply.replyFromUserVo.id+">"+reply.replyFromUserVo.userName+"</a>";
                //判断有没有回复对象
                if(reply.replyToUserVo){ //reply-con
                    //有回复对象
                    itemDiv += "<span>回复&nbsp;<a target='_blank' href="+contextPath+'/userBlog/'+""+reply.replyToUserVo.id+" class='content'>@"+reply.replyToUserVo.userName+"</a><span>："+reply.content+"</span></div></span>";
                }else{
                    itemDiv += '<span class="content">'+reply.content+'</span></div>';
                }

                itemDiv += '<div class="info">';
                itemDiv += '<span class="time">'+reply.createTime+'</span>';
                if(reply.isLiked===1){
                    itemDiv += '<span class="like_wrapper on"><img><span class="like">'+reply.likes+'</span></span>';
                }else {
                    itemDiv += '<span class="like_wrapper"><img><span class="like">' + reply.likes + '</span></span>';
                }
                itemDiv += '<span class="reply" onclick="showInputBox('+reply.id+')">回复</span>';
                //判断是否登录,且登录用户和回复的用户是一样的
                if(userInfo){
                    //登录了
                    if(userInfo.id === reply.replyFromUserVo.id){
                        itemDiv += "<span class='removeBtn'><img></span>";
                    }
                }
                itemDiv += '</div></div>'; //reply-Item
            }
        }
        itemDiv += '</div></div><div class="clear"></div></div></div>';
        commentList.append(itemDiv);
    }
}
//获取文章数据
function getArticle() {
    $.ajax({
        url:contextPath+"/getArticle",
        data:{
            id:articleId
        },
        success:function (data) {
            if(data.code===0){
                var article = data.data;
                putInArticle(article);
            }else{
                $("body").text("文章不存在");
            }
        },
        error:function () {
            serverError();
        }
    });

}


function delArticleDone(data) {
    if(data.code === 0){
        //删除成功,跳转页面
        window.location.replace(contextPath+"/index");
    }else if(data.code === 403){
        //没有登录
        window.location.replace(contextPath+"/login");
    }else{
        showErrorMsg("删除失败");
    }
}


/**
 * 收藏
 */

function doCollect(resourceId,resourceType) {
    $.ajax({
        type:'post',
        url:contextPath+'/insertResource',
        data:{
            resourceId:resourceId,
            resourceType:resourceType
        },
        success:function (data) {
            if(data.code===0){
                $(".collectBtn").addClass("on");
                $(".noticeBox .confirmBox .msg").text("操作成功");
                var noticeBox = $($(".noticeBox")[0]);
                noticeBox.show();
                setTimeout(function () {
                    noticeBox.hide()
                },1000);

            }else if(data.code===403){
                window.location.replace(contextPath+"/login");
            }else if(data.code===402){
            }else{
                alert("收藏失败,服务器异常:"+data.msg);
            }
        },
        error:function () {
            alert("收藏失败,服务器异常");
        }
    })
}



/**
 * 获取评论
 */
function getComments(pageNum,pageSize) {
    $.ajax({
        type:'get',
        url:contextPath+"/getComments",
        data:{
            articleId:articleId,
            pageNum:pageNum,
            pageSize:pageSize
        },
        success:function (data) {
            if(data.code===0){
                var comments = data.data.list;
                //填充评论总数
                $(".commentSum").text(data.data.commentNum);
                putInComment(comments);
                $(".pagination").paging({
                    pageNum:pageNum,
                    pageSize:pageSize,
                    pages:data.data.pages,
                    callback:function (pageNum) {
                        getComments(pageNum,pageSize);
                    }
                })
            }
        },
        error:function () {
            alert("获取评论失败");
        }

    });
}

/**
 * 根据回复id来删除一条回复
 */
function  delReply(id) {
    $.ajax({
        type:'post',
        url:contextPath + "/delReply",
        data:{
            id:id
        },
        success:function (data) {
            if(data.code===0){
                //删除成功
                //把对应的reply-item删除就可以了
                $('.replyBox .reply-item[data-id='+id+']').detach();
                var  count = parseInt($(".commentSum").text());
                $(".commentSum").text(count-1);
            }else if(data.code===403){
                window.location.replace(contextPath+"/login");
            }else{
                alert("删除失败");
            }
        },
        error:function () {
            alert("删除失败");
        }
    })
}

/**
 * 根据评论id来删除一条评论及其相关的回复
 */
function delComment(id) {
    //获取要被删除的元素
    var item = $('.comment-list .list-item[data-id='+id+']');
    $.ajax({
        type:'post',
        url:contextPath + "/delComment",
        data:{
            id:id
        },
        success:function (data) {
            var count =parseInt($(".commentSum").text());
            var replyItems = item.find(".reply-item");
            if(replyItems.length===0){
                count = count - 1;
            }else{
                count = count - replyItems.length-1;
            }
            $(".commentSum").text(count);
            if(data.code===0){
                item.detach();
            }if(data.code===403){
                window.location.replace(contextPath+"/login");
            }
        },
        error:function () {
            alert("删除这条评论失败！");
        }
    })

}


/**
 * 插入一条评论
 */
function insertComment() {
    var comment_content = $(".comment_content");
    var content = comment_content .val().trim();
    if(content===""){
        noticeBoxShow("评论不能为空白");
        return;
    }
    $.ajax({
        type:'post',
        url:contextPath+"/insertComment",
        data:{
            articleId:articleId,
            authorId:parseInt($("#userName").attr("data-id")),
            content:content
        },
        success:function (data) {
            //评论总数加1
            var sum =parseInt($(".commentSum").text());
            $(".commentSum").text(sum+1);
            if(data.code===0){
                var list = $(".comment-list");
                var html = "<div class='list-item' data-id="+data.data+">";

                html += "<div class='fl user-face'>";
                html +="<a target='_blank' href="+contextPath+'/userBlog/'+""+userInfo.id+"><img src="+userInfo.avatarUrl+"></a></div>";

                html += "<div class='fl con'>";
                html += "<div class='user'><a target='_blank' class='userName' href="+contextPath+'/userBlog/'+""+userInfo.id+" data-id="+userInfo.id+">"+userInfo.userName+"</a></div>";
                html += "<p class='content'>"+content+"</p>";

                html += "<div class='info'>";
                html += "<span class='time'>刚刚</span>";
                html += "<span  class='like_wrapper'><img><span class='like'>0</span></span>";
                html += '<span class="reply" onclick="showInputBox('+data.data+')">回复</span>';
                html += "<span class='removeBtn'><img></span>";
                html +="</div><div class='replyBox'></div></div><div class='clear'></div></div>";
                list.prepend(html);
                comment_content.val("");

            }else if(data.code===403){
                window.location.replace(contextPath+"/login");
            }
        },
        error:function () {
            alert("发布评论失败");
        }
    });
}

/**
 * 显示回复框框
 */
function showInputBox(id) {
    var toUid = 0;
    var pId = id;
    //先获取
    var inputBox = $($(".inputBox")[0]);
    if(inputBox.length===0){
        //说明没有,则需要创建
        inputBox = $(".comment-wrapper").clone();
        inputBox.addClass("inputBox");
    }else{
        inputBox.detach();
    }
    var commentCancel = inputBox.find(".commentCancel");
    commentCancel.off("click");
    commentCancel.click(function () {
        $(this).parent().parent().detach();
    });
    var ele = $('.comment-list .list-item[data-id='+id+']');
    var replyBox = "";
    inputBox.val("");
    if(ele.length!==0){
        //情况1:回复评论
        replyBox = $('.comment-list div[data-id='+id+'] .replyBox');

    }else{
        //情况2:回复回复
        replyBox = $('.comment-list div[data-id='+id+']').parent();
        toUid = $('.reply-item[data-id='+id+']').find(".userName").attr("data-id");
        //获取toUid对应的userName
        var toUserName =$($('.userName[data-id='+toUid+']')[0]).text();

        //提示用户回复的对象
        pId= replyBox.parent().parent().attr("data-id");
        inputBox.find(".comment_content").attr("placeholder","回复 "+toUserName+" :");
    }
    var commentPub = inputBox.find(".commentPub");
    var comment_content = inputBox.find(".comment_content");
    comment_content.val("");
    commentPub.attr("onclick","insertReply("+toUid+","+pId+")");
    inputBox.appendTo(replyBox.parent());
}





/**
 * 插入一条回复:
 * type:1.对评论进行回复
 * type:2.对回复进行回复
 */
function insertReply(toUid,pId) {
    var content = $(".inputBox").find(".comment_content").val().trim();
    var replyBox = $(".inputBox").parent().children(".replyBox");
    if(content===""){
        noticeBoxShow("回复不能为空!");
        return;
    }
    $.ajax({
        type:'post',
        url:contextPath+"/insertReply",
        data:{
            articleId:articleId,
            authorId:parseInt($("#userName").attr("data-id")),
            toUid:toUid,
            content:content,
            pId:pId
        },
        success:function (data) {
            if(data.code===0){
                var html = "<div class='reply-item' data-id="+data.data+">";
                html += "<a target='_blank' class='reply-face' href="+contextPath+'/userBlog/'+""+userInfo.id+"><img src="+userInfo.avatarUrl+"></a>";

                html += "<div class='reply-con'>";
                html += "<a target='_blank' href="+contextPath+'/userBlog/'+""+userInfo.id+" class='userName' data-id="+userInfo.id+">"+userInfo.userName+"</a>";
                if(toUid===0){
                    //表示是回复评论
                    html += "<span class='content'>"+content+"</span>";
                }else{
                    //表示是回复回复
                    //获取toUid对应的userName
                    var toUserName =$($('.userName[data-id='+toUid+']')[0]).text();
                    html += "<span>回复&nbsp;<a target='_blank' href="+contextPath+'/userBlog/'+""+toUid+" class='content'>@"+toUserName+"</a><span>："+content+"</span></span>";
                }
                html +="</div>";
                html += "<div class='info'>";
                html += "<span class='time'>刚刚</span>";
                html += "<span  class='like_wrapper'><img><span class='like'>0</span></span>";
                html += '<span class="reply" onclick="showInputBox('+data.data+')">回复</span>';
                html += "<span class='removeBtn'><img></span>";
                html += "</div></div>";
                replyBox.prepend(html);
                $(".inputBox").find(".comment_content").val("");
                var count = parseInt($(".commentSum").text());
                $(".commentSum").text(count+1);

            }else if(data.code===403){
                window.location.replace(contextPath+"/login");
            }else{
                alert("回复失败！");
            }

        },
        error:function () {
            alert("回复失败！");
        }

    })
}

$(function () {
    //获取文章
    getArticle();
    //获取评论
    getComments(1,4);

    //给收藏按钮绑定事件
    $(".collectBtn").click(function () {
        collectBoxShow(articleId);
    });

    //给评论取消按钮绑定事件
    $(".comment-top .commentCancel").click(function () {
        $(".comment-top .comment_content").val("");
    });
});


/**
 * 给删除文章按钮,绑定事件
 */
$("#own").on("click","#delArticleBtn",function () {
    var articleId=  parseInt($("#articleTitle").attr("data-id"));
    confirmBoxShow(function () {
        delArticle(articleId,delArticleDone);
    },"确定删除?");
});

function doLikeDone(data) {
    if(data.code===0){
        var div = $(".comment-list div[data-id="+data.typeId+"]");
        var like_wrapper = "";
        //判断是评论还是回复
        if(div.hasClass("list-item")){
            //表示评论
            like_wrapper = div.find(".con>.info .like_wrapper");
        }else if(div.hasClass("reply-item")){
            //表示回复
            like_wrapper = div.find(".info .like_wrapper");
        }
        like_wrapper.addClass("on");
        like_wrapper.children("span").text(parseInt(like_wrapper.children("span").text())+1);

    }else if(data.code===403){
        window.location.replace(contextPath+"/login");
    }else if(data.code===402){
        alert("已经点赞了");
    }
    else{
        alert("点赞失败");
    }
}



function undoLikeDone(data) {
    if(data.code===0){
        var div = $(".comment-list div[data-id="+data.typeId+"]");
        var like_wrapper = "";
        //判断是评论还是回复
        if(div.hasClass("list-item")){
            //表示评论
            like_wrapper = div.find(".con>.info .like_wrapper");
        }else if(div.hasClass("reply-item")){
            //表示回复
            like_wrapper = div.find(".info .like_wrapper");
        }
        like_wrapper.removeClass("on");
        like_wrapper.children("span").text(parseInt(like_wrapper.children("span").text())-1);

    }else if(data.code===403){
        window.location.replace(contextPath+"/login");
    }else{
        alert("取消点赞失败");
    }
}
/**
 * 给点评论或者回复赞按钮绑定事件
 */
$(".comment-list").on("click",".list-item .like_wrapper",function () {
    var typeId = "";
    var ownerId = 0;
    //获取评论id
    if($(this).parent().parent().hasClass("reply-item")){
        //回复
        typeId = parseInt($(this).parent().parent().attr("data-id"));
        ownerId = parseInt($(this).parent().prev().children(".userName").attr("data-id"));
    }else{
        //评论
        typeId = parseInt($(this).parent().parent().parent().attr("data-id"));
        ownerId = parseInt($(this).parent().parent().find(".userName").attr("data-id"));
    }
    if($(this).hasClass("on")){
        //表示要取消点赞
        undoLike(typeId,2,undoLikeDone);
    }else{
        //表示要进行点赞
        doLike(typeId,ownerId,2,doLikeDone);
    }
});


/**
 * 给删除评论按钮绑定事件
 */

$(".comment-list").on("click",".con>.info>.removeBtn",function () {
    //获取此条评论的id
    var commentId = parseInt($(this).parent().parent().parent().attr("data-id"));
    confirmBoxShow(function () {
        delComment(commentId);
    },"确定删除这条评论？");
});


/**
 * 给删除回复按钮绑定事件
 */
$(".comment-list").on("click",".reply-item>.info>.removeBtn",function () {
    var replyId = parseInt($(this).parent().parent().attr("data-id"));
    confirmBoxShow(function () {
        delReply(replyId);
    },"确定删除这条回复？");
});


/**
 * 给文章点赞按钮绑定事件
 */
$(".like_div .like_wrapper").click(function () {
    //获取文章id
    var typeId = parseInt($("#articleTitle").attr("data-id"));
    if($(this).hasClass("on")){
        //表示要取消点赞
        undoLike(typeId,1,function (data) {
            if(data.code===0){
                $(".like_div .like_wrapper").removeClass("on");
                var count = parseInt($(".like_div .like_wrapper .like").text());
                $(".like_div .like_wrapper .like").text(count-1);

            }else if(data.code===403){
                window.location.replace(contextPath+"/login");
            }else{
                alert("取消点赞失败");
            }

        });

    }else{
        //表示要进行点赞
        var ownerId = parseInt($("#userName").attr("data-id"));
        doLike(typeId,ownerId,1,function (data) {
            if(data.code===0){
                $(".like_div .like_wrapper").addClass("on");
                var count = parseInt($(".like_div .like_wrapper .like").text());
                $(".like_div .like_wrapper .like").text(count+1);

            }else if(data.code===403){
                window.location.replace(contextPath+"/login");
            }else if(data.code===402){
                alert("已经点赞了");
            }else{
                alert("点赞失败");
            }
        });
    }
});


