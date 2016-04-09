/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.service.facebook;

/**
 * リクエスト時に使用するパーミッション。
 * @author rued97
 */
public enum FBPermission {
	PublicProfile("public_profile"),
	UserAoubtMe("user_about_me"),
	UserPhotos("user_photos"),
	UserPosts("user_posts"),
	PublishActions("publish_actions"),
	;

	/** キー */
	private final String key;

	/** コンストラクタ */
	private FBPermission(String key) {
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
