package telnet.com.backend.manager;

import com.alibaba.fastjson2.JSON;
import telnet.com.backend.entity.ConfigConst;
import telnet.com.backend.entity.Monitor;
import telnet.com.backend.util.*;
import telnet.com.view.components.item.MonitorComponent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 数据维护接口接口实现
 * <p>
 * @author: cw
 * @since: 2023/7/26 22:36
 * @version: v0.1
 * <p>
 * 修改记录：
 * 时间      修改人员    修改内容
 * ------------------------------
 */
public class MonitorManagerImpl implements MonitorManager {

    /** monitor list data   */
    private final CopyOnWriteArrayList<Monitor> monitorList;
    private final ReentrantLock monitorLock = new ReentrantLock();

    public MonitorManagerImpl() {
        monitorList = new CopyOnWriteArrayList<>();
        this.initialize();
    }

    public MonitorManagerImpl(CopyOnWriteArrayList<Monitor> monitorList) {
        this.monitorList = monitorList;
        this.initialize();
    }

    // read db file, initial system data
    private void initialize() {

        // write to db file, the db does not exist to create
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

    @Override
    public boolean getResourceStatus() {
        if (monitorLock.tryLock()) {
            try {
                // 执行操作
                return true;
            } finally {
                monitorLock.unlock();
            }
        }
        // 锁被其他线程持有，不能执行操作
        return false;
    }

    @Override
    public void dataPersistence() {

        String data = JSON.toJSONString(getMonitorList());
        LogImpl.info(data);
        Writer.writer(data, ConfigConst.DB_FILE, false);
    }

    @Override
    public List<Monitor> getMonitorList() {
        return this.monitorList;
    }

    @Override
    public void remove(Monitor monitor) {

        Function function = () -> {
            // 从集合中删除当前元素
            getMonitorList().remove(monitor);
            // 将数据持久化到文件
            dataPersistence();
            // 刷新统计页面数据  StatsComponent.refresh();
            // 刷新监控页面数据
            MonitorComponent.refresh();
        };

        // 使用线程移除数据
        ReleaseImpl.release(monitorLock, function);
    }

    @Override
    public void add(Monitor monitor) {

        Function function = () -> {
            // 数据保存集合
            getMonitorList().add(monitor);
            // 将数据持久化到文件
            dataPersistence();
            // 刷新统计页面数据 StatsComponent.refresh();
            // 刷新监控页面数据
            MonitorComponent.refresh();
        };

        // 使用线程进行数据累加，自动释放资源
        ReleaseImpl.release(monitorLock, function);
    }

    @Override
    public void lock() {
        monitorLock.lock();
    }

    @Override
    public void unlock() {
        monitorLock.unlock();
    }
}
