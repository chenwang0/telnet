package telnet.com.backend.core.factory;

import telnet.com.backend.core.AutoTaskThread;
import telnet.com.backend.core.TelnetThread;
import telnet.com.backend.core.observer.MonitorComponentObserver;
import telnet.com.backend.core.observer.MonitorSubject;
import telnet.com.backend.entity.SystemConfig;
import telnet.com.backend.core.manager.*;
import telnet.com.view.components.item.MonitorComponent;

import java.util.Timer;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 实例工厂
 * <p>
 * @author: cw
 * @since: 2023/7/26 23:04
 * @version: v0.1
 * <p>
 * 修改记录：
 * 时间      修改人员    修改内容
 * ------------------------------
 */
public class SingleFactory {

    // volatile
    private static MonitorManager monitorManager;
    private static ConfigManager configManager;
    private static TelnetControlManager telnetControlManager;
    private static TelnetThread telnetThread;
    private static Thread synctelnetThread;
    private static AutoTaskThread autoTaskThread;
    private static Timer autoTaskTimer;

    private static SystemConfig systemConfig;

    private static MonitorSubject monitorSubject;

    private static final ReentrantLock reentrantLock = new ReentrantLock();


    public static MonitorSubject getMonitorSubject() {
        if (monitorSubject == null) {
            monitorSubject = new MonitorSubject();
            monitorSubject.addObserver(new MonitorComponentObserver());
        }
        return monitorSubject;
    }

    public static MonitorManager getMonitorManager() {

        // 对于小应用不加锁应也没事
        if (monitorManager == null) {
            monitorManager = new MonitorManagerImpl();
        }
        return monitorManager;
    }

    public static ConfigManager getConfigManager() {

        if (configManager == null) {
            SystemConfig config = getSystemConfig();
            configManager = new ConfigManagerImpl(config);
        }
        return configManager;
    }

    public static TelnetThread getTelnetThread() {

        if (telnetThread == null) {

            // waitStatus 属性也可以放在配置中去
            // 应用在运行时是否自启动运行一次检测
            telnetThread = new TelnetThread(false);
        }
        return telnetThread;
    }

    public static AutoTaskThread getAutoTaskThread() {
        if (autoTaskThread == null) {
            autoTaskThread = new AutoTaskThread();
        }
        return autoTaskThread;
    }

    public static Timer getAutoTaskTimer() {
        if (autoTaskTimer == null) {
            autoTaskTimer = new Timer();
        }
        return autoTaskTimer;
    }

    public static SystemConfig getSystemConfig() {
        if (systemConfig == null) {
            systemConfig = new SystemConfig();
        }
        return systemConfig;
    }

    public static Thread getSynctelnetThread() {
        if (synctelnetThread == null) {
            synctelnetThread = new Thread(getTelnetThread());
        }
        return synctelnetThread;
    }

    public static TelnetControlManager getTelnetControlManager() {

        if (telnetControlManager == null) {
            MonitorManager monitorManager = getMonitorManager();
            ConfigManager configManager = getConfigManager();
            TelnetThread telnet = getTelnetThread();
            Thread thread = getSynctelnetThread();
            AutoTaskThread autoTask = getAutoTaskThread();
            Timer taskTimer = getAutoTaskTimer();

            telnetControlManager = new TelnetControlManagerImpl(configManager.get(), monitorManager, telnet, thread, autoTask, taskTimer);
        }
        return telnetControlManager;
    }
}
