/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.service.facebook;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.stream.Collectors;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * Facebook接続時のURLの生成を行います。
 * @author rued97
 */
public class FBURLGenerator {

	/** GRAPH_API_BASE */
	private static final String GRAPH_API_BASE_URL_V2_4 = "https://graph.facebook.com/v2.4";

	/**
	 * 認可取得用のURLをパラメータ付きで生成します。<br>
	 * Facebookの仕様上、ブラウザでFacebookにログインした後、指定されたURLにリダイレクトされます。
	 * アクセストークン取得に、必要なコードはリダイレクト先にパラメータとして付与されます。<br>
	 * <br>
	 * @param redirectURL リダイレクトURL
	 * @param parameters 要求するパーミッション
	 * @return 認可取得用URL
	 */
	public static String generateAuthorizeWithParam(String redirectURL, FBPermission... parameters) {
		// 認可取得用URL
		String url = generateAuthorize();
		// パーミッションをカンマで連結
		String values = String.join(",", Arrays.stream(parameters)
		  .map(FBPermission::getKey)
		  .collect(Collectors.toList()));
		// パラメータまで設定する。
		WebTarget target =ClientBuilder.newClient()
		  .target(url)
		  .queryParam(FBParameter.RedirectURI.getKey(), redirectURL)
		  .queryParam(FBParameter.ClientID.getKey(), FBSettings.ClientId())
		  .queryParam(FBParameter.Scope.getKey(), values);
		return target.getUri().toString();
	}

	/**
	 * 認可取得用のURLを生成します。
	 * @return URL
	 */
	private static String generateAuthorize() {
		return GRAPH_API_BASE_URL_V2_4 + "/oauth/authorize";
	}

	/**
	 * アクセストークン取得用のURLを生成します。
	 * @return URL
	 */
	public static String generateAccessToken() {
		return  GRAPH_API_BASE_URL_V2_4 + "/oauth/access_token";
	}

	/**
	 * URLを生成します。
	 * @param node ノード
	 * @return URL
	 */
	public static String generate(String node) {
		return  GRAPH_API_BASE_URL_V2_4 + "/" + node;
	}
}