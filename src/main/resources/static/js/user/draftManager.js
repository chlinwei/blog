/**
 * 获取草稿列表
 */
function getDraftList(pageNum,pageSize,callback) {
    $.ajax({
        url:contextPath+"/getDraftList",
        type:'get',
        data:{
            pageNum:pageNum,
            pageSize:pageSize
        },
        success:function (data) {
            if(callback){
                callback(data);
            }
            $(".pagination").paging({
                pageNum:data.data.pageNum,
                pageSize:data.data.pageSize,
                pages:data.data.pages,
                callback:function (pageNum) {
                    window.location.href=contextPath+"/user/draftManager?pageNum=" + pageNum;
                }
            })
        },
        error:function () {
            alert("获取草稿列表失败");
        }
    })
}

function getDraftListDone(data) {
    if(data.code===0){
        var tbody = $(".draft-list tbody");
        var list = data.data.list;
        if(list.length===0){
            tbody.text("暂无草稿");
            return
        }
        var html = "";
        tbody.empty();
        for(var i=0;i<list.length;i++){
            html += "<tr>";
            html += "<td><input name='ids' type='checkbox' value="+list[i].id+"></td>";
            html += "<td class='title'><a target='_blank' href="+contextPath+'/draft/'+""+list[i].id+">"+list[i].articleTitle+"</td>";
            var time  = list[i].updateTime.substring(0,list[i].updateTime.lastIndexOf("."));
            html += "<td><p>"+time+"</p></td>";
            html += "<td class='op'>";
            html += "<a target='_blank' href="+contextPath+'/draft/'+""+list[i].id+" class='edit btn fl'>编辑</a>";
            html += "<a class='remove btn fr'>删除</a>";
            html += "<div class='clear'></div>";
            html += "</td></tr>";
        }
        tbody.append(html);
    }else if(data.code===403){
        window.location.replace(contextPath+"/login");
    }else{
        alert("获取草稿列表失败");
    }
}

$(function () {
    //获取草稿列表
    var pageNum = $.getUrlParam("pageNum");
    if(pageNum===null){
        pageNum=1;
    }else{
        pageNum = parseInt(pageNum);
    }
    getDraftList(pageNum,6,getDraftListDone);
});


/**
 * 全选按钮绑定事件
 */

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


function delDraftDone(data) {
    if(data.code===0){
        var input = $("input[value="+data.draftId+"]");
        input.parent().parent().detach();
        var size = $(".draft-list tbody").find("tr").length;
        if(size===0){
            window.location.reload();
        }
    }else if(data.code===403){
        window.location.replace(contextPath+"/login");
    }else{
        alert("删除草稿失败");
    }
    
}

function delDraft(draftId,callback) {
    $.ajax({
        type:'post',
        url:contextPath+"/delDraft",
        data:{
            draftId:draftId
        },
        success:function (data) {
            data.draftId = draftId;
            if(callback) {
                callback(data);
            }
        },
        error:function () {
            alert("删除草稿失败");
        }
    })
}

//删除多篇文章
function delDrafts(draftIds,callback) {
    $.ajax({
        type:'post',
        url:contextPath+"/delDrafts",
        data:{
            draftIds:draftIds
        },
        success:function (data) {
            data.draftIds = draftIds;
            if(callback){
                callback(data);
            }
        },
        error:function () {
            alert("删除失败");
        }
    })
}

function delDraftsDone(data) {
    var draftIds =  data.draftIds;
    if(data.code===0){
        for(var i=0;i<draftIds.length;i++){
            var input = $("input[value="+draftIds[i]+"]");
            input.parent().parent().detach();
        }
        var size = $(".draft-list tbody").find("tr").length;
        if(size===0){
            window.location.reload();
        }
    }else if(data.code===403){
        window.location.replace(contextPath+"/login");
    }else{
        alert("删除失败");
    }

}

/**
 * 单独删除一篇草稿
 */
$(".draft-list tbody").on("click","tr .remove",function () {
    var draftId =  $(this).parent().parent().children("td:first-child").children("input").val();
    confirmBoxShow(function () {
        delDraft(draftId,delDraftDone);
    },"确定删除吗？");
});


/**
 * 删除多篇文章
 */
$(".removeMany").click(function () {
    var inputs = $(".draft-list tbody tr td:first-child input");
    var draftIds = [];
    for(var i=0;i<inputs.length;i++){
        var input = $(inputs[i]);
        if(input.prop("checked")) {
            draftIds.push(parseInt(input.val()));
        }
    }
    if(draftIds.length===0){
        noticeBoxShow("请选中要删除的文章");
    }else{
        confirmBoxShow(function () {
            delDrafts(draftIds,delDraftsDone);
        },"确定删除吗？")
    }
});


