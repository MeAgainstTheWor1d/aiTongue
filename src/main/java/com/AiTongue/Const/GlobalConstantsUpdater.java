//package com.AiTongue.Const;
//
//import com.AiTongue.service.userService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//import java.io.File;
//import java.io.IOException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.net.URL;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardOpenOption;
//import java.time.Duration;
//import java.time.Instant;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static java.io.File.separator;
//
//
//// TODO 全局变量更新失败，实现舌头检测功能。
//@Component
//public class GlobalConstantsUpdater {
//    private static final long UPDATE_INTERVAL = 1; // 5小时的秒数
//    //public static String authorization = "eyJhbGciOiJIUzUxMiIsInppcCI6IkRFRiJ9.eNosy9EKAiEQheF3mesVRsfU2ZcJdSfYijbUjSB699Xo6sDH-T9wbSvMEBFzDoQqhbwomxkVEwWVvL94E5OYbGGCuqd-1uTQOO-Ysdtaa7e23eShqpSXlIGxwaw9arLahDCBvJ9_OPEPynaXEY497z2E7wEAAP__.J8j57FBNy_cbfZNSnyjSXectuu9sDTCBvh2SZtCk_fAMfhQTb99dTUCxbkCZYOSsg8siwGvT9vUfN6uDJ9f60A";
//    Path path;
//    private String lastUpdateTimeFile = "/home/aiTongue/lastUpdateTime.txt";
//
//    private Instant lastUpdateTime;
//
//    @Autowired
//    private userService userService;
//
//
//    @PostConstruct
//    public void init() throws IOException {
//        System.out.println("读取上次关闭时的时间");
//        lastUpdateTime = readLastUpdateTimeFromFile();
//        calculateInitialDelay();
//    }
//
//
//    private Instant readLastUpdateTimeFromFile() {
//        try {
//            // 获取 JAR 文件所在目录
//            String jarFilePath = GlobalConstantsUpdater.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
//            String jarDir = Paths.get(jarFilePath).getParent().toString();
//
//
//            // 构建文件路径
//            path = Paths.get(jarDir, "lastTime.txt");
//
//            if (Files.exists(path)) {
//                List<String> lines = Files.readAllLines(path);
//                if (!lines.isEmpty()) {
//                    String timeString = lines.get(0).trim();
//                    System.out.println("读取到的时间： " + timeString);
//                    return Instant.parse(timeString);
//                } else {
//                    Instant currentTime = Instant.now();
//                    saveLastUpdateTimeToFile(currentTime);
//                    System.out.println("txt为空，写入当前时间：" + currentTime);
//                    return currentTime;
//                }
//            }
//        } catch (IOException | URISyntaxException e) {
//            e.printStackTrace();
//        }
//        return Instant.now();
//    }
//
//    private void saveLastUpdateTimeToFile(Instant instant) {
//        try {
//            // 将Instant转换为字符串并写入文件
//            String timeString = instant.toString();
//            Files.write(path, Arrays.asList(timeString));
//            System.out.println("写入最后更新时间：" + timeString);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Scheduled(fixedDelay = UPDATE_INTERVAL * 1000)
//    public void updateGlobalConstant() throws IOException {
//        System.out.println("执行登录，进行令牌更新");
//        System.out.println("目前的authorization全局变量为：");
//        //userService.login(appId,password);
//        System.out.println("全局变量修改为：");
//    }
//
//    @PreDestroy
//    public void onExit() {
//        System.out.println("在关闭应用程序时保存下次更新的时间到文件");
//        // 在关闭应用程序时保存下次更新的时间到文件
//        Instant nextUpdateTime = Instant.now().plusSeconds(UPDATE_INTERVAL);
//        saveLastUpdateTimeToFile(nextUpdateTime);
//    }
//
//
//
//
//    private long calculateInitialDelay() throws IOException {
//        System.out.println("计算是否需要更新。。。");
//        Instant now = Instant.now();
//        System.out.println("lastUpdateTime为："+lastUpdateTime);
//
//        if (lastUpdateTime != null) {
//            long delayInSeconds = Duration.between(lastUpdateTime, now).getSeconds();
//            System.out.println("时间差：" + delayInSeconds + " 秒");
//
//            if (delayInSeconds < UPDATE_INTERVAL) {
//                System.out.println("不需要，时间差小于 " + UPDATE_INTERVAL + " 秒");
//                return delayInSeconds;
//            } else {
//                System.out.println("需要，立即执行更新");
//                updateGlobalConstant();
//                return 0;
//            }
//        } else {
//            // 如果 lastUpdateTime 为 null，说明是第一次运行，可以执行更新
//            System.out.println("首次运行，立即执行更新");
//            updateGlobalConstant();
//            return 0;
//        }
//    }
//
//}
