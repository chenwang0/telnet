package telnet.com.backend.core.observer;

public interface Observer {

    //当主题状态改变时,更新通知
    void update(int version);
}