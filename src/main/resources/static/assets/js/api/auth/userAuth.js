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
    console.log(data)
    post("/api/auth/signup",data,function (resp) {
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
    post("/api/auth/send_verify_code",data,function (resp) {
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
    post("/api/auth/login",data,function (resp) {
        let respbody = JSON.parse(resp)
        if (respbody["code"] === 200) {
            window.alert("登录成功！")//TODO:可能换个更好的提示方式？
            let user_params = {
                "user_email": data.UserEmail,
                "user_token": respbody["data"]["user_token"],
                "user_name":respbody["data"]["user_name"]
            }
            sessionStorage.setItem("user_token", user_params["user_token"])
            sessionStorage.setItem("user_email", user_params["user_email"])
            sessionStorage.setItem("user_name", user_params["user_name"])
            relocate(user_params,"/feeds.html")
        }
        if(respbody["code"]===403){
            window.alert("登录失败！用户名或密码错误！")
        }
    })
}

function dologout() {
    get("/api/auth/logout",{
        "user_email":sessionStorage.getItem("user_email"),
        "user_token":sessionStorage.getItem("user_token")
    },function (resp) {
        let respbody = JSON.parse(resp)
        if(respbody["code"]===200){
            window.alert("已登出！")
            sessionStorage.removeItem("user_token")
            sessionStorage.removeItem("user_email")
        }
        else {
            window.alert("未获得授权！")
        }
    })
}