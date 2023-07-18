package telnet.com.view.components;

import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import telnet.com.view.TelnetApplication;

/**
 * @remark: telnet 面板 tab 组件
 * @author: cwang
 * @since: 2022/9/27 - 15:51
 */
public class TabComponent {

    public static final TabPane tabPane = new TabPane();
    public static final Scene tabScene = new Scene(tabPane);

    public static final Tab tab1 = new Tab("telnet");
    public static final Tab tab2 = new Tab("状态统计");
    public static final Tab tab3 = new Tab("配置管理");

    static {

        tabPane.setPrefWidth(TelnetApplication.width);

        // 关闭 "telnet" 时的监听事件
        tab1.setOnClosed(event -> { });


        tab1.setContent( TelnetComponent.telnetPane );
        tab2.setContent( StatsComponent.statsPane );
        tab3.setContent( ConfigComponent.configPane );

        // 隐藏 ”telnet“ 页签关闭按钮
        tab1.setClosable(false);
        // 设置 ”telnet“ 不允许关闭
        // 阻止事件 setOnClosed 不会执行: tab1.setOnCloseRequest(Event::consume);
        // 设置 "telnet" 选中事件 tab1.setOnSelectionChanged(event -> {});

        // 设置 ”状态统计“ 默认选中
        tabPane.getSelectionModel().select(tab2);
        // 设置每次选中 ”状态统计“ 页签 执行刷新状态统计表格数据
        tab2.setOnSelectionChanged(event -> StatsComponent.refresh());

        // 设置关闭按钮显示模式
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        // 设置 Tab 页签的方位 默认 top
        tabPane.setSide(Side.TOP);
        // 旋转图标 tabPane.setRotateGraphic(true);

        // 将 三个页签添加到 tab 组件
        tabPane.getTabs().addAll(tab1, tab2, tab3);

        // Tab 切换监听
        tabPane.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {});
        // Tab 页签切换监听
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {});
    }

}
