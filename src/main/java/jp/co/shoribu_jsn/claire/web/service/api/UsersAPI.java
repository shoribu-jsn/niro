/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.web.service.api;

import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import jp.co.shoribu_jsn.claire.db.dao.UserDao;
import jp.co.shoribu_jsn.claire.logging.Logging;
import jp.co.shoribu_jsn.claire.web.service.api.container.UserContainer;

/**
 * ユーザー情報を提供します。
 * @author rued97
 */
@Path("users")
@RequestScoped
public class UsersAPI {

	@Inject
	private UserDao userDao;

	/**
	 * ユーザー情報を提供します。
	 * @return ユーザー一覧
	 */
	@Path("/")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Logging
	public List<UserContainer> getUsers() {
		return this.userDao.load().stream()
			.map(UserContainer::new )
			.collect(Collectors.toList());
	}

	/**
	 * ユーザーを登録します。
	 * @param container ユーザー情報
	 */
	@Path("/")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	@Logging
	public void putUser(UserContainer container) {
		if(container == null) {
			return;
		}
		this.userDao.persist(container.toEntity());
	}

}