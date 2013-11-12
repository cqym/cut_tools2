package com.tl.common.action;




import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tl.common.exception.NoPermissionException;
import com.tl.common.util.CheckUser;

public class ManageAction extends BaseAction {

	private String currentModule;

	public void checkUserRight(HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		CheckUser checkUser = new CheckUser();

		if (!checkUser.checkHasRight(session, currentModule))
			throw new NoPermissionException("δ����Ȩ�ķ���");

	}

	public String getCurrentModule() {
		return currentModule;
	}

	public void setCurrentModule(String currentModule) {
		this.currentModule = currentModule;
	}

}
