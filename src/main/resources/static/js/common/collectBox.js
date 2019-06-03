//之所以这里不用模板,而要用js动态创建出来,原因就是这个界面过于复杂,用模板无法初始化
/**
 * 右上角叉叉点击事件
 **/
$("body").on("click",".collectBox .title img",function () {
    $(".collectBox").detach();
});
/**
 * input绑定事件
 */
$("body").on("click",".collectBox li input",function () {
    //1.数字变动
    var newResult = $(this).prop("checked");
    var count = $(this).parent().children(".count");
    var num = parseInt(count.text().split("/"));
    if(newResult){
        //数字减一
        count.text(num+1+"/500");
    }else{
        //数字加1
        count.text(num-1+"/500");
    }
    //2.确定按钮
    initConfirmBtn();
});


function initConfirmBtn() {
    $(".collectBox .confirmBtn").show();
    var inputs = $(".collectBox ul li input");
    var confirmBtn = $(".collectBox .confirmBtn");
    if(inputs.length===0){
        return;
    }
    for(var i=0;i<inputs.length;i++){
        var oldResult = $(inputs[i]).attr("checked");
        var newResult = $(inputs[i]).prop("checked");
        if(newResult===true){
            newResult = "checked";
        }else{
            newResult = undefined;
        }
        if(oldResult!==newResult){
            //不相等表示变化了
            confirmBtn.removeClass("disable");
            confirmBtn.removeAttr("disabled");
            return;
        }
    }
    confirmBtn.addClass("disable");
    confirmBtn.attr("disabled","disabled");
}


/**
 * 给确定按钮绑定事件
 */
$("body").on("click",".collectBox .confirmBtn",function () {
    //flag:用来判断/article/1页面收藏夹是否显示已经收藏
    var flag = false;
    var collectIds = [];
    var inputs = $(".collectBox ul li input");
    if(inputs.length===0){
        return;
    }
    for(var i=0;i<inputs.length;i++){
        var oldResult = $(inputs[i]).attr("checked");
        var newResult = $(inputs[i]).prop("checked");
        if(newResult===true){
            flag = true;
            newResult = "checked";
        }else{
            newResult = undefined;
        }
        if(oldResult!==newResult){
            collectIds.push(parseInt($(inputs[i]).attr("data-id")));
        }
    }
    $.ajax({
        type:'post',
        url:contextPath+"/collectArticle",
        data:{
            articleId:articleId,
            authorId:parseInt($("#userName").attr("data-id")),
            collectIds:collectIds
        },
        success:function (data) {
            if(data.code===0){
                $(".collectBox").fadeOut(500);
                if(flag){
                    $(".collectBtn").addClass("on");
                }else{
                    $(".collectBtn").removeClass("on");
                }
            }else if(data.code===403){
                window.location.replace(contextPath+"/login");
            }
        },
        error:function () {
            alert("收藏失败!");
        }
    })

});
/**
 * 点击某个按钮,就会弹出collectBox框框
 */
function collectBoxShow(articleId) {
    var collectBox = "<div class=\"collectBox\">\n" +
        "    <div class=\"mask\">\n" +
        "    </div>\n" +
        "    <div class=\"content_wrapper\">\n" +
        "        <div class=\"title\">\n" +
        "            添加到收藏夹\n" +
        "            <img class='cross' style=\"float: right;\">\n" +
        "            <div style=\"clear: both;\"></div>\n" +
        "        </div>\n" +
        "        <div class=\"content\">\n" +
        "            <div class=\"group-list\">\n" +
        "                <ul>\n" +
        "                    <div class=\"collection-mask\"></div>\n" +
        "                </ul>\n" +
        "                <div class=\"add-group\">\n" +
        "                    <div class=\"errorMsg\"></div>\n" +
        "                    <div class=\"addBtn\">\n" +
        "                        <img>\n" +
        "                        新建收藏夹\n" +
        "                    </div>\n" +
        "                    <div class=\"newBtn_wrapper\">\n" +
        "                        <input class=\"name\" type=\"text\" maxlength=\"20\" placeholder=\"最多可输入20个\" autofocus=\"autofocus\">\n" +
        "                        <button class=\"newBtn\">新建</button>\n" +
        "                    </div>\n" +
        "                </div>\n" +
        "            </div>\n" +
        "        </div>\n" +
        "        <div class=\"bottom\">\n" +
        "            <button class=\"confirmBtn disable\" disabled=\"disabled\">确定</button>\n" +
        "        </div>\n" +
        "    </div>\n" +
        "</div>\n" +
        "</div>";
    $("body").remove(".collectBox");
    $("body").append(collectBox);

    //获取收藏夹列表
    getCollects(articleId,function (data) {
        console.log(data);
        if(data.code===0){
            var ul = $(".collectBox .content .group-list ul");
            var list = data.data;
            if(list.length===0){
                return;
            }else{
                var html = "";
                for(var i=0;i<list.length;i++){
                    html += "<li>";
                    html += "<label>";
                    //判断是否这个收藏夹已经包含了这篇文章
                    if(list[i].isCollected===1){
                        html += "<input type='checkbox' data-id="+list[i].id+" checked='checked'><i></i>";
                    }else{
                        html += "<input type='checkbox' data-id="+list[i].id+"><i></i>";
                    }
                    html += "<span class='name'>"+list[i].name+"</span>";
                    html += "<span class='count'>"+list[i].num+"/500</span>";
                    html += "<div style='clear: both'></div>";
                    html +="</label></li>";
                }
                ul.append(html);
            }

        }else if(data.code===403){
            window.location.replace(contextPath+"/login");
        }else{
            alert("获取收藏失败");
        }
    });
    //获取所有收藏夹
}


