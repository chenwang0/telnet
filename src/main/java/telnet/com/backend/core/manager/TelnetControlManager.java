package telnet.com.backend.core.manager;

/**
 * Telnet控制管理器。
 * 全局单例
 * <p>
 * @author: cw
 * @since: 2023/7/27 12:47
 * @version: v0.1
 * <p>
 * 修改记录：
 * 时间      修改人员    修改内容
 * ------------------------------
 */
public interface TelnetControlManager {

    /**
     * 初始化自动任务线程，是否启动执行
     */
    void initAutoTask();

    /**
     * 取消自动任务
     */
    void cancelAutoTask();

    /**
     * 以异步的方式运行一次 telnet
     */
    void runTelnetThread();

    /**
     * telnet 实现方法
     * telnet 信息时时面板交互
     */
    void telnet();
}
