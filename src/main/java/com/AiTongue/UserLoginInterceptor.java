package com.AiTongue;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @Author: 翰林猿
 * @Description: 自定义拦截器
 **/
@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {

	/**
	 * true 表示继续流程，false表示中断
	 */
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		User user = (User) request.getSession().getAttribute(CURRENT_USER);
//		if (user == null) {
//			throw new UserLoginException();
//		}
//		return true;
//	}
}
