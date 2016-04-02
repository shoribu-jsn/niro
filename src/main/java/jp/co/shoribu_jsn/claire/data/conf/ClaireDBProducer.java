/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.data.conf;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EntityManager創造主。
 * @author rued97
 */
@Dependent
public class ClaireDBProducer {

	@PersistenceContext(unitName="ClaireDB")
	private EntityManager em;

	@Produces
	public EntityManager getEntityManager() {
		return this.em;
	}

}
