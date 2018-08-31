package com.yxsd.kanshu.processor.ucenter;

import com.yxsd.kanshu.api.IUserApi;
import com.yxsd.kanshu.constant.Constants;
import com.yxsd.kanshu.constant.ErrorCodeEnum;
import com.yxsd.kanshu.constant.RedisKeyConstants;
import com.yxsd.kanshu.model.UserWelfare;
import com.yxsd.kanshu.processor.BaseApiProcessor;
import com.yxsd.kanshu.service.IUserReceiveService;
import com.yxsd.kanshu.service.IUserVipService;
import com.yxsd.kanshu.service.IUserWelfareService;
import com.yxsd.kanshu.ucenter.model.UserReceive;
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
 * Description: 领取新用户福利信息
 *
 * @version 1.0 2018年1月9日 by hushengmeng 创建
 */
@Component("hapireceiveUserWelfareprocessor")
public class ReceiveUserWelfareProcessor extends BaseApiProcessor {

	private static final Logger logger = LoggerFactory.getLogger(ReceiveUserWelfareProcessor.class);

	@Resource(name="userVipService")
	IUserVipService userVipService;

	@Resource(name="userReceiveService")
	IUserReceiveService userReceiveService;

	@Resource(name="userWelfareService")
	IUserWelfareService userWelfareService;

	@Resource
	private IUserApi userApi;

	@Override
	protected void process(HttpServletRequest request,HttpServletResponse response, ResultSender sender) throws Exception {
		//入参
		String token = request.getParameter("token");
		String channel = request.getParameter("channel");
		//新手礼包天数或赠币金额
		String days = request.getParameter("days");
		if(StringUtils.isBlank(token)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try{
			UserWelfare userWelfare = this.userWelfareService.getUserWelfare();
			String userId = UserUtils.getUserIdByToken(token);
			if(StringUtils.isNotBlank(userWelfare.getChannelsNo()) && userWelfare.getChannelsNo().indexOf(channel) != -1){
				//不显示新手礼包
			}else{
				if(StringUtils.isNotBlank(userWelfare.getChannels()) && userWelfare.getChannels().indexOf(channel) != -1){
					//赠币
					this.userApi.charge(Long.parseLong(userId), Constants.CONSUME_TYPE_13,StringUtils.isBlank(channel) ? null : Integer.parseInt(channel),null,null,0,userWelfare.getMoney());
				}else{
					//领取VIP
					this.userVipService.addVip(Long.parseLong(userId),userWelfare.getDays(),StringUtils.isBlank(channel) ? null : Integer.parseInt(channel));
				}
			}
			UserReceive userReceive = this.userReceiveService.findUniqueByParams("userId",userId);
			if(userReceive != null){
				userReceive.setVipStatus(1);
				userReceive.setUpdateDate(new Date());
				this.userReceiveService.update(userReceive);
			}else{
				userReceive = new UserReceive();
				userReceive.setUserId(Long.parseLong(userId));
				userReceive.setVipStatus(1);
				userReceive.setUpdateDate(new Date());
				userReceive.setCreateDate(new Date());
				this.userReceiveService.save(userReceive);
			}
			//清除用户缓存
			masterRedisTemplate.delete(RedisKeyConstants.CACHE_USER_ID_KEY + userId);
			sender.success(response);
		}catch (Exception e){
			logger.error("系统错误："+ request.getRequestURL()+"?"+request.getQueryString());
			e.printStackTrace();
			sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
		}
	}
}
