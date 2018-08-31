package com.yxsd.kanshu.processor.ucenter;

import com.yxsd.kanshu.api.IUserApi;
import com.yxsd.kanshu.constant.ErrorCodeEnum;
import com.yxsd.kanshu.processor.BaseApiProcessor;
import com.yxsd.kanshu.service.IUserService;
import com.yxsd.kanshu.ucenter.model.User;
import com.yxsd.kanshu.utils.ResultSender;
import com.yxsd.kanshu.utils.UserUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * Description: 账号密码登录
 *
 * @version 1.0 2018年1月4日 by hushengmeng 创建
 */
@Component("hapiloginByNameprocessor")
public class LoginByNameProcessor extends BaseApiProcessor {

	private static final Logger logger = LoggerFactory.getLogger(LoginByNameProcessor.class);

	@Resource
	private IUserApi userApi;

	@Resource(name="userService")
	IUserService userService;

	@Override
	protected void process(HttpServletRequest request,HttpServletResponse response, ResultSender sender) throws Exception {
		//入参
		String token = request.getParameter("token");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		if(StringUtils.isBlank(name)|| StringUtils.isBlank(password)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try{
			User user = this.userService.findUniqueByParams("name",name,"password",password);
			if(user != null){
				sender.put("status", 0);
				sender.put("token", UserUtils.createToken(String.valueOf(user.getUserId())));
				if(StringUtils.isNotBlank(token)){
					String currentUid = UserUtils.getUserIdByToken(token);
					User currentUser = this.userApi.getUserByUserId(Long.parseLong(currentUid));
					if (currentUser.isTourist()) {
						sender.put("tourist", 1);
					} else {
						sender.put("tourist", 0);
					}
				}
			}else{
				sender.put("status",-1);
				sender.put("message","账号或密码错误");
			}
			sender.send(response);
		}catch (Exception e){
			logger.error("系统错误："+ request.getRequestURL()+"?"+request.getQueryString());
			e.printStackTrace();
			sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
		}
	}
}
