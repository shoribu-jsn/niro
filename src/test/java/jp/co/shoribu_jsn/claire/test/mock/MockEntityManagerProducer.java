/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.test.mock;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import org.eclipse.persistence.config.PersistenceUnitProperties;

/**
 * テスト用のEntityManager創造主。
 * @author rued97
 */
public class MockEntityManagerProducer {

	/**
	 * テスト用のEntityManagerを取得します。
	 * @return テスト用のEntityManager
	 */
	public static EntityManager getEntityManager() {
		Map<String, String> setting = new HashMap<>();
		setting.put(PersistenceUnitProperties.ECLIPSELINK_PERSISTENCE_XML, "META-INF/test.persistence.xml");
		EntityManager em = Persistence.createEntityManagerFactory("ClaireDB", setting)
			.createEntityManager();
		return em;
	}

}