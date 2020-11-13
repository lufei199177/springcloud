package com.springcloud.demo.interceptor;

import com.springcloud.demo.controller.BaseController;
import com.springcloud.demo.model.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lufei
 * @date 2020/10/15
 * @desc
 */
public class ReqInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId=request.getParameter("userId");
        String userName=request.getParameter("userName");
        User user=new User();
        user.setId(userId);
        user.setName(userName);
        BaseController.set(user);
        return true;
    }

}
