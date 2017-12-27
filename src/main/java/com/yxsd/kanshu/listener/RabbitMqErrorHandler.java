/**
 * @author tanfeng Jan 5, 2013 12:13:31 PM 
 */
package com.yxsd.kanshu.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

/**
 * 
 * Description: 异常监听
 * All Rights Reserved.
 * @version 1.0  2014年12月31日 上午10:40:32  by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component
public class RabbitMqErrorHandler implements ErrorHandler {

	private static Logger logger = LoggerFactory
			.getLogger(RabbitMqErrorHandler.class);
	
	@Override
	public void handleError(Throwable t) {
		logger.error("Receive rabbitmq message error:{}", t); // error
	}

}
