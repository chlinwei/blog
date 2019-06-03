$(function () {
    findCollects(putInCollects);
    //获取默认收藏夹信息
    getDefaultCollect(putInCollect);
    //获取默认收藏夹里的文章
    getArticlesByDefaultCollect(putInArticle);

    $(".main-left .collect-list>ul").on("mouseover","li .more div",function () {
        var ul = $(this).next();
        var parent = $(this).parent().parent();
        //注意下面这行代码
        var top = parent.offset().top - $(document).scrollTop() + parent.outerHeight();
        ul.css("top",top);
        ul.show();
        $(document).bind("scroll",function () {
                top = parent.offset().top - $(document).scrollTop() + parent.outerHeight();
                ul.css("top",top);
        });

    });
    $(".main-left .collect-list>ul").on("mouseout","li .more div",function () {
        //获取ul
        var ul = $(this).next();
        if(!ul.is(":hover")){
            ul.hide();
            $(document).unbind("scroll");
        }
    });
    $(".main-left .collect-list>ul").on("mouseleave","li .more ul",function (e) {
        //获取div
        var div = $(this).prev();
        if(!div.is(":hover")){
            $(this).hide();
            $(document).unbind("scroll");
        }
    });
});


/**
 * 给新建按钮绑定事件
 */
$(".newBtn").click(function () {
    $(".newCollectBox").show(500);
    $(".newCollectBox .sub").unbind("click");
    $(".newCollectBox .sub").click(function () {
        //新建
        var name = $(".newCollectBox .name").val().trim();
        var brief = $(".newCollectBox .brief").val().trim();
        var collect = {
            id:null,
            name:name,
            brief:brief
        };
        createOrUpdateCollect(collect,putInNewCollect);
    });
});

//编辑collect
$(".main-left .collect-list>ul").on("click","li .more .edit",function (e) {
    $(".newCollectBox").show();
    var collectId = parseInt($(this).parent().parent().parent().attr("data-id"));
    getCollectById(collectId,function (data) {
        if(data.code===0){
            $(".newCollectBox .name").val(data.data.name);
            $(".newCollectBox .brief").val(data.data.brief);
            //编辑
            $(".newCollectBox .sub").unbind("click");
            $(".newCollectBox .sub").click(function () {
                var name = $(".newCollectBox .name").val().trim();
                var brief = $(".newCollectBox .brief").val().trim();
                var collect = {
                    id:collectId,
                    name:name,
                    brief:brief
                };
                createOrUpdateCollect(collect,putInUpdateCollect);
            });

        }else if(data.code===403){
            window.location.replace(contextPath+"/login");
        }else{
            alert("获取收藏夹信息失败!");
        }
    });
});
/**
 * 删除绑定事件
 */
$(".main-left .collect-list>ul").on("click","li .more .remove",function (e) {
    var collectId = parseInt($(this).parent().parent().parent().attr("data-id"));
    delCollect(collectId,delCollectDone);
});

function delCollect (collectId,callback) {
    $.ajax({
        type:'post',
        url:contextPath+"/delCollect",
        data:{
            collectId:collectId
        },
        success:function (data) {
            data.id=collectId;
            if(callback){
                callback(data);
            }
        },
        error:function () {
            alert("删除收藏夹失败");
        }
    })
}

/**
 * 删除后做的事情
 */
function delCollectDone(data) {
    if(data.code===0){
        //获取要被删除的li
        noticeBoxShow("删除成功",1500);
        var li = $(".main-left .collect-list ul li[data-id="+data.id+"]");
        li.detach();

    }else if(data.code===403){
        window.location.replace(contextPath+"/login");
    }else{
        alert("删除收藏夹失败");
    }
}


/**
 * 获取指定收藏夹信息
 */

function getCollectById(collectId,callback) {
    $.ajax({
        type:'get',
        url:contextPath+"/getCollectById",
        data:{
            collectId:collectId
        },
        success:function (data) {
            if(callback){
                callback(data)
            }
        },
        error:function () {
            alert("获取收藏夹信息失败");
        }
    })
}

