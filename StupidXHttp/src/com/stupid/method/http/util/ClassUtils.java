package com.stupid.method.http.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassUtils {

	public static Object newInstance(Class cls, Class<?>[] types,
			Object... params) {
		Constructor<?> mConstructor;
		try {
			return cls.getClass().newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * @名称 调用类方法
	 * @目的 达到的效果为 handler.callback(params);
	 * 
	 * @param handler
	 *            调用目标
	 * 
	 * @param callback
	 *            方法名称
	 * 
	 * @param cls
	 *            方法传入参数类型
	 * 
	 * @param params
	 *            方法传入参数
	 * @demo invokeMethod(System.out, "println", new Class[] { String.class },
	 *       "调用System.out.println");
	 ***/
	public static Object invokeMethod(Object handler, String callback,
			Class<?>[] cls, Object... params) {

		if (handler == null || callback == null)
			return null;

		Method method = null;

		try {
			if (cls == null)
				cls = new Class[0];
			method = handler.getClass().getMethod(callback, cls);
			return method.invoke(handler, params);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;

	}
}
