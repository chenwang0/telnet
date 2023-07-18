package telnet.com.backend.util;

import java.io.*;
import java.nio.charset.StandardCharsets;


/**
 * @author chWein
 * @remark 文件写入工具类
 * @createTime 2022/5/13 - 3:53
 */
public class Writer {

    /**
     * 文件写入
     *
     * @param context  写入的内容
     * @param fileInfo 写入文件的文件名及路径
     * @param append   是否将内容追加到末尾
     */
    public static void writer(final String context, final String fileInfo, boolean append) {

        exists(fileInfo);

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(fileInfo, append),
                        StandardCharsets.UTF_8))) {

            writer.write(context);
            writer.newLine();
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param path 判断文件是否存在
     */
    private static void exists(String path) {

        File file = new File(path);
        if (!file.getParentFile().exists()) {
            boolean result = file.getParentFile().mkdirs();
            if (!result) {
                System.out.println("创建失败");
            }
        }
    }
}