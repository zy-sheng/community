/*评论响应＋异常处理*/
/*通过JSON调用页面的值*/
function post() {
    var question_id = $("#question_id").val();
    var content = $("#content").val();
    comment2target(question_id, 1, content);
}


function comment2target(target, type, content) {
    if (!content) {
        alert("~你输入的内容为空，请重新输入~");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        /*data返回一个JSON , 在向服务器发送数据时一般是字符串，其中stringify将JavaScript对象转化为字符串*/
        data: JSON.stringify({
            "parentId": target,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();

            } else {
                if (response.code == 2003) {//未登录
                    var isAccept = confirm(response.message);
                    if (isAccept) {
                        window.open("https://github.com/login/oauth/authorize?client_id=0cca6fb0b65d47fd9559&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();
    comment2target(commentId, 2, content);
}

/*二级评论*/
function CollapseComment(e) {
    var id = e.getAttribute("data-id");
    //打印css元素
    var comments = $("#comment-" + id);
    //获取展开状态
    var collapse = e.getAttribute("data-collapse");

    //折叠二级评论
    if (collapse) {
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        /*使用js删除和添加类名*/
        e.classList.remove("active");
    } else {
             var subCommentContainer = $("#comment-"+id);
             if(subCommentContainer.children().length != 1){
                 //展开二级评论 其中.addClass()向被选元素添加一个类获多个类comments
                 comments.addClass("in");
                 e.setAttribute("data-collapse", "in");
                 e.classList.add("active");
             }
             else{
                 $.getJSON( "/comment/"+id, function( data ) {
                     console.log(data);
                     $.each( data.data.reverse(), function( index , comment ) {


                         //这里使用到ctrl+alt将变量移到内部
                         var mediaLeftElement = $("<div/>",{
                             "class" : "media-left"
                         }).append($("<img/>", {
                             "class": "media-object img-rounded",
                             "src": comment.user.avatarUrl
                         }));


                         //评论名
                         var mediaBodyElemenet = $("<div/>",{
                             "class" : "media-body"
                         }).append($("<h6/>", {
                             "class": "media-heading",
                             "html": comment.user.name
                         })).append($("<div/>", {
                             "html":comment.content
                         })).append($("<div/>", {
                             "class" : "comment-st"
                         }).append($("<span/>", {
                             //时间
                             "class" : "pull-right",
                             "html" : moment(comment.gmtCreate).format('YYYY-MM-DD')
                         })));

                         var mediaElement = $("<div/>",{
                             "class" : "media"
                         }).append(mediaLeftElement)
                             .append(mediaBodyElemenet);

                         var commentElement = $("<div/>",{
                             "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                             //在commentElement下加入mediaElement
                         }).append(mediaElement);


                         subCommentContainer.prepend(commentElement);
                     });
                     //展开二级评论 其中.addClass()向被选元素添加一个类获多个类comments
                     comments.addClass("in");
                     e.setAttribute("data-collapse", "in");
                     e.classList.add("active");
                 });

             }

    }
}