package com.AiTongue.service;

import com.AiTongue.Vo.userTongueImgForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utils.BASE64DecodedMultipartFile;
import com.utils.HttpUtils;
import com.utils.JsonFormatter;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ByteArrayResource;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static com.AiTongue.Const.GlobalConstantsUpdater.appId;
import static com.AiTongue.Const.GlobalConstantsUpdater.authorization;

/**
 * @Author: 翰林猿
 * @Description: TODO
 **/

@Service
public class getStaticService {

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

    public byte[] getStaticImg(String imgName, HttpHeaders requestHeaders){
        //规定图片存在文件夹
        String imagePath = "src/main/resources/static/images" + imgName;

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
            System.out.println("图片读取过程失败");
            return null;
        }
    }

    /**
     * 检查图片中是否有舌头
     * @param filename
     * @return 如果有舌头则返回一个int类型的 confidence 可信度，后续用于全息舌诊。如果没有则返回 0
     * @throws JsonProcessingException
     */

    public double checkTongueExist(String filename) throws JsonProcessingException {
        //用户图片
        String testTongueExistUrl = "https://api.aikanshe.com/pro/checkTongue/v100";  // 注意版本号要求是v100
        String filePath = "src/main/resources/static/images/hjb.png";
        //String filePath = "src/main/resources/static/images/"+filename;  // 实际的图片文件路径

        // 创建 RestTemplate 实例
        RestTemplate restTemplate = new RestTemplate();

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", authorization);
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

    public String quanXiTongue(double confidence,userTongueImgForm userTongueImgForm,String filename){
        //API 地址
        String quanxiUrl = "https://api.aikanshe.com/pro/quanxi/v100";


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
        headers.set("Authorization", authorization);
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

        System.out.println("Response Body: " + responseBody);
        String formatJson = JsonFormatter.formatJson(responseBody,nickName);

        System.out.println(formatJson);
        return formatJson;


    }




    private String getFileExtension(String imgName) {
        int dotIndex = imgName.lastIndexOf(".");
        return dotIndex == -1 ? "" : imgName.substring(dotIndex + 1);
    }

}
