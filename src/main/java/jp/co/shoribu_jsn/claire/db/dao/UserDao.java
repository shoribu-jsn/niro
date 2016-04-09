/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.db.dao;

import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import jp.co.shoribu_jsn.claire.db.entity.SystemUser;

/**
 * ユーザーに関連するデータ接続を提供します。
 * @author rued97
 */
public class UserDao {

	@Inject
	private EntityManager em;

	/**
	 * 全ユーザーを取得します。
	 * @return 全ユーザー
	 */
	public List<SystemUser> load() {
		return this.em.createQuery(
			"select u from SystemUser u", SystemUser.class)
			.getResultList();
	}

	/**
	 * IDからユーザーを探します。見つからなければnullを返します。
	 * @param ID ユーザーID
	 * @return ユーザー
	 */
	public SystemUser findBy(String ID) {
		TypedQuery<SystemUser> query = this.em.createQuery(
			"select u from SystemUser u where u.ID = :ID", SystemUser.class);
		query.setParameter("ID", ID);
		try {
			return query.getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}
	}

	/**
	 * ユーザーを新たに永続化コンテキストの管理対象に加えます。
	 * @param user ユーザー
	 */
	public void persist(SystemUser user) {
		this.em.persist(user);
	}

}