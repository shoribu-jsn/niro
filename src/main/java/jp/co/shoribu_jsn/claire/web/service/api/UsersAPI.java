/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.web.service.api;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import jp.co.shoribu_jsn.claire.data.accessor.UserAccessor;
import jp.co.shoribu_jsn.claire.data.entity.SystemUser;

/**
 * ユーザー情報を提供します。
 * @author rued97
 */
@Path("users")
@RequestScoped
public class UsersAPI {

	@Inject
	private UserAccessor userAccessor;

	/**
	 * ユーザー情報を提供します。
	 * TODO:てすと用
	 * @return ユーザー一覧
	 */
	@Path("")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<SystemUser> getUsers() {
		return this.userAccessor.load();
	}

}
