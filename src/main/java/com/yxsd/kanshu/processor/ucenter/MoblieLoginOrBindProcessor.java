package com.yxsd.kanshu.processor.ucenter;

import com.yxsd.kanshu.api.IUserApi;
import com.yxsd.kanshu.constant.ErrorCodeEnum;
import com.yxsd.kanshu.constant.RedisKeyConstants;
import com.yxsd.kanshu.processor.BaseApiProcessor;
import com.yxsd.kanshu.service.IUserReceiveService;
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
import java.util.Map;

/**
 *
 * Description: 手机号登录或绑定
 *
 * @version 1.0 2018年1月4日 by hushengmeng 创建
 */
@Component("hapimoblieLoginOrBindprocessor")
public class MoblieLoginOrBindProcessor extends BaseApiProcessor {

	private static final Logger logger = LoggerFactory.getLogger(MoblieLoginOrBindProcessor.class);

	@Resource
	private IUserApi userApi;

	@Resource(name="userService")
	IUserService userService;

	@Resource(name="userReceiveService")
	IUserReceiveService userReceiveService;

	@Override
	protected void process(HttpServletRequest request,HttpServletResponse response, ResultSender sender) throws Exception {
		//入参
		String token = request.getParameter("token");
		String mobile = request.getParameter("mobile");
		String verifyCode = request.getParameter("verifyCode");
		//1:登录 2：绑定
		String type = request.getParameter("type");
		if(StringUtils.isBlank(mobile) || StringUtils.isBlank(verifyCode)|| StringUtils.isBlank(type)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try{
			//校验验证码
			Map<String,String> result = UserUtils.verifyCode(mobile, verifyCode);
			if("200".equals(result.get("code"))) {
				if ("1".equals(type)) {
					//切换登录
					User user = this.userService.findUniqueByParams("tel", mobile);
					if (user != null) {
						if (StringUtils.isNotBlank(token)) {
							//当前用户登录状态
							String currentUid = UserUtils.getUserIdByToken(token);
							if (String.valueOf(user.getUserId()).equals(currentUid)) {
								//当前用户和该手机号的用户相同
								sender.put("status", 1);
								sender.put("message", "当前用户与该手机号用户相同");
							} else {
								//当前用户和切换手机号的用户不同
								sender.put("status", 0);
								sender.put("token", UserUtils.createToken(String.valueOf(user.getUserId())));
								User currentUser = this.userApi.getUserByUserId(Long.parseLong(currentUid));
								if (currentUser.isTourist()) {
									sender.put("tourist", 1);
								} else {
									sender.put("tourist", 0);
								}
							}
						} else {
							//用户当前未登录
							sender.put("status", 0);
							sender.put("token", UserUtils.createToken(String.valueOf(user.getUserId())));
						}
					} else {
						//该手机号没有绑定账号 无法登录
						sender.put("status", -2);
						sender.put("message", "该账号不存在，请先绑定账号再进行切换");
					}
				} else if ("2".equals(type)) {
					//绑定手机号
					if (StringUtils.isBlank(token)) {
						sender.put("status", -3);
						sender.put("message", "缺少token信息");
					} else {
						User user = this.userService.findUniqueByParams("tel", mobile);
						String currentUid = UserUtils.getUserIdByToken(token);
						if (user != null) {
							if (String.valueOf(user.getUserId()).equals(currentUid)) {
								//当前用户已绑定该手机号
								sender.put("status", 1);
								sender.put("message", "当前用户已绑定该手机号");
							} else {
								sender.put("status", -4);
								sender.put("message", "手机号已被其他账号绑定，请更换手机号");
							}
						} else {
							User currentUser = this.userApi.getUserByUserId(Long.parseLong(currentUid));
							currentUser.setTel(mobile);
							userService.update(currentUser);
							//清除用户缓存
							masterRedisTemplate.delete(RedisKeyConstants.CACHE_USER_ID_KEY + currentUser.getUserId());
							//设置用户第三方账号绑定状态
							this.userReceiveService.userThirdBind(currentUser.getUserId(), 1);
							sender.put("status", 0);
						}
					}
				}

			}else{
				sender.put("status", -1);
				sender.put("message", "验证码错误");
			}
			sender.send(response);
		}catch (Exception e){
			logger.error("系统错误："+ request.getRequestURL()+"?"+request.getQueryString());
			e.printStackTrace();
			sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
		}
	}
}
