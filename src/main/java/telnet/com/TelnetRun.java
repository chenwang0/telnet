package telnet.com;

import javafx.application.Application;
import telnet.com.backend.TelnetControl;
import telnet.com.backend.util.ConfigManager;
import telnet.com.backend.util.MonitorManager;
import telnet.com.view.TelnetApplication;


public class TelnetRun {

    public static void main(String[] args) {

        ConfigManager.check();
        MonitorManager.read();
        // 运行
        TelnetControl.runTelnetThread();
        // 运行 task 线程
        TelnetControl.initTask();

        Application.launch(TelnetApplication.class);

    }
}