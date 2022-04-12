function get(url,data, success){
    $.ajax({
        type: "get",
        url: url,
        data: {},
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