/**
 * 获取用户基本信息
 */
function getUserInfo(userId,callback) {
    $.ajax({
        url:contextPath+"/getUserInfoInUserBlog",
        type:'get',
        data:{
            userId:userId
        },
        success:function (data) {
            if(callback){
                callback(data);
            }
        },
        error:function () {
            alert("获取用户基本信息失败");
        }
    })
}

function getUserInfoDone(data) {
    if(data.code===0){
        var user = data.data;
        $(".userHead .userName").text(user.userName);
        $(".gender span").text(user.gender);
        $(".birthday span").text(user.birthday);
        $(".brief span").text(user.personalBrief);
        $(".sign span").text(user.sign);
        $(".userHead img").attr("src",user.avaUrl);

        //博客创建
        $(".createBlogTime span").text(user.createTime);
        //最近登录时间
        $(".lastLoginTime span").text(user.lastLoginTime);
    }else{
        alert("获取用户基本信息失败");
    }
}


/**
 * 获取文章分类列表
 */

function getCustomTypesInUserBlog(userId,callback) {
    $.ajax({
        type:'get',
        url:contextPath+"/getCustomTypesInUserBlog",
        data:{
            userId:userId
        },
        success:function (data) {
            if(callback){
                callback(data);
            }
        },
        error:function () {
            alert("获取文章分类列表失败");
        }
    })
}

function getCustomTypesInUserBlogDone(data) {
    if(data.code===0){
        var list = data.data;
        var ul = $(".customType-list ul");
        var html = "";
        if(list.length===0){
            html += "<div class='clear'></div>";
            ul.append(html);
            return;
        }
        var customTypeId = $.getUrlParam("customTypeId");
        if(customTypeId===null){
            //给主页加上on
            ul.find("li a").addClass("on");
        }
        for(var i=0;i<list.length;i++){
            if(customTypeId==list[i].id){
                html += "<li><a class='on' href=" + contextPath + '/userBlog/' + "" + userId + '?customTypeId=' + "" + list[i].id + ">" + list[i].name + "</a></li>";
            }else {
                html += "<li><a href=" + contextPath + '/userBlog/' + "" + userId + '?customTypeId=' + "" + list[i].id + ">" + list[i].name + "</a></li>";
            }
        }
        html += "<div class='clear'></div>";
        ul.append(html);
    }else{
        alert("获取文章分类列表失败");
    }
}


/**
 * 获取文章
 */
function getArticlesInUserBlog(customTypeId,userId,callback,pageNum,pageSize) {
    $.ajax({
        type:'get',
        url:contextPath+"/getArticlesInUserBlog",
        data:{
            customTypeId:customTypeId,
            userId:userId,
            pageNum:pageNum,
            pageSize:pageSize
        },
        success:function (data) {
            if(callback){
                callback(data);
                $(".pagination").paging({
                    pageNum:data.data.pageNum,
                    pageSize:data.data.pageSize,
                    pages:data.data.pages,
                    callback:function (pageNum) {
                        var uri = "";
                        if(customTypeId===null){
                            uri = contextPath+"/userBlog/" + userId+"?pageNum="+pageNum;
                        }else{
                            uri = contextPath +"/userBlog/"+userId+"?customTypeId="+customTypeId+"&currentNum="+pageNum;
                        }
                        window.location.href = uri;
                    }
                })
            }
        },
        error:function () {
            alert("获取文章列表失败");
        }
    })
}

function getArticlesInUserBlogDone(data) {
    if(data.code===0){
        var list = data.data.list;
        var contentList = $(".content-list");
        contentList.empty();
        if(list.length===0){
            contentList.text("暂无文章");
            return;
        }
        var html = "";
        for(var i=0;i<list.length;i++){
            html += "<div class='item' data-id="+list[i].articleId+">";
            html += "<div class='articleHead'>";
            html += "<a class='articleTitle' href="+contextPath+'/article/'+list[i].articleId+">"+list[i].articleTitle+"</a>";

            html += "<div class='info'>";
            html += "<span class='createTime'><img>"+list[i].createTime+"</span>";
            html += "<a data-id="+data.data.authorId+" class='author'><img src="+data.data.avatarUrl+"><span>"+data.data.author+"</span></a>";
            html += "<a class='customType'><img><span class='customTypeName'>"+list[i].customTypeName+"</span></a>"
            if(list[i].isLiked===1){
                html += "<a class='zan fr on'><img><span class='likes'>"+list[i].likes+"</span></a>";
            }else{
                html += "<a class='zan fr'><img><span class='likes'>"+list[i].likes+"</span></a>";
            }
            html += "<div class='clear'></div>";
            html += "</div>"; //info

            html += "</div>"; //articleHead

            html += "<div class='articleBrief'>"+list[i].articleBrief+"</div>";
            html += "<div class='read-all'><a href="+contextPath+'/article/'+list[i].articleId+">阅读全文</a></div>";

            html += "<div class='other'>";
            //判断是否是原创还是转载
            if(list[i].articleType==="原创"){
                html += "<p class='original type fl'>原创</p>";
            }else if(list[i].articleType==="转载"){
                html += "<p class='reprint type fl'>转载</p>";
            }
            html += "<p class='commentNum fl'>"+'评论&nbsp;'+list[i].commentNum+"</p>";
            html += "<p class='collectNum fl'>"+'收藏&nbsp;'+list[i].collectNum+"</p>";
            if(userInfo){
                html += "<a class='remove fr'>删除</a>";
            }
            html += "<a href="+contextPath+'/update/'+list[i].articleId+" class='edit fr'>编辑</a>";
            html += "<div class='clear'></div>";
            html += "</div>"; //other
            html += "</div>";//item
       }
       contentList.append(html);
    }else{
        alert("获取文章列表失败");
    }
}


