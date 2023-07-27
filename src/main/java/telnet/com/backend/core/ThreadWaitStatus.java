package telnet.com.backend.core;

/**
 * 线程状态
 * <p>
 * @author: cw
 * @since: 2023/7/27 13:06
 * @version: v0.1
 * <p>
 * 修改记录：
 * 时间      修改人员    修改内容
 * ------------------------------
 */
public interface ThreadWaitStatus {


    boolean isWaitStatus();

    void setWaitStatus(boolean waitStatus);
}
