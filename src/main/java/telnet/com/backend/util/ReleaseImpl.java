package telnet.com.backend.util;

/**
 * 资源操作线程，安全操作，事后自动释放资源锁
 */
public class ReleaseImpl implements Runnable{

    Function function;

    public void setFunction(Function function) {
        this.function = function;
    }


    @Override
    public void run() {
        while( true ) {
            if (MonitorManager.getMonitorStatus()) {
                LogImpl.info("资源状态安全，可操作资源");
                try {
                    function.run();
                    break;
                }finally {
                    MonitorManager.setMonitorStatus(true);
                    LogImpl.info("资源锁已释放");
                }
            }
        }
    }



    /**
     * 启动线执行程释放锁
     * @param function run
     */
    public static void release( Function function ){

        ReleaseImpl release = new ReleaseImpl();
        release.setFunction(function);
        new Thread(release).start();
    }
}
