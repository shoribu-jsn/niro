/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.test.junit;

import org.jboss.weld.environment.se.WeldContainer;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

/**
 * CDIをJUnitするためのRunner.
 * @author rued97
 */
public class JUnitWeldRunner extends BlockJUnit4ClassRunner {

	/** テスト対象のクラス */
	private final Class<?> targetClass;

	public JUnitWeldRunner(Class<?> targetClass) throws InitializationError {
		super(targetClass);
		System.out.println(this.getClass().getName() + " #Constract");
		this.targetClass = targetClass;
	}

	@Override
	protected void runChild(FrameworkMethod method, RunNotifier notifier) {
		System.out.println(this.getClass().getName() + " #runChild");
		super.runChild(method, notifier);
		JUnitWeld.reset();
	}

	@Override
	protected Object createTest() {
		WeldContainer container = JUnitWeld.initialize();
		return container.select(this.targetClass).get();
	}

}