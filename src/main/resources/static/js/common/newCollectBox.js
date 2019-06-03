/**
 * 右上角叉叉
 */
$(".newCollectBox .cross").click(function () {
    $(".newCollectBox").hide();
    $(".newCollectBox .name").val("");
    $(".newCollectBox .brief").val("");
});


/**
 * 给提交按钮绑定事件
 */
// $(".newCollectBox .sub").click(function () {
//
// });
function checkCollectParams(collect) {
    var name = collect.name;
    if(name===""){
        $(".name_errorMsg").text("请填写收藏夹名称");
        $(".name_errorMsg").show();
        setTimeout(function () {
            $(".name_errorMsg").hide();
        },2000);
        return false;
    }
    return true;
}


function createOrUpdateCollect(collect,callback) {
    if(checkCollectParams(collect)){
        newCollect(collect,callback);
    }
}
/**
 * 新建一个收藏夹
 */
function newCollect(collect,callback) {
    $.ajax({
        url:contextPath+"/createOrUpdateCollect",
        type:'post',
        data:{
            id:collect.id,
            name:collect.name,
            brief:collect.brief
        },
        success:function (data) {
            if(callback){
                callback(data);
            }
        },
        error:function () {
            alert("新建收藏夹失败！");
        }
    })
}