/**
 * 填充当前的收藏夹
 */
function putInCollect(data) {
    if(data.code===0){
        var collect = data.data;
        $(".collect .name").text(collect.name);
        $(".collect .brief").text(collect.brief);
    }else if(data.code===403){
        window.location.replace(contextPath+"/login");
    }else{
        alert("获取收藏夹信息失败!!");
    }
}
/**
 * 获取收藏夹列表
 */

function findCollects(callback) {
    $.ajax({
        type:'get',
        url:contextPath+"/findCollects",
        data:{},
        success:function (data) {
            if(callback){
                callback(data);
            }
        },
        error:function () {
            alert("获取收藏夹列表失败");
        }
    })
}


/**
 * 填充收藏夹列表
 */
function putInCollects(data) {
    if(data.code===0){
        var list = data.data;
        //这里可以
        var ul = $(".collect-list>ul");
        ul.empty();
        var html = "";
        for(var i=0;i<list.length;i++){
            var name = list[i].name;
            //判断是否为默认收藏夹
            if(list[i].isDefault===1){
                html += "<li class='default on' data-id="+list[i].id+">";
            }else{
                html += "<li data-id="+list[i].id+">";
            }
            html += "<a><img>"+name+"</i></a>";
            html += "<span class='count'>"+list[i].num+'/500'+"</span>";

            html += "<div class='more'>";
            html += "<div><img></div>";
            html += "<ul>";
            html += "<li class='edit'>编辑</li>";
            if(list[i].isDefault!==1){
                html += "<li class='remove'>删除</li>";
            }
            html += "</ul>";
            html += "</li>";
        }
        ul.append(html);
    }else if(data.code===403){
        window.location.replace(contextPath+"/login");
    }else{
        alert("获取收藏夹列表失败");
    }
}


/**
 * 根据收藏夹来获取收藏夹包含的文章
 */

function getArticlesByCollect (collectId,callback,pageNum,pageSize) {
    $.ajax({
        type:'get',
        url:contextPath+'/getArticlesByCollect',
        data:{
            collectId:collectId,
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
                        getArticlesByCollect(collectId,callback,pageNum,pageSize);
                    }
                })
            }

        },
        error:function () {
            alert("获取收藏夹里的文章失败!");
        }
    })
}

/**
 * 填充文章
 */
function putInArticle(data) {
    console.log(data);
    if(data.code===0){
        var contentList = $(".main-right .content .content-list");
        var html = "";
        contentList.empty();
        if(data.data.list.length===0){
            html = "<h3>暂无收藏的文章！</h3>";
            contentList.append(html);
            return;
        }else{
            var list = data.data.list;
            for(var i=0;i<list.length;i++){
                html += "<div class='item' data-id="+list[i].id+">";
                html += "<div class='articleHead'>";
                html += "<a href="+contextPath+'/article/'+list[i].id+" class='articleTitle'>"+list[i].articleTitle+"</a>";

                html += "<div class='info'>";
                html += "<span class='createTime'><img>"+list[i].createTime+"</span>";
                html += "<a class='author' ><img><span>"+list[i].author+"</span></a>";
                html += "<a class='customType'><img><span class='customTypeName'>"+list[i].customTypeName+"</span></a>";
                html += "</div></div>";
                html += "<div class='articleBrief'>"+list[i].brief+"</div>";
                html += "<div class='read-all'><a href="+contextPath+'/article/'+list[i].id+">阅读全文</a></div>";
                html += "<div class='cancelCollect'><a>取消收藏</a></div></div>";
            }
            contentList.append(html);
        }
    }else if(data.code===403){
        window.location.replace(contextPath+"/login");
    }else{
        alert("获取收藏夹里的文章失败!");
    }
}


/**
 * 获取默认收藏夹信息
 */
function getDefaultCollect(callback) {
    $.ajax({
        type:'get',
        url:contextPath+"/getDefaultCollect",
        data:{},
        success:function (data) {
            if(callback){
                callback(data);
            }
        },
        error:function () {
            alert("获取默认收藏夹失败");
        }
    })
}

