/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.service.facebook;

import java.util.Properties;
import mockit.Mock;
import mockit.MockUp;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 * FacebookURLGeneratorのテスト
 * @author rued97
 */
public class FacebookURLGeneratorTest {

	/** GRAPH_API_BASE */
	private static final String GRAPH_API_BASE_URL_V2_4 = "https://graph.facebook.com/v2.4";

	@Before
	public void 各テスト実行前の処理() {
		System.out.println(this.getClass().getName() + "#Before");
		new MockUp<FBSettings>() {
			@Mock
			public String ClientId() {
				return "12345";
			}
		};
	}

	@After
	public void 各テスト実行後の処理() {
		System.out.println(this.getClass().getName() + "#After");
	}

	@Test
	public void 認可用のリダイレクトURLが生成できる() {
		String generated = FBURLGenerator.generateAuthorizeWithParam("test_url",
																			  new FBPermission[] {FBPermission.UserAoubtMe, FBPermission.PublishActions});
		System.out.println(generated);
		assertThat(generated, is("https://graph.facebook.com/v2.4/oauth/authorize?redirect_uri=test_url&client_id=12345&scope=user_about_me%2Cpublish_actions"));
	}

}
