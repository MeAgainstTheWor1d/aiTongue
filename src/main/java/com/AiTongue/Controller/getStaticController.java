package com.AiTongue.Controller;

import java.io.File;
import java.io.IOException;


import com.AiTongue.Vo.*;
import com.AiTongue.service.getStaticService;
import com.utils.JsonFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Random;


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
     *
     * @param imgName
     * @param requestHeaders
     * @return
     */

    @GetMapping({"/getStaticImg/{imgName}"})
    public byte[] getStaticImg(@PathVariable("imgName") String imgName, @RequestHeader HttpHeaders requestHeaders) throws IOException {
        //获取图片byte流以便下载
        byte[] staticImg = getStaticService.getStaticImg(imgName, requestHeaders);
        return staticImg;
    }


    /**
     * 上传图片至服务器的static/images文件夹下，并做出舌诊相关业务逻辑。
     *
     * @param userTongueImgForm
     * @return
     * @throws IOException
     */

    @PostMapping("/uploadImg")
    public ResponseVo<dataVo> uploadImg(@RequestBody userTongueImgForm userTongueImgForm) throws IOException {

        //上传照片至服务器返回图片名字，然后通过getStaticImg接口+filename获取图片，测试用户上传的图片是否有舌头。如果有，再调用消耗次数的接口
        String filename = getStaticService.uploadImg(userTongueImgForm);

        double checkTongueExist = getStaticService.checkTongueExist(filename);

        String filePath = "src/main/resources/static/images/hjb.png";

        //如果为1，通过检测，执行ai舌诊
        if (checkTongueExist != 0) {
            dataVo dataVo = getStaticService.quanXiTongue(checkTongueExist, userTongueImgForm, filePath);
            return ResponseVo.success(dataVo);
        } else {
            //返回，图片内没有舌头
            return ResponseVo.error(ResponseEnum.NOT_EXIST_TONGUE);
        }
    }

    @PostMapping("/uploadImg2Linux")
    public ResponseVo<dataVo> uploadImg2Linux(@RequestBody userTongueImgForm userTongueImgForm) throws IOException {
//        //上传照片至服务器返回图片名字，然后通过getStaticImg接口+filename获取图片，测试用户上传的图片是否有舌头。如果有，再调用消耗次数的接口
//        File imgFile = getStaticService.uploadImg2Linux(userTongueImgForm);
//        double checkTongueExist = 0;
//        if (imgFile.exists() && imgFile.isFile() && imgFile.canRead()) {
//            System.out.println("文件存在，可读");
//            checkTongueExist = getStaticService.checkTongueExist2Linux(imgFile);
//        } else {
//            System.out.println("文件不存在或不可读");
//        }
//        //如果为1，通过检测，执行ai舌诊
//        if (checkTongueExist!=0){
//            dataVo dataVo = getStaticService.quanXiTongue2Linux(checkTongueExist, userTongueImgForm, imgFile);
//            return ResponseVo.success(dataVo);
//        }else {
//            //返回，图片内没有舌头
//            //return ResponseVo.success();
//            return ResponseVo.error(ResponseEnum.NOT_EXIST_TONGUE);
//        }
        String datareport = "{\n" +
                "  \"code\": \"40001\",\n" +
                "  \"data\": {\n" +
                "    \"id\": 77074,\n" +
                "    \"isYuejin\": 0,\n" +
                "    \"male\": 1,\n" +
                "    \"age\": 20,\n" +
                "    \"name\": \"张三\",\n" +
                "    \"feel\": \"记忆力差;\\r\\n身体有些地方常常疼痛、瘀斑;\\r\\n喉咙干，皮肤干燥，不光洁;\\r\\n皮肤上有血丝。\",\n" +
                "    \"recomand\": \"（1）去瘀血的原则：如果你的体内有瘀血，去中医那里开了方子以后，熬药喝是一部分，还要多剩点药来泡脚。\\r\\n\\r\\n\\t\\r\\n（2）腰腿受寒后的驱除寒湿泡脚方\\r\\n\\t\\t\\r\\n配方：熟地6克、当归6克、赤芍6克、川芎6克、伸筋草9克、透骨草9克、桃仁6 克、红花6克、桑枝6克、丝瓜络6克、桂枝6克、薏苡仁30克、艾叶3克。\\r\\n\\t\\t\\r\\n用法：上述药物放入水中熬开锅20分钟，将药汁兑入温水，泡脚。每天泡两次，每次泡20分钟，水不可太热，覆盖脚面即可。\\r\\n\\r\\n\\t\\r\\n（3）心脏瘀血最危险，可用三七粉配西洋参粉化瘀\\r\\n配方：三七粉、西洋参粉各0.5克。\\r\\n用法：每天口服，用白水送下。\\r\\n\\r\\n（4）骨折后的化瘀食方\\r\\n配方：三七10克（或三七粉1～3克）、鸡腿骨五六根。\\r\\n\\r\\n用法：把鸡腿上的肉剔掉，光剩骨头，当然，也可以稍微带一点点肉，用刀背将鸡腿骨捣裂，然后与三七（粉）一起煲汤，可以放入一点盐和佐料。用纱布过滤出汤，每天吃饭时饮用，坚持到骨折恢复为止。\\r\\n\\r\\n（5）治疗痔疮小方\\r\\n配方：地龙50克（中药店购买）。\\r\\n用法：用食物粉碎机将地龙打成粉末（或者让药店打成粉末），然后放入锅内翻炒，待微微发黄后闭火。每天温水冲服5克，如能装入胶囊服用更好。连用三天，如改善，用到五天即可。孕妇忌用。\\r\\n\\r\\n（6）女性瘀血调理\\r\\n配方：\\r\\na．月经不畅、不多，在经期服用同仁堂的“益母丸”；\\r\\nb．气血虚弱、体内有瘀血，导致闭经，服用“八珍益母丸”与“益母丸”；\\r\\n用法：平时可以服用同仁堂的中成药“八珍益母丸”，月经前三天停止服用，开始服用“益母丸”，持续整个月经期，一直到月经结束，月经过后，平时再服用“八珍益母丸”养血。按照这个周期往复。\\r\\n\\t\\t\\r\\n以上保养方法来源于百家讲坛罗大伦博士的《舌诊图谱》\",\n" +
                "    \"typeId\": 5,\n" +
                "    \"typeName\": \"血瘀\",\n" +
                "    \"typeConfidence\": 93.66696,\n" +
                "    \"zhengxing\": \"气虚血瘀\",\n" +
                "    \"zhengxingDesc\": \"是气虚运血无力，血行瘀滞而表现的证候。常由病久气虛，渐致瘀血内停而引!起。临床表现为面色淡白或晦滞，身倦乏力，少气懒言，疼痛如刺，常见于胸胁，痛处不移，拒按，舌淡暗或有紫斑，脉沉涩。\",\n" +
                "    \"bagang\": \"阳证，里证，实证，热证\",\n" +
                "    \"bagangDesc\": \"出现黄苔、红舌、绛舌、舌尖红、舌边红、芒刺、灰燥苔、黑燥苔,判断为热证；没有出现薄苔判断为里证；没有出现齿痕舌、淡白舌、凹陷、裂纹判断为实证；出现热证、实证判断为阳证；\",\n" +
                "    \"shemianName\": \"绛舌\",\n" +
                "    \"shemianDesc\": \"主热入营血，耗伤津液。\",\n" +
                "    \"shemianConfidence\": 30,\n" +
                "    \"shexinName\": \"胖大舌,老舌\",\n" +
                "    \"shexinDesc\": \"伸舌满口，多伴有齿痕，多主阳气虚弱，正气不足，多见于30-40岁工作繁忙，熬夜喝酒，生活不规律，饮食不规律者。\",\n" +
                "    \"shexinConfidence\": 80,\n" +
                "    \"taiseName\": \"黄苔\",\n" +
                "    \"taiseDesc\": \"主里证、热证。\",\n" +
                "    \"taiseConfidence\": 56,\n" +
                "    \"taizhiName\": \"滑苔,润苔\",\n" +
                "    \"taizhiDesc\": \"舌面水分过多，扪之湿滑欲滴。主虚证。舌面润泽，干湿适中。为正常舌苔或疾病初期。\",\n" +
                "    \"taizhiConfidence\": 56,\n" +
                "    \"createTime\": \"2023-12-12T14:37:52.393+0800\",\n" +
                "    \"uid\": \"13602676990\",\n" +
                "    \"medical\": \"（1）去瘀血的原则：如果你的体内有瘀血，去中医那里开了方子以后，熬药喝是一部分，还要多剩点药来泡脚。  \\t\\r\\n（2）腰腿受寒后的驱除寒湿泡脚方 \\t\\t\\r\\n配方：熟地6克、当归6克、赤芍6克、川芎6克、伸筋草9克、透骨草9克、桃仁6 克、红花6克、桑枝6克、丝瓜络6克、桂枝6克、薏苡仁30克、艾叶3克。 \\t\\t\\r\\n\\r\\n用法：上述药物放入水中熬开锅20分钟，将药汁兑入温水，泡脚。每天泡两次，每次泡20分钟，水不可太热，覆盖脚面即可。  \\t\\r\\n\\r\\n（3）心脏瘀血最危险，可用三七粉配西洋参粉化瘀 \\t\\t\\r\\n配方：三七粉、西洋参粉各0.5克。 \\r\\n\\r\\n用法：每天口服，用白水送下。\\r\\n\\r\\n（4）骨折后的化瘀食方\\r\\n\\t\\r\\n配方：三七10克（或三七粉1～3克）、鸡腿骨五六根。\\r\\n\\t\\t\\r\\n用法：把鸡腿上的肉剔掉，光剩骨头，当然，也可以稍微带一点点肉，用刀背将鸡腿骨捣裂，然后与三七（粉）一起煲汤，可以放入一点盐和佐料。用纱布过滤出汤，每天吃饭时饮用，坚持到骨折恢复为止。\\r\\n\\r\\n（5）治疗痔疮小方\\r\\n\\r\\n配方：地龙50克（中药店购买）。\\r\\n用法：用食物粉碎机将地龙打成粉末（或者让药店打成粉末），然后放入锅内翻炒，待微微发黄后闭火。每天温水冲服5克，如能装入胶囊服用更好。连用三天，如改善，用到五天即可。孕妇忌用。\\r\\n\\r\\n（6）女性瘀血调理\\r\\n配方：\\r\\na．月经不畅、不多，在经期服用同仁堂的“益母丸”\\r\\nb．气血虚弱、体内有瘀血，导致闭经，服用“八珍益母丸”与“益母丸”\\r\\n用法：平时可以服用同仁堂的中成药“八珍益母丸”，月经前三天停止服用，开始服用“益母丸”，持续整个月经期，一直到月经结束，月经过后，平时再服用“八珍益母丸”养血。按照这个周期往复。\\r\\n\\t\\r\\n以上保养方法来源于百家讲坛罗大伦博士的书籍《舌诊图谱》\",\n" +
                "    \"aijiu\": \"中医活血化瘀，艾灸3个穴位来调理：\\r\\n对血海穴进行艾灸，可以调和气血，改善血瘀，这个穴位在髌骨内上方六毫米左右的位置，对这个穴位进行艾灸，可以改善血瘀引起的荨麻疹、面部色素沉着等问题。\\r\\n\\r\\n位于手背第一、第二掌骨中间处的合谷穴，具有疏风清热的功效，对其进行艾灸，可以有效改善血瘀引起的风疹、荨麻疹等病症。\\r\\n\\r\\n此外，足三里也是活血化瘀的重要穴位，足三里穴在胫骨前嵴位置大约一横指的地方，对其进行艾灸，能够调补气血，起到美容、健脾胃的功效。\",\n" +
                "    \"food\": \"血瘀体质的人宜选用具有调畅气血作用的食物，如生山楂、醋、玫瑰花、桃仁 (花)、黑豆、油菜等。\",\n" +
                "    \"sport\": \"（1）八段锦。\\r\\n\\r\\n对于经络通畅非常有帮助，可以做做。\\r\\n\\r\\n（2）健康的生活方式是基础。\\r\\n\\r\\n不管何种类型血瘀，生活必须合理，比如作息避免熬夜，饮食清淡，切忌大补等等。\",\n" +
                "    \"life\": \"少坐多动，切勿熬夜，内容健康的娱乐活动，情趣高雅，生动活泼，在轻松愉快的环境和气氛中，给人以美的享受。情志畅达，赏心悦目，则百脉疏通，气血调和；情趣高雅，则可益智养心，故具有怡养神情之作用。娱乐活动的形式多样，动静不拘，可动静结合，柔刚相济，既可调养心神，又能活动筋骨，因而具有形神兼养之功。\\r\\n\\r\\n可以听一些积极向上的阳韵音乐，以补益肝肾、散寒活血解郁。当心情不安、思绪紊乱时，听民族乐曲，可起到安定情绪、调理思绪的作用；当精神忧郁时，可听欢快乐曲，能减轻或缓解忧郁，振奋精神；当烦躁易怒时，可听琴曲、古筝曲等，能使心绪平静安和。\\r\\n\\r\\n练习作画习书之功，提高鉴别能力，每每这种进步总使人欣慰，使人自得其乐，心情愉快，肝气得以舒展。\",\n" +
                "    \"goods\": [],\n" +
                "    \"qixiaoDesc\": \"头晕头胀，热扰心神；\\n容易长痤疮，脸爱出油；\\n湿邪轻；\",\n" +
                "    \"uploadPath\": \"/2023-12-12/testTongue.png\",\n" +
                "    \"tonguePicAddr\": \"4/2023-12-12/yuxue-testTongue_marked.png\",\n" +
                "    \"symptom\": null,\n" +
                "    \"symptomDesc\": null,\n" +
                "    \"score\": 100,\n" +
                "    \"scoreDesc\": \"您的健康得分击败了全国90%的人。\",\n" +
                "    \"roiImage\": \"/2023-12-12/testTongue_roi.png\",\n" +
                "    \"dianci\": 0,\n" +
                "    \"dianciDesc\": \"舌未见点刺表示脏腑没有邪热\",\n" +
                "    \"dianciMean\": \"舌体没有出现点刺。\",\n" +
                "    \"yudian\": 0,\n" +
                "    \"yudianDesc\": \"舌未见瘀点表示脏腑气血正常。\",\n" +
                "    \"yudianMean\": \"舌面没有出现瘀点。\",\n" +
                "    \"yuban\": 1,\n" +
                "    \"yubanDesc\": \"舌见瘀斑提示脏腑气血瘀滞，为实证，瘀斑越大越严重。\",\n" +
                "    \"yubanMean\": \"舌面局部出现青紫色斑块，大小不一。\",\n" +
                "    \"liewen\": 0,\n" +
                "    \"liewenDesc\": \"舌未见裂纹表示脏腑津液正常。\",\n" +
                "    \"liewenMean\": \"舌质和舌苔没有竖纹和横纹。\",\n" +
                "    \"chihen\": 0,\n" +
                "    \"chihenDesc\": \"舌未见齿痕表示脾气正常。\",\n" +
                "    \"chihenMean\": \"舌体边缘没有有牙齿压迫的痕迹。\",\n" +
                "    \"botai\": 0,\n" +
                "    \"botaiDesc\": \"舌未见剥落提示脏腑气充足,胃阴正常,气阴平衡。\",\n" +
                "    \"botaiMean\": \"舌苔比较完整，没有缺失。\",\n" +
                "    \"qiji\": 0,\n" +
                "    \"qijiDesc\": \"气机正常。\",\n" +
                "    \"qijiMean\": \"舌尖左右高度一致。\",\n" +
                "    \"xinzang\": 0,\n" +
                "    \"xinzangDesc\": \"心脏未见异常。\",\n" +
                "    \"xinzangMean\": \"舌尖没有凹陷、淤点、瘀斑、裂纹、芒刺。\",\n" +
                "    \"fei\": 0,\n" +
                "    \"feiDesc\": \"肺区未见异常。\",\n" +
                "    \"feiMean\": \"肺区没有凹陷、淤点、瘀斑、高凸、裂纹、芒刺等特征。\",\n" +
                "    \"gandan\": 0,\n" +
                "    \"gandanDesc\": \"肝胆区未见异常。\",\n" +
                "    \"gandanMean\": \"肝胆区没有凹陷、淤点、瘀斑、高凸、裂纹、芒刺等特征。\",\n" +
                "    \"piwei\": 0,\n" +
                "    \"piweiDesc\": \"脾胃区未见异常。\",\n" +
                "    \"piweiMean\": \"脾胃区没有凹陷、淤点、瘀斑、高凸、裂纹、芒刺等特征。\",\n" +
                "    \"zigon\": 0,\n" +
                "    \"zigonDesc\": null,\n" +
                "    \"zigonMean\": null,\n" +
                "    \"nuanchao\": 0,\n" +
                "    \"nuanchaoDesc\": null,\n" +
                "    \"nuanchaoMean\": null,\n" +
                "    \"qianliexian\": 0,\n" +
                "    \"qianliexianDesc\": \"前列腺未见异常。\",\n" +
                "    \"qianliexianMean\": \"前列腺区没有凹陷、淤点、瘀斑、高凸、裂纹、芒刺等特征。\",\n" +
                "    \"shen\": 0,\n" +
                "    \"shenDesc\": \"肾未见异常。\",\n" +
                "    \"shenMean\": \"肾区没有凹陷、淤点、瘀斑、高凸、裂纹、芒刺等特征。\",\n" +
                "    \"chagndao\": 0,\n" +
                "    \"chagndaoDesc\": \"肠道未见异常。\",\n" +
                "    \"chagndaoMean\": \"肠道区没有凹陷、淤点、瘀斑、高凸、裂纹、芒刺、黄、厚苔等特征。\",\n" +
                "    \"ruxian\": 0,\n" +
                "    \"ruxianDesc\": null,\n" +
                "    \"ruxianMean\": null,\n" +
                "    \"nao\": 0,\n" +
                "    \"naoDesc\": \"大脑神经正常。\",\n" +
                "    \"naoMean\": \"舌头不歪斜，舌中线正常。\",\n" +
                "    \"pifu\": 0,\n" +
                "    \"pifuDesc\": \"皮肤正常。\",\n" +
                "    \"pifuMean\": \"舌头未见白斑或者黑斑。\",\n" +
                "    \"tizhiRadarData\": [\n" +
                "      0,\n" +
                "      0,\n" +
                "      0,\n" +
                "      0,\n" +
                "      0,\n" +
                "      0,\n" +
                "      0,\n" +
                "      0,\n" +
                "      0\n" +
                "    ],\n" +
                "    \"tizhiTableXData\": [\n" +
                "      \"11-30\",\n" +
                "      \"12-01\",\n" +
                "      \"12-11\"\n" +
                "    ],\n" +
                "    \"tizhiTableYData\": [\n" +
                "      5,\n" +
                "      5,\n" +
                "      5\n" +
                "    ],\n" +
                "    \"sheSeRadarData\": [\n" +
                "      1,\n" +
                "      1,\n" +
                "      0,\n" +
                "      6,\n" +
                "      0,\n" +
                "      0\n" +
                "    ],\n" +
                "    \"sheSeTableXData\": [\n" +
                "      \"11-30\",\n" +
                "      \"12-01\",\n" +
                "      \"12-11\"\n" +
                "    ],\n" +
                "    \"sheSeTableYData\": [\n" +
                "      4,\n" +
                "      2,\n" +
                "      4\n" +
                "    ],\n" +
                "    \"taiSeRadarData\": [\n" +
                "      5,\n" +
                "      5,\n" +
                "      0\n" +
                "    ],\n" +
                "    \"taiSeTableXData\": [\n" +
                "      \"11-30\",\n" +
                "      \"12-01\",\n" +
                "      \"12-11\"\n" +
                "    ],\n" +
                "    \"taiSeTableYData\": [\n" +
                "      2,\n" +
                "      1,\n" +
                "      2\n" +
                "    ],\n" +
                "    \"pdfUrl\": \"report/2023-12-12/13602676990_2023-12-12_1702363072542.html\",\n" +
                "    \"frontCamera\": true\n" +
                "  },\n" +
                "  \"meta\": {\n" +
                "    \"code\": \"40001\",\n" +
                "    \"msg\": \"ok\",\n" +
                "    \"timestamp\": \"2023-12-12T14:37:52.612+0800\"\n" +
                "  }\n" +
                "}";
        String data = " {\"code\":\"40001\",\"data\":{\"id\":73864,\"isYuejin\":0,\"male\":1,\"age\":16,\"name\":null,\"feel\":\"记忆力差;\\r\\n身体有些地方常常疼痛、瘀斑;\\r\\n喉咙干，皮肤干燥，不光洁;\\r\\n皮肤上有血丝。\",\"recomand\":\"（1）去瘀血的原则：如果你的体内有瘀血，去中医那里开了方子以后，熬药喝是一部分，还要多剩点药来泡脚。\\r\\n\\r\\n\\t\\r\\n（2）腰腿受寒后的驱除寒湿泡脚方\\r\\n\\t\\t\\r\\n配方：熟地6克、当归6克、赤芍6克、川芎6克、伸筋草9克、透骨草9克、桃仁6 克、红花6克、桑枝6克、丝瓜络6克、桂枝6克、薏苡仁30克、艾叶3克。\\r\\n\\t\\t\\r\\n用法：上述药物放入水中熬开锅20分钟，将药汁兑入温水，泡脚。每天泡两次，每次泡20分钟，水不可太热，覆盖脚面即可。\\r\\n\\r\\n\\t\\r\\n（3）心脏瘀血最危险，可用三七粉配西洋参粉化瘀\\r\\n配方：三七粉、西洋参粉各0.5克。\\r\\n用法：每天口服，用白水送下。\\r\\n\\r\\n（4）骨折后的化瘀食方\\r\\n配方：三七10克（或三七粉1～3克）、鸡腿骨五六根。\\r\\n\\r\\n用法：把鸡腿上的肉剔掉，光剩骨头，当然，也可以稍微带一点点肉，用刀背将鸡腿骨捣裂，然后与三七（粉）一起煲汤，可以放入一点盐和佐料。用纱布过滤出汤，每天吃饭时饮用，坚持到骨折恢复为止。\\r\\n\\r\\n（5）治疗痔疮小方\\r\\n配方：地龙50克（中药店购买）。\\r\\n用法：用食物粉碎机将地龙打成粉末（或者让药店打成粉末），然后放入锅内翻炒，待微微发黄后闭火。每天温水冲服5克，如能装入胶囊服用更好。连用三天，如改善，用到五天即可。孕妇忌用。\\r\\n\\r\\n（6）女性瘀血调理\\r\\n配方：\\r\\na．月经不畅、不多，在经期服用同仁堂的“益母丸”；\\r\\nb．气血虚弱、体内有瘀血，导致闭经，服用“八珍益母丸”与“益母丸”；\\r\\n用法：平时可以服用同仁堂的中成药“八珍益母丸”，月经前三天停止服用，开始服用“益母丸”，持续整个月经期，一直到月经结束，月经过后，平时再服用“八珍益母丸”养血。按照这个周期往复。\\r\\n\\t\\t\\r\\n以上保养方法来源于百家讲坛罗大伦博士的《舌诊图谱》\",\"typeId\":5,\"typeName\":\"血瘀\",\"typeConfidence\":93.66696,\"zhengxing\":\"气虚血瘀\",\"zhengxingDesc\":\"是气虚运血无力，血行瘀滞而表现的证候。常由病久气虛，渐致瘀血内停而引!起。临床表现为面色淡白或晦滞，身倦乏力，少气懒言，疼痛如刺，常见于胸胁，痛处不移，拒按，舌淡暗或有紫斑，脉沉涩。\",\"bagang\":\"阴证，里证，虚证，热证\",\"bagangDesc\":\"出现黄苔、红舌、绛舌、舌尖红、舌边红、芒刺、灰燥苔、黑燥苔,判断为热证；没有出现薄苔判断为里证；出现齿痕、淡白舌、凹陷、裂纹判断为虚证；出现寒证、虚证判断为阴证；\",\"shemianName\":\"绛舌\",\"shemianDesc\":\"主热入营血，耗伤津液。\",\"shemianConfidence\":30.0,\"shexinName\":\"胖大舌,老舌\",\"shexinDesc\":\"伸舌满口，多伴有齿痕，多主阳气虚弱，正气不足，多见于30-40岁工作繁忙，熬夜喝酒，生活不规律，饮食不规律者。\",\"shexinConfidence\":80.0,\"taiseName\":\"黄苔\",\"taiseDesc\":\"主里证、热证。\",\"taiseConfidence\":56.0,\"taizhiName\":\"滑苔,润苔\",\"taizhiDesc\":\"舌面水分过多，扪之湿滑欲滴。主虚证。舌面润泽，干湿适中。为正常舌苔或疾病初期。\",\"taizhiConfidence\":56.0,\"createTime\":\"2023-11-30T18:51:57.703+0800\",\"uid\":\"13602676990\",\"medical\":\"（1）去瘀血的原则：如果你的体内有瘀血，去中医那里开了方子以后，熬药喝是一部分，还要多剩点药来泡脚。  \\t\\r\\n（2）腰腿受寒后的驱除寒湿泡脚方 \\t\\t\\r\\n配方：熟地6克、当归6克、赤芍6克、川芎6克、伸筋草9克、透骨草9克、桃仁6 克、红花6克、桑枝6克、丝瓜络6克、桂枝6克、薏苡仁30克、艾叶3克。 \\t\\t\\r\\n\\r\\n用法：上述药物放入水中熬开锅20分钟，将药汁兑入温水，泡脚。每天泡两次，每次泡20分钟，水不可太热，覆盖脚面即可。  \\t\\r\\n\\r\\n（3）心脏瘀血最危险，可用三七粉配西洋参粉化瘀 \\t\\t\\r\\n配方：三七粉、西洋参粉各0.5克。 \\r\\n\\r\\n用法：每天口服，用白水送下。\\r\\n\\r\\n（4）骨折后的化瘀食方\\r\\n\\t\\r\\n配方：三七10克（或三七粉1～3克）、鸡腿骨五六根。\\r\\n\\t\\t\\r\\n用法：把鸡腿上的肉剔掉，光剩骨头，当然，也可以稍微带一点点肉，用刀背将鸡腿骨捣裂，然后与三七（粉）一起煲汤，可以放入一点盐和佐料。用纱布过滤出汤，每天吃饭时饮用，坚持到骨折恢复为止。\\r\\n\\r\\n（5）治疗痔疮小方\\r\\n\\r\\n配方：地龙50克（中药店购买）。\\r\\n用法：用食物粉碎机将地龙打成粉末（或者让药店打成粉末），然后放入锅内翻炒，待微微发黄后闭火。每天温水冲服5克，如能装入胶囊服用更好。连用三天，如改善，用到五天即可。孕妇忌用。\\r\\n\\r\\n（6）女性瘀血调理\\r\\n配方：\\r\\na．月经不畅、不多，在经期服用同仁堂的“益母丸”\\r\\nb．气血虚弱、体内有瘀血，导致闭经，服用“八珍益母丸”与“益母丸”\\r\\n用法：平时可以服用同仁堂的中成药“八珍益母丸”，月经前三天停止服用，开始服用“益母丸”，持续整个月经期，一直到月经结束，月经过后，平时再服用“八珍益母丸”养血。按照这个周期往复。\\r\\n\\t\\r\\n以上保养方法来源于百家讲坛罗大伦博士的书籍《舌诊图谱》\",\"aijiu\":\"中医活血化瘀，艾灸3个穴位来调理：\\r\\n对血海穴进行艾灸，可以调和气血，改善血瘀，这个穴位在髌骨内上方六毫米左右的位置，对这个穴位进行艾灸，可以改善血瘀引起的荨麻疹、面部色素沉着等问题。\\r\\n\\r\\n位于手背第一、第二掌骨中间处的合谷穴，具有疏风清热的功效，对其进行艾灸，可以有效改善血瘀引起的风疹、荨麻疹等病症。\\r\\n\\r\\n此外，足三里也是活血化瘀的重要穴位，足三里穴在胫骨前嵴位置大约一横指的地方，对其进行艾灸，能够调补气血，起到美容、健脾胃的功效。\",\"food\":\"血瘀体质的人宜选用具有调畅气血作用的食物，如生山楂、醋、玫瑰花、桃仁 (花)、黑豆、油菜等。\",\"sport\":\"（1）八段锦。\\r\\n\\r\\n对于经络通畅非常有帮助，可以做做。\\r\\n\\r\\n（2）健康的生活方式是基础。\\r\\n\\r\\n不管何种类型血瘀，生活必须合理，比如作息避免熬夜，饮食清淡，切忌大补等等。\",\"life\":\"少坐多动，切勿熬夜，内容健康的娱乐活动，情趣高雅，生动活泼，在轻松愉快的环境和气氛中，给人以美的享受。情志畅达，赏心悦目，则百脉疏通，气血调和；情趣高雅，则可益智养心，故具有怡养神情之作用。娱乐活动的形式多样，动静不拘，可动静结合，柔刚相济，既可调养心神，又能活动筋骨，因而具有形神兼养之功。\\r\\n\\r\\n可以听一些积极向上的阳韵音乐，以补益肝肾、散寒活血解郁。当心情不安、思绪紊乱时，听民族乐曲，可起到安定情绪、调理思绪的作用；当精神忧郁时，可听欢快乐曲，能减轻或缓解忧郁，振奋精神；当烦躁易怒时，可听琴曲、古筝曲等，能使心绪平静安和。\\r\\n\\r\\n练习作画习书之功，提高鉴别能力，每每这种进步总使人欣慰，使人自得其乐，心情愉快，肝气得以舒展。\",\"goods\":[],\"qixiaoDesc\":\"头晕头胀，热扰心神；\\n容易长痤疮，脸爱出油；\\n湿邪轻；\\n肾阳、肾气不足；\\n肝血不足；\\n右大腿痛；\\n肺部有淤堵；\",\"uploadPath\":\"/2023-11-30/testTongue.png\",\"tonguePicAddr\":\"4/2023-11-30/yuxue-testTongue_marked.png\",\"symptom\":null,\"symptomDesc\":null,\"score\":95,\"scoreDesc\":\"肺有问题，扣5分；您的健康得分击败了全国90%的人。\",\"roiImage\":\"/2023-11-30/testTongue_roi.png\",\"dianci\":0,\"dianciDesc\":\"舌未见点刺表示脏腑没有邪热\",\"dianciMean\":\"舌体没有出现点刺。\",\"yudian\":1,\"yudianDesc\":\"舌见瘀点提示脏腑气血瘀滞，为实证，淤点越多越严重。\",\"yudianMean\":\"舌面出现青紫或紫黑色瘀点，多见于舌尖、舌边。\",\"yuban\":1,\"yubanDesc\":\"舌见瘀斑提示脏腑气血瘀滞，为实证，瘀斑越大越严重。\",\"yubanMean\":\"舌面局部出现青紫色斑块，大小不一。\",\"liewen\":0,\"liewenDesc\":\"舌未见裂纹表示脏腑津液正常。\",\"liewenMean\":\"舌质和舌苔没有竖纹和横纹。\",\"chihen\":0,\"chihenDesc\":\"舌未见齿痕表示脾气正常。\",\"chihenMean\":\"舌体边缘没有有牙齿压迫的痕迹。\",\"botai\":0,\"botaiDesc\":\"舌未见剥落提示脏腑气充足,胃阴正常,气阴平衡。\",\"botaiMean\":\"舌苔比较完整，没有缺失。\",\"qiji\":0,\"qijiDesc\":\"气机正常。\",\"qijiMean\":\"舌尖左右高度一致。\",\"xinzang\":0,\"xinzangDesc\":\"心脏未见异常。\",\"xinzangMean\":\"舌尖没有凹陷、淤点、瘀斑、裂纹、芒刺。\",\"fei\":1,\"feiDesc\":\"肺区有淤点。\",\"feiMean\":\"肺区有淤点，表示肺部有实证，会出现咳嗽、支气管炎等症状，需要去医院检查。\",\"gandan\":0,\"gandanDesc\":\"肝胆区未见异常。\",\"gandanMean\":\"肝胆区没有凹陷、淤点、瘀斑、高凸、裂纹、芒刺等特征。\",\"piwei\":0,\"piweiDesc\":\"脾胃区未见异常。\",\"piweiMean\":\"脾胃区没有凹陷、淤点、瘀斑、高凸、裂纹、芒刺等特征。\",\"zigon\":0,\"zigonDesc\":null,\"zigonMean\":null,\"nuanchao\":0,\"nuanchaoDesc\":null,\"nuanchaoMean\":null,\"qianliexian\":0,\"qianliexianDesc\":null,\"qianliexianMean\":null,\"shen\":0,\"shenDesc\":\"肾未见异常。\",\"shenMean\":\"肾区没有凹陷、淤点、瘀斑、高凸、裂纹、芒刺等特征。\",\"chagndao\":0,\"chagndaoDesc\":\"肠道未见异常。\",\"chagndaoMean\":\"肠道区没有凹陷、淤点、瘀斑、高凸、裂纹、芒刺、黄、厚苔等特征。\",\"ruxian\":0,\"ruxianDesc\":null,\"ruxianMean\":null,\"nao\":0,\"naoDesc\":\"大脑神经正常。\",\"naoMean\":\"舌头不歪斜，舌中线正常。\",\"pifu\":0,\"pifuDesc\":\"皮肤正常。\",\"pifuMean\":\"舌头未见白斑或者黑斑。\",\"frontCamera\":false},\"meta\":{\"code\":\"40001\",\"msg\":\"ok\",\"timestamp\":\"2023-11-30T18:51:57.836+0800\"}}\n";
        // 调用工具类提取data字段
        dataVo dataVo = JsonFormatter.extractDataFromJson(data, dataVo.class);

        userTongueVo userTongueVo = JsonFormatter.extractDataFromJson(datareport,com.AiTongue.Vo.userTongueVo.class);
        System.out.println("带报告的舌诊结果："+userTongueVo);
        // 打印结果
        return ResponseVo.success(dataVo);
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
