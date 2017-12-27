package com.yxsd.kanshu.processor;

import com.yxsd.kanshu.constant.ErrorCodeEnum;
import com.yxsd.kanshu.utils.ResultSender;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UnknownApiProcessor extends BaseApiProcessor {

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender)
			throws Exception {
		String action = request.getParameter("action");
		sender.fail(ErrorCodeEnum.ERROR_CODE_10011.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10011.getErrorMessage()
				+ ": " + action, response);
	}
}
