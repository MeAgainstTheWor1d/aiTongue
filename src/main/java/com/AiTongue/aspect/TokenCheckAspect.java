package com.AiTongue.aspect;

import com.AiTongue.Const.TokenManager;
import com.AiTongue.service.userService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.AiTongue.Const.TokenManager.appId;
import static com.AiTongue.Const.TokenManager.password;

/**
 * AOP，在每次检查舌头是否存在前都执行一遍更新令牌操作
 */
@Aspect
@Component
public class TokenCheckAspect {

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private userService userService;

    @Before("execution(* com.AiTongue.service.getStaticService.checkTongueExist2Linux(..))")
    public void checkTokenBeforeMethodExecution() throws IOException {
        // 在每次执行 checkTongueExist 方法前调用检查令牌更新的操作
        String currentToken = tokenManager.getGlobalToken();
        //System.out.println("Before调用检查令牌更新的操作 当前token为: " + currentToken);
        userService.login(appId,password);
        //System.out.println("检查令牌更新....当前token为: " +  tokenManager.getGlobalToken());
    }
}
