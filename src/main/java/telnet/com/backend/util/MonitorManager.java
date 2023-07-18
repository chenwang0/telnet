package telnet.com.backend.util;

import com.alibaba.fastjson2.JSON;
import telnet.com.backend.entity.ConfigConst;
import telnet.com.backend.entity.Monitor;
import telnet.com.view.components.item.MonitorComponent;

import java.io.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * manager data
 * v0.1: db file used .properties
 * v0.2: db file use .json
 * data save at run end time execute or
 * every time in remove() and add().
 * @author cwang
 */
public class MonitorManager {

    /** monitor list data   */
    public static CopyOnWriteArrayList<Monitor> monitorList = new CopyOnWriteArrayList<>();


    // read db file, initial system data
    public static void read() {

        // check file or create
        FileUtil.verify(ConfigConst.DB_FILE);

        try(
                //open the file
                FileReader file = new FileReader(ConfigConst.DB_FILE);
                BufferedReader buffer = new BufferedReader(file);
        ){

            //read the file line and uncompress
            String data = buffer.readLine();
            if (!CheckUtil.empty(data)){
                List<Monitor> tempList = JSON.parseArray(data, Monitor.class);
                // initial not lock
                monitorList.addAll(tempList);
            }

        } catch (IOException e) {
            e.printStackTrace();
            LogImpl.info("启动异常! 存储文件 ”db.tel“ 被损坏");
            FileUtil.remove(ConfigConst.DB_FILE);
        }
    }

    /**
     * 整体数据覆盖文件
     */
    public synchronized static void whiteData( ) {

        String data = JSON.toJSONString(getMonitorList());
        LogImpl.info(data);
        Writer.writer( data, ConfigConst.DB_FILE, false);
    }


    /**
     * 资源锁，避免任务线程使用资源不同步，避免资源数据乱
     */
    private static volatile boolean monitorStatus = true;
    public synchronized static boolean getMonitorStatus() {
        return monitorStatus;
    }
    public synchronized static void setMonitorStatus(boolean monitorStatus) {
        MonitorManager.monitorStatus = monitorStatus;
    }

    /**
     * 获取监控数据资源
     * 给资源加锁，避免线程使用同一资源时数据不同步问题
     * @return 返回监控资源
     */
    public synchronized static List<Monitor> getMonitorList() {
        setMonitorStatus(false);
        return monitorList;
    }



    /**
     * 删除实现方法
     * @param monitor 删除的监控信息对象
     */
    public synchronized static void remove( Monitor monitor ) {
        // 使用线程移除数据
        ReleaseImpl.release(() -> {
            // 从集合中删除当前元素
            getMonitorList().remove(monitor);
            // 将数据持久化到文件
            whiteData();
            // 刷新统计页面数据  StatsComponent.refresh();
            // 刷新监控页面数据
            MonitorComponent.refresh();
        });
    }

    /**
     * 添加数据实现方法
     * @param monitor 保存的监控对象
     */
    public synchronized static void add(  Monitor monitor ) {

        // 使用线程进行数据累加，自动释放资源
        ReleaseImpl.release(() -> {
            // 数据保存集合
            getMonitorList().add(monitor);
            // 将数据持久化到文件
            whiteData();
            // 刷新统计页面数据 StatsComponent.refresh();
            // 刷新监控页面数据
            MonitorComponent.refresh();
        });
    }

}
