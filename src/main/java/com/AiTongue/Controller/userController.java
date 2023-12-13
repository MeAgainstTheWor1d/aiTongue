package com.AiTongue.Controller;

import com.AiTongue.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @Author: 袁健城
 * @Description: 想调用ai看舌接口前需要注册、登录官网获取接口调用权限
 **/

@CrossOrigin
@RestController
@RequestMapping
public class userController {

    @Autowired
    private userService userService;

    @GetMapping("/test")
    @ResponseBody
    public String  test() throws IOException {
        String jarDirectory = userService.getJarDirectory();
        System.out.println("JAR Directory: " + jarDirectory);
        return "JAR Directory: " + jarDirectory;
    }

    /**
     * 注册功能，输入想注册的，账号，密码，电子邮箱
     */
    @GetMapping("/register")
    public void register() throws IOException {
        userService.register("13602676990","1783384763@qq.com","20030515");
    }

    /**
     * 登录功能，appid+password即可登录，appid就是注册时的username
     */

    @GetMapping("/login")
    public void login() throws IOException {
        userService.login("13602676990","20030515");
    }
}
