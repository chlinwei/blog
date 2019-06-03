function getAlllinks() {
    $.ajax({
        type:'get',
        url:contextPath+"/getAllFriendlinks",
        data:{},
        success:function (data) {
            if(data.code===0){
                var list = data.data;
                var content = $(".friendlinks .content-list");
                if(list.length===0){
                    content.text("暂无数据");
                }else{
                    var html = "";
                    for(var i=0;i<list.length;i++){
                        html += "<div class='item'>";
                        html += "<a target='_blank' href="+list[i].url+" >"+list[i].name+"</a></div>";

                    }
                    html += "<div class='clear'></div>";
                    content.append(html);
                }
            }else{
                alert("获取友情链接失败!");
            }
        },
        error:function () {
            alert("获取友情链接失败!");
        }
    })
}

$(function () {
    //获取友情链接
    getAlllinks();
});
