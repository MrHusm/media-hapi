package com.yxsd.kanshu.profile;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.servlet.ServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Description: 日志记录intercepter All Rights Reserved.
 * 
 * @version 1.0 2015年6月4日 上午11:41:21 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Aspect
@Component
public class ProccessorProfileIntercepter {

	private static final Logger logger = LoggerFactory.getLogger(ProccessorProfileIntercepter.class);

	@Around("@annotation(com.yxsd.kanshu.profile.ProccessorProfile)")
	public Object doLoggerProfiler(final ProceedingJoinPoint joinPoint) throws Throwable {
		try {

			if (logger.isInfoEnabled()) {
				Map<String, Object> logMap = new HashMap<String, Object>();
				Object[] args = joinPoint.getArgs();

				ServletRequest request = (ServletRequest) args[0];
				String action = request.getParameter("action");

				StopWatch clock = new StopWatch("Profiling for '" + joinPoint.getSignature() + "'");
				try {
					clock.start(joinPoint.toShortString());
					return joinPoint.proceed();
				} finally {
					clock.stop();
					logMap.put(action + "接口用时", clock.getLastTaskTimeMillis() + "ms");
					logger.info(JSON.toJSONString(logMap));
				}
			} else {
				return joinPoint.proceed();
			}
		} catch (Exception e) {
			logger.error("记录接口日志发生异常", e);
			//liyue 20161216：此处再执行一次proceed会导致接口被二次调用，正确的方法是抛出这个异常。
			throw e;
			//return joinPoint.proceed();
		}
	}

	/**
	 * @param joinPoint
	 * @return
	 * @throws NoSuchMethodException
	 */
	protected Method getMethod(final JoinPoint joinPoint) throws NoSuchMethodException {
		final Signature sig = joinPoint.getSignature();
		if (!(sig instanceof MethodSignature)) {
			throw new NoSuchMethodException("This annotation is only valid on a method.");
		}
		final MethodSignature msig = (MethodSignature) sig;
		final Object target = joinPoint.getTarget();
		return target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
	}
}
