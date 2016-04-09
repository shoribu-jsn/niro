/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.service.facebook.bean;

/**
 * FBのAccessToken。
 * @author rued97
 */
public class FBAccessToken implements FBBean {
	/** アクセストークン */
	public String access_token;
	/** トークンタイプ */
	public String token_type;
	/** 期限 */
	public String expires_in;
}
