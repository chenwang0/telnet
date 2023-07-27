package telnet.com.backend.util;

import telnet.com.backend.entity.SystemConfig;
import telnet.com.backend.core.factory.SingleFactory;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogImpl {


    // 日志默认输出格式
    public final static String LOG_INFO = "【{0}】- execute [ telnet {1} {2} ] receive the result >: {3}";
    public final static String LOG_TASK_INFO = "【{0}】- task execute [ telnet {1} {2} ] receive the result >: {3}";
    public final static String LOG_DEFAULT_INFO = "【{0}】- system info : {1}";

    private static SystemConfig systemConfig = SingleFactory.getSystemConfig();

    public static void info( String info, final Object... msg ) {

        String dataInfo = DateUtil.getDate();
        String filePath = systemConfig.getPath() + dataInfo + ".log";

        if ( msg.length > 0 ) {

            Object[] objs = new Object[msg.length+1];
            objs[0] = DateUtil.getTime();
            for (int i = 0,j = 1; i < msg.length; i++,j++) {
                objs[j] = msg[i];
            }

            Writer.writer( MessageFormat.format(info, objs), filePath,true);
        } else {

            // 执行 默认格式日志打印 LogImpl.info("msg")
            Writer.writer( MessageFormat.format( LOG_DEFAULT_INFO, DateUtil.getTime(),info ), filePath,true);
        }
    }

    public static void info() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd");
        String dataInfo = format.format(new Date());
        String filePath = systemConfig.getPath() + dataInfo + ".log";

        Writer.writer("", filePath,true);
    }
}
