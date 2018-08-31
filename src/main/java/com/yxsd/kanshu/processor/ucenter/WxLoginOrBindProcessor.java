package com.yxsd.kanshu.processor.ucenter;

import com.alibaba.fastjson.JSON;
import com.yxsd.kanshu.api.IUserApi;
import com.yxsd.kanshu.constant.ErrorCodeEnum;
import com.yxsd.kanshu.constant.RedisKeyConstants;
import com.yxsd.kanshu.processor.BaseApiProcessor;
import com.yxsd.kanshu.service.IUserReceiveService;
import com.yxsd.kanshu.service.IUserService;
import com.yxsd.kanshu.service.IUserWeixinService;
import com.yxsd.kanshu.ucenter.model.User;
import com.yxsd.kanshu.ucenter.model.UserWeixin;
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
 * Description: 微信登录或绑定
 *
 * @version 1.0 2018年1月4日 by hushengmeng 创建
 */
@Component("hapiwxLoginOrBindprocessor")
public class WxLoginOrBindProcessor extends BaseApiProcessor {

	private static final Logger logger = LoggerFactory.getLogger(WxLoginOrBindProcessor.class);

	@Resource
	private IUserApi userApi;

	@Resource(name="userService")
	IUserService userService;

	@Resource(name="userWeixinService")
	IUserWeixinService userWeixinService;

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
			UserWeixin userWeixin = JSON.parseObject(json,UserWeixin.class);
			//校验验证码
			if ("1".equals(type)) {
				//切换登录
				UserWeixin userWx = this.userWeixinService.findUniqueByParams("unionid",userWeixin.getUnionid());
				if (userWx != null) {
					if (StringUtils.isNotBlank(token)) {
						//当前用户登录状态
						String currentUid = UserUtils.getUserIdByToken(token);
						if (String.valueOf(userWx.getUserId()).equals(currentUid)) {
							//当前用户和该微信号的用户相同
							sender.put("status", 1);
							sender.put("message", "当前用户与该微信号用户相同");
						} else {
							//当前用户和切换微信号的用户不同
							sender.put("status", 0);
							sender.put("token", UserUtils.createToken(String.valueOf(userWx.getUserId())));
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
						sender.put("token", UserUtils.createToken(String.valueOf(userWx.getUserId())));
					}
				} else {
					//该微信号没有绑定账号 无法登录
					sender.put("status", -2);
					sender.put("message", "账号不存在");
				}
			} else if ("2".equals(type)) {
				//绑定微信号
				if (StringUtils.isBlank(token)) {
					sender.put("status", -3);
					sender.put("message", "缺少token信息");
				} else {
					String currentUid = UserUtils.getUserIdByToken(token);
					UserWeixin userWx = this.userWeixinService.findUniqueByParams("unionid",userWeixin.getUnionid());
					if (userWx != null) {
						if (String.valueOf(userWx.getUserId()).equals(currentUid)) {
							//当前用户已绑定该微信号
							User currUser = this.userApi.getUserByUserId(Long.parseLong(currentUid));
							currUser.setSex(userWeixin.getSex());
							currUser.setLogo(userWeixin.getHeadimgurl());
							userService.update(currUser);
							//清除用户缓存
							masterRedisTemplate.delete(RedisKeyConstants.CACHE_USER_ID_KEY + currentUid);
							sender.put("status", 1);
							sender.put("message", "当前用户已绑定该微信号");
						} else {
							sender.put("status", -4);
							sender.put("message", "微信已被其他账号绑定，请更换微信");
						}
					} else {
						//修改用户头像
						User user = this.userApi.getUserByUserId(Long.parseLong(currentUid));
						user.setSex(userWeixin.getSex());
						user.setLogo(userWeixin.getHeadimgurl());
						userService.update(user);
						//删除已绑定的微信
						userWeixinService.deleteByByParams("userId",user.getUserId());
						//保存微信相关信息
						userWeixin.setUserId(Long.parseLong(currentUid));
						userWeixin.setUpdateDate(new Date());
						userWeixin.setCreateDate(new Date());
						userWeixinService.save(userWeixin);
						//清除用户缓存
						masterRedisTemplate.delete(RedisKeyConstants.CACHE_USER_ID_KEY + user.getUserId());
						//设置用户第三方账号绑定状态
						this.userReceiveService.userThirdBind(user.getUserId(),3);
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
