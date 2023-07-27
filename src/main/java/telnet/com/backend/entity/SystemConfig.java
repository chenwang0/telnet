package telnet.com.backend.entity;

/**
 * 系统配置属性
 * <p>
 * @author: cw
 * @since: 2023/7/27 12:07
 * @version: v0.1
 * <p>
 * 修改记录：
 * 时间      修改人员    修改内容
 * ------------------------------
 */
public class SystemConfig {

    /** 日志输出路径 */
    private String path;
    /** telnet连接超时时间 */
    private Integer timeout;
    /** 线程休息时间 */
    private Integer timeInterval;
    /** 自启动状态 */
    private boolean autoTask;

    public Integer getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(Integer timeInterval) {
        this.timeInterval = timeInterval;
    }

    public boolean isAutoTask() {
        return autoTask;
    }

    public void setAutoTask(boolean autoTask) {
        this.autoTask = autoTask;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
