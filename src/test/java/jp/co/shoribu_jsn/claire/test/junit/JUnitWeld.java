/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.test.junit;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.spi.Context;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import org.jboss.weld.bean.builtin.BeanManagerProxy;
import org.jboss.weld.context.AbstractBoundContext;
import org.jboss.weld.context.PassivatingContextWrapper;
import org.jboss.weld.context.beanstore.BoundBeanStore;
import org.jboss.weld.context.beanstore.MapBeanStore;
import org.jboss.weld.context.beanstore.SimpleNamingScheme;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.jboss.weld.manager.BeanManagerImpl;

/**
 * JUnit用に使用するWeldContainer。
 * @author rued97
 */
public class JUnitWeld {

	/** Weld */
	private static final Weld weld = new Weld();
	/** WeldContainer */
	private static WeldContainer container;
	/** JUnit用のExtensionを使ってWeldをNEW */
	static {
		weld.addExtension(new JUnitExtension());
	}

	/**
	 * WeldContainerの初期化。
	 * @return WeldContainer
	 */
	public static WeldContainer initialize() {
		if(container == null) {
			container = weld.initialize();
		}
		return container;
	}

	/**
	 * コンテキストをリセットする。
	 */
	public static void reset() {
		resetContext(container.getBeanManager(), RequestScoped.class);
	}

	/**
	 * BeanManagerからコンテキストを取り出す。
	 * @param bm BeanManager
	 * @param scopedType 対象スコープ
	 * @return 
	 */
	public static AbstractBoundContext findContext(BeanManager bm, Class<? extends Annotation> scopedType) {
		// BeanManagerの実装を無理やり取り出す。
		BeanManagerImpl impl;
		if(bm instanceof BeanManagerProxy) {
			BeanManagerProxy proxy = (BeanManagerProxy)bm;
			impl = proxy.delegate();
		} else {
			impl = (BeanManagerImpl)bm;
		}

		// コンテキストを取り出す。
		Map<Class<? extends Annotation>, List<Context>> contextMap;
		try {
			Method getContexts = BeanManagerImpl.class.getDeclaredMethod("getContexts");
			getContexts.setAccessible(true);
			contextMap = (Map<Class<? extends Annotation>, List<Context>>)getContexts.invoke(impl);
		} catch(NoSuchMethodException ex) {
			throw new IllegalStateException("メソッドの構築に失敗しました。", ex);
		} catch(InvocationTargetException | IllegalAccessError | IllegalAccessException ex) {
			throw new IllegalStateException("メソッドの実行に失敗しました。", ex);
		}
		Context context = contextMap.get(scopedType).get(0);

		return (AbstractBoundContext)PassivatingContextWrapper.unwrap(context);
	}

	/**
	 * 指定されたスコープをアクティブにする。
	 * @param bm BeanManager
	 * @param scopedType 対象スコープ
	 */
	public static void activateScoped(BeanManager bm, Class<? extends Annotation> scopedType) {
		AbstractBoundContext boundContext = JUnitWeld.findContext(bm, scopedType);
		try {
			Method setBeanStore = AbstractBoundContext.class.getDeclaredMethod("setBeanStore", BoundBeanStore.class);
			setBeanStore.setAccessible(true);
			setBeanStore.invoke(boundContext, new MapBeanStore(new SimpleNamingScheme("BoundRequestContext"), new HashMap<>()));
		} catch(NoSuchMethodException ex) {
			throw new IllegalStateException("メソッドの構築に失敗しました。", ex);
		} catch(InvocationTargetException | IllegalAccessError | IllegalAccessException ex) {
			throw new IllegalStateException("メソッドの実行に失敗しました。", ex);
		}
		boundContext.activate();
	}

	/**
	 * コンテナをリセット。
	 * @param bm BeanManager
	 * @param scopedType 対象スコープ
	 */
	public static void resetContext(BeanManager bm, Class<? extends Annotation> scopedType) {
		AbstractBoundContext boundContext = findContext(bm, scopedType);
		boundContext.deactivate();
		boundContext.cleanup();
		activateScoped(bm, scopedType);
	}

	/**
	 * JUnit用のExtension。
	 * @author rued97
	 */
	public static class JUnitExtension implements Extension {

		/**
		 * Weld-SEでアクティブでないリクエストスコープを有効にする。
		 * セッションスコープとかはとりあえずしていない。
		 * @param adv
		 * @param bm
		 */
		public void afterDeploymentValidation(@Observes AfterDeploymentValidation adv, BeanManager bm) {
			JUnitWeld.activateScoped(bm, RequestScoped.class);
		}

	}

}