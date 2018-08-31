package com.yxsd.kanshu.processor.ucenter;

import com.yxsd.kanshu.constant.ErrorCodeEnum;
import com.yxsd.kanshu.processor.BaseApiProcessor;
import com.yxsd.kanshu.utils.ResultSender;
import com.yxsd.kanshu.utils.UserUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *
 * Description: 发送短信
 *
 * @version 1.0 2018年1月4日 by hushengmeng 创建
 */
@Component("hapisendMessageprocessor")
public class SendMessageProcessor extends BaseApiProcessor {

	private static final Logger logger = LoggerFactory.getLogger(SendMessageProcessor.class);

	@Override
	protected void process(HttpServletRequest request,HttpServletResponse response, ResultSender sender) throws Exception {
		//入参
		String mobile = request.getParameter("mobile");
		if(StringUtils.isBlank(mobile)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try{
			Map<String,String> result = UserUtils.sendMessage(mobile);
			sender.put("data",result);
			sender.success(response);
		}catch (Exception e){
			logger.error("系统错误："+ request.getRequestURL()+"?"+request.getQueryString());
			e.printStackTrace();
			sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
		}
	}
}
