//修改用户密码
function updatePasswd() {
    $("#settingSaveBtn").click(function () {
        if($("#passwd").val()===""){
            errorMsgShow($(".passwdErrorBox"),"密码不能为空");
            return;
        }

        var flag = true;
        $(".main-right_setting .errorBox").each(function (i,ele) {
            if($(ele).css("display")!=="none"){
                flag = false;
                //跳出循环
                return;
            }
        });
        if(!flag){
            return;
        }
        $.ajax({
            type:'post',
            url:contextPath+"/updatePasswd",
            data:{
                newPasswd:$("#passwd").val()
            },
            success:function (data) {
                if(data.code===0) {
                    alert("密码更改,需要重新登录生效");
                    window.location.replace(contextPath + "/login");
                }else{
                    alert("更改密码失败");
                }
            },
            error:function () {
                alert("更改密码失败");
            }
        })
    });
}


/**
 * 修改密码时错误信息提示
 */

$("#userName").bind("input",function () {
    var userName = $("#userName").val();
    var userNameResult = userNameCheck(userName);
    var errorBox = $(".userNameErrorBox");
    if(!userNameResult.flag){
        errorMsgShow(errorBox,userNameResult.msg);
    }else{
        errorBox.hide();
    }
});

$("#phone").bind("input",function () {
    var phone = $("#phone").val();
    var flag = phoneCheck(phone);
    var errorBox = $(".phoneErrorBox");
    if(!flag){
        errorMsgShow(errorBox,"手机号格式不正确");
    }else{
        errorBox.hide();
    }
});

$("#email").bind("input",function () {
    var email = $("#email").val();
    var flag = emailCheck(email);
    var errorBox = $(".emailErrorBox");
    if(!flag){
        errorMsgShow(errorBox,"邮箱格式不正确");
    }else{
        errorBox.hide();
    }
});


$("#passwd").bind("input",function () {
    var passwd = $("#passwd").val();
    var obj = passwdCheck(passwd)
    var errorBox = $(".passwdErrorBox");
    if(!obj.flag){
        errorMsgShow(errorBox,obj.text);
    }else{
        errorBox.hide();
    }

    var repasswd = $("#repasswd").val();
    var errorBox1 = $(".repasswdErrorBox");
    if(repasswd!==passwd){
        errorMsgShow(errorBox1,"两次输入的密码不一致");
    }else{
        errorBox1.hide();
    }
});


$("#repasswd").bind("input",function () {
    var passwd = $("#passwd").val();
    var repasswd = $("#repasswd").val();
    var errorBox = $(".repasswdErrorBox");
    if(passwd!==repasswd){
        errorMsgShow(errorBox,"两次输入的密码不一致");
    }else{
        errorBox.hide();
    }

});
$(function () {
    updatePasswd();
});

