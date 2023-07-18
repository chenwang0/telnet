package telnet.com.backend.util;

import java.io.*;

public class FileUtil {

    public static boolean verify(String filePath) {
        try {

            File file = new File(filePath);
            File dir = new File(filePath.substring(0, filePath.lastIndexOf("\\")));

            // 判断目录存不存在 不存在则创建
            if (!dir.exists() && dir.isDirectory()) {
                boolean mkdirs = dir.mkdirs();
            }

            // 判断文件是否存在
            if (!file.exists()) {
                boolean newFile = file.createNewFile();

            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("system err");
    }

    public static void remove( String filePath ) {

        File file = new File(filePath);
        boolean delete = file.delete();
    }

    /**
     * 备份 db.tel 文件
     * @param path db.tel 文件路径
     */
    public static void dbFileBackend( String path ) {
        File source = new File(path);

        File target = new File( source.getPath().substring(0, source.getPath().lastIndexOf("\\")) + "\\db_backup.tel" );
        verify(target.getPath());

        try (
                InputStream is = new FileInputStream(source);
                OutputStream os = new FileOutputStream(target)
        ) {
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = is.read(b)) != -1) {
                os.write(b, 0, len);
            }
            LogImpl.info("数据文件备份成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
