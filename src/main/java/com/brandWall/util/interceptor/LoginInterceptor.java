package com.brandWall.util.interceptor;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.brandWall.user.jpa.FwTokenJpa;
import com.brandWall.user.model.FwToken;
import com.brandWall.util.ConstantMsg;
import com.brandWall.util.ValidateUtil;
import com.brandWall.util.exception.MyException;

/**
 * 登录拦截�?
 * @author 王翠
 *
 */
@Component
public class LoginInterceptor  implements HandlerInterceptor{

	@Inject
	private FwTokenJpa fwTokenJpa;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws MyException {
		String token = (String) request.getParameter("token");
		if(!ValidateUtil.isValid(token)){
			throw new MyException(ConstantMsg.MSG_NOLOGIN, ConstantMsg.MSG_NOLOGIN_CONTANT);
		}
		token=token.replaceAll(" ", "+");
		FwToken ft=this.fwTokenJpa.findByTkTokenContent(token);
		if(null==ft){
			throw new MyException(ConstantMsg.MSG_NOLOGIN, ConstantMsg.MSG_NOLOGIN_CONTANT);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
