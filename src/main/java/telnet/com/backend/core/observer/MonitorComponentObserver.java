package telnet.com.backend.core.observer;

import telnet.com.view.components.item.MonitorComponent;

/**
 * 订阅者
 * <p>
 *
 * @author: cw
 * @since: 2023/7/27 15:00
 * @version: v0.1
 * <p>
 * 修改记录：
 * 时间      修改人员    修改内容
 * ------------------------------
 */
public class MonitorComponentObserver implements Observer {

    @Override
    public void update(int version) {
        MonitorComponent.refresh();
    }
}
