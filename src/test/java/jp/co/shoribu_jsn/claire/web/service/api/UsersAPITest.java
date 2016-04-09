/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.web.service.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import jp.co.shoribu_jsn.claire.db.dao.UserDao;
import jp.co.shoribu_jsn.claire.db.entity.SystemUser;
import jp.co.shoribu_jsn.claire.test.junit.TemporaryTransactionalRule;
import jp.co.shoribu_jsn.claire.web.service.api.container.UserContainer;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;

/**
 * UsersAPIのテスト。
 * @author rued97
 */
public class UsersAPITest {

	/** テスト用のEMをMock化 */
	@Rule
	public TemporaryTransactionalRule rule = new TemporaryTransactionalRule();
	/** テスト対象 */
	@Inject
	private UsersAPI testTarget;

	@Inject
	private UserDao userDao;

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
		List<SystemUser> targets = new ArrayList<>();
		{
			{
				SystemUser target = new SystemUser();
				target.setID("test1");
				target.setName("てすと1");
				targets.add(target);
			}
			{
				SystemUser target = new SystemUser();
				target.setID("test2");
				target.setName("てすと2");
				targets.add(target);
			}
			targets.forEach(this.em::persist);
		}

		// 対象メソッド実行
		List<UserContainer> results = this.testTarget.getUsers();

		// 以下検証。
		assertThat(results.size(), is(targets.size()));
		Map<String, String> targetMap = targets.stream()
		  .collect(Collectors.toMap((SystemUser user) -> user.getID(), (SystemUser user) -> user.getName()));
		results.forEach(result -> {
			assertTrue(targetMap.containsKey(result.ID));
			assertThat(result.Name, is(targetMap.get(result.ID)));
		});
	}

	@Test
	public void ユーザーを登録できる() {
		// テストデータ作成
		UserContainer target = new UserContainer();
		target.ID = "test";
		target.Name = "てすと";

		// 対象メソッド実行
		this.testTarget.putUser(target);

		// 以下検証。
		SystemUser found = this.userDao.findBy(target.ID);
		assertThat(found.getName(), is(target.Name));
	}

}
