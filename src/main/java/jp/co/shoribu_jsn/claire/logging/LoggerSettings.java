/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.logging;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ロガーの設定。
 * logging.propertiesを読み込みます。
 * @author rued97
 */
public class LoggerSettings {

	/** ログレベル */
	public static final java.util.logging.Level Level;
	/** フォーマット */
	public static final Formatter Formatter;
	/** コンソール出力の使用有無 */
	public static final boolean IsConsoleUsed;

	static {
		Properties properties = getProperties();
		Level = java.util.logging.Level.parse(properties.getProperty(Key.logger_level));
		Formatter = new Formatter(properties.getProperty(Key.logger_format));
		IsConsoleUsed = Boolean.valueOf(properties.getProperty(Key.logger_console_output_use_flag));
	}

	/**
	 * ロガー設定ファイルを読み込みます。
	 * @return ロガー設定
	 */
	public static Properties getProperties() {
		Properties properties = new Properties();
		try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("logging.properties")) {
			properties.load(is);
			is.close();
		} catch (IOException | NullPointerException e) {
			throw new IllegalStateException("ログ設定ファイルの読み込みに失敗しました。", e);
		}
		return properties;
	}

	/**
	 * ログ設定のキー。
	 */
	private static class Key {
		private static final String logger_level = "logger_level";
		private static final String logger_format = "logger_format";
		private static final String logger_console_output_use_flag = "logger_console_output_use_flag";
		private static final String logger_file_output_use_flag = "logger_file_output_use_flag";
		private static final String logger_file_output_encoding = "logger_file_output_encoding";
		private static final String logger_file_output_limit = "logger_file_output_limit";
		private static final String logger_file_output_count = "logger_file_output_count";
		private static final String logger_file_output_pattern = "logger_file_output_pattern";
	}

}