package telnet.com.backend.core.manager;

/**
 * 暴露锁接口
 * <p>
 * @author: cw
 * @since: 2023/7/27 12:37
 * @version: v0.1
 * <p>
 * 修改记录：
 * 时间      修改人员    修改内容
 * ------------------------------
 */
public interface ExposeLockApi {

    void lock();

    void unlock();

    boolean getResourceStatus();
}
