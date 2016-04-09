/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.web.service.auth;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import jp.co.shoribu_jsn.claire.db.entity.SystemUser;

/**
 * ログインユーザーの管理。
 * RequestScopedにしたい。。
 * @author rued97
 */
@SessionScoped
public class LoggedInUserProducer implements Serializable  {

	/** ログインユーザー */
	private SystemUser loggedInUser;

	/**
	 * ログインユーザーを取得します。
	 * @return ログインユーザー
	 */
	@Produces
	@LoggedIn
	public SystemUser getLoggedInUser() {
		if(this.loggedInUser == null) {
			this.loggedInUser = new SystemUser();
		}
		return this.loggedInUser;
	}

	/**
	 * ログインユーザを設定します。
	 * @param loggedInUser 
	 */
	public void setLoggedInUser(SystemUser loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

}