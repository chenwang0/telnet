package telnet.com.view.components;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import telnet.com.view.TelnetApplication;


/**
 * 程序的主页面板组件
 * 程序的菜单控制组件
 * 所有的组件都将安插在这个面板上
 */
public class HomeComponent {

    // telnet主页面板 所有的组件都将安插在这个面板上
    public static final BorderPane homePane = new BorderPane();
    // JAVAFX 场景对象
    public static final Scene homeScene = new Scene(homePane);

    public static final Alert ALERT = new Alert(Alert.AlertType.ERROR);


    // 使用静态加载 这样不用定义很多初始化方法并去调用它们
    static {

        // 创建 “设置”菜单
        Menu firstMenu = new Menu("设置");
        // 创建 设置 菜单下挂载的 ”状态统计“ 子菜单
        MenuItem statsMenuItem = new MenuItem("状态统计");
        // 给 状态统计子菜 单设置点击事件
        statsMenuItem.setOnAction( e -> {

            // Menu    : 第一块功能区域-> |  设置  |  帮助  |
            // tabPane : 第二块功能区域-> |   telnet × | 状态管理 × | 配置管理 × |

            // 检查第二块功能区域中 状态管理 是否有从 tab 组件中删除
            if (!TabComponent.tabPane.getTabs().contains(TabComponent.tab2)) {
                // 这里做了：将状态管理添加到 tab 组件中
                TabComponent.tabPane.getTabs().add(TabComponent.tab2);
            }
            TabComponent.tabPane.getSelectionModel().select(TabComponent.tab2);

        });

        // 创建 设置 菜单下挂载的 ”配置管理“ 子菜单
        MenuItem openMenuItem = new MenuItem("配置管理");
        openMenuItem.setOnAction( e -> {

            if (!TabComponent.tabPane.getTabs().contains(TabComponent.tab3)) {
                TabComponent.tabPane.getTabs().add(TabComponent.tab3);
            }
            TabComponent.tabPane.getSelectionModel().select(TabComponent.tab3);
        });

        // # 将 状态统计 和 配置管理 添加到 ”设置“
        firstMenu.getItems().addAll(statsMenuItem, openMenuItem);


        // 创建 “帮助”菜单
        Menu secondMenu = new Menu("帮助");
        // 创建 帮助 菜单下挂载的 ”使用说明“ 子菜单
        MenuItem instructionsItem = new MenuItem("使用说明");
        // 创建 帮助 菜单下挂载的 ”版本信息“ 子菜单
        MenuItem versionItem = new MenuItem("版本信息");

        // # 将 使用说明 和 版本信息 添加到 ”帮助“
        secondMenu.getItems().addAll(instructionsItem, versionItem);

        // 创建菜单栏组件
        MenuBar menuBar = new MenuBar();
        // 设置 菜单栏的宽度 这里跟随应用面板的宽度
        menuBar.setPrefWidth(TelnetApplication.width);

        // # 将 设置 和 帮助 添加到 菜单栏组件
        menuBar.getMenus().addAll(firstMenu, secondMenu);

        // 将菜单栏组件 添加到 主页面板组件
        homePane.setTop( menuBar );
        // 将 tab 组件添加到 主页面板组件
        homePane.setCenter(TabComponent.tabPane);
    }

}
