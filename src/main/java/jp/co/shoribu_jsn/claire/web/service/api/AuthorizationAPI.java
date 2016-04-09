/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.web.service.api;

import java.net.URI;
import java.net.URISyntaxException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import jp.co.shoribu_jsn.claire.db.entity.SystemUser;
import jp.co.shoribu_jsn.claire.logging.Logging;
import jp.co.shoribu_jsn.claire.service.facebook.bean.FBAccessToken;
import jp.co.shoribu_jsn.claire.service.facebook.FBPermission;
import jp.co.shoribu_jsn.claire.service.facebook.Facebook;
import jp.co.shoribu_jsn.claire.service.facebook.FBURLGenerator;
import jp.co.shoribu_jsn.claire.service.facebook.bean.FBUser;
import jp.co.shoribu_jsn.claire.web.service.api.container.UserContainer;
import jp.co.shoribu_jsn.claire.web.service.api.utils.URLUtils;
import jp.co.shoribu_jsn.claire.web.service.auth.LoggedIn;

/**
 * 認証に関するAPIを提供します。
 * @author rued97
 */
@Path("authorization")
@RequestScoped
public class AuthorizationAPI {

	@Inject @LoggedIn
	private SystemUser loggedInUser;

	/**
	 * 認可用のリダイレクトを返します。
	 * @return Response
	 */
	@Path("/")
	@GET
	@Logging
	public Response authorization() {
		String redirectURL = FBURLGenerator.generateAuthorizeWithParam(URLUtils.getContextRootPath() + "/api/authorization/login",
																			FBPermission.PublicProfile,
																			FBPermission.UserAoubtMe,
																			FBPermission.UserPhotos,
																			FBPermission.UserPosts,
																			FBPermission.PublishActions);
		URI uri;
		try {
			uri = new URI(redirectURL);
		} catch(URISyntaxException ex) {
			throw new IllegalStateException("リダイレクトURLの構築に失敗しました。", ex);
		}
		return Response.seeOther(uri).build();
	}

	/**
	 * Facebook認可の受け口です。
	 * アクセストークンの取得後、成功した場合は適切なページをリダイレクトします。
	 * @param code Facebookから送信されるコード
	 * @return Response
	 */
	@Path("/login")
	@GET
	@Logging
	public Response login(@QueryParam("code") String code) {
		if(code == null) {
			// 認可の取得に失敗。
			return null;
		}
		// アクセストークンを取得する
		FBAccessToken accessToken = Facebook.requestAccessToken(URLUtils.getContextRootPath() + "/api/authorization/login", code);
		if((accessToken == null) || (accessToken.access_token == null) || (accessToken.access_token.isEmpty())) {
			// アクセストークンの取得に失敗。
			return null;
		}

		// ユーザー情報を確認する。
		Facebook fb = new Facebook(accessToken.access_token);
		FBUser fbuser = fb.Me();
		this.loggedInUser.setName(fbuser.name);
		this.loggedInUser.setAccessToken(accessToken.access_token);
		this.loggedInUser.setFacebookID(fbuser.id);


		URI uri;
		try {
			uri = new URI(URLUtils.getContextRootPath() + "/pages/Top.html");
		} catch(URISyntaxException ex) {
			throw new IllegalStateException("リダイレクトURLの構築に失敗しました。", ex);
		}
		return Response.seeOther(uri).build();
	}

	/**
	 * ログインユーザー情報を提供します。
	 * @return ユーザー
	 */
	@Path("/user")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Logging
	public UserContainer getUser() {
		return new UserContainer(this.loggedInUser);
	}

}