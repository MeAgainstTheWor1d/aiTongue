package com.AiTongue.Controller;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.AiTongue.Const.GlobalConstantsUpdater;
import com.AiTongue.Vo.userTongueImgForm;
import com.AiTongue.service.getStaticService;
import com.utils.BASE64DecodedMultipartFile;
import com.utils.HttpUtils;
import org.apache.commons.io.FileUtils;
import org.apache.http.entity.mime.MultipartEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.AiTongue.Const.GlobalConstantsUpdater.appId;
import static com.AiTongue.Const.GlobalConstantsUpdater.authorization;

/**
 * @Author: 袁健城
 * @Description: 获取图片以及上传图片至服务器
 **/

@CrossOrigin
@RestController
@RequestMapping
public class getStaticController {

    @Autowired
    private getStaticService getStaticService;

    /**
     * 获取resource/static/images下的图片，存放一些前端的背景图和头像。
     * @param imgName
     * @param requestHeaders
     * @return
     */

    @GetMapping({"/getStaticImg/{imgName}"})
    public byte[] getStaticImg(@PathVariable("imgName") String imgName, @RequestHeader HttpHeaders requestHeaders) {
        //获取图片byte流以便下载
        byte[] staticImg = getStaticService.getStaticImg(imgName, requestHeaders);
        return staticImg;
    }


    /**
     * 上传图片至服务器的static/images文件夹下，并做出舌诊相关业务逻辑。
     * @param userTongueImgForm
     * @return
     * @throws IOException
     */

    @PostMapping("/uploadImg")
    public double uploadImg(@RequestBody userTongueImgForm userTongueImgForm) throws IOException {

        //上传照片至服务器返回图片名字，然后通过getStaticImg接口+filename获取图片，测试用户上传的图片是否有舌头。如果有，再调用消耗次数的接口
        String filename = getStaticService.uploadImg(userTongueImgForm);

        double checkTongueExist = getStaticService.checkTongueExist(filename);

        String filePath = "src/main/resources/static/images/hjb.png";

        //如果为1，通过检测，执行ai舌诊
        if (checkTongueExist!=0){
            String quanXiTongueReport = getStaticService.quanXiTongue(checkTongueExist, userTongueImgForm, filePath);
        }else {
            //返回，图片内没有舌头
            return 0;
        }
        System.out.println("checkTongueExist:"+checkTongueExist);

        return checkTongueExist;
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
