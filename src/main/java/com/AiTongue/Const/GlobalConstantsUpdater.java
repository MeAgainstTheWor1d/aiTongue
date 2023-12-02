package com.AiTongue.Const;

import com.AiTongue.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;


// TODO 全局变量更新失败，实现舌头检测功能。
@Component
public class GlobalConstantsUpdater {
    private static final long UPDATE_INTERVAL = 1; // 5小时的秒数
    public static String authorization = "eyJhbGciOiJIUzUxMiIsInppcCI6IkRFRiJ9.eNosy9EKAiEQheF3mesVRsfU2ZcJdSfYijbUjSB699Xo6sDH-T9wbSvMEBFzDoQqhbwomxkVEwWVvL94E5OYbGGCuqd-1uTQOO-Ysdtaa7e23eShqpSXlIGxwaw9arLahDCBvJ9_OPEPynaXEY497z2E7wEAAP__.J8j57FBNy_cbfZNSnyjSXectuu9sDTCBvh2SZtCk_fAMfhQTb99dTUCxbkCZYOSsg8siwGvT9vUfN6uDJ9f60A";
    public static String appId = "13602676990";
    public static String password = "20030515";

    Path path;
    private String lastUpdateTimeFile = "lastUpdateTime.txt";

    private Instant lastUpdateTime;

    @Autowired
    private userService userService;


    @PostConstruct
    public void init() throws IOException {
        System.out.println("读取上次关闭时的时间");
        lastUpdateTime = readLastUpdateTimeFromFile();
        calculateInitialDelay();
    }


    private Instant readLastUpdateTimeFromFile() {
        //System.out.println("读取中。。。。");
        try {
            // 使用 ClassLoader 获取资源
            ClassLoader classLoader = getClass().getClassLoader();

            URL url = classLoader.getResource("lastUpdateTime.txt");
            System.out.println("读取到lastUpdateTime.txt");
            if (url != null) {
                //System.out.println("url!=null");
                path = Paths.get(url.toURI());

                if (Files.exists(path)) {
                    //System.out.println("开始读取txt");
                    List<String> lines = Files.readAllLines(path);
                    if (!lines.isEmpty()) {
                        //System.out.println("txt不为空");
                        String timeString = lines.get(0).trim();
                        System.out.println("读取到的时间： "+timeString);
                        return Instant.parse(timeString);
                    }else {
                        Instant currentTime = Instant.now();
                        saveLastUpdateTimeToFile(currentTime);
                        System.out.println("txt为空，写入当前时间："+currentTime);
                        return currentTime;
                    }
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return Instant.now();
    }


    @Scheduled(fixedDelay = UPDATE_INTERVAL * 1000)
    public void updateGlobalConstant() throws IOException {
        System.out.println("执行登录，进行令牌更新");
        System.out.println("目前的authorization全局变量为："+authorization);
        userService.login(appId,password);
        System.out.println("全局变量修改为："+authorization);
    }

    @PreDestroy
    public void onExit() {
        System.out.println("在关闭应用程序时保存下次更新的时间到文件");
        // 在关闭应用程序时保存下次更新的时间到文件
        Instant nextUpdateTime = Instant.now().plusSeconds(UPDATE_INTERVAL);
        saveLastUpdateTimeToFile(nextUpdateTime);
    }

    private void saveLastUpdateTimeToFile(Instant time) {
        System.out.println("更新时间到 lastUpdateTime.txt 里");
        try {
            Path path = Paths.get(lastUpdateTimeFile);
            Files.write(path, Collections.singleton(time.toString()), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private long calculateInitialDelay() throws IOException {
        System.out.println("计算是否需要更新。。。");
        Instant now = Instant.now();
        System.out.println("lastUpdateTime为："+lastUpdateTime);

        if (lastUpdateTime != null) {
            long delayInSeconds = Duration.between(lastUpdateTime, now).getSeconds();
            System.out.println("时间差：" + delayInSeconds + " 秒");

            if (delayInSeconds < UPDATE_INTERVAL) {
                System.out.println("不需要，时间差小于 " + UPDATE_INTERVAL + " 秒");
                return delayInSeconds;
            } else {
                System.out.println("需要，立即执行更新");
                updateGlobalConstant();
                return 0;
            }
        } else {
            // 如果 lastUpdateTime 为 null，说明是第一次运行，可以执行更新
            System.out.println("首次运行，立即执行更新");
            updateGlobalConstant();
            return 0;
        }
    }

}
