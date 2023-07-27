package telnet.com.view;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import telnet.com.backend.core.factory.SingleFactory;
import telnet.com.backend.core.manager.TelnetControlManager;
import telnet.com.backend.util.LogImpl;
import telnet.com.view.components.HomeComponent;

import java.util.Timer;

public class TelnetApplication extends Application {

    public static final int width = 600;
    public static final int height = 370;

    private TelnetControlManager controlManager;
    private Timer autoTaskTimer = SingleFactory.getAutoTaskTimer();
    private Thread syncTelnetThread = SingleFactory.getSynctelnetThread();

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


            autoTaskTimer.cancel();
            LogImpl.info("自动任务线程关闭成功！");
            syncTelnetThread.interrupt();
            LogImpl.info("手动telnet线程关闭成功！");

            LogImpl.info("程序已关闭");
        });

        // setting logo
        stage.getIcons().add(new Image( "logo.ico" ));

    }


}