/**
 * 获取某个用户最新发布的几篇文章
 */

function getLatestArticles (userId,num,callback) {
    $.ajax({
        url:contextPath+"/getLatestArticlesInSomeone",
        type:'get',
        data:{
            userId:userId,
            num:num
        },
        success:function (data) {
            if(callback){
                callback(data);
            }
        },
        error:function () {
            alert("获取最近发布的文章失败");
        }

    })

}
function getLatestArticlesDone (data) {
    if(data.code===0){
        var list = data.data;
        var ul = $(".latestArticles ul");
        var html = "";
        if(list.length===0){
            ul.text("暂无");
        }else{
            for(var i=0;i<list.length;i++){
                html += "<li><a class='articleTitle' href="+contextPath+'/article/'+list[i].articleId+"><img>"+list[i].articleTitle+"</a></li>";
            }
            ul.append(html);

        }

    }else{
        alert("获取最近发布的文章失败");
    }
}



/**
 * 获取某个用户发布的文章总数
 */

function countArticlebyUserId(userId) {
    $.ajax({
        type:'get',
        url:contextPath+"/countArticlesByUserId",
        data:{
            userId:userId
        },
        success:function (data) {
            if(data.code===0){
                $(".articleNum span").text(data.data);
            }

        },
        error:function () {
            alert("获取文章总数失败");
        }
    })
    
}

//获取最近访问的用户
function getVisitors(userId,callback) {
    $.ajax({
        type:'get',
        url:contextPath+"/getVisitors",
        data:{
            userId:userId
        },
        success:function (data) {
            if(callback){
                callback(data);
            }
        },
        error:function () {
            alert("获取最近访问人员列表失败");
        }
    })
}

function getVisitorsDone(data) {
    if(data.code===0){
        var ul = $(".latestVisitor ul");
        var list = data.data;
        if(list.length===0){
            ul.text("暂无来访人员");
            return;
        }
        var html = "";
        for(var i=0;i<list.length;i++){
            html += "<li><a target='_blank' href="+contextPath+'/userBlog/'+""+list[i].visitorId+"><img src="+list[i].visitorAvatarUrl+"><p>"+list[i].visitorName+"</p></a></li>";
        }
        html += "<div class='clear'></div>";
        ul.append(html);

    }else{
        alert("获取最近访问人员列表失败");
    }
}

/**
 * 该用户所有文章包含的所有评论及回复总和
 */
function countCommentsByAuthorId(authorId) {
    $.ajax({
        type:'get',
        url:contextPath+"/countCommentsByAuthorId",
        data:{
            authorId:authorId
        },
        success:function (data) {
            if(data.code===0){
                $(".commentNum span").text(data.data);
            }else{
                alert("获取评论总数失败");
            }
        },
        error:function () {
            alert("获取评论总数失败");
        }
    })
}

function getSummary(userId) {
    $.ajax({
        type:'get',
        url:contextPath+"/getSummary",
        data:{
            userId:userId
        },
        success:function (data) {
            if(data.code===0){
                $(".articleNum span").text(data.data.articleNum);
                $(".commentNum span").text(data.data.commentNum);
                $(".likeNum span").text(data.data.likeNum);
            }else{
                alert("获取用户概况信息失败");
            }
        },
        error:function () {
            alert("获取用户概况信息失败");
        }
    })

}
$(function () {
    if(!userIsExist){
        $("body").text("此用户不存在");
    }
    if(userIsExist){
        //获取参数
        var customTypeId = $.getUrlParam("customTypeId");
        var currentNum = $.getUrlParam("pageNum");
        if(currentNum===null){
            currentNum=1;
        }
        //获取基本信息
        getUserInfo(userId,getUserInfoDone);

        //获取文章分类列表
        getCustomTypesInUserBlog(userId,getCustomTypesInUserBlogDone);
        //获取文章列表
        getArticlesInUserBlog(customTypeId,userId,getArticlesInUserBlogDone,currentNum);

        //获取最新发布的文章
        getLatestArticles(userId,6,getLatestArticlesDone);

        //获取最近访问人员列表
        getVisitors(userId,getVisitorsDone);

        //获取用户概括信息
        getSummary(userId);
    }
});


/**
 * 删除文章按钮绑定事件
 */
$(".content-list").on("click",".item .remove",function () {
    //获取文章id
    var articleId = parseInt($(this).parent().parent().attr("data-id"));
    confirmBoxShow(function () {
        delArticle(articleId,function (data) {
            if(data.code===0){
                //1.先删除item
                var item = $(".content-list .item[data-id="+articleId+"]");
                item.detach();

                //2.判断此页面是否还有数据
                var items = $(".content-list .item");
                if(items.length===0){
                    //没有数据则刷新当前页面
                    window.location.reload();
                }
            }else if(data.code===403){
                window.location.replace(contextPath+"/login");
            }else{
                alert("删除文章失败");
            }
        });

    },"确定删除？");
});
