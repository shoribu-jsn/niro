/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.test.junit;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import jp.co.shoribu_jsn.claire.db.EntityManagerProducer;
import mockit.Mock;
import mockit.MockUp;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * EntityManagerのモック化と、@Transactionalをカバーを担当。
 *
 * @author rued97
 */
public class TemporaryTransactionalRule implements TestRule {

	@Override
	public Statement apply(Statement statement, Description description) {
		System.out.println(this.getClass().getName() + " #apply");
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				// EntityManagerのMock化
				EntityManager em = mockUpEntityManager();
				EntityTransaction tran = em.getTransaction();

				try {
					tran.begin();
					statement.evaluate();
					tran.rollback();
				} catch(Exception ex) {
					ex.printStackTrace();
					if(tran != null) {
						tran.rollback();
					}
				}

			}

		};
	}

	/**
	 * EntityManagerをMock化します。
	 */
	private static EntityManager mockUpEntityManager() {
		// Test用のpersistence.xmlを読み込む
		Map<String, String> setting = new HashMap<>();
		setting.put(PersistenceUnitProperties.ECLIPSELINK_PERSISTENCE_XML, "META-INF/test.persistence.xml");
		// RESOURCE_LOCALなEntityManagerを取得
		final EntityManager testEM = Persistence.createEntityManagerFactory("ClaireDB", setting)
			.createEntityManager();

		new MockUp<EntityManagerProducer>() {
			@Mock
			public EntityManager getEntityManager() {
				// 注入
				return testEM;
			}
		};
		return testEM;
	}
}