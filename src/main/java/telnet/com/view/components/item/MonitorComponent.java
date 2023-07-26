package telnet.com.view.components.item;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.util.Callback;
import telnet.com.backend.InstanceFactory;
import telnet.com.backend.entity.Monitor;
import telnet.com.backend.entity.StatsSSPVO;
import telnet.com.backend.manager.MonitorManager;
import telnet.com.view.TelnetApplication;

import java.util.ArrayList;
import java.util.List;

public class MonitorComponent {


    public static final StackPane monitorPane = new StackPane();

    private static final TableView<StatsSSPVO> table = new TableView<>();
    private static final ObservableList<StatsSSPVO> data = FXCollections.observableArrayList();

    public static Scene scene = new Scene( monitorPane );

    static {


        TableColumn hostnameCol = new TableColumn("hostname");
//        firstNameCol.setMinWidth(100);
        hostnameCol.setCellValueFactory( new PropertyValueFactory<>("hostname"));


        TableColumn portCol = new TableColumn("port");
//        lastNameCol.setMinWidth(100);
        portCol.setCellValueFactory( new PropertyValueFactory<>("port"));

        TableColumn remarkCol = new TableColumn("remark");
        remarkCol.setMinWidth(200);
        remarkCol.setCellValueFactory( new PropertyValueFactory<>("remark"));


        TableColumn optionsCol = new TableColumn("options");
        optionsCol.setMinWidth(100);
        optionsCol.setCellFactory(new Callback<TableColumn<StatsSSPVO, String>, TableCell<StatsSSPVO, String>>() {
            @Override
            public TableCell<StatsSSPVO, String> call(TableColumn<StatsSSPVO, String> personStringTableColumn) {
                Button btn = new Button("删除");
                return new TableCell<>(){
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction( actionEvent -> {
                                StatsSSPVO vo = getTableView().getItems().get(getIndex());
                                MonitorManager manager = InstanceFactory.getMonitorManager();
                                if (!manager.remove(vo.getHostname(), Integer.parseInt(vo.getPort()))) {
                                    throw new RuntimeException("数据已经不存在");
                                }
                                MonitorComponent.refresh();
                            });
                            //设置按钮
                            setGraphic(btn);
                            //清空文本
                            setText(null);
                        }
                    }
                };
            }
        });


        table.setItems(data);
        table.setPrefWidth(TelnetApplication.width);
        table.setPrefHeight(TelnetApplication.height - 40);
        table.getColumns().addAll(hostnameCol, portCol, remarkCol, optionsCol);

        monitorPane.getChildren().addAll( table );
    }


    public static void refresh() {
        data.clear();
        List<StatsSSPVO> statsSSPVOList = new ArrayList<>();
        MonitorManager monitorManager = InstanceFactory.getMonitorManager();
        monitorManager.getMonitorList().forEach(stats -> statsSSPVOList.add(new StatsSSPVO(stats)) );
        data.addAll(statsSSPVOList);
    }

}

