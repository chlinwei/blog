//显示异常信息
function doMsg(obj,text,flag) {
    if(flag){
        //显示
        obj.text(text);
        obj.show();
    }else{
        obj.hide();
    }
}



function errorMsgShow(obj,text) {
    obj.text(text);
    obj.show();
}


//文件大小检查
/**true:表示文件大小合适,false,表示文件大小不合适
 * @param maxFileSize:表示单个文件最大值
 * @param allFileSize:一次上传得所有文件大小最大值
 * @param formData:FormDate类型表单数据
 */
function fileSizeCheck(maxFileSize,allFileSize,formData) {
    var values = formData.entries();
    var totalSize = 0;
    while(true){
        var item = values.next();
        if(item.done){
            break;
        }
        //array是数组,包含两个元素key和value
        var array = item.value;
        if(array[1] instanceof File){
            //如果是文件,则判断文件大小
            var size = array[1].size;
            if(size>maxFileSize){
                return false;
            }
            totalSize += size;
            if(totalSize>allFileSize){
                return false;
            }
        }
    }
    return true;
}


/**
 * @param email:邮箱字符串
 * @returns boolean
 */
function  emailCheck(email){
    var pattern_mail= /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
    return pattern_mail.test(email);
}


/**
 * @param phone:手机号码字符串
 * @returns boolean
 */
function phoneCheck(phone) {
    var pattern_phone = /^1[34578]\d{9}$/;
    return pattern_phone.test(phone);
}

/**
 * 用户名检查
 */
function userNameCheck(userName) {
    var min = 4;
    var max = 20;
    //正常字符
    var pattern = /^(\w|[\u4E00-\u9FA5])+$/;
    var obj = {flag:true,msg:"用户名正确"};
    var length = userName.length;
    if(userName===""){
        obj.msg = "用户名不为空";
        obj.flag = false;
    }else if(!pattern.test(userName)){
        obj.msg="用户名不能使用特殊字符";
        obj.flag = false;
    }else if(length<min){
        obj.msg="用户名不能小于4个字符";
        obj.flag = false;
    }else if(length>max){
        obj.msg="用户名不能大于20个字符";
        obj.flag = false;
    }
    return obj;
}


/**
 * 性别检查
 */
function genderCheck(gender) {
    if(gender==="男"||gender==="女"){
        return true;
    }else{
        return false;
    }
}

/**
 * 密码检查
 */

function passwdCheck(passwd) {
    var obj = {
        flag:true,
        text:"密码格式正确"
    };
    //非空白字符
    var pattern = /^[^\s]*$/;
    var min = 6;
    var max = 20;
    var length = passwd.length;
    if(!pattern.test(passwd)){
        obj.flag = false;
        obj.text = "密码不能含有空白字符";
    }else if(length<min){
        obj.flag = false;
        obj.text = "密码长度不能小于"+min+"个字符";
    }else if(length>max){
        obj.flag = false;
        obj.text = "密码长度不能大于"+max+"个字符";
    }
    return obj;
}

//连接服务器失败后将body里的信息改为提示信息
//场景:用于访问一个页面,页面通过ajax获取数据,连接失败时,来调用此函数
function serverError () {
    $("body").empty();
    $("body").text("服务器出错");
}



//下拉列表,主要时给drop_head添加on
$(".drop_head").hover(function () {
    $(this).addClass("on");
},function (e) {

    if(!$(this).next().is(":hover")){
        $(this).removeClass("on");
    }
});

$(".drop_down").hover(null,function () {
    var drop_head = $(this).prev();
    if(!drop_head.is(":hover")){
        drop_head.removeClass("on");
    }
});

/**
 * 获取get方式的请求参数
 */

(function ($) {
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return decodeURI (r[2]); return null;
    }
})(jQuery);


/**
 * 点赞和取消点赞
 */
function doLike(typeId,ownerId,type,callback) {
    $.ajax({
        type:'post',
        url:contextPath+"/doLike",
        data:{
            typeId:typeId,
            ownerId:ownerId,
            type:type
        },
        success:function (data) {
            data.typeId = typeId;
            if(callback){
                callback(data);
            }
        },
        error:function () {
            alert("点赞失败！");
        }
    })
}

function undoLike(typeId,type,callback) {
    $.ajax({
        type:'post',
        url:contextPath+"/undoLike",
        data:{
            typeId:typeId,
            type:type
        },
        success:function (data) {
            data.typeId = typeId;
            if(callback){
                callback(data);
            }
        },
        error:function () {
            alert("取消点赞失败");
        }
    })
}


/**
 * @param articleId:文章id
 */
function delArticle(articleId,callback) {
    //确定提示框框出现,点击确定执行删除任务,删除成功后跳转
    $.ajax({
        type:'post',
        url:contextPath+"/delArticle",
        data: {
            id: articleId
        },
        success:function (data) {
            data.articleId = articleId;
            if(callback){
                callback(data);
            }
        },
        error:function (data) {
            alert("删除失败");
        }
    });
}


