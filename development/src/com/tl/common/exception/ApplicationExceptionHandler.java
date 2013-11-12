package com.tl.common.exception;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

public class ApplicationExceptionHandler extends ExceptionHandler {

	/**
	 * ex : �ػ���쳣. ae : struts-config.xml�жԸ��쳣��������Ϣ
	 */
	public ActionForward execute(Exception ex, ExceptionConfig ae,
			ActionMapping mapping, ActionForm formInstance,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {

		// ����쳣���������Լ�������쳣,�����׳�,��struts����.
		if (!(ex instanceof ApplicationException)) {
			return super.execute(ex, ae, mapping, formInstance, request,
					response);
		}

		// ������Ϣ
		ActionMessage msg = null;
		// ��Ϣ�ڹ��ʻ���Ϣ�ı��е�key
		String key = null;
		// �Ѵ�����Ϣ�ŵ�request������ĸ�����������
		// HTTPRequest�������4����������:
		// page: ��ǰҳ����Ч
		// request: ��ǰ�Ự��Ч
		// session: ��������ڹر�ʧЧ
		// application: �������ر�ʧЧ
		// ����鿴SystemException��������Ϣ���Ƿ�������scope����
		// ���û������,Ĭ��ʹ��requestScope,����,ʹ�����õ�����ֵ.
		String scope = (ae.getScope() == null || ae.getScope().trim()
				.equals("")) ? "request" : ae.getScope();
		// ��������쳣֮��Ҫת���ҳ��
		ActionForward forward = null;
		// ���ʻ���Ϣ�ı��е�ռλ����Ӧ��ֵ
		Object[] values = null;

		// �Ӹ��쳣��������Ϣ�л�ȡ"path"
		// ���path��������,��ת��path���õ�·��
		// ���pathû������,ת�����쳣��action��������Ϣ�е�input����ָ���ҳ��
		if (ae.getPath() != null) {
			forward = new ActionForward(ae.getPath());
		} else {
			forward = mapping.getInputForward();
		}

		ApplicationException exception = (ApplicationException) ex;

		// ���쳣�л�ȡkeyֵ
		key = exception.getKey();
		// ���쳣�л�ȡռλ���ľ���ֵ
		values = exception.getValues();

		// ��������������׳��쳣��ʱ��,û��ָ��keyֵ,
		// ʹ���쳣SystemException��������Ϣ�е�keyֵ.
		// ��������׳��쳣��ʱ��,ָ����keyֵ,��ʹ��ָ����keyֵ.
		// ʹ��keyֵ��ռλ������ľ���ֵ����������Ϣ����.
		if (key == null || key.trim().equals("")) {
			msg = new ActionMessage(ae.getKey(), values);
		} else {
			msg = new ActionMessage(key, values);
		}

		// ����Struts�Ĵ洢�쳣����,���쳣��Ϣ���浽ָ������������������
		storeException(request, key, msg, forward, scope);
		return forward;

	}

	protected void logException(Exception e) {
		logException(e);
	}

	@Override
	protected void storeException(HttpServletRequest request, String property,
			ActionError error, ActionForward forward, String scope) {
		storeException(request, property, error, forward, scope);
	}

	protected void storeException(HttpServletRequest request, String property,
			ActionMessage error, ActionForward forward, String scope) {

		ActionMessages errors = new ActionMessages();
		errors.add(property, error);

		if ("request".equals(scope)) {
			request.setAttribute(Globals.ERROR_KEY, errors);
		} else {
			request.getSession().setAttribute(Globals.ERROR_KEY, errors);
		}

	}

}
