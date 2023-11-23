package com.AiTongue.Controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping
public class getStaticController {
    @GetMapping({"/getStatic"})
    public String test() {
        return "getStatic ok";
    }

    @GetMapping({"/getStaticImg/{imgName}"})
    public byte[] getStaticImg(@PathVariable("imgName") String imgName,
                                               @RequestHeader HttpHeaders requestHeaders) {
        //规定图片存在文件夹
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
            System.out.println("图片读取过程失败");
            return null;
        }
    }

    private String getFileExtension(String imgName) {
        int dotIndex = imgName.lastIndexOf(".");
        return dotIndex == -1 ? "" : imgName.substring(dotIndex + 1);
    }
}
