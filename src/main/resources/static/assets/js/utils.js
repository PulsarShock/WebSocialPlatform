function get(url, params, success){
    $.ajax({
        type: "get",
        url: url,
        data: params,
        async: true,
        contentType: "application/json",
        xhrFields: {
            withCredentials: true
        },
        success: success
    });
}

function post(url, data, success){
    $.ajax({
        type: "post",
        url: url,
        async: true,
        data: data,
        contentType: "application/json",
        xhrFields: {
            withCredentials: true
        },
        success: success
    });
}

function relocate(user_params,url){
    window.location.href=url+"?user_email="+user_params["user_email"]+"&user_token="+user_params["user_token"]
}