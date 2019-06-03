$(function () {
    //获取文章分类
    getCustomTypes(function (data) {
        //填充
        if(data.code===0){
            var customTypes = data.data;
            // var drop_down = $($(".customType .drop_down")[0]);
            var drop_down = $(".customType .drop_down");
            for(var i=0;i<customTypes.length;i++){
                drop_down.append("<a href='javascript:void(0)' data-id="+customTypes[i].id+">"+customTypes[i].name+"</a>");
            }
            //
            initSelectBox($.getUrlParam("customTypeId"),$.getUrlParam("articleType"));
        }
    });
    //获取请求参数
    var articleType = $.getUrlParam("articleType");
    var customTypeId = $.getUrlParam("customTypeId");

    //初始化搜索框框


    //获取文章,并填充
    var pageNum = $.getUrlParam("pageNum");
    if(pageNum===null){
        pageNum = 1;
    }else{
        pageNum = parseInt(pageNum);
    }
    getArticlesInManager(pageNum,5,customTypeId,articleType,function (data) {
        var articles = data.list;
        var tbody = $(".content tbody");
        tbody.empty();
        var html = "";
        if(articles.length===0){
            html = "<tr><td colspan='6'>暂无文章</td></tr>;"
            tbody.append(html);
            return;
        }
        for(var i=0;i<articles.length;i++){
            html +="<tr>";
            html += "<td><input type='checkbox' name='ids' value="+articles[i].id+"></td>";
            html += "<td><a href="+contextPath+'/article/'+articles[i].id+">"+articles[i].articleTitle+"</a></td>";
            html += "<td><p>"+articles[i].articleType+"</p></td>";
            //根据分类id获取分类名称
            var customTypeName = $(".selectBox .customType .drop_down a[data-id="+articles[i].customTypeId+"]").text();
            if(customTypeName===""){
                html += "<td><p>未分类</p></td>";
            }else{
                html += "<td><p>"+customTypeName+"</p></td>";
            }
            html += "<td><p>"+articles[i].createTime+"</p></td>";
            html += "<td class='op'><a target='_blank' class='edit' href="+contextPath+'/update/'+articles[i].id+">编辑</a><p class='removeOne' onclick='deleteArticleOne("+articles[i].id+")' data-id="+articles[i].id+">删除</p></td>";
            html +="</tr>";
        }
        tbody.append(html);
    });

    //全选按钮绑定事件
    $(".checkAll").click(function (e) {
        var inputs = $("input[name=ids]");
        if($(this).prop("checked")){
            //进行全选
            for(var i=0;i<inputs.length;i++){
                $(inputs[i]).prop("checked",true);
            }
        }else{
            //进行全选
            for(var i=0;i<inputs.length;i++){
                $(inputs[i]).prop("checked",false);
            }
        }

    });

});


/**
 * 文章分类相关操作
 */
$(".selectBox .customType .drop_down").on("click","a",function () {
    var customType = $("#customType");
    var a = $(this);
    customType.text(a.text());
    customType.attr("data-id",a.attr("data-id"));
    var drop_head = $(this).parent().prev();
    drop_head.removeClass("on");
});


/**
 * 文章类型相关操作
 */
$(".articleType .drop_down a").click(function () {
    $("#articleType").text($(this).text());
    $(this).parent().prev().removeClass("on");
});


/**
 * 查找
 */
$(".searchBtn").click(function () {
    var customTypeId = $("#customType").attr("data-id");
    var articleType = $("#articleType").text();
    var url = contextPath+"/user/articleManager";
    if(customTypeId===""){
        if(articleType!=="全部"){
            url += "?articleType="+articleType;
        }
    }else{
        if(articleType!=="全部"){
            url +="?customTypeId=" +customTypeId+"&articleType="+articleType;
        }else{
            url +="?customTypeId=" +customTypeId;
        }
    }
    //注意这里不要使用location.replace(url)
    window.location.href=url;
});


/**
 * 获取文章列表
 */
