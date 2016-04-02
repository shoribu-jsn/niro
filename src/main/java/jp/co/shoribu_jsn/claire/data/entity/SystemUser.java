/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.data.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * ユーザーエンティティ
 * @author rued97
 */
@Entity
public class SystemUser implements Serializable {

	/**
	 * ユーザーID
	 */
	@Id
	private String ID;

	/**
	 * ユーザー名
	 */
	private String Name;

	@Override
	public String toString() {
		return "ID=" + this.ID + " Name=" + this.Name;
	}

	/* 以下自動生成 */

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getName() {
		return Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}
	
}