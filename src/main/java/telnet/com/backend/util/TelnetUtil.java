package telnet.com.backend.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author Miracle Luna
 * @version 1.0
 * @date 2019/12/16 16:11
 */
public class TelnetUtil {


    /**
     * 测试telnet 机器端口的连通性
     * @param hostname  检测 ip
     * @param port      检测端口
     * @param timeout   超时时间
     * @return  返回监测结果：true表示存活，false则表示不连通
     */
    public static boolean telnet(String hostname, int port, int timeout){

        boolean isConnected = false;

        try (Socket socket = new Socket()) {

            // 建立连接
            socket.connect(new InetSocketAddress(hostname, port), timeout);

            // 通过现有方法查看连通状态 true为连通
            isConnected = socket.isConnected();

        } catch (IOException e) {
            System.out.println(hostname+":"+port+"发生异常："+e.getMessage());
        }

        return isConnected;
    }
}
