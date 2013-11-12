package com.tl.common.util;

import javax.servlet.http.HttpSession;

import com.tl.common.SystemConstants;

public class CheckUser {

	/**
	 * 判断当前用户是否合法
	 * 
	 * @param session
	 * @return
	 */
	public boolean checkUserValidity(HttpSession session) {

		String userId = (String) session.getAttribute("userId");
		String userName = (String) session.getAttribute("account");
		String ip = (String) session.getAttribute("userIp");

		Object userSort = session.getAttribute("userSort");
		if (null == userSort)
			return false;

		String userType = userSort.toString();

		if (isEmpty(userId))
			return false;
		else if (isEmpty(userName))
			return false;
		else if (!isAdmin(userType))
			return false;
		else if (isEmpty(ip))
			return false;
		else
			return true;
	}

	/**
	 * 判断当前用户是否具有模块操作权限
	 * 
	 * @param session
	 * @param url
	 * @return
	 */
	public boolean checkHasRight(HttpSession session, String url) {
		String userId = (String) session.getAttribute("userId");
		if (isEmpty(userId))
			return false;

//		UserInfoService userInfoService = (UserInfoService) SystemInstance
//				.getInstance().getBean("userInfoService");
//
//		try {
//			int rightCount = userInfoService.lookModuleRight(userId, url);
//
//			if (rightCount > 0)
//				return true;
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}

		return false;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param prepareString
	 * @return
	 */
	private boolean isEmpty(String prepareString) {
		if (null != prepareString && !"".equals(prepareString))
			return false;
		return true;
	}

	/**
	 * 判断当前用户是否为管理员
	 * 
	 * @param userType
	 * @return
	 */
	private boolean isAdmin(String userType) {
		if (StringHelper.isEmpty(userType))
			return false;

		int type = 0;
		try {
			type = Integer.parseInt(userType);
		} catch (NumberFormatException e) {
			return false;
		}

		if (SystemConstants.USER_ADMIN_TYPE == type)
			return true;
		else
			return false;
	}

}
