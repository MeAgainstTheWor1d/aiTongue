package com.AiTongue.service;

import com.AiTongue.Const.TokenManager;
import com.AiTongue.Vo.dataVo;
import com.AiTongue.Vo.userTongueImgForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utils.BASE64DecodedMultipartFile;
import com.utils.JsonFormatter;
import lombok.var;
import org.apache.commons.io.FileUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.event.ItemListener;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static com.AiTongue.Const.TokenManager.appId;


/**
 * @Author: 翰林猿
 * @Description: 上传下载图片等服务，以及舌诊业务。
 **/


@Service
public class getStaticService {

    @Autowired
    private TokenManager tokenManager;

    public String uploadImg(userTongueImgForm userTongueImgForm) throws IOException {
        String nickName = userTongueImgForm.getNickName();
        String base64Data = userTongueImgForm.getBase64Data();
        MultipartFile imgFile = BASE64DecodedMultipartFile.base64ToMultipart(base64Data);

        // 生成5位随机数
        String randomSuffix = String.format("%05d", (int) (Math.random() * 100000));

        // 构建文件名
        String fileName = nickName+"Tongue" + randomSuffix + ".jpg";

        // 指定本地保存路径和文件名，保存到当前项目的 images 文件夹下
        String separator = File.separator;

        String folderPath = "src/main/resources/static/images";

        Path filePath = Paths.get(folderPath + separator + fileName);

        // 将 MultipartFile 转换为 File
        File file = new File(filePath.toUri());
        FileUtils.writeByteArrayToFile(file, imgFile.getBytes());

        System.out.println("图片保存成功：" + filePath);

        return fileName;
    }

