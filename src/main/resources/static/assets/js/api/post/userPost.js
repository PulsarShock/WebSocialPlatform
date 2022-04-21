let auth_params={
    'user_email': sessionStorage.getItem("user_email"),
    'user_token': sessionStorage.getItem("user_token")
}

function get_all_posts(user_email) {
    post("/api/posts_and_comments/get_posts_list",user_email,function (resp) {
        let response=JSON.parse(resp)
        let posts_list=response['data']
        if(posts_list===null){
            window.alert("拉取失败，您或您的朋友还没发过动态哦~")
            return;
        }
        for (const id in posts_list) {
            let post_str=fill_in_post(get_single_post(id))
            $("#space>a").before($(post_str))
        }
        do_delete_it($("#wait_for_load"))
    },auth_params)
}

function get_single_post(identity) {
    let response=null
    post("/api/posts_and_comments/get_single_post", {'identity':identity,'user_name':sessionStorage.getItem("user_email")},function (resp) {
        response=JSON.parse(resp)
    },auth_params)
    return response['data'];//obj
}

function fill_in_post(post_data) {//传参前记得转为obj
    return "<div class=\"post\" isclosed=\""+post_data['comments_closed']+"\" style=\"width:150%\" id=\"" + post_data['identity'] + "\">\n" +
        "                            <div class=\"post-heading\">\n" +
        "                                <div class=\"post-avature\">\n" +
        "                                    <img src=\"assets/images/avatars/" + post_data['user_email'] + ".png\" alt=\"?\" id=\"avatar\">\n" +
        "                                </div>\n" +
        "                                <div class=\"post-title\">\n" +
        "                                    <a href=\"#\">\n" +
        "                                        <h4 id=\"name\">" + post_data['user_name'] + "</h4>\n" +
        "                                    </a>\n" +
        "                                </div>\n" +
        "                               "+is_owned(post_data['user_email'],'post',post_data['identity'])+"\n"+
        "                            </div>\n" +
        "                            <div class=\"post-description\">\n" +
        "                                " + post_data['content'] + "\n" +//TODO:后端在把文本转化为标签时，记得p标签带上一个id="content"
        "                                <div class=\"post-state-details\">\n" +
        "                                    <div>\n" +
        "                                        <p>发布于&nbsp;" + post_data['time_stamp'] + "</p>\n" +
        "                                    </div>\n" +
        "                                </div>\n" +
        "\n" +
        "                            </div>\n" +
        "\n" +
        "                            <div class=\"post-state\">\n" +
        "                                " + uped_or_downed(post_data['ups_downs'],'post') + "\n" +
        "                            </div>\n" +
        "\n" +
        "                            <!-- post comments -->\n" +
        "                                " + get_all_comments(post_data['identity'],post_data['comments'],post_data['comments_closed']) + "\n" +
        "\n" +
        "                                <div class=\"post-add-comment\">\n" +
        "                                    <div class=\"post-add-comment-avature\">\n" +
        "                                        <img src=\"assets/images/avatars/" + sessionStorage.getItem("user_email") + ".png\" alt=\"?\">\n" +
        "                                    </div>\n" +
        "                                    <div class=\"post-add-comment-text-area\" style=\"width: 83%\">\n" +
        "                                        <input type=\"text\" class=\"uk-input\" placeholder=\"写点评价~\"/>\n" +
        "                                    </div>\n" +
        "                                    <a onclick=\"reply_post(this)\" class=\"button primary px-4\" style=\"position: absolute;right: 1%\">发布~</a>\n" +
        "                                </div>\n" +
        "                            </div>\n" +
        "                        </div>";
}

function get_all_comments(which_post,comments,is_closed) {
    if(is_closed===true){
        return null;
    }
    if(comments===null){
        return null;
    }
    for (const id in comments) {
        let comment_str=fill_in_comment(get_single_comment(id))
        $("#"+which_post+"> div.post-state").after($(comment_str))
    }
}

function get_single_comment(identity) {
    let response=null
    post("/api/posts_and_comments/get_single_comment",{'identity':identity,'user_name':sessionStorage.getItem("user_email")},function (resp) {
        response=JSON.parse(resp)
    },auth_params)
    return response['data'];//obj
}

