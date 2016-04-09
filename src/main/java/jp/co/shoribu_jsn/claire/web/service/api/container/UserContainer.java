/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.web.service.api.container;

import javax.xml.bind.annotation.XmlRootElement;
import jp.co.shoribu_jsn.claire.db.entity.SystemUser;

/**
 * ユーザー情報を保持するコンテナ。
 * @author rued97
 */
@XmlRootElement
public class UserContainer {

	/** ユーザーID */
	public String ID;
	/** ユーザー名 */
	public String Name;

	/** JSON変換用のコンストラクタ */
	public UserContainer() {}

	/**
	 * エンティティからインスタンスを生成します。
	 * @param user 
	 */
	public UserContainer(SystemUser user) {
		this.ID = user.getID();
		this.Name = user.getName();
	}

	/**
	 * コンテナの情報をエンティティに変換します。
	 * @return ユーザーエンティティ
	 */
	public SystemUser toEntity() {
		SystemUser user = new SystemUser();
		user.setID(this.ID);
		user.setName(this.Name);
		return user;
	}

}