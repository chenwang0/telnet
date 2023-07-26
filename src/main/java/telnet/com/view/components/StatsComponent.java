package telnet.com.view.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import telnet.com.backend.InstanceFactory;
import telnet.com.backend.entity.StatsSSPVO;
import telnet.com.view.TelnetApplication;

import java.util.ArrayList;
import java.util.List;

public class StatsComponent {

    public static final StackPane statsPane = new StackPane();

    private static final TableView<StatsSSPVO> table = new TableView<>();
    private static final ObservableList<StatsSSPVO> data = FXCollections.observableArrayList();

    public static Scene scene = new Scene( statsPane );

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


        TableColumn countNumberCol = new TableColumn("countNumber");
//        lastNameCol.setMinWidth(100);
        countNumberCol.setCellValueFactory( new PropertyValueFactory<>("countNumber"));

        TableColumn errNumberCol = new TableColumn("errNumber");
//        lastNameCol.setMinWidth(100);
        errNumberCol.setCellValueFactory( new PropertyValueFactory<>("errNumber"));

        table.setItems(data);
        table.setPrefWidth(TelnetApplication.width);
        table.setPrefHeight(TelnetApplication.height - 40);
        table.getColumns().addAll(hostnameCol, portCol, countNumberCol, errNumberCol, remarkCol);

        statsPane.getChildren().addAll( table );
    }

    public static void refresh( ) {
        data.clear();
        List<StatsSSPVO> statsSSPVOList = new ArrayList<>();
        InstanceFactory.getMonitorManager().getMonitorList().forEach(stats -> statsSSPVOList.add(new StatsSSPVO(stats)) );
        data.addAll(statsSSPVOList);
    }

}
