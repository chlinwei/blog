
/**
 * 获取所有文章
 */
function getAllArticle(callback,pageNum,pageSiZe) {
    $.ajax({
        type:'get',
        url:contextPath+"/getAllArticle",
        data:{
            pageNum:pageNum,
            pageSize:pageSiZe
        },
        success:function (data) {
            if(callback){
                callback(data);
                $(".pagination").paging({
                    pageNum:data.data.pageNum,
                    pageSize:data.data.pageSize,
                    pages:data.data.pages,
                    callback:function (pageNum) {
                        getAllArticle(getAllArticleDone,pageNum);
                        //滚动条
                        $(document).scrollTop(0);
                    }
                })
            }
        },
        error:function () {
            alert("获取文章列表失败");
        }
    })
}




function getAllArticleDone(data) {
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
            html += "<a data-id="+list[i].authorId+" target='_blank' href="+contextPath+'/userBlog/'+""+list[i].authorId+" class='author'><img src="+list[i].avatarUrl+"><span>"+list[i].author+"</span></a>";
            html += "<a target='_blank' href="+contextPath+'/userBlog/'+""+list[i].authorId+'?customTypeId='+""+list[i].customTypeId+" class='customType'><img><span class='customTypeName'>"+list[i].customTypeName+"</span></a>";
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
            html += "<div class='clear'></div>";
            html += "</div>"; //other
            html += "</div>";//item
        }
        contentList.append(html);
    }else{
        alert("获取文章列表失败");
    }
}


function getLatestArticles(callback,num) {
    if(!num){
        num = 10;
    }
    $.ajax({
        type:'get',
        url:contextPath+"/getLatestArticles",
        data:{
            num:num
        },
        success:function (data) {
            if(callback){
                callback(data);
            }
        },
        error:function () {
            alert("获取最近博文失败");
        }
    })
}
function getLatestArticlesDone(data) {
    if(data.code===0){
        var list = data.data;
        var ul = $(".latestArticle ul");
        var html = "";
        if(list.length===0){
            ul.text("暂无博文");
            return;
        }else{
            for(var i=0;i<list.length;i++) {
                html += "<li><a class='articleTitle' href=" + contextPath + '/article/' + list[i].articleId + "><img>" + list[i].articleTitle + "</a></li>";
            }
        }
        ul.append(html);

    }else{
        alert("获取最近博文失败");
    }
}

/**
 * 获取index概括信息
 */

function getIndexSummary() {
    $.ajax({
        type:'get',
        url:contextPath+"/getIndexSummary",
        data:{},
        success:function (data) {
            if(data.code===0){
                $(".articleNum span").text(data.data.articleNum);
                $(".commentNum span").text(data.data.commentNum);
            }else{
                alert("获取index概括信息失败");
            }
        },
        error:function () {
            alert("获取index概括信息失败");
        }
    })
}


$(function () {
    //这两个时间需要手动更改
    //网站最后更新时间
    var websiteUpdateTime = '2019-05-26 14:23:11';
    //网站开始运行时间
    var websiteRunningTime = '2019-04-26 14:34:00';

    $(".websiteUpdateTime").text(websiteUpdateTime);
    var time = parseInt((new Date().getTime() - new Date(websiteRunningTime).getTime())/1000);
    setInterval(function () {
        siteRunningTime(time);
        time++;
    },1000);

    getAllArticle(getAllArticleDone);
    getLatestArticles(getLatestArticlesDone);

    //获取index概括信息
    getIndexSummary();
});

function siteRunningTime(time) {
    var theTime;
    var strTime = "";
    if (time >= 86400){
        theTime = parseInt(time/86400);
        strTime += theTime + "天";
        time -= theTime*86400;
    }
    if (time >= 3600){
        theTime = parseInt(time/3600);
        strTime += theTime + "时";
        time -= theTime*3600;
    }
    if (time >= 60){
        theTime = parseInt(time/60);
        strTime += theTime + "分";
        time -= theTime*60;
    }
    strTime += time + "秒";
    $('.websiteRunningTime').html(strTime);
}
