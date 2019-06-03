$(function () {
    /**
     * 获取文章分类
     */
    getCustomTypes(function (data) {
        var tbody = $(".content tbody");
        if(data.code===0){
            tbody.empty();
            var list = data.data;
            var html = "";
            var msg ="确定删除？";
            if(list.length===0){
                html +="<tr><td colspan='2'>暂无数据</td></tr>"
                tbody.append(html);
                return;
            }
            for(var i=0;i<list.length;i++){
                html += "<tr>";
                html += "<td><input data-id="+list[i].id+" class='name' type='text' value="+list[i].name+" ></td>";
                html += "<td><p data-id="+list[i].id+" onclick='confirmBoxShow(function() {delCustomType("+list[i].id+",function(data) {delCustomTypeDone(data)  })},\" "+msg+"\")'></p></td>";
            }
            tbody.append(html);

        }else if(data.code===403){
            window.location.replace(contextPath+"/login");
        }else{
            alert("获取文章分类数据失败");
        }
    });

    $(".addBtn").click(function () {
        addCustomType();
    });
    $(".saveBtn").click(function () {
        updateCustomTypes(getAllCustomType(),function (data) {
            if(data.code===0){
                window.location.reload();
            }else if(data.code===403){
                window.location.replace(contextPath+"/login");
            }else{
                alert("保存失败！");
            }
        });
    });
});


/**
 * 删除完成后的工作
 */
function delCustomTypeDone(data) {
    if(data.code===0){
        var tr = $(".content tbody p[data-id="+data.data+"]").parent().parent();
        tr.detach();
    }else if(data.code===403){
        window.location.replace(contextPath+"/login");
    }else{
        alert("删除文章失败");
    }
}


/**
 * 添加文章分类,最多添加20个
 */
function addCustomType() {
    var tbody = $(".content tbody");
    var trs = tbody.find("tr");
    if(trs.length>=20){
        noticeBoxShow("您最多可添加20个分类");
        return;
    }
    var html = "<tr><td><input data-id='' class='name' type='text' placeholder='请输入分类'></td><td><p onclick='delCustomTypeBeforeSaved($(this).parent().parent())' data-id=''></p></td></tr>"
    tbody.append(html);
}

/**
 *删除没有保存之前的按钮
 */
function delCustomTypeBeforeSaved(ele) {
    ele.detach();
}


/**
 * 获取所有的文章分类
 */
function getAllCustomType() {
    var inputs = $(".content table input");
    if(inputs.length===0){
        noticeBoxShow("请先添加分类,再保存");
        return;
    }
    var arr = [];
    //检查是否合适
    for(var i=0;i<inputs.length;i++){
        var input = $(inputs[i]);
        var id=0;
        var name = input.val().trim();
        if(name===""){
            noticeBoxShow("分类名称不能为空");
            return;
        }
        if(input.attr("data-id")!==""){
            id = parseInt(input.attr("data-id"));
        }
        var obj = {
            id:id,
            name:name
        };
        arr.push(obj);
    }
    return arr;
}

/**
 保存所有文章分类
**/
function updateCustomTypes(arr,callback) {
    if(arr===undefined){
        return;
    }
    $.ajax({
        type:'post',
        url:contextPath+"/updateCustomTypes",
        data:JSON.stringify(arr),
        dataType:"json", //必填
        contentType:"application/json", //必填
        success:function (data) {
            if(callback){
                callback(data);
            }
        },
        error:function () {
            alert("保存失败！");
        }
    })
}

