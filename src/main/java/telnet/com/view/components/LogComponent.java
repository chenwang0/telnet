package telnet.com.view.components;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import telnet.com.backend.util.CheckUtil;
import telnet.com.backend.util.DateUtil;

/**
 * 日志打印组件
 */
public class LogComponent {


    public static final Label[] LABEL = new Label[6];

    static {


        LABEL[0] = new Label();
        LABEL[0].setFont(Font.font("黑体", FontWeight.NORMAL, 11));
        LABEL[0].setLayoutX(26);
        LABEL[0].setLayoutY(100);
        LABEL[0].setTextFill(Color.GRAY);

        for (int i = 1; i < LABEL.length; i++) {

            LABEL[i] = new Label();
            LABEL[i].setFont(Font.font("黑体", FontWeight.NORMAL, 11));
            LABEL[i].setLayoutX( 26 );
            LABEL[i].setLayoutY( LABEL[i-1].getLayoutY() + 18  );
            LABEL[i].setTextFill(Color.GRAY);

        }

    }

    // 其他线程打印面板信息实现方法
    public static void print( String msg ){
        Platform.runLater(()->show(msg));
    }

    // 打印信息实现方法
    public static void show( String msg ) {

        for (int i = 0; i < LABEL.length -1; i++) {
            LABEL[i].setText( LABEL[i+1].getText() );
        }
        LABEL[LABEL.length-1].setText(msg);

        for (Label label : LABEL) {
            if (!CheckUtil.empty(label.getText())) {
                if (label.getText().contains("err") || label.getText().contains("sys")) {
                    label.setTextFill(Color.DARKRED);
                } else {
                    label.setTextFill(Color.GRAY);
                }
            }
        }
    }


}