function fill_in_comment(comment_data) {//传参前记得转为obj
    return "<div class=\"post-comments-single\" id=\""+comment_data['identity']+"\">\n" +
        "                                    <div class=\"post-comment-avatar\">\n" +
        "                                        <img src=\"assets/images/avatars/" + comment_data['user_email'] + ".png\" alt=\"?\" id=\"avatar\">\n" +
        "                                    </div>\n" +
        "                                    <div class=\"post-comment-text\">\n" +
        "                                        <div class=\"post-comment-text-inner\">\n" +
        "                                            <h6 id=\"user_name\"> "+comment_data['user_name']+"</h6>\n" +
        "                                            <p id=\"content\">"+comment_data['content']+"</p>\n" +
        "                                        </div>\n" +
        "                                        <div class=\"uk-text-small\">\n" +
        "                                          "+uped_or_downed(comment_data['ups_downs'],'comment')+"\n"+
        "                                           "+is_owned(comment_data['user_email'],'comment',comment_data['identity'])+"\n"+
        "                                            <span>发表于&nbsp;"+comment_data['time_stamp']+"</span>\n" +
        "                                        </div>\n" +
        "                                    </div>\n" +
        "                                </div>";
}

function uped_or_downed(ups_downs,type) {
    let img1 = "thumbs-up.png"
    let img2 = "thumbs-down.png"
    let up_state = "false"
    let down_state = "false"
    if (ups_downs['activated'] === 'up') {
        img1 = "thumbs-up-activated.png"
        up_state = 'true'
    }
    if (ups_downs['activated'] === 'down') {
        img2 = "thumbs-down-activated.png"
        down_state = 'true'
    }
    if(type==="post"){
        return "<div id=\"up\" onclick=\"up_or_down_it(this,'post')\" class=\"post-state-btns\" activated=\"" + up_state + "\">\n" +
            "                                    <img src=\"assets/images/" + img1 + "\" style=\"width: 5%;height: 5%\" alt=\"?\">\n" +
            "                                    <span style=\"color: blue\">\n" +
            "                                        " + ups_downs['ups'] + "\n" +
            "                                    </span>\n" +
            "                                </div>\n" +
            "                                <div id=\"down\" onclick=\"up_or_down_it(this,'post')\" class=\"post-state-btns\" activated=\"" + down_state + "\">\n" +
            "                                    <img src=\"assets/images/" + img2 + "\" style=\"width: 5%;height: 5%\" alt=\"?\">\n" +
            "                                    <span style=\"color: red\">\n" +
            "                                        " + ups_downs['downs'] + "\n" +
            "                                    </span>\n" +
            "                                </div>";
    }
    if (type==="comment"){
        return "<a id=\"up\" onclick=\"up_or_down_it(this,'comment')\" class=\"text-primary mr-1\" style=\"font-size: 13px\" activated=\"" + up_state + "\" >\n" +
            "                                                <img src=\"assets/images/" + img1 + "\" style=\"width: 2%;height: 2%\" alt=\"?\">\n" +
            "                                                " + ups_downs['ups'] + "\n" +
            "                                                <span>&nbsp;&nbsp;</span>\n" +
            "                                            </a>\n" +
            "                                            <a id=\"down\" onclick=\"up_or_down_it(this,'comment')\" class=\"text-danger\" style=\"font-size: 13px\" activated=\"" + down_state + "\" >\n" +
            "                                                <img src=\"assets/images/" + img2 + "\" style=\"width: 2%;height: 2%\" alt=\"?\">\n" +
            "                                                " + ups_downs['downs'] + "\n" +
            "                                                <span>&nbsp;&nbsp;</span>\n" +
            "                                            </a>";
    }
    return null;
}

function is_owned(user_email,type,identity) {
    let main_str=null
    if(sessionStorage.getItem("user_email")===user_email){
        if(type==='post'){
            main_str="<div class=\"post-btn-action\">\n" +
                "                                    <span class=\"icon-more uil-ellipsis-h\"></span>\n" +
                "                                    <div class=\"mt-0 p-2\" uk-dropdown=\"pos: top-right;mode:hover \">\n" +
                "                                        <ul class=\"uk-nav uk-dropdown-nav\">\n" +
                "                                            <li><a onclick=\"shutdown_comments(this,"+identity+")\"> <i class=\"uil-comment-slash mr-1\"></i>关闭评论</a></li>\n" +
                "                                            <li><a onclick=\"delete_it('post',"+identity+")\" class=\"text-danger\"> <i class=\"uil-trash-alt mr-1\"></i>删除 </a></li>\n" +
                "                                        </ul>\n" +
                "                                    </div>\n" +
                "                                </div>"
        }
        if(type==='comment'){
            main_str="<a class=\"text-dark\" style=\"font-size: 13px\" onclick=\"delete_it('comment',"+identity+")\">\n" +
                "                                                <i class=\"uil-trash\"></i>\n" +
                "                                                删除\n" +
                "                                                <span>&nbsp;&nbsp;</span>\n" +
                "                                            </a>"
        }
    }
    return main_str;
}

