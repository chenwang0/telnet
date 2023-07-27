package telnet.com.backend.core.manager;

import telnet.com.backend.entity.ConfigConst;
import telnet.com.backend.entity.SystemConfig;
import telnet.com.backend.util.CheckUtil;
import telnet.com.backend.util.FileUtil;
import telnet.com.backend.util.LogImpl;

import java.io.*;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 系统配置接口实现类
 * 该实现类因设计为全局单例
 * <p>
 * @author: cw
 * @since: 2023/7/27 12:04
 * @version: v0.1
 * <p>
 * 修改记录：
 * 时间      修改人员    修改内容
 * ------------------------------
 */
public class ConfigManagerImpl implements ConfigManager {

    private final SystemConfig systemConfig;

    private final Properties PROPERTIES =  new  Properties();

    private final ReentrantLock confLock;

    public ConfigManagerImpl() {
        this(new SystemConfig(), new ReentrantLock());
    }

    public ConfigManagerImpl(SystemConfig systemConfig) {
        this(systemConfig, new ReentrantLock());
    }

    public ConfigManagerImpl(SystemConfig systemConfig, ReentrantLock confLock) {
        this.confLock = confLock;
        this.systemConfig = systemConfig;
    }



    @Override
    public void sysConfInit() {

        // 检查文件是否存在，不存在则创建
        boolean verify = FileUtil.verify(ConfigConst.C_FILE_PATH);

        try (InputStream in =new FileInputStream(ConfigConst.C_FILE_PATH)) {

            // 加载
            PROPERTIES.load(in);

            // 读入日志输出路径
            if (CheckUtil.empty(systemConfig.getPath()) ) {

                systemConfig.setPath(Optional.ofNullable(PROPERTIES.getProperty("config.path")).orElse(ConfigConst.PATH));
            }

            // 读取超时时间
            if (systemConfig.getTimeout() == null) {

                systemConfig.setTimeout(Optional.ofNullable(CheckUtil.parseInt(PROPERTIES.getProperty("config.timeout"))).orElse(ConfigConst.TIMEOUT));
            }

            // 定时任务执行间隔时间
            if (systemConfig.getTimeInterval() == null) {

                systemConfig.setTimeInterval(Optional.ofNullable(CheckUtil.parseInt(PROPERTIES.getProperty("config.timeInterval"))).orElse(ConfigConst.TIME_INTERVAL));
            }

            // 定时任务自启动状态
            if (systemConfig.getTimeInterval() == null) {
                boolean autoTaskInterval = Boolean.parseBoolean(Optional.ofNullable(PROPERTIES.getProperty("config.autoTask")).orElse(String.valueOf(ConfigConst.AUTO_TASK)));
                systemConfig.setAutoTask(autoTaskInterval);
            }

            if (!verify) {
                // 文件不存在将数据写入
                dataPersistence();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dataPersistence() {
        confLock.lock();
        try ( OutputStream output = new FileOutputStream(ConfigConst.C_FILE_PATH) ) {

            PROPERTIES.setProperty("config.path", systemConfig.getPath());
            PROPERTIES.setProperty("config.timeout", String.valueOf(systemConfig.getTimeout()));
            PROPERTIES.setProperty("config.timeInterval", String.valueOf(systemConfig.getTimeInterval()));
            PROPERTIES.setProperty("config.autoTask", String.valueOf(systemConfig.isAutoTask()));

            // 将数据保存到文件中
            PROPERTIES.store(output, " config ");

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            confLock.unlock();
        }
    }

    @Override
    public void inspect() {

        LogImpl.info();
        LogImpl.info(" telnet 程序正在启动");
        LogImpl.info("开始检查基础配置数据");
        LogImpl.info("检查 monitor.path=" + systemConfig.getPath());
        LogImpl.info("检查 monitor.timeout=" + systemConfig.getTimeout());
        LogImpl.info("检查 monitor.timeInterval=" + systemConfig.getTimeInterval());
        LogImpl.info("检查 monitor.autoTask=" + systemConfig.isAutoTask());
        LogImpl.info("基础配置数据检查结束");
    }

    @Override
    public SystemConfig get() {
        return systemConfig;
    }

    @Override
    public void lock() {
        confLock.lock();
    }

    @Override
    public void unlock() {
        confLock.unlock();
    }

    @Override
    public boolean getResourceStatus() {

        if (confLock.tryLock()) {
            try {
                // 执行操作
                return true;
            } finally {
                confLock.unlock();
            }
        }
        // 锁被其他线程持有，不能执行操作
        return false;
    }
}
