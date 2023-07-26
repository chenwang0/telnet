package telnet.com.view.components.item;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import telnet.com.backend.InstanceFactory;
import telnet.com.backend.entity.Monitor;
import telnet.com.backend.manager.MonitorManager;
import telnet.com.backend.util.CheckUtil;

/**
 * 配置新增组件
 */
public class MonitorAddComponent {

    public static final GridPane monitorAddPane = new GridPane();
    public static Scene scene = new Scene( monitorAddPane );

    static {
        monitorAddPane.setPadding(new Insets(5));
        monitorAddPane.setHgap(5);
        monitorAddPane.setVgap(5);
        ColumnConstraints column1 = new ColumnConstraints(100);
        ColumnConstraints column2 = new ColumnConstraints(50, 150, 300);
        column2.setHgrow(Priority.ALWAYS);
        monitorAddPane.getColumnConstraints().addAll(column1, column2);

        Label hintLab = new Label();
        Label hostnameLab = new Label("*hostname:");
        TextField hostnameFld = new TextField();
        Label portLab = new Label("*port:");
        TextField portFld = new TextField();
        Label remarkLab = new Label("remark:");
        TextField remarkFld = new TextField();


        Button saveButt = new Button("Save");
        saveButt.setOnAction( e -> {

            hintLab.setTextFill(Color.RED);
            hintLab.setText("");
            if (CheckUtil.empty(  hostnameFld.getText() )) {
                hintLab.setText("hostname 不能为空！ ");
                return;
            }
            if ( CheckUtil.empty( portFld.getText() )){
                hintLab.setText("port 不能为空！ ");
                return;
            }

            MonitorManager monitorManager = InstanceFactory.getMonitorManager();

            if (monitorManager.contains(hostnameFld.getText(), Integer.parseInt(portFld.getText()))) {

                hintLab.setText("这条数据已存在！"+hostnameFld.getText() + ":" + portFld.getText());
                return;
            }

            int anInt = Integer.parseInt(portFld.getText());
            if ( anInt < 0 || anInt > 65535 ) {
                hintLab.setText("端口号超出范围 0 - 65535");
                return;
            }

            hintLab.setTextFill(Color.GREEN);
            hintLab.setText("添加成功");
            monitorManager.add( new Monitor( hostnameFld.getText(),  Integer.parseInt(portFld.getText()),  remarkFld.getText()));
        });

        // hostname label
        GridPane.setHalignment(hostnameLab, HPos.RIGHT);
        monitorAddPane.add(hostnameLab, 0, 0);
        // hostname field
        GridPane.setHalignment(hostnameFld, HPos.LEFT);
        monitorAddPane.add(hostnameFld, 1, 0);

        // port label
        GridPane.setHalignment(portLab, HPos.RIGHT);
        monitorAddPane.add(portLab, 0, 1);
        // port field
        GridPane.setHalignment(portFld, HPos.LEFT);
        monitorAddPane.add(portFld, 1, 1);

        // remark label
        GridPane.setHalignment(remarkLab, HPos.RIGHT);
        monitorAddPane.add(remarkLab, 0, 2);
        // remark field
        GridPane.setHalignment(remarkFld, HPos.LEFT);
        monitorAddPane.add(remarkFld, 1, 2);

        // hint label
        GridPane.setHalignment(hintLab, HPos.RIGHT);
        monitorAddPane.add(hintLab, 1, 3);
        // Save button
        GridPane.setHalignment(saveButt, HPos.RIGHT);
        monitorAddPane.add(saveButt, 0, 3);


    }

}
