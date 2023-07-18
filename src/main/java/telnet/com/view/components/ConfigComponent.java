package telnet.com.view.components;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import telnet.com.view.components.item.MonitorAddComponent;
import telnet.com.view.components.item.MonitorComponent;
import telnet.com.view.components.item.SystemConfigComponent;

/**
 * @remark: 配置管理面板
 * @author: cwang
 * @since: 2022/9/27 - 15:51
 */
public class ConfigComponent {

    public static final BorderPane configPane = new BorderPane();
    public static final Scene configScene = new Scene(configPane);

    static {

        TreeItem<String> rootItem = new TreeItem<>("配置管理");
        TreeItem<String> configItem = new TreeItem<>("监控数据");
        TreeItem<String> configAddItem = new TreeItem<>("新增监控数据");
        TreeItem<String> systemConfigItem = new TreeItem<>("系统配置");

        rootItem.setExpanded(true);
        rootItem.getChildren().add( configItem );
        rootItem.getChildren().add( configAddItem);
        rootItem.getChildren().add( systemConfigItem);
        TreeView<String> tree = new TreeView<>(rootItem);
        // 设置树形菜单组件的显示宽度
        tree.setPrefWidth(120);

        // 设置树形菜单点击事件
        tree.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            Node node = event.getPickResult().getIntersectedNode();

            // 判断点击所属节点
            if (tree.getSelectionModel().getSelectedItem().equals(configItem)) {
                // 数据表格执行：

                // 每次点击时刷新表格中的数据
                MonitorComponent.refresh();
                // 将监控数据操作面板添加到 配置管理面板中
                configPane.setCenter( MonitorComponent.monitorPane );
            } else if ( tree.getSelectionModel().getSelectedItem().equals(configAddItem) ) {
                // 系统监控数据执行：

                // 系统监控数据面板添加到 配置管理面板中
                configPane.setCenter( MonitorAddComponent.monitorAddPane );
            } else if ( tree.getSelectionModel().getSelectedItem().equals(systemConfigItem) ) {
                // 系统配置：

                // 系统配置添加到 配置管理面板中
                configPane.setCenter( SystemConfigComponent.systemPane );
            }
        });

        // 将 树形菜单组件添加到 配置管理面板中
        configPane.setLeft(tree);
    }
}
