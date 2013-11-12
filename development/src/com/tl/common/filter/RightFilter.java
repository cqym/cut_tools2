package com.tl.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

import com.tl.common.util.CheckUser;

public class RightFilter implements Filter {

	private CheckUser checkUser = new CheckUser();

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();
		String contextPath = request.getContextPath(); // 取得项目当前根目录
		String currentPath = request.getRequestURI(); // 取得当前要访问的页面目录

		if (!isMatch(currentPath, "\\S*/log(in|out).jsp")) {
			if (!checkUser.checkUserValidity(session)) {
				// 检查当前访问的路径是否包含在此用户的权限列表中，如果不存在，则重新登陆
				response.sendRedirect(contextPath + "/pages/manage/logout.jsp");
				return;
			}
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

	public void destroy() {

	}

	public static boolean isMatch(String input, String regEx) {

		PatternCompiler compiler = new Perl5Compiler();
		PatternMatcher matcher = new Perl5Matcher();
		Pattern pattern = null;

		try {
			pattern = compiler.compile(regEx,
					Perl5Compiler.CASE_INSENSITIVE_MASK);
		} catch (MalformedPatternException e) {
			e.printStackTrace();
		}

		return matcher.matches(input, pattern);
	}

}
