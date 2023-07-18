package telnet.com.backend.entity;

/**
 * 常量池
 */
public class ConfigConst {

    public final static String SYS_PATH = System.getProperty("user.dir");

    /** 日志输出路径 */
    public final static String PATH = SYS_PATH + "\\log\\";
    /** telnet连接超时时间 */
    public final static Integer TIMEOUT = 5000;
    /** 线程休息时间 */
    public final static Integer TIME_INTERVAL = 60 * 30 * 1000;
    /** 自启动状态 */
    public final static boolean AUTO_TASK = false;


    /** config file path  */
    public static final String C_FILE_PATH = SYS_PATH + "\\conf\\config.properties";

    /** db file path  */
    public static final String DB_FILE = SYS_PATH + "\\db\\db.tel";

}
