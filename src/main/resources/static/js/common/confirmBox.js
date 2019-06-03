$(".cancelBtn").click(function () {
    var confirmBtn = $($(".confirmBox .confirmBtn")[0]);
    confirmBtn.off("click");
    $(".box").fadeOut(500);
});

$(".confirmBox img").click(function () {
    var confirmBtn = $($(".confirmBox .confirmBtn")[0]);
    confirmBtn.off("click");
    $(".box").fadeOut(500);
});

//工具函数
function showErrorMsg(msg) {
    //hide是异步函数
    $(".msgBox").hide(function () {
        alert(msg);
    });
}

/**
 * 点击确定按钮就会执行f函数
 */

function confirmBoxShow(f,text) {
    console.log("哈哈哈");
    $(".confirmAndCancelBox .confirmBox .msg").text(text);
    $(".confirmAndCancelBox").fadeIn(500);
    $(".confirmAndCancelBox .confirmBtn").off("click");
    $(".confirmAndCancelBox .confirmBtn").click(function () {
        f();
        $(".confirmAndCancelBox").hide();
    });
}

function noticeBoxShow(text,time) {
    if(!time){
        time = 2000;
    }
    $(".noticeBox .confirmBox .msg").text(text);
    $(".noticeBox").show();
    setTimeout(function () {
        $(".noticeBox").hide()
    },time);
}

