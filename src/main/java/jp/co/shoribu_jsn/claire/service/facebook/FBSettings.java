/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.service.facebook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Facebook設定。
 * facebook.propertiesを読み込みます。
 * @author rued97
 */
public class FBSettings {

	/** プロパティ */
	private static final Properties properties = getProperties();

	/**
	 * ロケール
	 * @return ロケール 
	 */
	public static String Locale() {
		return properties.getProperty(Key.facebook_locale);
	};

	/** 
	 * 文字コード
	 * @return 文字コード
	 */
	public static String Charset() {
		return properties.getProperty(Key.facebook_charset);
	};

	/** 
	 * App ID
	 * @return App ID
	 */
	public static String ClientId() {
		return properties.getProperty(Key.facebook_client_id);
	};

	/** 
	 * App Secret
	 * @return App Secret
	 */
	public static String ClientSecret() {
		return properties.getProperty(Key.facebook_client_secret);
	}

	/**
	 * Facebook設定ファイルを読み込みます。
	 * @return Facebook設定
	 */
	public static Properties getProperties() {
		Properties properties = new Properties();
		try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("facebook.properties")) {
			properties.load(is);
			is.close();
		} catch (IOException | NullPointerException e) {
			throw new IllegalStateException("Facebook設定ファイルの読み込みに失敗しました。", e);
		}
		return properties;
	}

	/**
	 * Facebook設定のキー。
	 */
	private static class Key {
		private static String facebook_locale = "facebook_locale";
		private static String facebook_charset = "facebook_charset";
		private static String facebook_client_id = "facebook_client_id";
		private static String facebook_client_secret = "facebook_client_secret";
	}

}