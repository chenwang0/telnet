package telnet.com.backend.core.manager;

import telnet.com.backend.entity.Monitor;

import java.util.List;
import java.util.Optional;

/**
 * Monitor 监控数据的维护接口
 * @author: cw
 * @since: 2023/7/26 22:00
 * @version: v0.1
 * <p>
 * 修改记录：
 * 时间      修改人员    修改内容
 * ------------------------------
 */
public interface MonitorManager extends ExposeLockApi {


    void dataPersistence();

    List<Monitor> getMonitorList();

    void remove(Monitor monitor);

    void add(Monitor monitor);

    default boolean contains(String username, Integer port) {
        try {

            this.lock();
            return getMonitorList().stream().anyMatch(
                    m ->
                            m.getHostname().equals(username)  && m.getPort().equals(port)
            );
        } finally {
            unlock();
        }
    }

    default Monitor get(String username, Integer port) {
        try {

            this.lock();
            Optional<Monitor> any = getMonitorList().stream().filter(
                    m -> m.getHostname().equals(username) && m.getPort().equals(port)
            ).findAny();
            return any.orElse(null);
        } finally {
            unlock();
        }
    }

    default boolean remove(String username, Integer port) {
        try {

            this.lock();
            boolean removed = getMonitorList().removeIf(monitor -> monitor.getHostname().equals(username) && monitor.getPort().equals(port));
            this.dataPersistence();
            return removed;
        } finally {
            this.unlock();
        }
    }

}