    public static File convertMultipartFileToFile(MultipartFile multipartFile,String fileName) throws IOException {
        // 创建临时文件
        File tempFile = File.createTempFile(fileName, null);

        // 将 MultipartFile 的 InputStream 写入临时文件
        try (var inputStream = multipartFile.getInputStream()) {
            Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        return tempFile;
    }

    public File uploadImg2Linux(userTongueImgForm userTongueImgForm) throws IOException {
        String nickName = userTongueImgForm.getNickName();
        String base64Data = userTongueImgForm.getBase64Data();
        MultipartFile imgFile = BASE64DecodedMultipartFile.base64ToMultipart(base64Data);
        // 生成5位随机数
        String randomSuffix = String.format("%05d", (int) (Math.random() * 100000));

        // 构建文件名
        String fileName = nickName+"Tongue" + randomSuffix + ".jpg";

        //直接变成临时文件，也同时用这个临时文件做舌诊，后续用官方自带的图床即可
        File tempFile = getStaticService.convertMultipartFileToFile(imgFile,fileName);
        System.out.println("临时文件的名字"+ tempFile.getName());
        System.out.println("临时文件的路径"+ tempFile.getPath());
        return tempFile;
    }


    public byte[] getStaticImg(String imgName, HttpHeaders requestHeaders) throws IOException {
        String imagePath = "static/images/" + imgName;
        //获取文件夹路径的资源
        Resource resource = new ClassPathResource(imagePath);

        try (InputStream inputStream = resource.getInputStream()) {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            byte[] imageBytes = outputStream.toByteArray();


            String fileExtension = getFileExtension(imgName);
            if ("jpg".equalsIgnoreCase(fileExtension) || "jpeg".equalsIgnoreCase(fileExtension)) {
                requestHeaders.setContentType(MediaType.IMAGE_JPEG);
            } else if ("png".equalsIgnoreCase(fileExtension)) {
                requestHeaders.setContentType(MediaType.IMAGE_PNG);
            } else {
                //当服务器返回的数据是二进制格式而无法明确指定具体的文件类型时，通常会使用这个 MIME 类型。
                requestHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            }
            return imageBytes;
        } catch (IOException e) {
            System.out.println("图片读取失败");
            return null;
        }
    }

    /**
     * 检查图片中是否有舌头
     * @param filename
     * @return 如果有舌头则返回一个int类型的 confidence 可信度，后续用于全息舌诊。如果没有则返回 0
     * @throws JsonProcessingException
     */

    public double checkTongueExist(String filename) throws IOException {
        //用户图片
        String testTongueExistUrl = "https://api.aikanshe.com/pro/checkTongue/v100";  // 注意版本号要求是v100
        String filePath = "src/main/resources/static/images/hjb.png";

        String globalToken = tokenManager.getGlobalToken();

        // 创建 RestTemplate 实例
        RestTemplate restTemplate = new RestTemplate();

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", globalToken);
        headers.set("appId",appId);

        // 设置请求体，使用 MultiValueMap 包装参数
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        //把图片变成文件类型放入formData类型的body里
        FileSystemResource resource = new FileSystemResource(new File(filePath));
        body.add("file", resource);

        // 构造 HttpEntity 包含请求头和请求体
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // 发送 POST 请求
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                testTongueExistUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // 处理响应
        String responseBody = responseEntity.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        if (jsonNode.get("code").asText().equals("40001")){
            System.out.println("通过检测，图片内有舌头");
            double confidence = jsonNode.get("data").get("confidence").asDouble();
            return confidence;
        }else {
            System.out.println("未通过检测，图片内没有舌头");
            System.out.println("Response Body为: " + responseBody);
            return 0;
        }
    }

    public double checkTongueExist2Linux(File imgFile) throws IOException {
        //用户图片
        String testTongueExistUrl = "https://api.aikanshe.com/pro/checkTongue/v100";  // 注意版本号要求是v100

        String globalToken = tokenManager.getGlobalToken();

        // 创建 RestTemplate 实例
        RestTemplate restTemplate = new RestTemplate();

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", globalToken);
        headers.set("appId",appId);

        // 检查文件扩展名是否为jpg，如果不是，重命名为jpg（废弃）
//        if (!imgFile.getName().toLowerCase().endsWith(".jpg")) {
//            File renamedFile = new File(imgFile.getParent(), imgFile.getName() + ".jpg");
//            if (imgFile.renameTo(renamedFile)) {
//                System.out.println("文件重命名成功：" + renamedFile.getPath());
//                imgFile = renamedFile;
//            } else {
//                System.out.println("文件重命名失败：" + imgFile.getPath());
//                // 处理文件重命名失败的情况
//                return 0;
//            }
//        }

        // 设置请求体，使用 MultiValueMap 包装参数
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        //把图片文件放入formData类型的body里
        // 使用 FileSystemResource 封装文件
        FileSystemResource fileResource = new FileSystemResource(imgFile);
        body.add("file", fileResource);

        System.out.println("检查方法里的文件："+imgFile.getPath());

        // 构造 HttpEntity 包含请求头和请求体
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // 发送 POST 请求
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                testTongueExistUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // 处理响应
        String responseBody = responseEntity.getBody();
        System.out.println("responseBody="+responseBody);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        if (jsonNode.get("code").asText().equals("40001")){
            System.out.println("通过检测，图片内有舌头");
            double confidence = jsonNode.get("data").get("confidence").asDouble();
            return confidence;
        }else {
            System.out.println("未通过检测，图片内没有舌头");
            System.out.println("Response Body为: " + responseBody);
            return 0;
        }
    }

    public dataVo quanXiTongue(double confidence,userTongueImgForm userTongueImgForm,String filename){
        //API 地址
        String quanxiUrl = "https://api.aikanshe.com/pro/quanxi/v100";

        String globalToken = tokenManager.getGlobalToken();
        String nickName = userTongueImgForm.getNickName();
        int isYuejin = userTongueImgForm.getIsYuejin();
        int gender = userTongueImgForm.getGender();
        int age = userTongueImgForm.getAge();



        // 拼接请求 URL
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(quanxiUrl)
                .queryParam("age", age)
                .queryParam("isYuejin", isYuejin)
                .queryParam("male", gender)
                .queryParam("methodName", "quanxi");

        // 将参数拼接到 URL 后
        String quanxiUrlWithParams = builder.toUriString();

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", globalToken);
        headers.set("appId", appId);


        // 上传文件
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        File file = new File(filename);
        requestBody.add("file", new FileSystemResource(file));

        // 创建 HttpEntity
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // 创建 RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // 发送 POST 请求
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                quanxiUrlWithParams,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // 处理响应
        HttpStatus statusCode = responseEntity.getStatusCode();
        String responseBody = responseEntity.getBody();

        System.out.println("Response Body: ");
        dataVo dataVo = JsonFormatter.extractDataFromJson(responseBody, dataVo.class);
        System.out.println(dataVo);
        return dataVo;
    }

    public dataVo quanXiTongue2Linux(double confidence,userTongueImgForm userTongueImgForm,File imgFile) throws JsonProcessingException {
        //API 地址
        String quanxiUrl = "https://api.aikanshe.com/pro/quanxi/v100";

        String globalToken = tokenManager.getGlobalToken();
        String nickName = userTongueImgForm.getNickName();
        int isYuejin = userTongueImgForm.getIsYuejin();
        int gender = userTongueImgForm.getGender();
        int age = userTongueImgForm.getAge();

        // 检查文件扩展名是否为jpg，如果不是，重命名为jpg
        if (!imgFile.getName().toLowerCase().endsWith(".jpg")) {
            File renamedFile = new File(imgFile.getParent(), imgFile.getName() + ".jpg");
            if (imgFile.renameTo(renamedFile)) {
                System.out.println("文件重命名成功：" + renamedFile.getPath());
                imgFile = renamedFile;
            } else {
                System.out.println("文件重命名失败：" + imgFile.getPath());
            }
        }

        // 拼接请求 URL
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(quanxiUrl)
                .queryParam("age", age)
                .queryParam("isYuejin", isYuejin)
                .queryParam("male", gender)
                .queryParam("methodName", "quanxi")
                .queryParam("frontCamera",1);

        // 将参数拼接到 URL 后
        String quanxiUrlWithParams = builder.toUriString();

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", globalToken);
        headers.set("appId", appId);


        // 上传文件
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        // 使用 FileSystemResource 封装文件
        FileSystemResource fileResource = new FileSystemResource(imgFile);
        requestBody.add("file", fileResource);

        // 创建 HttpEntity
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // 创建 RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // 发送 POST 请求
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                quanxiUrlWithParams,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        System.out.println("requestEntity：请求体的内容为："+requestEntity);

        // 处理响应
        String responseBody = responseEntity.getBody();
        HttpHeaders headers2 = responseEntity.getHeaders();
        // 创建一个ObjectMapper对象
        ObjectMapper objectMapper = new ObjectMapper();

//        System.out.println("舌诊Response Body: " + responseBody);
//        System.out.println("舌诊Response header："+headers2);
//        System.out.println("舌诊Response Entity: " + responseEntity);

        // 将JSON字符串直接转换为 dataVo 对象
        dataVo dataVo = JsonFormatter.extractDataFromJson(responseBody, dataVo.class);
        System.out.println("================dataVo 对象===============");

        // 将对象转换为JSON字符串
        String jsonString = objectMapper.writeValueAsString(dataVo);
        System.out.println("================以下是dataVo的JSON格式===============");
        // 打印JSON字符串
        System.out.println(jsonString);
        return dataVo;
    }

    private String getFileExtension(String imgName) {
        int dotIndex = imgName.lastIndexOf(".");
        return dotIndex == -1 ? "" : imgName.substring(dotIndex + 1);
    }

}