/**
 * 获取所有的收藏夹
 */
function getCollects(articleId,callback) {
    $.ajax({
        url:contextPath + '/getCollects',
        type:'get',
        data:{
            articleId:articleId
        },
        success:function (data) {
            if(callback){
                callback(data)
            }
        },
        error:function () {
            alert("获取收藏夹失败");
        }
    })
}




/**
 * 执行收藏的操作
 */

function collectArticle(articleId,collectId) {
    $.ajax({
        type:'post',
        url:contextPath+"/collectArticle",
        data:{
            articleId:articleId,
            collectId:collectId
        },
        success:function (data) {
            console.log(data);
        },
        error:function () {
            alert("收藏失败!");
        }
    })
}

/**
 * 给新建收藏夹绑定事件
 * 用户最多创建100个收藏夹
 */
$("body").on("click",".collectBox .addBtn",function () {
    if($(".collectBox ul li").length>100){
        $(".collectBox .errorMsg").text("最多创建100个收藏夹");
        $(".collectBox .errorMsg").show();
        setTimeout(function () {
            $(".errorMsg").hide()
        },2000);
        return;
    }
    // $("#addBtn").hide();
    console.log($(".collectBox .addBtn").css("display"));
    $(".collectBox .addBtn").hide();
    console.log($(".collectBox .addBtn").css("display"));
    // $(this).hide();
    $(".collectBox .collection-mask").show();
    $(".collectBox .confirmBtn").hide();
    $(".collectBox .newBtn_wrapper").show(0,function () {
        //此时newBtn_wrapper已经display了
        $(document).bind("click",hiddenNewBtnWrapper);
    });
});

/**
 *监听点击事件,如果点击时,鼠标不在.newBtn_wrapper区域里,则newBtn_wrapper hide()
 */
function hiddenNewBtnWrapper(e) {
    if($(e.target).hasClass("newBtn_wrapper")){
        return;
    }else if($(e.target).parents(".newBtn_wrapper").length===1){
        return;
    }else if($(e.target).hasClass("addBtn")){
        return;
    }else{
        initAddBtn();
    }
}
function initAddBtn() {
    $(document).unbind("click",hiddenNewBtnWrapper);
    $(".collectBox .newBtn_wrapper input").val("");
    $(".collectBox .collection-mask").hide();
    $(".collectBox .newBtn_wrapper").hide();
    $(".collectBox .addBtn").show();
    initConfirmBtn();
}

/**
 * 给新建按钮绑定事件,收藏夹最多100个
 */

$("body").on("click",".collectBox .newBtn",function () {
    var name = $(".collectBox .newBtn_wrapper input").val().trim();
    if(name===""){
        $(".errorMsg").text("请填写收藏夹名称");
        $(".errorMsg").show();
        setTimeout(function () {
            $(".errorMsg").hide()
        },2000);
    }else{
        createCollect(name);
    }
});

/**
 * 新建一个收藏夹
 */
function createCollect(name,brief) {
    $.ajax({
        type:'post',
        url:contextPath+"/createCollect",
        data:{
            name:name,
            brief:brief
        },
        success:function (data) {
            if(data.code===0){
                var ul = $(".collectBox .content ul");
                var html  = "<li>";
                html += "<label>";
                html += "<input type='checkbox' data-id="+data.data.id+"><i></i>";
                html += "<span class='name'>"+name+"</span>";
                html += "<span class='count'>1/500</span>";
                html += "<div style='clear: both'></div>";
                html +="</label></li>";
                ul.append(html);
                $(".collectBox .content li input[data-id="+data.data.id+"]").prop("checked",true);
                initAddBtn();
            }else if(data.code===403){
                window.location.replace(contextPath+"/login");
            }
        },
        error:function () {
            alert("创建收藏夹失败");
        }
    })
}


