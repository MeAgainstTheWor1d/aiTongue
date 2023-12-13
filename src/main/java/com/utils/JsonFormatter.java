package com.utils;

import com.AiTongue.Vo.dataVo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonFormatter {
    public static void main(String[] args) {
        String data = " {\"code\":\"40001\",\"data\":{\"id\":73864,\"isYuejin\":0,\"male\":1,\"age\":16,\"name\":null,\"feel\":\"记忆力差;\\r\\n身体有些地方常常疼痛、瘀斑;\\r\\n喉咙干，皮肤干燥，不光洁;\\r\\n皮肤上有血丝。\",\"recomand\":\"（1）去瘀血的原则：如果你的体内有瘀血，去中医那里开了方子以后，熬药喝是一部分，还要多剩点药来泡脚。\\r\\n\\r\\n\\t\\r\\n（2）腰腿受寒后的驱除寒湿泡脚方\\r\\n\\t\\t\\r\\n配方：熟地6克、当归6克、赤芍6克、川芎6克、伸筋草9克、透骨草9克、桃仁6 克、红花6克、桑枝6克、丝瓜络6克、桂枝6克、薏苡仁30克、艾叶3克。\\r\\n\\t\\t\\r\\n用法：上述药物放入水中熬开锅20分钟，将药汁兑入温水，泡脚。每天泡两次，每次泡20分钟，水不可太热，覆盖脚面即可。\\r\\n\\r\\n\\t\\r\\n（3）心脏瘀血最危险，可用三七粉配西洋参粉化瘀\\r\\n配方：三七粉、西洋参粉各0.5克。\\r\\n用法：每天口服，用白水送下。\\r\\n\\r\\n（4）骨折后的化瘀食方\\r\\n配方：三七10克（或三七粉1～3克）、鸡腿骨五六根。\\r\\n\\r\\n用法：把鸡腿上的肉剔掉，光剩骨头，当然，也可以稍微带一点点肉，用刀背将鸡腿骨捣裂，然后与三七（粉）一起煲汤，可以放入一点盐和佐料。用纱布过滤出汤，每天吃饭时饮用，坚持到骨折恢复为止。\\r\\n\\r\\n（5）治疗痔疮小方\\r\\n配方：地龙50克（中药店购买）。\\r\\n用法：用食物粉碎机将地龙打成粉末（或者让药店打成粉末），然后放入锅内翻炒，待微微发黄后闭火。每天温水冲服5克，如能装入胶囊服用更好。连用三天，如改善，用到五天即可。孕妇忌用。\\r\\n\\r\\n（6）女性瘀血调理\\r\\n配方：\\r\\na．月经不畅、不多，在经期服用同仁堂的“益母丸”；\\r\\nb．气血虚弱、体内有瘀血，导致闭经，服用“八珍益母丸”与“益母丸”；\\r\\n用法：平时可以服用同仁堂的中成药“八珍益母丸”，月经前三天停止服用，开始服用“益母丸”，持续整个月经期，一直到月经结束，月经过后，平时再服用“八珍益母丸”养血。按照这个周期往复。\\r\\n\\t\\t\\r\\n以上保养方法来源于百家讲坛罗大伦博士的《舌诊图谱》\",\"typeId\":5,\"typeName\":\"血瘀\",\"typeConfidence\":93.66696,\"zhengxing\":\"气虚血瘀\",\"zhengxingDesc\":\"是气虚运血无力，血行瘀滞而表现的证候。常由病久气虛，渐致瘀血内停而引!起。临床表现为面色淡白或晦滞，身倦乏力，少气懒言，疼痛如刺，常见于胸胁，痛处不移，拒按，舌淡暗或有紫斑，脉沉涩。\",\"bagang\":\"阴证，里证，虚证，热证\",\"bagangDesc\":\"出现黄苔、红舌、绛舌、舌尖红、舌边红、芒刺、灰燥苔、黑燥苔,判断为热证；没有出现薄苔判断为里证；出现齿痕、淡白舌、凹陷、裂纹判断为虚证；出现寒证、虚证判断为阴证；\",\"shemianName\":\"绛舌\",\"shemianDesc\":\"主热入营血，耗伤津液。\",\"shemianConfidence\":30.0,\"shexinName\":\"胖大舌,老舌\",\"shexinDesc\":\"伸舌满口，多伴有齿痕，多主阳气虚弱，正气不足，多见于30-40岁工作繁忙，熬夜喝酒，生活不规律，饮食不规律者。\",\"shexinConfidence\":80.0,\"taiseName\":\"黄苔\",\"taiseDesc\":\"主里证、热证。\",\"taiseConfidence\":56.0,\"taizhiName\":\"滑苔,润苔\",\"taizhiDesc\":\"舌面水分过多，扪之湿滑欲滴。主虚证。舌面润泽，干湿适中。为正常舌苔或疾病初期。\",\"taizhiConfidence\":56.0,\"createTime\":\"2023-11-30T18:51:57.703+0800\",\"uid\":\"13602676990\",\"medical\":\"（1）去瘀血的原则：如果你的体内有瘀血，去中医那里开了方子以后，熬药喝是一部分，还要多剩点药来泡脚。  \\t\\r\\n（2）腰腿受寒后的驱除寒湿泡脚方 \\t\\t\\r\\n配方：熟地6克、当归6克、赤芍6克、川芎6克、伸筋草9克、透骨草9克、桃仁6 克、红花6克、桑枝6克、丝瓜络6克、桂枝6克、薏苡仁30克、艾叶3克。 \\t\\t\\r\\n\\r\\n用法：上述药物放入水中熬开锅20分钟，将药汁兑入温水，泡脚。每天泡两次，每次泡20分钟，水不可太热，覆盖脚面即可。  \\t\\r\\n\\r\\n（3）心脏瘀血最危险，可用三七粉配西洋参粉化瘀 \\t\\t\\r\\n配方：三七粉、西洋参粉各0.5克。 \\r\\n\\r\\n用法：每天口服，用白水送下。\\r\\n\\r\\n（4）骨折后的化瘀食方\\r\\n\\t\\r\\n配方：三七10克（或三七粉1～3克）、鸡腿骨五六根。\\r\\n\\t\\t\\r\\n用法：把鸡腿上的肉剔掉，光剩骨头，当然，也可以稍微带一点点肉，用刀背将鸡腿骨捣裂，然后与三七（粉）一起煲汤，可以放入一点盐和佐料。用纱布过滤出汤，每天吃饭时饮用，坚持到骨折恢复为止。\\r\\n\\r\\n（5）治疗痔疮小方\\r\\n\\r\\n配方：地龙50克（中药店购买）。\\r\\n用法：用食物粉碎机将地龙打成粉末（或者让药店打成粉末），然后放入锅内翻炒，待微微发黄后闭火。每天温水冲服5克，如能装入胶囊服用更好。连用三天，如改善，用到五天即可。孕妇忌用。\\r\\n\\r\\n（6）女性瘀血调理\\r\\n配方：\\r\\na．月经不畅、不多，在经期服用同仁堂的“益母丸”\\r\\nb．气血虚弱、体内有瘀血，导致闭经，服用“八珍益母丸”与“益母丸”\\r\\n用法：平时可以服用同仁堂的中成药“八珍益母丸”，月经前三天停止服用，开始服用“益母丸”，持续整个月经期，一直到月经结束，月经过后，平时再服用“八珍益母丸”养血。按照这个周期往复。\\r\\n\\t\\r\\n以上保养方法来源于百家讲坛罗大伦博士的书籍《舌诊图谱》\",\"aijiu\":\"中医活血化瘀，艾灸3个穴位来调理：\\r\\n对血海穴进行艾灸，可以调和气血，改善血瘀，这个穴位在髌骨内上方六毫米左右的位置，对这个穴位进行艾灸，可以改善血瘀引起的荨麻疹、面部色素沉着等问题。\\r\\n\\r\\n位于手背第一、第二掌骨中间处的合谷穴，具有疏风清热的功效，对其进行艾灸，可以有效改善血瘀引起的风疹、荨麻疹等病症。\\r\\n\\r\\n此外，足三里也是活血化瘀的重要穴位，足三里穴在胫骨前嵴位置大约一横指的地方，对其进行艾灸，能够调补气血，起到美容、健脾胃的功效。\",\"food\":\"血瘀体质的人宜选用具有调畅气血作用的食物，如生山楂、醋、玫瑰花、桃仁 (花)、黑豆、油菜等。\",\"sport\":\"（1）八段锦。\\r\\n\\r\\n对于经络通畅非常有帮助，可以做做。\\r\\n\\r\\n（2）健康的生活方式是基础。\\r\\n\\r\\n不管何种类型血瘀，生活必须合理，比如作息避免熬夜，饮食清淡，切忌大补等等。\",\"life\":\"少坐多动，切勿熬夜，内容健康的娱乐活动，情趣高雅，生动活泼，在轻松愉快的环境和气氛中，给人以美的享受。情志畅达，赏心悦目，则百脉疏通，气血调和；情趣高雅，则可益智养心，故具有怡养神情之作用。娱乐活动的形式多样，动静不拘，可动静结合，柔刚相济，既可调养心神，又能活动筋骨，因而具有形神兼养之功。\\r\\n\\r\\n可以听一些积极向上的阳韵音乐，以补益肝肾、散寒活血解郁。当心情不安、思绪紊乱时，听民族乐曲，可起到安定情绪、调理思绪的作用；当精神忧郁时，可听欢快乐曲，能减轻或缓解忧郁，振奋精神；当烦躁易怒时，可听琴曲、古筝曲等，能使心绪平静安和。\\r\\n\\r\\n练习作画习书之功，提高鉴别能力，每每这种进步总使人欣慰，使人自得其乐，心情愉快，肝气得以舒展。\",\"goods\":[],\"qixiaoDesc\":\"头晕头胀，热扰心神；\\n容易长痤疮，脸爱出油；\\n湿邪轻；\\n肾阳、肾气不足；\\n肝血不足；\\n右大腿痛；\\n肺部有淤堵；\",\"uploadPath\":\"/2023-11-30/testTongue.png\",\"tonguePicAddr\":\"4/2023-11-30/yuxue-testTongue_marked.png\",\"symptom\":null,\"symptomDesc\":null,\"score\":95,\"scoreDesc\":\"肺有问题，扣5分；您的健康得分击败了全国90%的人。\",\"roiImage\":\"/2023-11-30/testTongue_roi.png\",\"dianci\":0,\"dianciDesc\":\"舌未见点刺表示脏腑没有邪热\",\"dianciMean\":\"舌体没有出现点刺。\",\"yudian\":1,\"yudianDesc\":\"舌见瘀点提示脏腑气血瘀滞，为实证，淤点越多越严重。\",\"yudianMean\":\"舌面出现青紫或紫黑色瘀点，多见于舌尖、舌边。\",\"yuban\":1,\"yubanDesc\":\"舌见瘀斑提示脏腑气血瘀滞，为实证，瘀斑越大越严重。\",\"yubanMean\":\"舌面局部出现青紫色斑块，大小不一。\",\"liewen\":0,\"liewenDesc\":\"舌未见裂纹表示脏腑津液正常。\",\"liewenMean\":\"舌质和舌苔没有竖纹和横纹。\",\"chihen\":0,\"chihenDesc\":\"舌未见齿痕表示脾气正常。\",\"chihenMean\":\"舌体边缘没有有牙齿压迫的痕迹。\",\"botai\":0,\"botaiDesc\":\"舌未见剥落提示脏腑气充足,胃阴正常,气阴平衡。\",\"botaiMean\":\"舌苔比较完整，没有缺失。\",\"qiji\":0,\"qijiDesc\":\"气机正常。\",\"qijiMean\":\"舌尖左右高度一致。\",\"xinzang\":0,\"xinzangDesc\":\"心脏未见异常。\",\"xinzangMean\":\"舌尖没有凹陷、淤点、瘀斑、裂纹、芒刺。\",\"fei\":1,\"feiDesc\":\"肺区有淤点。\",\"feiMean\":\"肺区有淤点，表示肺部有实证，会出现咳嗽、支气管炎等症状，需要去医院检查。\",\"gandan\":0,\"gandanDesc\":\"肝胆区未见异常。\",\"gandanMean\":\"肝胆区没有凹陷、淤点、瘀斑、高凸、裂纹、芒刺等特征。\",\"piwei\":0,\"piweiDesc\":\"脾胃区未见异常。\",\"piweiMean\":\"脾胃区没有凹陷、淤点、瘀斑、高凸、裂纹、芒刺等特征。\",\"zigon\":0,\"zigonDesc\":null,\"zigonMean\":null,\"nuanchao\":0,\"nuanchaoDesc\":null,\"nuanchaoMean\":null,\"qianliexian\":0,\"qianliexianDesc\":null,\"qianliexianMean\":null,\"shen\":0,\"shenDesc\":\"肾未见异常。\",\"shenMean\":\"肾区没有凹陷、淤点、瘀斑、高凸、裂纹、芒刺等特征。\",\"chagndao\":0,\"chagndaoDesc\":\"肠道未见异常。\",\"chagndaoMean\":\"肠道区没有凹陷、淤点、瘀斑、高凸、裂纹、芒刺、黄、厚苔等特征。\",\"ruxian\":0,\"ruxianDesc\":null,\"ruxianMean\":null,\"nao\":0,\"naoDesc\":\"大脑神经正常。\",\"naoMean\":\"舌头不歪斜，舌中线正常。\",\"pifu\":0,\"pifuDesc\":\"皮肤正常。\",\"pifuMean\":\"舌头未见白斑或者黑斑。\",\"frontCamera\":false},\"meta\":{\"code\":\"40001\",\"msg\":\"ok\",\"timestamp\":\"2023-11-30T18:51:57.836+0800\"}}\n";
        // 调用工具类提取data字段
        dataVo userTongueVo = JsonFormatter.extractDataFromJson(data, dataVo.class);
        // 打印结果
        JsonFormatter.printFields(userTongueVo);
    }

    public static void printFields(Object obj) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                System.out.println("字段名：" + field.getName() + "，值：" + field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    //https://www.aikanshe.com/static//2023-11-30/testTongue_roi.png
    public static <T> T extractDataFromJson(String jsonString, Class<T> targetType) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // 解析JSON
            JsonNode rootNode = objectMapper.readTree(jsonString);

            // 提取data节点
            JsonNode dataNode = rootNode.path("data");

            // 将data节点下的所有字段映射为目标类型对象
            return objectMapper.convertValue(dataNode, targetType);

        } catch (Exception e) {
            e.printStackTrace();
            // 处理异常情况，例如日志记录或抛出自定义异常
        }

        return null;
    }


    public static String replaceKeys(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, String> keyMapping = new HashMap<>();
        keyMapping.put("code", "状态码");
        keyMapping.put("data", "数据");
        keyMapping.put("age", "年龄");
        keyMapping.put("aijiu", "艾灸与按摩");
        keyMapping.put("bagang", "八纲辨证");
        keyMapping.put("bagangDesc", "八纲辨证描述");
        keyMapping.put("botai", "是否有剥苔,0-否，1-是");
        keyMapping.put("botaiDesc", "剥苔描述");
        keyMapping.put("botaiMean", "剥苔诊断意义");
        keyMapping.put("chagndao", "肠道是否正常,0-否，1-是");
        keyMapping.put("chagndaoDesc", "肠道描述");
        keyMapping.put("chagndaoMean", "肠道诊断意义");
        keyMapping.put("chihen", "是否有齿痕,0-否，1-是");
        keyMapping.put("chihenDesc", "齿痕描述");
        keyMapping.put("chihenMean", "齿痕诊断意义");
        keyMapping.put("createTime", "检测时间");
        keyMapping.put("dianci", "是否有点刺,0-否，1-是");
        keyMapping.put("dianciDesc", "点刺描述");
        keyMapping.put("dianciMean", "点刺诊断意义");
        keyMapping.put("feel", "身体表现");
        keyMapping.put("fei", "肺是否正常,0-否，1-是");
        keyMapping.put("feiDesc", "肺描述");
        keyMapping.put("feiMean", "肺诊断意义");
        keyMapping.put("food", "饮食调养");
        keyMapping.put("frontCamera", "boolean");
        keyMapping.put("gandan", "肝胆是否正常,0-否，1-是");
        keyMapping.put("gandanDesc", "肝胆描述");
        keyMapping.put("gandanMean", "肝胆诊断意义");
        keyMapping.put("goods", "商品配置");
        keyMapping.put("id", "id");
        keyMapping.put("isYuejin", "是否月经，0:否，1-是");
        keyMapping.put("liewen", "是否有裂纹,0-否，1-是");
        keyMapping.put("liewenDesc", "裂纹描述");
        keyMapping.put("liewenMean", "裂纹诊断意义");
        keyMapping.put("life", "情志起居");
        keyMapping.put("male", "是否男性,0:女，1-男");
        keyMapping.put("medical", "药物治疗");
        keyMapping.put("name", "姓名");
        keyMapping.put("nao", "大脑是否正常,0-否，1-是");
        keyMapping.put("naoDesc", "大脑描述");
        keyMapping.put("naoMean", "大脑诊断意义");
        keyMapping.put("nuanchao", "卵巢是否正常,0-否，1-是");
        keyMapping.put("nuanchaoDesc", "卵巢描述");
        keyMapping.put("nuanchaoMean", "卵巢诊断意义");
        keyMapping.put("pifu", "皮肤是否正常,0-否，1-是");
        keyMapping.put("pifuDesc", "皮肤描述");
        keyMapping.put("pifuMean", "皮肤诊断意义");
        keyMapping.put("piwei", "脾胃是否正常,0-否，1-是");
        keyMapping.put("piweiDesc", "脾胃描述");
        keyMapping.put("piweiMean", "脾胃诊断意义");
        keyMapping.put("qianliexian", "前列腺是否正常,0-否，1-是");
        keyMapping.put("qianliexianDesc", "前列腺描述");
        keyMapping.put("qianliexianMean", "前列腺诊断意义");
        keyMapping.put("qiji", "气机是否正常,0-否，1-是");
        keyMapping.put("qijiDesc", "气机描述");
        keyMapping.put("qijiMean", "气机诊断意义");
        try {
            JsonNode originalJson = objectMapper.readTree(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatJson2txt(String jsonString, String name) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            String formattedJson = formatJsonNode(jsonNode, 0);

            // Write to file
            String filePath = writeToFile(formattedJson, name);
            System.out.println("Formatted JSON written to: " + filePath);

            return formattedJson;
        } catch (Exception e) {
            e.printStackTrace();
            return jsonString;  // Return the original string in case of an exception
        }
    }

    private static String formatJsonNode(JsonNode jsonNode, int indentLevel) {
        StringBuilder formattedJson = new StringBuilder();

        if (jsonNode.isObject()) {
            formattedJson.append("{\n");

            Iterator<Map.Entry<String, JsonNode>> fieldsIterator = jsonNode.fields();
            while (fieldsIterator.hasNext()) {
                Map.Entry<String, JsonNode> entry = fieldsIterator.next();

                appendIndent(formattedJson, indentLevel + 1);
                formattedJson.append('"').append(entry.getKey()).append("\": ");
                formattedJson.append(formatJsonNode(entry.getValue(), indentLevel + 1));

                if (fieldsIterator.hasNext()) {
                    formattedJson.append(",\n");
                }
            }

            formattedJson.append('\n');
            appendIndent(formattedJson, indentLevel);
            formattedJson.append("}");
        } else if (jsonNode.isArray()) {
            formattedJson.append("[\n");

            for (JsonNode childNode : jsonNode) {
                appendIndent(formattedJson, indentLevel + 1);
                formattedJson.append(formatJsonNode(childNode, indentLevel + 1));

                if (formattedJson.charAt(formattedJson.length() - 1) == '\n') {
                    appendIndent(formattedJson, indentLevel + 1);
                }

                if (childNode != jsonNode.get(jsonNode.size() - 1)) {
                    formattedJson.append(",\n");
                }
            }

            formattedJson.append('\n');
            appendIndent(formattedJson, indentLevel);
            formattedJson.append("]");
        } else if (jsonNode.isTextual()) {
            formattedJson.append('"').append(jsonNode.textValue()).append('"');
        } else {
            formattedJson.append(jsonNode.toString());
        }

        return formattedJson.toString();
    }

    private static void appendIndent(StringBuilder stringBuilder, int indentLevel) {
        for (int i = 0; i < indentLevel * 2; i++) {
            stringBuilder.append(' ');
        }
    }

    private static String writeToFile(String content, String name) throws IOException {
        File folderPath = new File("src/main/resources/static/report");
        String fileName = name + ".txt";
        File filePath = new File(folderPath, fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toString()))) {
            writer.write(content);
        }

        return filePath.toString();
    }
}
