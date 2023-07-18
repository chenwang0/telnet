package telnet.com.view;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import telnet.com.backend.TelnetControl;
import telnet.com.backend.util.ConfigManager;
import telnet.com.backend.util.LogImpl;
import telnet.com.backend.util.MonitorManager;
import telnet.com.view.components.HomeComponent;

public class TelnetApplication extends Application {

    public static final int width = 600;
    public static final int height = 370;

    @Override
    public void start(Stage stage) {

        // setting window title
        stage.setTitle("telnet");
        // When open window show HomeScene
        stage.setScene( HomeComponent.homeScene );
        // setting window width
        stage.setWidth(width);
        // setting window height
        stage.setHeight(height);

        // show window
        stage.show();

        // setting window closes execute matter
        stage.setOnCloseRequest(event -> {

            LogImpl.info();
            LogImpl.info("程序即将关闭，进行资源清扫");
            // 窗口关闭时处理线程关闭
            TelnetControl.taskThread.cancel();
            LogImpl.info("自动任务线程关闭成功！");
            TelnetControl.telnetThread.interrupt();
            LogImpl.info("手动telnet线程关闭成功！");

            LogImpl.info("程序已关闭");
        });

        // setting logo
        stage.getIcons().add(new Image( "logo.ico" ));

    }


}