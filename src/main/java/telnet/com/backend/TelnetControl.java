package telnet.com.backend;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogEvent;
import telnet.com.backend.entity.Monitor;
import telnet.com.backend.util.*;
import telnet.com.view.components.HomeComponent;
import telnet.com.view.components.LogComponent;
import telnet.com.view.components.StatsComponent;
import telnet.com.view.components.TelnetComponent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

/**
 * @remark: telnet 线程控制层
 * @author: chwein
 * @since: 2022/9/27 - 15:51
 */
public class TelnetControl {


	/**
	 * 普通线程 运行手动执行 telnet
	 */
	public static final TelnetThread telnet = new TelnetThread();
	public static final Thread telnetThread = new Thread(telnet);


	/**
	 * 定时线程 定时运行执行 telnet
	 */
	public static final AutoTaskThread task = new AutoTaskThread();
	public static final Timer taskThread = new Timer();


	/**
	 * 初始化自动任务线程，是否启动执行
	 */
	public static void initTask() {
		// 30 毫秒后启动执行，之后每间隔 timeInterval 毫秒执行
		taskThread.schedule(task, 300, ConfigManager.timeInterval);
	}



	public static void runTelnetThread() {

		// 获取状态
		Thread.State state = telnetThread.getState();
		if (state.equals( Thread.State.NEW )) {
			// 启动线程
			telnetThread.start();
		} else if ( state.equals( Thread.State.RUNNABLE ) && !telnet.status ){
			// 设置线程状态为执行
			telnet.status = true;
		} else {
			// 添加面板信息 Platform.runLater(()-> LogComponent.show( "线程繁忙... 让它缓一会在点" ));
			LogComponent.show("[sys]: 线程繁忙... 让它缓一会在点" );
		}
	}


	/**
	 * telnet 实现方法
	 * telnet 信息时时面板交互
	 */
	public static void telnet() {

		if (MonitorManager.monitorList.isEmpty()) {
			LogComponent.print( "[sys]: 还没有监控数据！快去配置管理里配置吧~" );
			return;
		}

		// 当线程资源状态为 false 的时候不执行
		if (!MonitorManager.getMonitorStatus()){

			LogImpl.info("occupancy of resources");
			LogComponent.print( "[sys]: 资源正在被其他线程使用，此线程本次不执行" );
			return;
		}
		try{

			// 声明创建用于保存telnet 异常监控对象，使用于弹出层提示
			List<Monitor> errMonitor = new ArrayList<>();
			// 遍历监控数据，循环执行 telnet. 使用线程数据同步的方式执行 MonitorManager.getMonitorList()
			for (Monitor monitor : MonitorManager.getMonitorList()) {

				// 调用 telnet 实现工具类执行 telnet 监听
				boolean result = TelnetUtil.telnet(monitor.getHostname(), monitor.getPort(), ConfigManager.timeout);
				// 验证 telnet 执行结果，判定 当前IP PORT是否正常
				if ( !result ){
					// 对异常IP PORT进行累加异常记录值
					monitor.setErrNumber( monitor.getErrNumber() + 1 );
					// 将此次 telnet 异常数据添加到异常集合中
					errMonitor.add(monitor);
				}
				// 累加telnet 总数据
				monitor.setCountNumber(monitor.getCountNumber() + 1 );
				// 将 telnet 信息输入日志
				LogImpl.info( LogImpl.LOG_INFO,monitor.getHostname(), monitor.getPort().toString(), result);
			}
			LogImpl.info("telnet run end");

			MonitorManager.whiteData();

			// 判断是否有监控异常信息 没有结束方法
			if (errMonitor.isEmpty())
				return;

			// 发现异常监控对象集合中有数据，准备组装异常信息调用弹窗，提示异常信息
			StringBuffer buffer = new StringBuffer("telnet err >>>");
			for (Monitor monitor : errMonitor) {
				buffer.append("\n\t").append(monitor.getHostname()).append("\t").append(monitor.getPort());
			}

			Platform.runLater(() -> {

				HomeComponent.ALERT.setTitle("telnet 监控程序");
				HomeComponent.ALERT.setHeaderText("监控发现异常 IP Port !\n"+DateUtil.getTime());
				HomeComponent.ALERT.setContentText(buffer.toString());
				HomeComponent.ALERT.show();
			});

		} finally {
			MonitorManager.setMonitorStatus(true);
		}

	}
}
