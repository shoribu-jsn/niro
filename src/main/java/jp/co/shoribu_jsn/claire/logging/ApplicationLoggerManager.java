/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.logging;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;

/**
 * ロガーの管理をやります。。。いるかなあ。
 * @author rued97
 */
@ApplicationScoped
public class ApplicationLoggerManager {

	/** loggerをためておく */
	private final Map<String, Logger> loggerMap;

	/**
	 * コンストラクタ。
	 */
	public ApplicationLoggerManager() {
		this.loggerMap = new HashMap<>();
	}

	/**
	 * ロガーを取得します。
	 * @param loggerName ロガー名
	 * @return ない場合はnull
	 */
	public Logger get(String loggerName) {
		return this.loggerMap.get(loggerName);
	}

	/**
	 * ロガーを保存します。
	 * @param loggerName ロガー名
	 * @param logger ロガー
	 */
	public void put(String loggerName, Logger logger) {
		this.loggerMap.put(loggerName, logger);
	}

}
