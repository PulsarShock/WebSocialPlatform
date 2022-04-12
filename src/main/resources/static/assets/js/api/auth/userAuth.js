function doregister() {
    let data={}
    data.UserName=$.trim($("#UserName").val())
    data.UserEmail=$.trim($("#UserEmail").val())
    data.Password=$.trim($("#Password").val())
    data.VerifyCode=$.trim($("#VerifyCode").val())
    let ConfirmPassword=$.trim($("#ConfirmPassword").val())
    if(ConfirmPassword!==data.Password){
        window.alert("两次输入密码要一致！")
        return
    }
    if(data.UserName===null){
        window.alert("请输入姓名！")
        return
    }
    let i=checkPasswordFormat()
    if(i===0){
        window.alert("密码不符合规范！应包含大小写字母、数字和特殊字符，且长度不小于8位！")
        return
    }
    console.log(JSON.stringify(data))
    post("/api/auth/signup",JSON.stringify(data),function (resp) {
        let respbody = JSON.parse(resp)
        if(respbody["code"]===200){
            window.alert("注册成功！现在转入登录前界面~")
            window.location.href="/form-login.html"
        }
        if(respbody["code"]===100401){
            window.alert("用户邮箱已被注册！")
        }
        if(respbody["code"]===100402){
            window.alert("验证码不正确！")
        }
    })
}

function getverifycode() {
    let data=$.trim($("#UserEmail").val())
    post("/api/auth/sendverifycode",data,function (resp) {
        let respbody=JSON.parse(resp)
        if(respbody["code"]===200){
            window.alert("已发出验证码！")
        }
        else if (respbody["code"]===100403){
            window.alert("验证码还未过期！两次请求应间隔至少60秒~")
        }
        else {
            window.alert("验证码发送失败！")
        }
    })
}

function checkPasswordFormat() {
    let str=$.trim($("#Password").val())
    let reg =/^(?=.*[a-zA-Z])(?=.*\d)(?=.*[~!@#$%^&*()_+`\-={}:";'<>?,.\/]).{6,18}$/
    if(!reg.test(str)){
        return 0
    }
    return 1
}

function dologin() {
    let data={}
    data.UserEmail=$.trim($("#UserEmail").val())
    data.Password=$.trim($("#Password").val())
    post("/api/auth/login",JSON.stringify(data),function (resp) {
        let respbody = JSON.parse(resp)
        if(respbody["code"]===200){
            window.alert("登录成功！")//TODO:可能换个更好的提示方式？
            sessionStorage.setItem("user_token",respbody["data"])
            sessionStorage.setItem("user_email",data.UserEmail)
            window.location.href="/feeds.html"
        }
        if(respbody["code"]===403){
            window.alert("登录失败！用户名或密码错误！")
        }
    })
}

function dologout() {
    $.ajax({
        type: 'GET',
        url: '/api/auth/logout',
        contentType: "application/json",
        headers: {
            user_token: sessionStorage.getItem("user_token"),
            user_email: sessionStorage.getItem("user_email")
        },
        success: function (resp) {
            let respbody = JSON.parse(resp)
            if(respbody["code"]===200){
                window.alert("已登出！")
                sessionStorage.removeItem("user_token")
                sessionStorage.removeItem("user_email")
            }
            else {
                window.alert("未获得授权！")
            }
        }
    })
}