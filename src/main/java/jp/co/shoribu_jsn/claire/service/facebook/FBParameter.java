/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.service.facebook;

/**
 * Facebookでリクエスト時に使用するパラメータ。
 * @author rued97
 */
public enum FBParameter {
	/** App ID */
	ClientID("client_id"),
	/** Secret ID */
	ClientSecret("client_secret"),
	/** コード */
	Code("code"),
	/** アクセストークン */
	AccessToken("access_token"),
	/** スコープ */
	Scope("scope"),
	/** リダイレクトURL */
	RedirectURI("redirect_uri"),
	/** 表示方法 */
	Display("display"),
	/** state */
	State("state"),
	/** ロケール */
	Locale("locale"),
	/** フィールド */
	Fields("fields"),
	/** メッセージ */
	Message("message"),
	/** ソース */
	Source("source"),
	/** 場所 */
	Place("place"),
	;

	/** キー */
	private final String key;

	/** コンストラクタ */
	private FBParameter(String key) {
		this.key = key;
	}

	/**
	 * パラメータのKeyを返します。
	 * @return Key
	 */
	public String getKey() {
		return this.key;
	}

}
