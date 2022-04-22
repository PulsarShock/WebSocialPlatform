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

function post(url, data, success,auth_params=null,async=true){
    if(auth_params!==null){
        url=url+"?user_email="+auth_params['user_email']+"&user_token="+auth_params['user_token']
    }
    $.ajax({
        type: "post",
        url: url,
        async: async,
        data: JSON.stringify(data),
        contentType: "application/json",
        xhrFields: {
            withCredentials: true
        },
        success: success
    });
}

function relocate(user_params=null,url){
    if(user_params===null){
        window.location.href=url
    }
    window.location.href=url+"?user_email="+user_params["user_email"]+"&user_token="+user_params["user_token"]
}