package com.wwx.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class PostInterceptor implements HandlerInterceptor {

    // 进入控制器前
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws IOException {
        String id = request.getParameter("poId");

        // isNumeric 判断是否为数字，通过拆分成char数组进行的判断
        // 保证id在int类型的最大值之内-2^31~2^31-1,int在java固定为32位
        if(id.length()<10 && StringUtils.isNumeric(id)) {
            // 防止mvc因为无法绑定数据显示400错误
            return true;
        }
        // 响应重定向到首页
        response.sendRedirect("home");
        return false;
    }

    // 解析视图前
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws IOException {
        if(request.getAttribute("poName")==null) {
            response.sendRedirect("home");
        }
    }

    // 解析视图后
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {

    }
}