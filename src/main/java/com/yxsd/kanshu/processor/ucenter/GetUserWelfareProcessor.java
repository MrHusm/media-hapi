package com.yxsd.kanshu.processor.ucenter;

import com.yxsd.kanshu.api.IUserApi;
import com.yxsd.kanshu.constant.ErrorCodeEnum;
import com.yxsd.kanshu.model.UserWelfare;
import com.yxsd.kanshu.processor.BaseApiProcessor;
import com.yxsd.kanshu.service.IUserReceiveService;
import com.yxsd.kanshu.service.IUserWelfareService;
import com.yxsd.kanshu.ucenter.model.User;
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
 * Description: 获取新用户福利信息
 *
 * @version 1.0 2018年1月9日 by hushengmeng 创建
 */
@Component("hapigetUserWelfareprocessor")
public class GetUserWelfareProcessor extends BaseApiProcessor {

	private static final Logger logger = LoggerFactory.getLogger(GetUserWelfareProcessor.class);

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
		if(StringUtils.isBlank(token)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try{
			UserWelfare userWelfare = this.userWelfareService.getUserWelfare();
			if(userWelfare != null){
				if(StringUtils.isNotBlank(userWelfare.getChannelsNo()) && userWelfare.getChannelsNo().indexOf(channel) != -1){
					//不显示新手礼包
					sender.put("receiveStatus",1);
				}else{
					String userId = UserUtils.getUserIdByToken(token);
					User user = userApi.getUserByUserId(Long.parseLong(userId));
					Date now = new Date();
					if((now.getTime() - user.getCreateDate().getTime()) > 7 * 24 * 60 * 60 * 1000){
						//注册时间超过7天
						sender.put("receiveStatus",1);
					}else{
						UserReceive userReceive = this.userReceiveService.findUniqueByParams("userId",userId);
						if(userReceive != null && userReceive.getVipStatus() != null && userReceive.getVipStatus() == 1){
							//用户领取过新手礼包
							sender.put("receiveStatus",1);
						}else {
							sender.put("receiveStatus",0);
							if(StringUtils.isNotBlank(userWelfare.getChannels()) && userWelfare.getChannels().indexOf(channel) != -1){
								//赠币
								sender.put("days",userWelfare.getMoney());
								sender.put("message","新用户限量礼包\n"+userWelfare.getMoney()+"钻，全站图书随心看");
							}else{
								//赠送VIP
								sender.put("days",userWelfare.getDays());
								sender.put("message","新用户限量礼包\n全站图书免费读("+userWelfare.getDays()+"天)");
							}
						}
					}
				}
			}else{
				sender.put("receiveStatus",1);
			}
			sender.success(response);
		}catch (Exception e){
			logger.error("系统错误："+ request.getRequestURL()+"?"+request.getQueryString());
			e.printStackTrace();
			sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
		}
	}
}
