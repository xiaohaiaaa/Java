package com.hai.test.common.filter;


import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName RequestBodyFilter
 * @Description RequestBody过滤器
 * @Author ZXH
 * @Date 2021/12/8 20:07
 * @Version 1.0
 **/
public class RequestBodyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        RequestWrapper requestWrapper = new RequestWrapper(httpServletRequest);
        RequestBodyContext.REQUEST_BODY.set(requestWrapper);
        chain.doFilter(requestWrapper, response);
        RequestBodyContext.REQUEST_BODY.remove();
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
