/**
 * 获取文章分类列表
 */
function getCustomTypes(callback) {
    $.ajax({
        type:'get',
        url:contextPath+'/getAllCustomTypes',
        data:{},
        success:function (data) {
            if(callback){
                callback(data);
            }
        },
        error:function () {
            console.log("连接服务器失败");
        }
    })
}


/**
 * 删除一个文章分类
 */
function delCustomType(customTypeId,callback) {
    $.ajax({
        type:'post',
        url:contextPath+"/delCustomType",
        data:{
            id:customTypeId
        },
        success:function (data) {
            if(data.code===0){
                if(callback){
                    data.data = customTypeId;
                    callback(data);
                }
            }
        },
        error:function () {
            alert("删除文章分类失败")
        }
    });
}

