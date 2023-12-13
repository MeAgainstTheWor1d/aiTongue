package com.AiTongue.Const;

import org.springframework.stereotype.Component;

@Component
public class TokenManager {
    public static String appId = "13602676990";
    public static String password = "20030515";

    private String globalToken;

    public String getGlobalToken() {
        return globalToken;
    }

    public void setGlobalToken(String globalToken) {
        //System.out.println("令牌更新set操作执行："+ globalToken);
        this.globalToken = globalToken;
        System.out.println("令牌更新set操作执行之后:"+ this.getGlobalToken());
    }
}
