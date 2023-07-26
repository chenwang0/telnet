package telnet.com.backend.util;

import telnet.com.backend.entity.ConfigConst;
import telnet.com.backend.entity.Monitor;
import telnet.com.view.components.LogComponent;

import java.io.*;
import java.util.Properties;

/**
 * @remark: 从配置文件中读取系统参数
 * @author: cw
 * @since: 2022/9/27 - 13:56
 */
public class ConfigManager {

	/** 日志输出路径 */
	public static String path;
	/** telnet连接超时时间 */
	public static Integer timeout;
	/** 线程休息时间 */
	public static Integer timeInterval;
	/** 自启动状态 */
	public static boolean autoTask;

	private static final Properties PROPERTIES =  new  Properties();

	static {
		// 检查文件是否存在，不存在则创建
		boolean verify = FileUtil.verify(ConfigConst.C_FILE_PATH);

		try (InputStream in =new FileInputStream(ConfigConst.C_FILE_PATH)) {

			// 加载
			PROPERTIES.load(in);

			// 读入日志输出路径
			path = PROPERTIES.getProperty("config.path");
			if ( CheckUtil.empty(path) ) {
				path = ConfigConst.PATH;
			}

			// 读取超时时间
			String monitorTimeout = PROPERTIES.getProperty("config.timeout");
			if ( CheckUtil.empty(monitorTimeout) ) {
				timeout = ConfigConst.TIMEOUT;
			} else {
				timeout = Integer.parseInt(monitorTimeout);
			}

			// 定时任务执行间隔时间
			String monitorTimeInterval = PROPERTIES.getProperty("config.timeInterval");
			if ( CheckUtil.empty(monitorTimeInterval) ) {
				timeInterval = ConfigConst.TIME_INTERVAL;
			} else {
				timeInterval = Integer.parseInt(monitorTimeInterval);
			}

			// 定时任务自启动状态
			String autoTaskInterval = PROPERTIES.getProperty("config.autoTask");
			if ( CheckUtil.empty(autoTaskInterval) ) {
				autoTask = ConfigConst.AUTO_TASK;
			} else {
				autoTask = Boolean.parseBoolean(autoTaskInterval);
			}

			if (!verify) {
				// 文件不存在将数据写入
				read();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 配置文件数据持久化实现方法
	 */
	public synchronized static void read() {

		try ( OutputStream output =  new FileOutputStream(ConfigConst.C_FILE_PATH) ) {

			PROPERTIES.setProperty("config.path", path);
			PROPERTIES.setProperty("config.timeout", String.valueOf(timeout));
			PROPERTIES.setProperty("config.timeInterval", String.valueOf(timeInterval));
			PROPERTIES.setProperty("config.autoTask", String.valueOf(autoTask));

			// 将数据保存到文件中
			PROPERTIES.store(output, " config ");

		} catch (IOException io) {
			io.printStackTrace();
		}
	}



	/**
	 * 程序启动前的属性检查方法
	 */
	public static void check() {

		LogImpl.info();
		LogImpl.info(" telnet 程序正在启动");
		LogImpl.info("开始检查基础配置数据");
		LogImpl.info("检查 monitor.path=" + path);
		LogImpl.info("检查 monitor.timeout=" + timeout);
		LogImpl.info("检查 monitor.timeInterval=" + timeInterval);
		LogImpl.info("检查 monitor.autoTask=" + autoTask);
		LogImpl.info("基础配置数据检查结束");
	}
}
