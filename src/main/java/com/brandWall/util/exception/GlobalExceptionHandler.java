package com.brandWall.util.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brandWall.util.CayUtil;
import com.brandWall.util.Msg;

@ControllerAdvice
public class GlobalExceptionHandler {

	Logger log = Logger.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = MyException.class)
	@ResponseBody
	public Msg jsonErrorHandler(HttpServletRequest req, MyException e) throws Exception {

		log.error(CayUtil.getInterfaceMethod(req), e);

		return new Msg(e.getRetCd(), e.getMsgDes(), null);
	}
}
