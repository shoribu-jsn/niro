/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.logging;

import java.util.Arrays;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

/**
 * Loggerの創造主。
 * @author rued97
 */
@Dependent
public class LoggerProducer {

	@Inject
	private ApplicationLoggerManager manager;

	/**
	 * ロガー生成君。
	 * @param ip InjectionPoint
	 * @return ロガー
	 */
	@Produces
	@Dependent
	public Logger getLogger(InjectionPoint ip) {
		String name = ip.getMember().getDeclaringClass().getName();
		Logger logger = this.manager.get(name);
		if(logger != null) {
			return logger;
		}
		logger = createLogger(name);
		this.manager.put(name, logger);
		return logger;
	}

	/**
	 * ロガー名を指定してロガーを取得。
	 * @param loggerName ロガー名
	 * @return ロガー
	 */
	private Logger createLogger(String loggerName) {
		Logger logger = Logger.getLogger(loggerName);
		// 余計なものは消去しておく。
		Arrays.stream(logger.getHandlers()).forEach(logger::removeHandler);
		logger.setLevel(LoggerSettings.Level);
		if (LoggerSettings.IsConsoleUsed) {
			ConsoleHandler handler = new ConsoleHandler();
			handler.setLevel(LoggerSettings.Level);
			handler.setFormatter(LoggerSettings.Formatter);
			logger.addHandler(handler);
		}
		// 重複してログが表示されないようにする。
		logger.setUseParentHandlers(false);
		this.manager.put(loggerName, logger);
		return logger;
	}

}