/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.data.conf;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * EntityManager創造主。
 * @author rued97
 */
@Dependent
public class EntityManagerProducer {

	@PersistenceUnit(unitName="ClaireDB")
	private EntityManagerFactory emf;

	private EntityManager em;

	@Produces
	public EntityManager getEntityManager() {
		if(this.emf == null) {
			return null;
		}
		// TODO synchronizedにする必要がある？
		return this.emf.createEntityManager();
	}

}
