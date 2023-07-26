package telnet.com.backend.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {


    public static final SimpleDateFormat DATE = new SimpleDateFormat("yyyy_MM_dd");
    public static final SimpleDateFormat TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getDate(){
        return DATE.format(new Date());
    }
    public static String getTime(){
        return TIME.format(new Date());
    }

}
