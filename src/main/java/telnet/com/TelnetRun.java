package telnet.com;

import javafx.application.Application;
import telnet.com.backend.core.factory.SingleFactory;
import telnet.com.backend.core.manager.ConfigManager;
import telnet.com.backend.core.manager.TelnetControlManager;
import telnet.com.view.TelnetApplication;

public class TelnetRun {


    public static void main(String[] args) {

        ConfigManager configManager = SingleFactory.getConfigManager();
        configManager.sysConfInit();
        configManager.inspect();

        TelnetControlManager controlManager = SingleFactory.getTelnetControlManager();

        // 运行
        controlManager.runTelnetThread();
        // 运行 task 线程
        controlManager.initAutoTask();

        Application.launch(TelnetApplication.class);
    }
}