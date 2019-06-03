
$("#userName").bind('input',function () {
    var obj = $("#userNameMsg");
    var value=$(this).val();
    doMsg(obj,"用户名不能为空",value==="");
});

$("#passwd").bind('input',function () {
    var obj = $("#passwdMsg");
    var value=$(this).val();
    doMsg(obj,"密码不能为空",value==="")
});


$("#submit").click(function () {
    var userName = $("#userName");
    var passwd = $("#passwd");
    var userNameMsg = $("#userNameMsg");
    var passwdMsg = $("#passwdMsg");
    doMsg(userNameMsg,"账号不能为空",userName.val()==="");
    doMsg(passwdMsg,"密码不能为空",passwd.val()==="");
    if(userNameMsg.css("display")==='none'&&passwdMsg.css("display")==='none'){
        return true;
    }
    return false;
});

