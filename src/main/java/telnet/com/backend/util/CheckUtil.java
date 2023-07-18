package telnet.com.backend.util;

public class CheckUtil {


    public static boolean empty(String str){

        return str == null || str.replace(" ","").length() == 0;
    }

    public static Integer parseInt(String data){

        return data == null ? null : Integer.parseInt(data);
    }


}
