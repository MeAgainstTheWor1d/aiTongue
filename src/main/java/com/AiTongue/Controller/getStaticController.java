package com.AiTongue.Controller;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.AiTongue.Vo.userTongueImgForm;
import com.utils.BASE64DecodedMultipartFile;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Random;

/**
 * @Author: 翰林猿
 * @Description: 获取图片以及上传图片至服务器
 **/

@CrossOrigin
@RestController
@RequestMapping
public class getStaticController {

    /**
     * 获取resource/static/images下的图片，存放一些前端的背景图和头像。
     * @param imgName
     * @param requestHeaders
     * @return
     */

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


    /**
     * 上传图片至服务器的static/images文件夹下
     * @param userTongueImgForm
     * @return
     * @throws IOException
     */

    @PostMapping("/uploadImg")
    public String uploadImg(@RequestBody userTongueImgForm userTongueImgForm) throws IOException {
        String nickName = userTongueImgForm.getNickName();
        String base64Data = userTongueImgForm.getBase64Data();
        MultipartFile imgFile = BASE64DecodedMultipartFile.base64ToMultipart(base64Data);


        // 生成5位随机数
        String randomSuffix = String.format("%05d", (int) (Math.random() * 100000));

        // 构建文件名
        String fileName = nickName+"Tongue" + randomSuffix + ".jpg";

        // 指定本地保存路径和文件名，保存到当前项目的 images 文件夹下
        String separator = File.separator;

        String folderPath = "static/images/";

        Path filePath = Paths.get(folderPath + separator + fileName);

        // 将 MultipartFile 转换为 File
        File file = new File(filePath.toUri());
        FileUtils.writeByteArrayToFile(file, imgFile.getBytes());

        System.out.println("图片保存成功：" + filePath);

        return "1";
    }

    private String generateRandomSuffix() {
        // 生成 5 位简单随机数
        Random random = new Random();
        int randomInt = random.nextInt(100000); // 生成 [0, 99999] 之间的随机整数
        return String.format("%05d", randomInt);
    }

    private String getFileExtension(String imgName) {
        int dotIndex = imgName.lastIndexOf(".");
        return dotIndex == -1 ? "" : imgName.substring(dotIndex + 1);
    }


}
