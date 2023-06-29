package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfReader {
    public static void main(String[] args) {
        String inputFilePath = "E:\\桌面\\计算机书籍\\程序员内功修炼-V2.0.pdf"; // 输入PDF文件路径
        String outputFilePath = "E:\\桌面\\计算机书籍\\pdfreader.txt"; // 输出txt文件路径

        try {
            PDDocument document = PDDocument.load(new File(inputFilePath));
            PDFTextStripper stripper = new PDFTextStripper();
//            stripper.setParagraphStart("<START>");
//            stripper.setParagraphEnd("<END>");

            FileWriter writer = new FileWriter(outputFilePath);

            int count = 0;
            StringBuilder sb = new StringBuilder();
            for (int pageNum = 1; pageNum <= document.getNumberOfPages(); pageNum++) {
                stripper.setStartPage(pageNum);
                stripper.setEndPage(pageNum);
                String pageText = stripper.getText(document);

                sb.append(pageText);
                sb.append("\n\n");

                count++;
                if (count == 5) {
                    writer.write(sb.toString());
                    writer.write("\n\n---\n\n"); // 分隔符
                    sb.setLength(0);
                    count = 0;
                }
            }

            // 处理剩余的自然段
            if (sb.length() > 0) {
                writer.write(sb.toString());
            }

            writer.close();
            document.close();

            System.out.println("转换完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
