
package com.vic.rest.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.vic.rest.constant.BaseConstant;
import com.vic.rest.service.UserService;
import com.vic.rest.util.JsonUtil;
import com.vic.rest.vo.OdinResult;


public class LoginInterceptor implements HandlerInterceptor {
	
	@Value("${user.session.key}")
	private String USER_SESSION_KEY;
	
	@Autowired
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getHeader(USER_SESSION_KEY);
		LinkedHashMap result = userService.getRedisUserByToken(token);
		if (null == result) {
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.setHeader("Cache-Control", "no-cache");
		    response.setHeader("Pragma", "no-cache");
		    try
		    {
		    	String content = JsonUtil.objectToJson(OdinResult.build(BaseConstant.API_RESPONSE_STATUS_TOKEN_EXPIRED, "login overtime"));
		    	PrintWriter out = response.getWriter();
		    	out.print(content);
		    	out.flush();
		    	out.close();
		    }
		    catch (IOException e)
		    {
		      e.printStackTrace();
		    }
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
