package com.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * post和get请求的封装工具类
 */

public class HttpUtils {


    /**
     * 用于发送GET请求
     * @param url
     * @return
     * @throws IOException
     */
    public static String sendGetRequest(String url) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);

            return handleResponse(response);
        }
    }

    /**
     * 用于发送POST请求，支持包括headers，queryParams，formData，jsonBody 4种swagger格式的发送
     * @return 以String类型展示post某个url之后返回的消息
     * @throws IOException
     */

    public static String sendPostHeaders(String url, Map<String, String> headers) throws IOException {
        return sendPostRequest(url, headers, null, null, null);
    }

    public static String sendPostQueryParams(String url, Map<String, String> queryParams) throws IOException {
        return sendPostRequest(url, null, queryParams, null, null);
    }

    public static String sendPostFormData(String url, Map<String, Object> formData) throws IOException {
        return sendPostRequest(url, null, null, formData, null);
    }

    public static String sendPostJsonBody(String url, String jsonBody) throws IOException {
        return sendPostRequest(url, null, null, null, jsonBody);
    }

    public static String sendPostHeadersAndQueryParams(String url, Map<String, String> headers, Map<String, String> queryParams) throws IOException {
        return sendPostRequest(url, headers, queryParams, null, null);
    }

    public static String sendPostHeadersAndFormData(String url, Map<String, String> headers, Map<String, Object> formData) throws IOException {
        return sendPostRequest(url, headers, null, formData, null);
    }

    public static String sendPostHeadersAndJsonBody(String url, Map<String, String> headers, String jsonBody) throws IOException {
        return sendPostRequest(url, headers, null, null, jsonBody);
    }

    public static String sendPostQueryParamsAndFormData(String url, Map<String, String> queryParams, Map<String, Object> formData) throws IOException {
        return sendPostRequest(url, null, queryParams, formData, null);
    }

    public static String sendPostQueryParamsAndJsonBody(String url, Map<String, String> queryParams, String jsonBody) throws IOException {
        return sendPostRequest(url, null, queryParams, null, jsonBody);
    }

    public static String sendPostFormDataAndJsonBody(String url, Map<String, Object> formData, String jsonBody) throws IOException {
        return sendPostRequest(url, null, null, formData, jsonBody);
    }

    public static String sendPostAll(String url, Map<String, String> headers, Map<String, String> queryParams, Map<String, Object> formData, String jsonBody) throws IOException {
        return sendPostRequest(url, headers, queryParams, formData, jsonBody);
    }


    public static String sendPostRequest(String url, Map<String, String> headers, Map<String, String> queryParams, Map<String, Object> formData, String jsonBody) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 添加query类型的查询参数到URL
            if (queryParams != null && !queryParams.isEmpty()) {
                StringBuilder queryString = new StringBuilder("?");
                for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                    queryString.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
                url += queryString.substring(0, queryString.length() - 1);
            }

            // 构造HttpPost请求
            HttpPost httpPost = new HttpPost(url);

            // 设置请求头
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    httpPost.setHeader(key, value);
                    System.out.println("Header: " + key + " = " + value);
                }
            }

            // 设置请求体
            if (jsonBody != null && !jsonBody.isEmpty()) {
                StringEntity stringEntity = new StringEntity(jsonBody, ContentType.APPLICATION_JSON);
                httpPost.setEntity(stringEntity);
            }

            // 修改 MultipartEntityBuilder 部分
            if (formData != null && !formData.isEmpty()) {
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                for (Map.Entry<String, Object> entry : formData.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();

                    if (value instanceof String) {
                        // 如果值是字符串，直接添加文本字段
                        builder.addTextBody(key, (String) value, ContentType.TEXT_PLAIN);
                    } else if (value instanceof File) {
                        // 如果值是文件，使用 FileBody 添加文件字段
                        builder.addPart(key, new FileBody((File) value));
                        System.out.println("Added file to formData: " + key + " = " + ((File) value).getPath());
                    }
                }
                httpPost.setEntity(builder.build());
            }

            // 发送请求并获取响应
            HttpResponse response = httpClient.execute(httpPost);

            // 处理响应
            return handleResponse(response);
        }
    }


    private static String handleResponse(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            return EntityUtils.toString(entity);
        } else {
            return null;
        }
    }

}
