package com.tl.common.context;

import java.util.Locale;

import org.springframework.context.ApplicationContext;

public class SystemInstance {

	private static SystemInstance instance = null;

	private static ApplicationContext context = null;

	public SystemInstance() {
	}

	/**
	 * @return
	 */
	public static SystemInstance getInstance() {
		if (instance == null) {
			instance = new SystemInstance();
		}

		return instance;
	}

	public static ApplicationContext getAppContext() {
		return context;
	}

	public static void setAppContext(ApplicationContext appContext) {
		context = appContext;
	}

	public Object getBean(String beanName) {
		return getAppContext().getBean(beanName);
	}

	/**
	 * get message by specific key
	 * 
	 * @param key
	 * @return
	 */
	public String getMessage(String key) {
		return context.getMessage(key, null, Locale.getDefault());
	}

	/**
	 * get message by specific key
	 * 
	 * @param key
	 * @param args
	 * @return
	 */
	public String getMessage(String key, Object[] args) {
		return context.getMessage(key, args, Locale.getDefault());
	}

	/**
	 * get message by specific key
	 * 
	 * @param key
	 * @param locale
	 * @return
	 */
	public String getMessage(String key, Locale locale) {
		return context.getMessage(key, null, locale);
	}

	/**
	 * get message by specific key
	 * 
	 * @param key
	 * @param args
	 * @param locale
	 * @return
	 */
	public String getMessage(String key, Object[] args, Locale locale) {
		return context.getMessage(key, args, locale);
	}
}