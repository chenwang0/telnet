//package telnet.com.backend.util;
//
//import com.alibaba.fastjson2.JSON;
//import telnet.com.backend.entity.ConfigConst;
//import telnet.com.backend.entity.Monitor;
//import telnet.com.view.components.item.MonitorComponent;
//
//import java.io.*;
//import java.util.*;
//import java.util.concurrent.CopyOnWriteArrayList;
//
///**
// * manager data
// * v0.1: db file used .properties
// * v0.2: db file use .json
// * data save at run end time execute or
// * every time in remove() and add().
// * @author cw
// */
//@Deprecated
//public class MonitorManager {
//
//    /** monitor list data   */
//    public static CopyOnWriteArrayList<Monitor> monitorList = new CopyOnWriteArrayList<>();
//
//
//    // read db file, initial system data
//    public static void read() {
//
//        // write to db file, the db does not exist to create
//        FileUtil.verify(ConfigConst.DB_FILE);
//
//        try(
//                //open the file
//                FileReader file = new FileReader(ConfigConst.DB_FILE);
//                BufferedReader buffer = new BufferedReader(file);
//        ){
//
//            //read the file line and uncompress
//            String data = buffer.readLine();
//            if (!CheckUtil.empty(data)){
//                List<Monitor> tempList = JSON.parseArray(data, Monitor.class);
//                // initial not lock
//                monitorList.addAll(tempList);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            LogImpl.info("启动异常! 存储文件 ”db.tel“ 被损坏");
//            FileUtil.remove(ConfigConst.DB_FILE);
//        }
//    }
//
//    /**
//     * 整体数据覆盖文件
//     */
//    public synchronized static void whiteData( ) {
//
//        String data = JSON.toJSONString(getMonitorList());
//        LogImpl.info(data);
//        Writer.writer( data, ConfigConst.DB_FILE, false);
//    }
//
//
//    /**
//     * 资源锁，避免任务线程使用资源不同步，避免资源数据乱
//     */
//    @Deprecated
//    private static volatile boolean monitorStatus = true;
//    @Deprecated
//    public synchronized static boolean getMonitorStatus() {
//        return monitorStatus;
//    }
//    @Deprecated
//    public synchronized static void setMonitorStatus(boolean monitorStatus) {
//        MonitorManager.monitorStatus = monitorStatus;
//    }
//
//    /**
//     * 获取监控数据资源
//     * 给资源加锁，避免线程使用同一资源时数据不同步问题
//     * @return 返回监控资源
//     */
//    public synchronized static List<Monitor> getMonitorList() {
//        setMonitorStatus(false);
//        return monitorList;
//    }
//
//
//
//    /**
//     * 删除实现方法
//     * @param monitor 删除的监控信息对象
//     */
//    public synchronized static void remove( Monitor monitor ) {
//    }
//
//    /**
//     * 添加数据实现方法
//     * @param monitor 保存的监控对象
//     */
//    public synchronized static void add(  Monitor monitor ) {
//
//
//    }
//
//}
