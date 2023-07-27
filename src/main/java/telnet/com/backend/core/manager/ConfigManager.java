package telnet.com.backend.core.manager;

import telnet.com.backend.entity.SystemConfig;

/**
 * 系统配置类的维护接口
 *
 * <p>
 * @author: cw
 * @since: 2023/7/27 11:58
 * @version: v0.1
 * <p>
 * 修改记录：
 * 时间      修改人员    修改内容
 * ------------------------------
 */
public interface ConfigManager extends ExposeLockApi {


    /**
     * 声明系统配置初始化接口
     */
    void sysConfInit();

    /**
     * 声明配置文件数据持久化实现方法
     */
    void dataPersistence();


    /**
     * 声明程序系统属性检查方法
     * 窗体启动前，系统配置初始化后执行
     */
    void inspect();

    SystemConfig get();
}
