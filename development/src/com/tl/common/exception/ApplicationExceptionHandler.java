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
	 * ex : 截获的异常. ae : struts-config.xml中对该异常的配置信息
	 */
	public ActionForward execute(Exception ex, ExceptionConfig ae,
			ActionMapping mapping, ActionForm formInstance,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {

		// 如果异常不是我们自己定义的异常,向上抛出,由struts处理.
		if (!(ex instanceof ApplicationException)) {
			return super.execute(ex, ae, mapping, formInstance, request,
					response);
		}

		// 错误消息
		ActionMessage msg = null;
		// 消息在国际化消息文本中的key
		String key = null;
		// 把错误消息放到request请求的哪个生命周期中
		// HTTPRequest请求包括4个生命周期:
		// page: 当前页面有效
		// request: 当前会话有效
		// session: 浏览器窗口关闭失效
		// application: 服务器关闭失效
		// 这里查看SystemException的配置信息中是否配置了scope属性
		// 如果没有配置,默认使用requestScope,否则,使用配置的属性值.
		String scope = (ae.getScope() == null || ae.getScope().trim()
				.equals("")) ? "request" : ae.getScope();
		// 发生这个异常之后要转向的页面
		ActionForward forward = null;
		// 国际化消息文本中的占位符对应的值
		Object[] values = null;

		// 从该异常的配置信息中获取"path"
		// 如果path属性配置,则转向path配置的路径
		// 如果path没有配置,转向发生异常的action的配置信息中的input属性指向的页面
		if (ae.getPath() != null) {
			forward = new ActionForward(ae.getPath());
		} else {
			forward = mapping.getInputForward();
		}

		ApplicationException exception = (ApplicationException) ex;

		// 从异常中获取key值
		key = exception.getKey();
		// 从异常中获取占位符的具体值
		values = exception.getValues();

		// 如果程序中声明抛出异常的时候,没有指定key值,
		// 使用异常SystemException的配置信息中的key值.
		// 如果声明抛出异常的时候,指定了key值,则使用指定的key值.
		// 使用key值和占位符代表的具体值创建错误信息对象.
		if (key == null || key.trim().equals("")) {
			msg = new ActionMessage(ae.getKey(), values);
		} else {
			msg = new ActionMessage(key, values);
		}

		// 调用Struts的存储异常方法,将异常信息保存到指定的请求生命周期中
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
