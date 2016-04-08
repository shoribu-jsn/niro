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
import javax.xml.bind.annotation.XmlRootElement;
import jp.co.shoribu_jsn.claire.db.dao.UserDao;
import jp.co.shoribu_jsn.claire.db.entity.SystemUser;

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
	 * TODO:てすと用
	 * @return ユーザー一覧
	 */
	@Path("/")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@RequestScoped
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
	public void register(UserContainer container) {
		this.userDao.persist(container.toEntity());
	}

	/**
	 * ユーザー情報を保持するコンテナ。
	 */
	@XmlRootElement
	public static class UserContainer {
		/** ユーザーID */
		public String ID;
		/** ユーザー名 */
		public String Name;
	
		/** JSON変換用のコンストラクタ */
		public UserContainer() {}

		/**
		 * エンティティからインスタンスを生成します。
		 * @param user 
		 */
		public UserContainer(SystemUser user) {
			this.ID = user.getID();
			this.Name = user.getName();
		}
	
		/**
		 * コンテナの情報をエンティティに変換します。
		 * @return ユーザーエンティティ
		 */
		public SystemUser toEntity() {
			SystemUser user = new SystemUser();
			user.setID(this.ID);
			user.setName(this.Name);
			return user;
		}

	}
}