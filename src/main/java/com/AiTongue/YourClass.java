package com.AiTongue;

import java.io.File;
import java.net.URISyntaxException;

public class YourClass {
    public static void main(String[] args) {
        String jarDirectory = getJarDirectory();
        System.out.println("JAR Directory: " + jarDirectory);
    }
    private static String getJarDirectory() {
        try {
            String path = YourClass.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            return new File(path).getParentFile().getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}
