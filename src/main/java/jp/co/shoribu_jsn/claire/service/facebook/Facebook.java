/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.service.facebook;

import java.util.Map;
import java.util.logging.Logger;
import javax.inject.Inject;
import jp.co.shoribu_jsn.claire.service.facebook.bean.FBAccessToken;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import jp.co.shoribu_jsn.claire.service.facebook.bean.FBBean;
import jp.co.shoribu_jsn.claire.service.facebook.bean.FBUser;

/**
 * Facebook関連のサービスを提供します。
 * @author rued97
 */
public class Facebook {

	@Inject
	Logger logger;

	/** アクセストークン */
	private final String token;

	public Facebook(String token) {
		this.token = token;
	}

	/**
	 * アクセストークンを取得します。
	 * リダイレクトURLには認可取得時と同じURLが指定する必要があります。
	 * コードには認可取得時に取得したコードを指定する必要があります。
	 * @param redirectURL リダイレクトURL
	 * @param code コード
	 * @return 
	 */
	public static FBAccessToken requestAccessToken(String redirectURL, String code) {
		String url = FBURLGenerator.generateAccessToken();
		WebTarget target =ClientBuilder.newClient()
			.target(url)
			.queryParam(FBParameter.ClientID.getKey(), FBSettings.ClientId())
			.queryParam(FBParameter.ClientSecret.getKey(), FBSettings.ClientSecret())
			.queryParam(FBParameter.RedirectURI.getKey(), redirectURL)
			.queryParam(FBParameter.Code.getKey(), code);
		return target.request().get(FBAccessToken.class);
	}

	public FBUser Me() {
		String url = FBURLGenerator.generate("me");
		return this.requestGet(url, null, FBUser.class);
	}

	/**
	 * GETメソッドでリクエストします。
	 * @param url リクエストURL
	 * @param parameters セットするパラメータ
	 * @param klass 
	 * @return リクエスト結果
	 */
	private <T extends FBBean> T requestGet(String url, Map<FBParameter, String> parameters, Class<T> klass) {
		WebTarget target =ClientBuilder.newClient()
			.target(url)
			.queryParam(FBParameter.AccessToken.getKey(), this.token)
			.queryParam(FBParameter.Locale.getKey(), FBSettings.Locale());
		if(parameters != null) {
			parameters.entrySet().forEach(e -> { target.queryParam(e.getKey().getKey(), e.getValue()); });
		}
		return target.request().get(klass);
	}

}