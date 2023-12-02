package com.AiTongue.service;

import com.AiTongue.Const.GlobalConstantsUpdater;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.AiTongue.Const.GlobalConstantsUpdater.appId;
import static com.AiTongue.Const.GlobalConstantsUpdater.authorization;

/**
 * @Author: 袁健城
 * @Description: userController的一些主要业务代码
 **/

@Service
public class userService {

    @Autowired
    private GlobalConstantsUpdater globalConstantsUpdater;


    public String login(String appId, String password) throws IOException {

        //获取userKey网址
        String getUserKeyUrl = "https://api.aikanshe.com/account/login?tokenKey=get";
        // 调用GET方式获取tokenKey和userKey
        String tokenAndUserKeysResponse = HttpUtils.sendGetRequest(getUserKeyUrl);

        // 解析获取到的tokenKey和userKey
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(tokenAndUserKeysResponse);

        JsonNode dataNode = jsonNode.get("data");

        String userKey = "";
        //2023.11.28日注册的用户名和密码，200次，100元
//        String appId = "13602676990";
//        String password = "20030515";

        if (dataNode != null) {
            userKey = dataNode.get("userKey").asText();
        } else {
            System.out.println("获取tokenKey和userKey的dataNode为空");
        }

//        System.out.println("tokenkey" + tokenKey);
//        System.out.println("userkey" + userKey);

        //登录url
        String loginUrl = "https://api.aikanshe.com/account/login";

        // 构建登录请求体JSON数据
        String requestBody = "{"
                + "\"appId\": \"" + appId + "\","
                + "\"methodName\": \"login\","
                + "\"password\": \"" + password + "\","
                + "\"timestamp\": \"" + getCurrentTimestamp() + "\","
                + "\"userKey\": \"" + userKey + "\""
                + "}";

        //System.out.println("检查登录请求体："+requestBody);

        String loginResponse = HttpUtils.sendPostJsonBody(loginUrl, requestBody);
        JsonNode loginResponseNode = objectMapper.readTree(loginResponse);

        //获取登录接口返回的data里的数据
        JsonNode loginDataNode = loginResponseNode.get("data");

        System.out.println("data" + dataNode);

        //获取登录接口返回的meta里的数据
        JsonNode meta = loginResponseNode.get("meta");
        System.out.println("meta=" + meta);

        String success = meta.get("success").asText();
        System.out.println(success);

        if (!success.equals("true")) {
            System.out.println("登录失败!未获取jwt");
            System.out.println("检查登录失败返回的loginResponse数据：" + loginResponse);
        }

        //获取jwt令牌
        System.out.println("执行更新jwt操作");
        authorization = loginDataNode.get("jwt").asText();

        if (authorization != null) {
            System.out.println("登录成功，jwt更新，同时查询剩余使用次数.....");
            checkAvailableTimes("v101", appId, authorization);
        }

        return "1";

    }


    public String register(String username, String email, String password) throws IOException {
        //获取userkey网址
        String getUserKeyUrl = "https://api.aikanshe.com/account/login?tokenKey=get";
        // 调用GET方式获取tokenKey和userKey
        String tokenAndUserKeysResponse = HttpUtils.sendGetRequest(getUserKeyUrl);

        // 解析获取到的tokenKey和userKey
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(tokenAndUserKeysResponse);

        /**
         * 响应体示例：
         {
         "data": {
         "tokenKey": "ahk1ztutiwmh2bcr",
         "userKey": "50Y588"
         },
         "meta": {
         "msg": "issued tokenKey success",
         "code": 1000,
         "success": true,
         "timestamp": 1701155343685
         }
         }
         */

        //所以要先提取data，在获取data里的东西
        JsonNode dataNode = jsonNode.get("data");

        String tokenKey = "";
        String userKey = "";

        if (dataNode != null) {
            tokenKey = dataNode.get("tokenKey").asText();
            userKey = dataNode.get("userKey").asText();
        } else {
            System.out.println("获取tokenKey和userKey的dataNode为空");
        }

        // 使用获取到的userKey进行注册
        if (tokenKey != null && userKey != null) {
            String registerApiUrl = "https://api.aikanshe.com/account/register";

            // 请求体JSON数据
            String requestBody = "{"
                    + "\"email\": \"" + email + "\","
                    + "\"methodName\": \"register\","
                    + "\"password\": \"" + password + "\","
                    + "\"userKey\": \"" + userKey + "\","
                    + "\"username\": \"" + username + "\","
                    + "}";

            String registerResponse = HttpUtils.sendPostJsonBody(registerApiUrl, requestBody);
            System.out.println(registerResponse);

            System.out.println("注册成功！");

        } else {
            System.out.println("注册失败！");
        }
        return "1";
    }

    public static void checkAvailableTimes(String version, String appId, String authorization) throws IOException {

        System.out.println("检查可用次数中。。。");
        // 检查可用次数接口地址
        String checkAvailableTimesUrl = "https://api.aikanshe.com/pro/getAvalTimes/" + version;

        // 构建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authorization);
        headers.set("appId", appId);


        // Create a HttpEntity with headers
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        // Create RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Make a POST request to check available times
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                checkAvailableTimesUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        String checkTimesResponse = responseEntity.getBody();

        //检查jwt是否正确
        //System.out.println("authorization  =  "+authorization);

        // 调用POST方式检查可用次数
//        String checkTimesResponse = HttpUtils.sendPostHeaders(checkAvailableTimesUrl, headers);

        // 输出检查可用次数响应体
        //System.out.println("Check Times Response: " + checkTimesResponse);

        // 解析获取到的可用次数信息
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(checkTimesResponse);

        System.out.println("Remaining available times: " + jsonNode.get("data").asText() + " times");

        System.out.println("剩余可用次数为：" + jsonNode.get("data").asText() + "次");
    }


    private static String getCurrentTimestamp() {
        // 获取当前时间戳，使用英文月份和GMT/UTC格式
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(new Date());
    }
}
