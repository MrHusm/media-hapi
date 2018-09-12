package com.yxsd.kanshu.processor.ucenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxsd.kanshu.api.IUserApi;
import com.yxsd.kanshu.constant.ErrorCodeEnum;
import com.yxsd.kanshu.constant.RedisKeyConstants;
import com.yxsd.kanshu.processor.BaseApiProcessor;
import com.yxsd.kanshu.service.IUserQqService;
import com.yxsd.kanshu.service.IUserReceiveService;
import com.yxsd.kanshu.service.IUserService;
import com.yxsd.kanshu.ucenter.model.User;
import com.yxsd.kanshu.ucenter.model.UserQq;
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
 * Description: QQ登录或绑定
 *
 * @version 1.0 2018年1月4日 by hushengmeng 创建
 */
@Component("hapiqqLoginOrBindprocessor")
public class QQLoginOrBindProcessor extends BaseApiProcessor {

	private static final Logger logger = LoggerFactory.getLogger(QQLoginOrBindProcessor.class);

	@Resource
	private IUserApi userApi;

	@Resource(name="userService")
	IUserService userService;

	@Resource(name="userQqService")
	IUserQqService userQqService;

	@Resource(name="userReceiveService")
	IUserReceiveService userReceiveService;

	@Override
	protected void process(HttpServletRequest request,HttpServletResponse response, ResultSender sender) throws Exception {
		//入参
		String token = request.getParameter("token");
		String openID = request.getParameter("openID");
		String json = request.getParameter("json");
		//1:登录 2：绑定
		String type = request.getParameter("type");
		logger.info("json:" + json);
		if(StringUtils.isBlank(openID) || StringUtils.isBlank(json)|| StringUtils.isBlank(type)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try{
			//校验验证码
			if ("1".equals(type)) {
				//切换登录
				UserQq userQq = this.userQqService.findUniqueByParams("openid",openID);
				if (userQq != null) {
					if (StringUtils.isNotBlank(token)) {
						//当前用户登录状态
						String currentUid = UserUtils.getUserIdByToken(token);
						if (String.valueOf(userQq.getUserId()).equals(currentUid)) {
							//当前用户和该QQ号的用户相同
							sender.put("status", 1);
							sender.put("message", "当前用户与该QQ号用户相同");
						} else {
							//当前用户和切换QQ号的用户不同
							sender.put("status", 0);
							sender.put("token", UserUtils.createToken(String.valueOf(userQq.getUserId())));
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
						sender.put("token", UserUtils.createToken(String.valueOf(userQq.getUserId())));
					}
				} else {
					//该QQ号没有绑定账号 无法登录
					sender.put("status", -2);
					sender.put("message", "该账号不存在，请先绑定账号再进行切换");
				}
			} else if ("2".equals(type)) {
				//绑定QQ号
				if (StringUtils.isBlank(token)) {
					sender.put("status", -3);
					sender.put("message", "缺少token信息");
				} else {
					JSONObject qqJson = JSON.parseObject(json);
					String currentUid = UserUtils.getUserIdByToken(token);
					UserQq userQq = this.userQqService.findUniqueByParams("openid",openID);
					if (userQq != null) {
						if (String.valueOf(userQq.getUserId()).equals(currentUid)) {
							//当前用户已绑定该QQ号
							User currUser = this.userApi.getUserByUserId(Long.parseLong(currentUid));
							currUser.setSex("男".equals(qqJson.getString("gender")) ? 1 : 2 );
							currUser.setLogo(qqJson.getString("figureurl_qq_1"));
							userService.update(currUser);
							//清除用户缓存
							masterRedisTemplate.delete(RedisKeyConstants.CACHE_USER_ID_KEY + currentUid);
							sender.put("status", 1);
							sender.put("message", "当前用户已绑定该QQ号");
						} else {
							sender.put("status", -4);
							sender.put("message", "QQ已被其他账号绑定，请更换QQ");
						}
					} else {
						//修改用户头像
						User user = this.userApi.getUserByUserId(Long.parseLong(currentUid));
						user.setSex("男".equals(qqJson.getString("gender")) ? 1 : 2 );
						user.setLogo(qqJson.getString("figureurl_qq_1"));
						userService.update(user);
						//删除已绑定的QQ
						userQqService.deleteByByParams("userId",user.getUserId());
						//保存QQ相关信息
						userQq = userQqService.saveUserQq(qqJson, openID, user.getUserId());
						//清除用户缓存
						masterRedisTemplate.delete(RedisKeyConstants.CACHE_USER_ID_KEY + user.getUserId());
						//设置用户第三方账号绑定状态
						this.userReceiveService.userThirdBind(user.getUserId(),2);
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
