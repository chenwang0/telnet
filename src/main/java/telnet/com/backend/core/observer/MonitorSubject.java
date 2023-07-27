package telnet.com.backend.core.observer;

import java.util.ArrayList;
import java.util.List;

public class MonitorSubject implements Subject {

    //存放订阅者
    private final List<Observer> observers = new ArrayList<>();
    //期刊版本
    private int version;

    @Override
    public void addObserver(Observer obj) {
        observers.add(obj);
    }

    @Override
    public void deleteObserver(Observer obj) {
        int i = observers.indexOf(obj);
        if(i >= 0) {
            observers.remove(obj);
        }
    }

    @Override
    public void notifyObserver() {
        for (Observer o : observers) {
            o.update(version);
        }
    }

    //该杂志发行了新版本
    public void publish() {
        //新版本
        this.version++;
        //信息更新完毕，通知所有观察者
        this.notifyObserver();
    }
}