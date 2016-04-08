/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.logging;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * ログ出力用のいんたせぷたあ
 * @author rued97
 */
@Interceptor
@Dependent
@Logging
@Priority(Interceptor.Priority.APPLICATION)
public class LoggingInterceptor {

	@Inject
	private Logger logger;

	/**
	 * メソッドの前後にログを吐き出します。
	 * 例外発生時は、例外のメッセージも出力します。
	 * @param ic
	 * @return
	 * @throws Exception 
	 */
	@AroundInvoke
	public Object invoke(InvocationContext ic) throws Exception {
		String className = ic.getTarget().getClass().getSuperclass().getSimpleName();
		String methodName = ic.getMethod().getName();
		logger.log(Level.INFO, MessageFormat.format("{0}#{1} ==> 開始します。", className, methodName));
		try {
			Object o = ic.proceed();
			logger.log(Level.INFO, MessageFormat.format("{0}#{1} ==> 終了します。", className, methodName));
			return o;
		} catch(Exception ex) {
			logger.log(Level.SEVERE, "実行中に例外が発生しました。", ex);
			throw ex;
		}
	}

}