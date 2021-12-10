package com.hai.test.common.error;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hai.test.common.constant.BizConstant;
import com.hai.test.common.filter.RequestBodyContext;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName ExceptionErrorHandler
 * @Description 全局异常处理
 * @Author ZXH
 * @Date 2021/12/9 19:15
 * @Version 1.0
 **/
@RestController
@ControllerAdvice
@Slf4j
public class ExceptionErrorHandler {

    /**
     * 运行时异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = RuntimeException.class)
    public String runtimeExceptionHandler(HttpServletRequest request, RuntimeException e){
        getExceptionParam(request);
        log.error("运行时异常:", e);
        return e.getMessage();
    }

    /**
     * 其他异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public String globalErrorHandler(HttpServletRequest request, Exception e) {
        getExceptionParam(request);
        log.error("其它异常:", e);
        return e.getMessage();
    }

    private void getExceptionParam(HttpServletRequest req) {
        StringBuilder error = new StringBuilder();
        error.append("\n--------------------------代码调试请求信息begin-----------------------------------------------");
        error.append("\n请求路径：" + req.getServletPath());
        error.append("\n会话Token：" + req.getHeader(BizConstant.JWT_ACCESS_TOKEN));
        error.append("\n请求方式：" + req.getMethod());
        //error.append("\n请求IP：" + WebUtils.getIp(req));
        error.append("\nGet请求参数：" + JSONObject.toJSONString(req.getParameterMap()));
        error.append("\nPost请求参数：" + RequestBodyContext.REQUEST_BODY.get().getBody());
        error.append("\n--------------------------代码调试请求信息end-------------------------------------------------");
        log.error(error.toString());
    }
}
