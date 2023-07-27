package telnet.com.view.components;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import telnet.com.backend.entity.SystemConfig;
import telnet.com.backend.core.factory.SingleFactory;
import telnet.com.backend.core.manager.TelnetControlManager;

/**
 * 监控面板主页
 */
public class TelnetComponent {

    // telnet 操作面板 也可以把面板理解呼唤让 java 创建一个页面吧
    public static final Pane telnetPane = new Pane();
    public static final Scene telnetScene = new Scene(telnetPane);
    static SystemConfig systemConfig = SingleFactory.getSystemConfig();
    static TelnetControlManager tcm = SingleFactory.getTelnetControlManager();

    static {

        Label label = new Label("端口探活应用");
        initComponents(label,25, 45);
        label.setFont(Font.font("微软雅黑", FontWeight.BOLD, 24));


        Label wefng = new Label("中国民生 v0.2");
        wefng.setFont(Font.font("微软雅黑", FontWeight.BOLD, 12));
        wefng.setTextFill(Color.GAINSBORO);
        initComponents(wefng,25, 240);


        // telnet btn
        Button telnetBtn = new Button("run telnet");
        initComponents(telnetBtn,25, 210, 75);
        telnetBtn.setOnAction(e -> tcm.runTelnetThread());


        // task btn
        Button taskBtn = new Button("run task");
        if (systemConfig.isAutoTask()) {
            taskBtn.setText("close task");
        }

        initComponents(taskBtn,110, 210, 75);
        taskBtn.setOnAction(e -> {

            // 点击事件设置按钮切换状态
            if ("close task".equals(taskBtn.getText())) {

                systemConfig.setAutoTask(false);
                taskBtn.setText("run task");
                LogComponent.show("[sys]: 自动任务关闭成功");
            } else {

                systemConfig.setAutoTask(true);
                taskBtn.setText("close task");
                LogComponent.show("[sys]: 自动任务开启成功，等待运行");
            }
        });

        telnetPane.getChildren().addAll( label, wefng, telnetBtn, taskBtn );
        telnetPane.getChildren().addAll(LogComponent.LABEL );
    }


    private static void initComponents(Labeled led, int ...param) {

        led.setLayoutX(param[0]);
        led.setLayoutY(param[1]);
        if ( param.length == 3){

            led.setPrefWidth(param[2]);
        }
    }


}
