package com.yxsd.kanshu.processor.ucenter;

import com.yxsd.kanshu.constant.ErrorCodeEnum;
import com.yxsd.kanshu.model.UserDevice;
import com.yxsd.kanshu.processor.BaseApiProcessor;
import com.yxsd.kanshu.service.IUserDeviceService;
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
 * Description: app启动上报用户设备信息
 *
 * @version 1.0 2018年1月4日 by hushengmeng 创建
 */
@Component("hapireportUserDeviceprocessor")
public class ReportUserDeviceProcessor extends BaseApiProcessor {

	private static final Logger logger = LoggerFactory.getLogger(ReportUserDeviceProcessor.class);

	@Resource
	private IUserDeviceService userDeviceService;

	@Override
	protected void process(HttpServletRequest request,HttpServletResponse response, ResultSender sender) throws Exception {
		//入参
		//设备唯一标识
		String registrationId = request.getParameter("registrationId");
		//类型 1:安卓 2:IOS
		String type = request.getParameter("type");
		String token = request.getParameter("token");

		if(StringUtils.isBlank(registrationId) || StringUtils.isBlank(token) || StringUtils.isBlank(type)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		String userId = UserUtils.getUserIdByToken(token);
		if(StringUtils.isBlank(userId)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}

		try{
			//获取章节列表
			UserDevice userDevice = this.userDeviceService.findUniqueByParams("userId",userId);
			if(userDevice ==null){
				userDevice = new UserDevice();
				userDevice.setRegistrationId(registrationId);
				userDevice.setType(Short.parseShort(type));
				userDevice.setUserId(Long.parseLong(userId));
				userDevice.setCreateDate(new Date());
				userDevice.setUpdateDate(new Date());
				userDeviceService.save(userDevice);
			}else{
				if(!registrationId.equals(userDevice.getRegistrationId())){
					userDevice.setRegistrationId(registrationId);
					userDevice.setType(Short.parseShort(type));
					userDevice.setUpdateDate(new Date());
					userDeviceService.update(userDevice);
				}
			}
			sender.success(response);
		}catch (Exception e){
			logger.error("系统错误：" + request.getRequestURL() + "?" + request.getQueryString());
			e.printStackTrace();
			sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
		}
	}
}
