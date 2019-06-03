$("#register").click(function () {
    $.ajax({
        url:contextPath+"/doRegister",
        type:'post',
        data:{
            userName:$("#userName").val(),
            passwd:$("#passwd").val(),
            gender:$("input[type='radio']:checked").val()
        },
        success:function (data) {
            if(data.code===0){
                window.location.replace(contextPath+"/login");
            }else{
                alert(data.msg);
            }
        },
        error:function () {
            alert("服务器出错");
        }

    });
    return false;
});

$("#userName").bind("input",function () {
    //正常字符的正则表达式
    var pattern = /^(\w|[\u4E00-\u9FA5])+$/;
    var obj = $("#userNameErrorMsg");
    var value=$(this).val();
    var text = "" ;
    // doMsg(obj,"用户名不能为空",value==="");
    if(value===""){
        text="用户名不为空";
    }else if(!pattern.test(value)){
        text="用户名不能使用特殊字符";
    }else if(value.length<4){
        text="用户名不能小于4个字符";
    }else if(value.length>20){
        text="用户名不能大于20个字符";
    }
    else{
        obj.hide();
        return;
    }
    errorMsgShow(obj,text);
});

$("#passwd").bind('input',function () {
    var obj = $("#passwdErrorMsg");
    var value=$(this).val();
    var text = "" ;
    var pattern = /\s+/;
    if(pattern.test(value)){
        text="密码不能含有任何空白字符";
    }else if(value.length<6){
        text="密码长度不能小于6个字符";
    }else if(value.length>20){
        text="密码长度不能大于20个字符";
    }
    else{
        obj.hide();
        return;
    }
    errorMsgShow(obj,text);
});

