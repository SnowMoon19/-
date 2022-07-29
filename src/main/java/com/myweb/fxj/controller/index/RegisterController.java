package com.myweb.fxj.controller.index;

import com.myweb.fxj.pojo.info.Account;
import com.myweb.fxj.service.interfac.index.LoginService;
import com.myweb.fxj.service.interfac.index.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class RegisterController {
    @Autowired
    RegisterService registerServiceImpl;

    /**
     * 跳转到注册界面
     */
    @GetMapping("/register")
    public String register(HttpSession session) {
        //每次重新访问注册页面的时候把之前有的注册信息弄掉
        session.setAttribute("registerMsg", "");
        return "index/register";
    }

    /**
     * 注册用户`
     * post方式提交注册表单，也不会被拦截
     */
    @PostMapping("/register")
    public String registerAccount(Account account, HttpSession session) {
        if(!registerServiceImpl.registerCheck(account)) {
            // 账号已存在
            session.setAttribute("registerMsg", "Username already exists");
        }
        else {
            if(registerServiceImpl.addAccount(account)) {
                // 注册成功
                session.setAttribute("registerMsg", "Register successfully");
            }
            else {
                // 注册失败
                session.setAttribute("registerMsg", "Database is busy");
            }
        }
        return "index/register";
    }

}
