$(function () {
    //获取文章分类
    getCustomTypes(getCustomTypesDone)
    //初始化编辑器
    initEditor();
    autoSaveDraft();
});

//新建草稿
$(".draftBtn").click(function () {
    var draftId = $(".draftId").val();
    if(draftId===""){
        //此时是新建草稿
        saveDraft();
    }else{
        //此时是修改草稿
        updateDraft();
    }
});

//发布文章
$(".publishBtn").click(function () {
    pubArticle(getArticle());
});





