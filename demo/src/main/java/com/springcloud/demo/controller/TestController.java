package com.springcloud.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author lufei
 * @date 2020/9/10
 * @desc
 */
@RestController
public class TestController extends BaseController{

    //@HystrixCommand(fallbackMethod = "handleHelloFallback")
    @GetMapping("/test/hello")
    public String hello(@RequestParam("a") int a){
        int b=1;
        int c=b/a;
        return "hello,world!";
    }

    @GetMapping("/test/handleHelloFallback")
    private String handleHelloFallback(int a){
        return "sorry,fail";
    }

    @GetMapping("/test/testGateway")
    public String testGateway(HttpServletRequest request){
        System.out.println("headers:");
        Enumeration<String> headers= request.getHeaderNames();
        while (headers.hasMoreElements()){
            String item=headers.nextElement();
            System.out.println(item+":"+request.getHeader(item));
        }
        System.out.println();

        System.out.println("cookies:");
        Cookie[] cookies=request.getCookies();
        if(cookies!=null&&cookies.length>0){
            for(Cookie cookie:cookies){
                System.out.println(cookie.getName()+":"+cookie.getValue());
            }
        }
        System.out.println();

        System.out.println("params:");
        Enumeration<String> params=request.getParameterNames();
        while (params.hasMoreElements()){
            String item=params.nextElement();
            System.out.println(item+":"+request.getParameter(item));
        }
        System.out.println();

        /*System.out.println("attributes:\r\n");
        Enumeration<String> attributes=request.getAttributeNames();
        while (attributes.hasMoreElements()){
            String item=attributes.nextElement();
            System.out.println(item+":"+request.getAttribute(item));
        }*/

        System.out.println("currentUser:");
        System.out.println(this.getCurrentUser());

        return "访问testGateway成功!";
    }

    @GetMapping("/show")
    public String show(){
        return "show";
    }
}
