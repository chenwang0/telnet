package telnet.com.backend.util;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 资源操作线程，安全操作，事后自动释放资源锁
 */
public class ReleaseImpl implements Runnable {

    final Function function;
    final ReentrantLock lock;

    public ReleaseImpl(Function function, ReentrantLock lock) {
        this.function = function;
        this.lock = lock;
    }

    @Override
    public void run() {
        lock.lock();
        try {

            LogImpl.info("资源状态安全，可操作资源");
            function.run();
        } finally {
            lock.unlock();
        }
    }


    /**
     * 启动线执行程释放锁
     * 目的想避免单线程卡顿
     * @param function run
     */
    public static void release( ReentrantLock lock, Function function ) {

        ReleaseImpl release = new ReleaseImpl(function, lock);
        new Thread(release).start();
    }
}
