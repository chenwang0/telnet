package telnet.com.backend.core;

import telnet.com.backend.core.factory.SingleFactory;
import telnet.com.view.components.LogComponent;

/**
 * @remark: telnet 手工线程
 * @author: cw
 * @since: 2022/9/27 - 15:51
 */
public class TelnetThread implements Runnable, ThreadWaitStatus {

    private boolean waitStatus;

    public TelnetThread(boolean waitStatus) {
        this.waitStatus = waitStatus;
    }


    @Override
    public void run() {

        // 触发条件设置中断 thread.interrupt();
        while (!Thread.currentThread().isInterrupted()){

            if (isWaitStatus()) {
                LogComponent.print( "[tel]: 启动执行 telnet ..." );
                try {

                    SingleFactory.getTelnetControlManager().telnet();

                } catch (Exception e) {
                    e.printStackTrace();
                    LogComponent.print("[err]: 建议在任务不工作的情况下添加或删除监控数据");
                } finally {
                    this.setWaitStatus(false);
                    LogComponent.print( "[tel]: telnet 工作结束" );
                }
            }
        }
    }


    public boolean isWaitStatus() {
        return waitStatus;
    }

    public void setWaitStatus(boolean waitStatus) {
        this.waitStatus = waitStatus;
    }
}
