package com.tl.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HttpSessionFilter implements Filter {

	public static final ThreadLocal<HttpSession> sessionLocal = new ThreadLocal<HttpSession>();

	public void destroy() {
		try {

		} catch (Exception e) {

		} finally {
			sessionLocal.remove();
		}

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		sessionLocal.set(((HttpServletRequest) request).getSession());
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
