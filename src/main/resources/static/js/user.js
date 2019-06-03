//获取个人信息
function getUserInfo() {
    $.ajax({
        type:'get',
        url:contextPath+'/getUserInfo',
        data:{
        },
        success:function (data) {
            if(data.code===0){
                var result = data.data;
                $("#userName").val(result.userName);
                $("#phone").val(result.phone);
                $("#birthday").val(result.birthday);
                $("#email").val(result.email);
                if(result.gender==="男"){
                    $("#boy").attr("checked",true);
                    $("#girl").attr("checked",false);
                }else{
                    $("#girl").attr("checked",true);
                    $("#boy").attr("checked",false);
                }
                $("#personalBrief").val(result.personalBrief);
            }else{
                console.log(data.msg);
            }
        },
        error:function () {
            console.log("连接服务器失败");
        }
    });
}


//保存个人资料
$("#saveBtn").click(function () {

   //1.检查是否有格式错误信息
    var flag = true;
    $(".userInfo .errorBox").each(function (i,ele) {
        if($(ele).css("display")!=="none"){
            flag = false;
            //跳出循环
            return;
        }
    });
    if(!flag){
        return;
    }
    var usreName = $("#userName").val();
    var phone = $("#phone").val();
    var email = $("#email").val();
    var gender =$("input[type='radio']:checked").val();
    $.ajax({
        type:'POST',
        url:contextPath+"/saveUserInfo",
        data:{
            userName:usreName,
            phone:phone,
            birthday:$("#birthday").val(),
            email:email,
            personalBrief:$("#personalBrief").val().trim(),
            gender:gender
        },
        success:function (data) {
            if(data.code===0){
                console.log("个人信息保存成功");
            }else if(data.code===201){
                alert("个人信息保存成功,重新登录生效");
                window.location.replace(contextPath+"/login");
            }else if(data.code === 403){
                window.location.reload();
            }
            else{
                console.log(data);
            }
        },
        error:function () {
            console.log('连接服务器失败');
        }
    })
});


//更改头像
function changeAvatar ($img) {
    $("#avatar").change(function () {
        var file = $(this)[0].files[0];
        var fileReader =  new FileReader();
        fileReader.readAsDataURL(file);
        var formData = new FormData();
        formData.append('file',file);

        //检查文件大小
        var maxFileSize = 1024*700;
        var allFileSize = maxFileSize*10;
        var flag = fileSizeCheck(maxFileSize,allFileSize,formData);
        if(!flag){
            //不合适
            console.log("文件过大");
            return;
        }
        fileReader.onload=function () {
            $.ajax({
                type:'post',
                url:contextPath+'/uploadAvatar',
                data:formData,
                contentType:false, //不能省略
                processData: false, //不能省略
                // dataType: "json",  //可以省略
                success:function (data) {
                    if(data.code===0){
                        $("#avatarImg").attr("src",data.data);
                        //顶部的头像也要改变
                        $("#headerImg").attr("src",data.data);

                    }else{
                        console.log(data);
                    }
                },
                error:function () {
                    console.log("连接服务器失败");
                }
            })
            // $img.attr("src",(this.result));
        };
    });
}



// function nav_bind() {
//     $(".personal_nav").click(function () {
//         $(".main-right").children().hide();
//         $(".main-right_userInfo").show();
//     });
//     $(".setting_nav").click(function () {
//         $(".main-right").children().hide();
//         $(".main-right_setting").show();
//
//     });
//     $(".comment_nav").click(function () {
//         $(".main-right").children().hide();
//     });
//     $(".blogManage_nav").click(function () {
//         $(".main-right").children().hide();
//     });
// }
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
                    console.log(data);
                }
            },
            error:function () {
                console.log(data)
            }
        })
    });
}

updatePasswd();
changeAvatar($("#avatarImg"));
getUserInfo();

/**
 * 错误信息提示
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


