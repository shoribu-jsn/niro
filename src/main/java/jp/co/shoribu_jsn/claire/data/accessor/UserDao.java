/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.data.accessor;

import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import jp.co.shoribu_jsn.claire.data.entity.SystemUser;

/**
 * ユーザーに関連するデータ接続を提供します。
 * @author rued97
 */
public class UserDao {

	@Inject
	private EntityManager em;

	public List<SystemUser> load() {
		return this.em.createQuery(
			"select u from SystemUser u", SystemUser.class)
			.getResultList();
	}

	/**
	 * ユーザーを取得します。
	 * @param ID ユーザーID
	 * @return 見つからない場合はnull
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

}