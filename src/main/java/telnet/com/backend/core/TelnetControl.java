//package telnet.com.backend.core;
//
//import javafx.application.Platform;
//import telnet.com.backend.entity.SystemConfig;
//import telnet.com.backend.entity.Monitor;
//import telnet.com.backend.factory.SingleFactory;
//import telnet.com.backend.manager.ConfigManager;
//import telnet.com.backend.manager.MonitorManager;
//import telnet.com.backend.util.*;
//import telnet.com.view.components.HomeComponent;
//import telnet.com.view.components.LogComponent;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Timer;
//
///**
// * @remark: telnet 线程控制层
// * @author: chwein
// * @since: 2022/9/27 - 15:51
// */
//public class TelnetControl {
//
//	static ConfigManager configManager = SingleFactory.getConfigManager();
//	static SystemConfig systemConfig = configManager.get();
//
//	/**
//	 * 普通线程 运行手动执行 telnet
//	 */
//	public static final TelnetThread telnet = new TelnetThread(false);
//	public static final Thread telnetThread = new Thread(telnet);
//
//
//	/**
//	 * 定时线程 定时运行执行 telnet
//	 */
//	public static final AutoTaskThread task = new AutoTaskThread();
//	public static final Timer taskThread = new Timer();
//
//
//	/**
//	 * 初始化自动任务线程，是否启动执行
//	 */
//	public static void initTask() {
//	}
//
//
//
//	public static void runTelnetThread() {
//
//	}
//
//
//	public static void telnet() {
//
//	}
//}