/**
 * 获取默认收藏夹里的文章
 */

function getArticlesByDefaultCollect(callback,pageNum,pageSize) {
    $.ajax({
        type:'get',
        url:contextPath+"/getArticlesByDefaultCollect",
        data:{
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
                        getArticlesByDefaultCollect(callback,pageNum,pageSize);
                    }
                })
            }
        },
        error:function () {
            alert("获取默认收藏夹里的文章失败");
        }
    })
}


/**
 * 给左侧栏的a标签绑定事件
 */

$(".main-left .collect-list> ul").on("click","li a",function () {
    //1.更改当前li背景色
    var li = $(".main-left .collect-list>ul li.on");
    li.removeClass("on");
    $(this).parent().addClass("on");

    //2.获取当前collect信息
    var collectId = parseInt($(this).parent().attr("data-id"));
    getCollectById(collectId,putInCollect);

    //3.获取当前collect所包含的文章
    //清空分页信息
    $(".pagination").empty();
    getArticlesByCollect(collectId,putInArticle);

});

/**
 * 填充新建的收藏夹
 */
function putInNewCollect(data) {
    if(data.code===0){
        var ul = $(".main-left .collect-list>ul");
        var html = "<li data-id="+data.data.id+">";
        html += "<a><img>"+data.data.name+"</i></a>";
        html += "<span class='count'>0/500</span>";
        html += "<div class='more'>";
        html += "<div><img></div>";
        html += "<ul>";
        html += "<li class='edit'>编辑</li>";
        if(data.data.id!==1){
            html += "<li class='remove'>删除</li>";
        }
        html += "</ul>";
        html += "</li>";
        ul.append(html);
        $(".newCollectBox").hide();
        $(".newCollectBox .name").val("");
        $(".newCollectBox .brief").val("");
        noticeBoxShow("收藏夹新建成功",1500);
    }else if(data.code=403){
        window.location.replace(contextPath+"/login");
    }else{
        alert("新建收藏夹失败！");
    }

}

/**
 * 填充修改后的collect
 */
function putInUpdateCollect(data) {
    if(data.code===0){
        var a = $(".main-left .collect-list ul li[data-id="+data.data.id+"] a");
        a.html("<img>"+data.data.name);
        $(".newCollectBox").hide();
        $(".newCollectBox .name").val("");
        $(".newCollectBox .brief").val("");
        noticeBoxShow("收藏夹编辑成功",1500);
    }else if(data.code=403){
        window.location.replace(contextPath+"/login");
    }else{
        alert("修改收藏夹失败！");
    }
}

/**
 * 给取消收藏按钮绑定事件
 */

$(".content-list").on("click",".item .cancelCollect a",function () {
    //1.获取当前的收藏夹id
    var collectId = parseInt($(".collect-list>ul>li.on").attr("data-id"));
    //2.获取文章id
    var articleId = parseInt($(this).parent().parent().attr("data-id"));
    cancelCollect(articleId,collectId,cancelCollectDone);

});



/**
 * 取消收藏
 */
function cancelCollect(articleId,collectId,callback) {
    $.ajax({
        type:'post',
        url:contextPath+"/cancelArticle",
        data:{
            articleId:articleId,
            collectId:collectId
        },
        success:function (data) {
            data.articleId = articleId;
            data.collectId = collectId;
            if(callback){
                callback(data);
            }
        },
        error:function () {
            alert("取消收藏失败");
        }
    })
}

/**
 * 取消收藏后的事件
 */

function cancelCollectDone(data) {
    if(data.code===0){
       //1.删除item
        var item =  $(".content-list .item[data-id="+data.articleId+"]");
       item.detach();
       //2.右边的count数目减一
        var span = $(".collect-list>ul>li[data-id="+data.collectId+"]>span");
        var count = parseInt(span.text().split("/")[0]);
        span.text(count-1+"/500");
    }else if(data.code===403){
        window.location.replace(contextPath+"/login");
    }else{
        alert("取消收藏失败");
    }
}
