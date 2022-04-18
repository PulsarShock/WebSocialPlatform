package com.zlz.websocialplatform.controller.error;


import com.zlz.websocialplatform.exception.BaseProjectException;
import com.zlz.websocialplatform.exception.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/error")
public class ErrorController {
    //这里处理的都是没通过拦截器重定向后产生的异常，因为拦截器preHandle产生的异常不能被全局异常处理器捕获，会给前端返回500，响应体也无了，后台还报错，很烦~
    //所以改成拦截出问题后阻止后续请求，重定向到这里，由这里抛出异常，再被全局异常处理器捕获然后包装回复前端
    @GetMapping("/unauthorized")
    public void unauthorizeError(){
        throw new BaseProjectException(ExceptionEnum.UNAUTHORIZED);
    }

    @GetMapping("/invalidToken")
    public void invalidToken(){
        throw new BaseProjectException(ExceptionEnum.INVALID_TOKEN);
    }

}
