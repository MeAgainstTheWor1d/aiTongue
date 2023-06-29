package org.example;

import java.io.*;

public class TxtDuplicateCharacterRemover {
    public static void main(String[] args) {
        String inputFilePath = "E:\\桌面\\计算机书籍\\pdfreader.txt"; // 输入PDF文件路径
        String outputFilePath = "E:\\桌面\\计算机书籍\\pdfreader2.txt"; // 输出txt文件路径

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFilePath));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFilePath));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String processedLine = removeDuplicateCharacters(line);
                bufferedWriter.write(processedLine);
                bufferedWriter.newLine();
            }

            bufferedReader.close();
            bufferedWriter.close();

            System.out.println("处理完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String removeDuplicateCharacters(String text) {
        StringBuilder stringBuilder = new StringBuilder();

        char prevChar = '\0';
        int repeatCount = 0;
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);

            if (currentChar == 12068) {
                continue;
            }

            if (currentChar == prevChar) {
                repeatCount++;
            } else {
                repeatCount = 1;
            }

            if (repeatCount <= 2) {
                stringBuilder.append(currentChar);
            }

            prevChar = currentChar;
        }

        return stringBuilder.toString();
    }
}
