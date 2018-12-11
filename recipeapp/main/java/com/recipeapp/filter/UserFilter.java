package com.recipeapp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.recipeapp.bean.UserBean;
import com.recipeapp.dto.UserDto;

@WebFilter(filterName = "/UserFilter", urlPatterns = { "/*" })
public class UserFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = ((HttpServletRequest) request).getSession(false);
		UserBean userBean = (session != null) ? (UserBean) session.getAttribute("userBean") : null;
		UserDto userDto = (userBean != null) ? userBean.getUserDto() : null;
		String currentPath = ((HttpServletRequest) request).getRequestURL().toString();

		if (userDto != null) {
			if ((currentPath.contains("login.xhtml") || currentPath.contains("403error.xhtml"))
					&& !allowed(currentPath)) {
				res.sendRedirect("recipeManagement.xhtml");
			} else {
				chain.doFilter(request, response);
			}
		} else {
			if (!currentPath.contains("login") && !allowed(currentPath) )

			{
				res.sendRedirect("/recipeapp/login.xhtml");
			} else {
				chain.doFilter(request, response);
			}
		}

	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

	private boolean allowed(String path) {
		return path.contains("javax.faces.resource") || path.contains(".png") || path.contains("resources") || path.contains("register");
	}

}
