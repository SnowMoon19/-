package com.myweb.fxj.controller.index;

import com.myweb.fxj.common.utils.result.Result;
import com.myweb.fxj.pojo.info.Account;
import com.myweb.fxj.pojo.login;
import com.myweb.fxj.service.impl.loginInfoServiceImpl;
import com.myweb.fxj.service.interfac.index.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;

@RestController
@CrossOrigin
public class LoginController {
    @Autowired
    LoginService loginServiceImpl;
    @Autowired
    loginInfoServiceImpl lImpl;

    @GetMapping("/index/login/{username}/{password}")
    public Result checkAccount(@PathVariable("username") String username,
                               @PathVariable("password") String password) {
        System.out.println(username + password);
        Account check = new Account(username,password);
        Account success = loginServiceImpl.loginCheck(check);
        if(success == null) return Result.fail(null); // 账号密码错误返回201的状态码

        login l = new login();
        l.setId(success.getId());
        l.setTime(new Date());
        lImpl.insertLoginInfo(l);
        return Result.ok(success);
    }


    @GetMapping("/")
    public String checkAccount() {
        Date date = new Date();
        System.out.println(date);
        return "index/login";
    }

//    @RequestMapping(value = "/", method = RequestMethod.OPTIONS)
//    public String checkAccount1() {
//        System.out.println(1231222223);
//        return "index/login";
//    }

//    /**
//     * 登陆界面
//     */
//    @GetMapping(value = {"/", "/login"})
//    public String login() {
//        return "index/login";
//    }
//
//
//    /**
//     * 登录账户验证请求
//     */
//    @PostMapping("/login")
//    public String userLogin(Account account, HttpSession session) {
//        Account success = loginServiceImpl.loginCheck(account);
//        if(success != null) {
//            session.setAttribute("loginUser", success);
//            // 登录成功重定向到main页面，防止表单重复提交
//            return "redirect:/main";
//        }
//        else {
//            session.setAttribute("errorMsg", "Wrong account or password");
//            return "index/login";
//        }
//    }
//
//    /**
//     * 跳转到主页面
//     */
//    @GetMapping("/main")
//    public String main() {return "main/blank";}


}
