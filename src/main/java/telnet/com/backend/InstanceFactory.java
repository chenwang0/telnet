package telnet.com.backend;

import telnet.com.backend.manager.MonitorManager;
import telnet.com.backend.manager.MonitorManagerImpl;

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
public class InstanceFactory {

    private static volatile MonitorManager monitorManager;
    private ReentrantLock monitorLock;

    public static MonitorManager getMonitorManager() {
        if (monitorManager == null) {
            monitorManager = new MonitorManagerImpl();
        }
        return monitorManager;
    }

}
