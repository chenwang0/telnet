package telnet.com.backend;

import telnet.com.view.components.LogComponent;

/**
 * @remark: telnet 手工线程
 * @author: chwein
 * @since: 2022/9/27 - 15:51
 */
public class TelnetThread implements Runnable {

    public boolean status = false;

    @Override
    public void run() {

        // 触发条件设置中断 thread.interrupt();
        while (!Thread.currentThread().isInterrupted()){

            if (status) {
                LogComponent.print( "[tel]: 启动执行 telnet ..." );
                try {

                    TelnetControl.telnet();

                } catch (Exception e) {
                    e.printStackTrace();
                    LogComponent.print("[err]: 建议在任务不工作的情况下添加或删除监控数据");
                } finally {
                    status = false;
                    LogComponent.print( "[tel]: telnet 工作结束" );
                }
            }
        }
    }
}