function getArticlesInManager(pageNum,pageSize,customTypeId,articleType,callback) {
    $.ajax({
        type:'get',
        url:contextPath+"/getArticlesInManager",
        data:{
            pageNum:pageNum,
            pageSize:pageSize,
            customTypeId:customTypeId,
            articleType:articleType
        },
        success:function (data) {
            console.log(data);
            if(data.code===0){
                if(callback){
                    callback(data.data);
                    $(".pagination").paging({
                        pageNum:data.data.pageNum,
                        pageSize:data.data.pageSize,
                        pages:data.data.pages,
                        callback:function (pageNum) {
                            var uri =contextPath+"/user/articleManager?pageNum="+pageNum;
                            if($.getUrlParam("customTypeId")!==null){
                                uri += "&customTypeId=" + parseInt(customTypeId);
                            }
                            if($.getUrlParam("articleType")!==null){
                                uri += "&articleType=" + articleType;
                            }
                            window.location.href=uri;
                        }
                    })
                }
            }else if(data.code=403){
                window.location.replace(contextPath+"/login");
            }
        },
        error:function () {
            alert("获取文章列表失败！");
        }
    })
}

/**
 * 初始化搜索框框
 */
function initSelectBox(customTypeId,articleType) {
    var articleTypeObj = $("#articleType");
    if(articleType==="转载"){
        articleTypeObj.text("转载");
    }else if(articleType==="原创"){
        articleTypeObj.text("原创");
    }else{
        articleTypeObj.text("全部");
    }
    var a = $(".selectBox .customType .drop_down a[data-id="+customTypeId+"]");
    var customType = $("#customType");
    if(a.length!==0){
        customType.text(a.text());
        customType.attr("data-id",a.attr("data-id"));
    }else{
        customType.text("全部分类");
        customType.attr("data-id","");
    }
}

/**
 * 返回被选中的文章id列表
 */

function getArticleIds() {
    var inputs = $("input[name=ids]");
    var articleIds = [];
    for(var i=0;i<inputs.length;i++){
        if($(inputs[i]).prop("checked")){
            var id = parseInt($(inputs[i]).val());
            articleIds.push(id);
        }
    }
    return articleIds;
}


/**
 * 更改文章列表的分类
 */

$(".bottom .customType .drop_down").on("click","a",function () {
    var articleIds = getArticleIds();
    var customTypeId = parseInt($(this).attr("data-id"));
    if(articleIds.length === 0){
        noticeBoxShow("你还没有选择要移动的文章");
    }else{
        confirmBoxShow(function () {
            $.ajax({
                type:'post',
                url:contextPath+"/updateArticleCustomTypes",
                data:{
                    articleIds:articleIds,
                    customTypeId:customTypeId
                },
                success:function (data) {
                    if(data.code===0){
                        window.location.reload();
                    }else if(data.code === 403){
                        window.location.replace(contextPath+"/login");
                    }else{
                        alert("移动文章失败");
                    }
                },
                error:function () {
                    alert("移动文章失败");
                }
            })
        },"确定移动文章？")
    }
});

/**
 * 删除一篇文章
 */
function deleteArticleOne(id) {
    confirmBoxShow(function () {
        $.ajax({
            type:'post',
            url:contextPath+'/delArticle',
            data:{
                id:id
            },
            success:function (data) {
                if(data.code===0){
                    window.location.reload();
                }else if(data.code===403){
                    window.location.replace(contextPath+"/login");
                }else{
                    alert("删除文章失败")
                }
            },
            error:function () {
                alert("删除文章失败")
            }
        })
    },"确定删除？");
}


/**
 * 删除多个文章
 */
$(".removeMonyBtn ").click(function () {
    var articleIds = getArticleIds();
    if(articleIds.length===0){
        noticeBoxShow("请选中要删除的文章");
    }else{
        confirmBoxShow(function () {
            $.ajax({
                type:'post',
                url:contextPath+"/delArticles",
                data:{
                    articleIds:articleIds
                },
                success:function (data) {
                    if(data.code===0){
                        window.location.reload();
                    }else if(data.code===403){
                        window.location.replace(contextPath+"/login");
                    }else{
                        alert("删除文章失败！");
                    }
                },
                error:function () {
                    alert("删除文章失败！");
                }
            })
        },"确定删除？");
    }
});
