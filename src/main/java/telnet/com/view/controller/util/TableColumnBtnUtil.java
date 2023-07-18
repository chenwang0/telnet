package telnet.com.view.controller.util;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import telnet.com.backend.entity.Monitor;
import telnet.com.backend.entity.StatsSSPVO;
import telnet.com.backend.util.MonitorManager;
import telnet.com.view.controller.function.BtnOptionFunction;

/**
 * TableColumn 添加操作操作按钮工具类
 */
public class TableColumnBtnUtil {

    /**
     * 添加操作按钮
     * @param btn       添加的按钮
     * @param function  设置点击后的回调函数
     * @param <E>       声明表格数据类型
     * @return Callback
     */
    public static <E> Callback<TableColumn<E, String>, TableCell<E, String>> getCallback( Button btn, E e, BtnOptionFunction function ){

        return new Callback<>() {
            @Override
            public TableCell<E, String> call(TableColumn<E, String> personStringTableColumn) {

                return new TableCell<>(){
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction( actionEvent -> {
                                E e = getTableView().getItems().get(getIndex());
                                function.touch( e );
                            });
                            //设置按钮
                            setGraphic(btn);
                            //清空文本
                            setText(null);
                        }
                    }
                };
            }
        };
    }

}