function up_or_down_it(button_obj,type) {
    console.log("click")
    if (type==='post'){
        let identity=$(button_obj).parentsUntil("div.post").attr('id')
        let user_email=sessionStorage.getItem('user_email')
        let cancel='no_canceled'
        let confirm="no_confirmed"
        if($(button_obj).attr("activated")==="true"){
            $(button_obj).attr("activated","false")
            let tmp_id=$(button_obj).attr("id")
            $(button_obj).find("img").attr("src","assets/images/thumbs-"+tmp_id+".png")
            cancel=tmp_id
            let val=$(button_obj).find("span").text()
            $(button_obj).find("span").text(val-1)
            post("/api/posts_and_comments/change_up_down_state",{
                'table':'posts',
                'identity': identity,
                'user_email':user_email,
                'confirm':confirm,
                'cancel':cancel
            },function (resp) {
               let response=JSON.parse(resp)
                if(response['code']===200){
                    window.alert("取消"+tmp_id==='up'?'点赞':'点踩'+"成功！")
                }
                if(response['code']===404){
                    window.alert("该动态已被删除！")
                    do_delete_it($(button_obj).parentsUntil("div.post"))
                }
            },auth_params)

        }
        else {
            let here=$(button_obj)
            let tmp_id=here.attr("id")
            let bro=$(button_obj).siblings("#"+tmp_id==="up"?"down":"up")
            if(bro.attr("activated")==="true"){
                cancel=bro.attr("id")
                bro.attr("activated","false")
                bro.find("img").attr("src","assets/images/thumbs-"+bro.attr("id")+".png")
                let val=bro.find("span").text()
                bro.find("span").text(val-1)
            }
            confirm=tmp_id
            here.attr("activated","true")
            here.find("img").attr("src","assets/images/thumbs-"+tmp_id+"-activated.png")
            let val=here.find("span").text()
            here.find("span").text(val+1)
            post("/api/posts_and_comments/change_up_down_state",{
                'table':'posts',
                'identity': identity,
                'user_email':user_email,
                'confirm':confirm,
                'cancel':cancel
            },function (resp) {
                let response=JSON.parse(resp)
                if(response['code']===200){
                    window.alert(tmp_id==='up'?'点赞':'点踩'+"成功！")
                }
                if(response['code']===404){
                    window.alert("该动态已被删除！")
                    do_delete_it($(button_obj).parentsUntil("div.post"))
                }
            },auth_params)
        }
    }
    if (type==='comment'){
        let identity=$(button_obj).parentsUntil("div.post").attr('id')
        let user_email=sessionStorage.getItem('user_email')
        let cancel='no_canceled'
        let confirm="no_confirmed"
        if($(button_obj).attr("activated")==="true"){
            $(button_obj).attr("activated","false")
            let tmp_id=$(button_obj).attr("id")
            $(button_obj).find("img").attr("src","assets/images/thumbs-"+tmp_id+".png")
            cancel=tmp_id
            let val=$(button_obj).text()
            $(button_obj).text(val-1)
            post("/api/posts_and_comments/change_up_down_state",{
                'table':'comments',
                'identity': identity,
                'user_email':user_email,
                'confirm':confirm,
                'cancel':cancel
            },function (resp) {
                let response=JSON.parse(resp)
                if(response['code']===200){
                    window.alert("取消"+tmp_id==='up'?'点赞':'点踩'+"成功！")
                }
                if(response['code']===404){
                    window.alert("该动态已被删除！")
                    do_delete_it($(button_obj).parentsUntil("div.post-comments-single"))
                }
            },auth_params)

        }
        else {
            let here=$(button_obj)
            let tmp_id=here.attr("id")
            let bro=$(button_obj).siblings("#"+tmp_id==="up"?"down":"up")
            if(bro.attr("activated")==="true"){
                cancel=bro.attr("id")
                bro.attr("activated","false")
                bro.find("img").attr("src","assets/images/thumbs-"+bro.attr("id")+".png")
                let val=bro.text()
                bro.text(val-1)
            }
            confirm=tmp_id
            here.attr("activated","true")
            here.find("img").attr("src","assets/images/thumbs-"+tmp_id+"-activated.png")
            let val=here.text()
            here.text(val+1)
            post("/api/posts_and_comments/change_up_down_state",{
                'table':'comments',
                'identity': identity,
                'user_email':user_email,
                'confirm':confirm,
                'cancel':cancel
            },function (resp) {
                let response=JSON.parse(resp)
                if(response['code']===200){
                    window.alert(tmp_id==='up'?'点赞':'点踩'+"成功！")
                }
                if(response['code']===404){
                    window.alert("该动态已被删除！")
                    do_delete_it($(button_obj).parentsUntil("div.post-comments-single"))
                }
            },auth_params)
        }
    }
}

