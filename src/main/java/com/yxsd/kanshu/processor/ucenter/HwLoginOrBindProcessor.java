package com.yxsd.kanshu.processor.ucenter;

import com.alibaba.fastjson.JSON;
import com.yxsd.kanshu.api.IUserApi;
import com.yxsd.kanshu.constant.ErrorCodeEnum;
import com.yxsd.kanshu.constant.RedisKeyConstants;
import com.yxsd.kanshu.processor.BaseApiProcessor;
import com.yxsd.kanshu.service.IUserHuaweiService;
import com.yxsd.kanshu.service.IUserReceiveService;
import com.yxsd.kanshu.service.IUserService;
import com.yxsd.kanshu.ucenter.model.User;
import com.yxsd.kanshu.ucenter.model.UserHuawei;
import com.yxsd.kanshu.utils.ResultSender;
import com.yxsd.kanshu.utils.UserUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 *
 * Description: 华为登录或绑定
 *
 * @version 1.0 2018年1月4日 by hushengmeng 创建
 */
@Component("hapihwLoginOrBindprocessor")
public class HwLoginOrBindProcessor extends BaseApiProcessor {

	private static final Logger logger = LoggerFactory.getLogger(HwLoginOrBindProcessor.class);

	@Resource
	private IUserApi userApi;

	@Resource(name="userService")
	IUserService userService;

	@Resource(name="userHuaweiService")
	IUserHuaweiService userHuaweiService;

	@Resource(name="userReceiveService")
	IUserReceiveService userReceiveService;

	@Override
	protected void process(HttpServletRequest request,HttpServletResponse response, ResultSender sender) throws Exception {
		//入参
		String token = request.getParameter("token");
		String json = request.getParameter("json");
		//1:登录 2：绑定
		String type = request.getParameter("type");

		if(StringUtils.isBlank(json)|| StringUtils.isBlank(type)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try{
			UserHuawei userHw = JSON.parseObject(json,UserHuawei.class);
			if ("1".equals(type)) {
				//切换登录
				UserHuawei userHuawei = this.userHuaweiService.findUniqueByParams("unionId",userHw.getUnionId());
				if (userHuawei != null) {
					if (StringUtils.isNotBlank(token)) {
						//当前用户登录状态
						String currentUid = UserUtils.getUserIdByToken(token);
						if (String.valueOf(userHuawei.getUserId()).equals(currentUid)) {
							//当前用户和该华为账号的用户相同
							sender.put("status", 1);
							sender.put("message", "当前用户与该华为账号用户相同");
						} else {
							//当前用户和切换微信号的用户不同
							sender.put("status", 0);
							sender.put("token", UserUtils.createToken(String.valueOf(userHuawei.getUserId())));
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
						sender.put("token", UserUtils.createToken(String.valueOf(userHuawei.getUserId())));
					}
				} else {
					//该华为账号没有绑定账号 无法登录
					sender.put("status", -2);
					sender.put("message", "该账号不存在，请先绑定账号再进行切换");
				}
			} else if ("2".equals(type)) {
				//绑定华为账号
				if (StringUtils.isBlank(token)) {
					sender.put("status", -3);
					sender.put("message", "缺少token信息");
				} else {
					String currentUid = UserUtils.getUserIdByToken(token);
					UserHuawei userHuawei = this.userHuaweiService.findUniqueByParams("unionId",userHw.getUnionId());
					if (userHuawei != null) {
						if (String.valueOf(userHuawei.getUserId()).equals(currentUid)) {
							//当前用户已绑定该华为账号
							User currUser = this.userApi.getUserByUserId(Long.parseLong(currentUid));
							if(StringUtils.isNotBlank(userHw.getPhotoUrl())){
								currUser.setLogo(userHw.getPhotoUrl());
							}
							userService.update(currUser);
							//清除用户缓存
							masterRedisTemplate.delete(RedisKeyConstants.CACHE_USER_ID_KEY + currentUid);
							sender.put("status", 1);
							sender.put("message", "当前用户已绑定该华为账号");
						} else {
							sender.put("status", -4);
							sender.put("message", "华为账号已被其他账号绑定，请更换华为账号");
						}
					} else {
						//修改用户头像
						User user = this.userApi.getUserByUserId(Long.parseLong(currentUid));
						if(StringUtils.isNotBlank(userHw.getPhotoUrl())){
							user.setLogo(userHw.getPhotoUrl());
						}
						userService.update(user);
						//删除已绑定的华为账号
						userHuaweiService.deleteByByParams("userId",user.getUserId());
						//保存华为账号相关信息
						userHw.setUserId(Long.parseLong(currentUid));
						userHw.setUpdateDate(new Date());
						userHw.setCreateDate(new Date());
						userHuaweiService.save(userHw);
						//清除用户缓存
						masterRedisTemplate.delete(RedisKeyConstants.CACHE_USER_ID_KEY + user.getUserId());
						//设置用户第三方账号绑定状态
						this.userReceiveService.userThirdBind(user.getUserId(),5);
						sender.put("status", 0);
					}
				}
			}
			sender.send(response);
		}catch (Exception e){
			logger.error("系统错误："+ request.getRequestURL()+"?"+request.getQueryString());
			e.printStackTrace();
			sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
		}
	}
}
