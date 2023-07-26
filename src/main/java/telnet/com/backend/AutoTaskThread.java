package telnet.com.backend;

import telnet.com.backend.util.ConfigManager;
import telnet.com.backend.util.LogImpl;
import telnet.com.view.components.LogComponent;

import java.util.TimerTask;

/**
 * 自动运行定时任务线程
 */
public class AutoTaskThread extends TimerTask {

    public boolean runStatus = ConfigManager.autoTask;

    @Override
    public void run() {
        if ( runStatus && !Thread.currentThread().isInterrupted()) {
            LogImpl.info();
            LogImpl.info("will run task");
            LogComponent.print("[task]: 自动任务即将工作");

            try {
                // 运行监控
                TelnetControl.telnet();
            } catch (Exception e) {
                e.printStackTrace();
                LogComponent.print("[err]: 建议在任务不工作的情况下新增或删除数据");
                LogImpl.info(e.getClass() + ": " + e.getMessage());
            }
            LogComponent.print("[task]: 自动任务工作结束");
            LogImpl.info("task termination");
        }
    }

}