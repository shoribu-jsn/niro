/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.db.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import jp.co.shoribu_jsn.claire.db.entity.SystemUser;
import jp.co.shoribu_jsn.claire.test.junit.JUnitWeldRunner;
import jp.co.shoribu_jsn.claire.test.junit.TemporaryTransactionalRule;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * UserAccessorのテスト
 * @author rued97
 */
@RunWith(JUnitWeldRunner.class)
public class UserDaoTest {

	/** テスト用のEMをMock化 */
	@Rule
	public TemporaryTransactionalRule rule = new TemporaryTransactionalRule();
	/** テスト対象 */
	@Inject
	private UserDao testTarget;

	@Inject
	private EntityManager em;

	@Before
	public void 各テスト実行前の処理() {
		System.out.println(this.getClass().getName() + "#Before");
	}

	@After
	public void 各テスト実行後の処理() {
		System.out.println(this.getClass().getName() + "#After");
	}

	@Test
	public void ユーザーを取得できる() {
		// テストデータ登録
		SystemUser target = new SystemUser();
		target.setID("test");
		target.setName("てすと");
		this.em.persist(target);

		// 対象メソッド実行
		SystemUser result = this.testTarget.findBy(target.getID());

		// 以下検証。
		assertThat(result.getID(), is(target.getID()));
		assertThat(result.getName(), is(target.getName()));
	}

	@Test
	public void ユーザーを登録できる() {
		// テストデータ作成
		SystemUser target = new SystemUser();
		target.setID("test");
		target.setName("てすと");

		// 対象メソッド実行
		this.testTarget.persist(target);

		// 以下検証。
		SystemUser found = this.testTarget.findBy(target.getID());
		assertThat(found.getID(), is(target.getID()));
		assertThat(found.getName(), is(target.getName()));
	}

}