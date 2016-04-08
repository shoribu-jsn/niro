/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.data.dao;

import jp.co.shoribu_jsn.claire.db.dao.UserDao;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import jp.co.shoribu_jsn.claire.db.EntityManagerProducer;
import jp.co.shoribu_jsn.claire.db.entity.SystemUser;
import jp.co.shoribu_jsn.claire.test.mock.MockEntityManagerProducer;
import mockit.Mock;
import mockit.MockUp;
import static org.hamcrest.CoreMatchers.is;
import org.jboss.weld.environment.se.Weld;
import org.junit.After;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 * UserAccessorのテスト
 * @author rued97
 */
public class UserDaoTest {

	/** JavaEE環境のテスト用 */
	private Weld weld;
	private UserDao testTarget;

	/** テスト用ユーザーID */
	private static final String TEST_USER_ID = "test_user_id";

	private EntityManager em = MockEntityManagerProducer.getEntityManager();

	@Before
	public void テスト実行前処理() {
		// 以下SE環境でEE環境のテストを行うための準備
		// TODO:ルール化したい。ランナー？
		new MockUp<EntityManagerProducer>() {
			@Mock
			public EntityManager getEntityManager() {
				return MockEntityManagerProducer.getEntityManager();
			}
		};
		this.weld = new Weld();
		this.testTarget = this.weld.initialize().instance().select(UserDao.class).get();
	}

	@After
	public void テスト実行後処理() {
		this.weld.shutdown();
	}

	@Test
	public void ユーザーを取得できる() {
		SystemUser user = new SystemUser();
		user.setID(TEST_USER_ID);
		user.setName("テスト名");

		EntityTransaction tran = this.em.getTransaction();
		tran.begin();
		this.em.persist(user);
		tran.commit();
	
		SystemUser result = this.testTarget.findBy(TEST_USER_ID);
		assertThat(result.getID(), is(TEST_USER_ID));
		assertThat(result.getName(), is("テスト名"));

		tran.begin();
		this.em.remove(user);
		tran.commit();
	}

	@Test
	public void ユーザーを登録できる() {
		SystemUser user = new SystemUser();
		user.setID(TEST_USER_ID);
		user.setName("テスト名");

		EntityTransaction tran = this.em.getTransaction();
		tran.begin();
		this.testTarget.persist(user);
	
		SystemUser result = this.testTarget.findBy(TEST_USER_ID);
		assertThat(result.getID(), is(TEST_USER_ID));
		assertThat(result.getName(), is("テスト名"));

		tran.rollback();
	}

}