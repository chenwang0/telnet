package telnet.com.view.components.item;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import telnet.com.backend.entity.ConfigConst;
import telnet.com.backend.util.CheckUtil;
import telnet.com.backend.util.ConfigManager;

import java.util.Objects;


/**
 * 系统配置
 */
public class SystemConfigComponent {

    public static final GridPane systemPane = new GridPane();
    public static Scene scene = new Scene( systemPane );
    /** 设置显示状态 */
    public static Boolean showStatus = true;

    static int rowIndex = 0;


    private static final Label hintLab = new Label();
    private static final Label timeoutLab = new Label("监控超时时间: ");
    private static final Label timeIntervalLab = new Label("自动任务间隔时间: ");
    private static final Label autoTaskLab = new Label("开启自动任务: ");
    private static final Label pathLab = new Label("日志文件路径: ");


    private static final TextField timeoutFld = new TextField( ConfigManager.timeout.toString() );
    private static final TextField timeIntervalFld = new TextField( ConfigManager.timeInterval.toString() );
    private static final TextField autoTaskFld = new TextField( ConfigManager.timeInterval.toString() );
    private static final TextField pathFld = new TextField( ConfigManager.path );

    static final ChoiceBox<String> autoTaskCB = new ChoiceBox<>(FXCollections.observableArrayList("true", "false"));


    private static final Button openEditBtn = new Button("开启编辑");
    private static final Button cancelBtn = new Button("取消编辑");
    private static final Button editBtn = new Button("保存编辑");

    static {

        systemPane.setPadding(new Insets(5));
        systemPane.setHgap(5);
        systemPane.setVgap(5);
        ColumnConstraints column1 = new ColumnConstraints(100);
        ColumnConstraints column2 = new ColumnConstraints(50, 150, 300);
        column2.setHgrow(Priority.ALWAYS);
        systemPane.getColumnConstraints().addAll(column1, column2);

        timeoutFld.setDisable(showStatus);
        timeIntervalFld.setDisable(showStatus);
        autoTaskFld.setDisable(showStatus);
        pathFld.setDisable(showStatus);


        timeoutFld.setPromptText("设置定时任务执行间隔时间(默认: 5000 ms )");
        timeIntervalFld.setPromptText("设置连接超时时间(默认: 1,800,000 ms ) ");
        autoTaskFld.setPromptText("设置自启动定时任务(默认: false)");
        pathFld.setPromptText("设置日志输出位置(默认: C:\\telnet\\log\\ )");


        autoTaskCB.setValue(String.valueOf(ConfigManager.autoTask));
        autoTaskCB.setDisable(showStatus);

        // hostname label
        GridPane.setHalignment(timeoutLab, HPos.RIGHT);
        systemPane.add(timeoutLab, 0, rowIndex);
        // hostname field
        GridPane.setHalignment(timeoutFld, HPos.LEFT);
        systemPane.add(timeoutFld, 1, rowIndex++);

        // port label
        GridPane.setHalignment(timeIntervalLab, HPos.RIGHT);
        systemPane.add(timeIntervalLab, 0, rowIndex);
        // port field
        GridPane.setHalignment(timeIntervalFld, HPos.LEFT);
        systemPane.add(timeIntervalFld, 1, rowIndex++);

        // auto Task label
        GridPane.setHalignment(autoTaskLab, HPos.RIGHT);
        systemPane.add(autoTaskLab, 0, rowIndex);
        // auto Task field
        GridPane.setHalignment(autoTaskCB, HPos.LEFT);
        systemPane.add(autoTaskCB, 1, rowIndex++);

        // log path label
        GridPane.setHalignment(pathLab, HPos.RIGHT);
        systemPane.add(pathLab, 0, rowIndex);
        // log path field
        GridPane.setHalignment(pathFld, HPos.LEFT);
        systemPane.add(pathFld, 1, rowIndex++);


        // hint label
        GridPane.setHalignment(hintLab, HPos.CENTER);
        systemPane.add(hintLab, 0, rowIndex++, 2 , 1);


        // open btn button
        // cancel button
        // edit button
        GridPane.setHalignment(openEditBtn, HPos.LEFT);
        GridPane.setHalignment(cancelBtn, HPos.CENTER);
        GridPane.setHalignment(editBtn, HPos.RIGHT);
        systemPane.add(openEditBtn, 1, rowIndex );
        systemPane.add(cancelBtn, 1, rowIndex);
        systemPane.add(editBtn, 1, rowIndex++);



        setBtn();
    }



    static void setBtn() {

        cancelBtn.setVisible(false);
        editBtn.setVisible(false);

        openEditBtn.setOnAction( e -> {
            //自身隐藏
            openEditBtn.setVisible(false);

            //其他按钮显示
            cancelBtn.setVisible(true);
            editBtn.setVisible(true);

            showStatus = !showStatus;

            //输入框取消禁用
            timeoutFld.setDisable(showStatus);
            timeIntervalFld.setDisable(showStatus);
            autoTaskFld.setDisable(showStatus);
            pathFld.setDisable(showStatus);
            autoTaskCB.setDisable(showStatus);
        });

        cancelBtn.setOnAction( e -> {
            //开启编辑按钮恢复显示
            openEditBtn.setVisible(true);

            //其他按钮隐藏
            cancelBtn.setVisible(false);
            editBtn.setVisible(false);

            showStatus = !showStatus;

            //输入框开启禁用
            timeoutFld.setDisable(showStatus);
            timeIntervalFld.setDisable(showStatus);
            autoTaskFld.setDisable(showStatus);
            pathFld.setDisable(showStatus);
            autoTaskCB.setDisable(showStatus);

            refresh();
        });

        // 编辑提交
        editBtn.setOnAction( e -> {

            hintLab.setTextFill(Color.RED);
            hintLab.setText("");

            if (CheckUtil.empty( timeoutFld.getText() )) {
                ConfigManager.timeout = ConfigConst.TIMEOUT;
            } else {
                ConfigManager.timeout = Integer.valueOf(timeoutFld.getText());
            }

            if ( CheckUtil.empty( timeIntervalFld.getText() )){
                ConfigManager.timeInterval = ConfigConst.TIME_INTERVAL;
            } else {
                ConfigManager.timeInterval = Integer.valueOf(timeIntervalFld.getText());
            }

            if ( CheckUtil.empty( autoTaskCB.getValue() )){
                ConfigManager.autoTask = ConfigConst.AUTO_TASK;
            } else {
                ConfigManager.autoTask = Boolean.parseBoolean(autoTaskCB.getValue());
            }

            if ( CheckUtil.empty(pathFld.getText() )){
                ConfigManager.path = ConfigConst.PATH;
            } else {
                ConfigManager.path = String.valueOf(pathFld.getText());
            }

            ConfigManager.read();

            refresh();
            hintLab.setTextFill(Color.GREEN);
            hintLab.setText("编辑成功！下次启动生效");
            cancelBtn.fire();
        });
    }


    static void refresh() {
        timeoutFld.setText( ConfigManager.timeout.toString() );
        timeIntervalFld.setText( ConfigManager.timeInterval.toString() );
        autoTaskFld.setText( ConfigManager.timeInterval.toString() );
        pathFld.setText( ConfigManager.path );
        autoTaskCB.setValue(String.valueOf(ConfigManager.autoTask));
    }


}
