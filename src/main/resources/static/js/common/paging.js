(function($, window, document, undefined) {
    //分页类
    function Paging(element,options) {
        this.element = element;
        this.options ={
            //当前页
            pageNum:options.pageNum,
            //总页数
            pages:options.pages,
            //数据库中总共的数据条数
            total:options.total,
            callback:options.callback
        };
        this.init();
    }

    Paging.prototype = {
        //构造函数
        constructor:Paging,
        init:function () {
            this.creatHtml();
            this.bindEvent();
        },
        creatHtml:function () {
            //当前元素,这里时dom元素,不是jquery元素,me表示Paging的实例
            var me = this;
            var html = "<ul>";
            var pageNum = me.options.pageNum;
            var pages = me.options.pages;
            var total = me.options.total;
            //导航显示的数字个数,不包含第一页和最后一页
            var navigate = 3;
            if(pages===0||pages===1){
                //如果没有数据,或者只有一页数据,则不显示ul
                return;
            }
            //如果当前页不是1,则有上一页
            if(pageNum!==1){
                html += '<li><a id="prePage">上一页</a></li>'
            }
            if(pageNum===1){
                html += '<li class="active"><a>1</a></li>';
            }else{
                html += '<li><a>1</a></li>';
            }
            if(pages>navigate+2){
                //当前页数在左中间,则...在右边
                if(pageNum<Math.floor(navigate/2+3)){
                    for(var i=2;i<=navigate+1;i++){
                        if(pageNum===i){
                            html += '<li class="active"><a>'+i+'</a></li>';
                        }else{
                            html += '<li><a>'+i+'</a></li>';
                        }
                    }
                    html +='...';
                    //当前页在中间
                }else if(pageNum<pages-Math.floor(navigate/2)){

                    if(pageNum-Math.floor(navigate/2)!==2){
                        html += '...';
                    }
                    for(var i=pageNum-Math.floor(navigate/2);i<=pageNum+Math.floor(navigate/2);i++){
                            if(pageNum===i){
                                html += '<li class="active"><a>'+i+'</a></li>';
                            }else{
                                html += '<li><a>'+i+'</a></li>';
                            }

                    }
                    if(pageNum+Math.floor(navigate/2)!==pages-1){
                        html += "...";
                    }

                    //当前页在右边,...在左边
                }else{
                    if(pages-navigate!==2){
                        html += "...";
                    }
                    for(var i=pages-navigate;i<pages;i++){
                            if(pageNum===i){
                                html += '<li class="active"><a>'+i+'</a></li>';
                            }else{
                                html += '<li><a>'+i+'</a></li>';
                            }
                    }
                }
            }else{
                for(var i=2;i<pages;i++){
                    if(i===pageNum){
                        // html += '<li><a>'+i+'</a></li>';
                        html += '<li class="active"><a>'+i+'</a></li>';
                    }else {
                        html += '<li><a>' + i + '</a></li>';
                    }
                }
            }
            if(pageNum===pages){
                html += '<li class="active"><a>'+pages+'</a></li>';
            }else{
                html += '<li><a>'+pages+'</a></li>';
            }
            //如果当前页不是最后一页,则有下一页
            if(pageNum!==pages){
                html += '<li><a id="nextPage">下一页</a></li>'
            }
            html += "</ul>";
            me.element.html(html);
        },
        bindEvent:function () {
            var me = this;
            me.element.off('click','a');
            me.element.on('click','a',function () {
                var currentPage = parseInt($(this).html());
                var id=$(this).attr("id");
                if(id==="prePage"){
                    me.options.pageNum -=1;
                }else if(id==="nextPage"){
                    me.options.pageNum +=1;
                }else{
                    me.options.pageNum = currentPage;

                }
                me.creatHtml();
                if(me.options.callback){
                    me.options.callback(me.options.pageNum);
                }
            });
        }
    };
    //通过jQuery对象初始化分页对象
    $.fn.paging = function(options) {
        return new Paging($(this),options);
    }
})(jQuery,window,document);


// $(function () {
//     $("#pagination").paging({
//         pageNum:1,
//         pages:20
//     });
// });