function shutdown_comments(button_obj,identity) {
    if($(button_obj).parentsUntil("div.post").attr("isclosed")==="true"){
        return;
    }
    if(!window.confirm("您确定要关闭这条动态的评论吗？该操作是一次性的！（误操作请找管理员撤回）")){
        return;
    }
    post("/api/posts_and_comments/shutdown_comments",{//TODO:后端接收到时记得把该动态评论连带删除，然后给动态添加关闭评论标记
        'identity':identity,
        'user_email': sessionStorage.getItem("user_email")
    },function (resp) {
        let response=JSON.parse(resp)
        if(response['code']===200){
            for (let findKey in $(button_obj).parentsUntil("div.post").find("div.post-comments-single")) {
                do_delete_it(findKey)
            }
        }
    },auth_params)
}

function delete_it(type,identity) {
    post("/api/posts_and_comments/delete",{
        'table': type+'s',
        'identity':identity,
        'user_email':sessionStorage.getItem("user_email")
    },function (resp) {
        let response=JSON.parse(resp)
        if(response['code']===200){
            window.alert("删除成功！")
        }
    },auth_params)
    do_delete_it($("#"+identity))
}

function do_delete_it(html_obj) {
    html_obj.remove()
}

function reply_post(button_obj) {
    let val=$(button_obj).siblings("input.post-add-comment-text-area").val()
    if(val===null){
        window.alert("不能发空评论哦！")
        return;
    }
    if(val.length>200){
        window.alert("输入太多字符啦！")
    }
    let identity=$(button_obj).parentsUntil("div.post").attr("id")
    post("/api/posts_and_comments/reply",{
        'identity':identity,
        'text':val,
        'user_email':sessionStorage.getItem("user_email")
    },function (resp) {
        let response=JSON.parse(resp)
        if(response['code']===200){
            $(button_obj).parentsUntil("div.post").find("div.post-add-comment").before(fill_in_comment({
                'user_email':sessionStorage.getItem("user_email"),
                'identity':response['data']['identity'],
                'user_name':sessionStorage.getItem("user_name"),
                'time_stamp':response['data']['time_stamp'],
                'content':val,
                'ups_downs':{
                    'ups': 0,
                    'downs': 0,
                    'activated':'none'
                }
            }))
            window.alert("发表成功！")
        }
    },auth_params)
}

function post_new() {
    let val=$("#new_post").find("textarea").val()
    if(val===null){
        window.alert("不能发空动态哦！")
        return;
    }
    if(val.length>8000){
        window.alert("输入太多字符啦！")
    }
    post("/api/posts_and_comments/new_post",{
        'user_email':sessionStorage.getItem("user_email"),
        'text':val
    },function (resp) {
        let response=JSON.parse(resp)
        if(response['code']===200){
            $("#new_post").after(fill_in_post({
                'user_email':sessionStorage.getItem("user_email"),
                'identity':response['data']['identity'],
                'user_name':sessionStorage.getItem("user_name"),
                'time_stamp':response['data']['time_stamp'],
                'content':response['data']['content'],
                'ups_downs':{
                    'ups': 0,
                    'downs': 0,
                    'activated':'none'
                },
                'comments_closed':"false",
                'comments':[]
            }))
        }
    },auth_params)
}