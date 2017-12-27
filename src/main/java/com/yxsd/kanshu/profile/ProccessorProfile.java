package com.yxsd.kanshu.profile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Description: 记录接口调用 All Rights Reserved.
 * 
 * @version 1.0 2015年6月10日 下午2:08:25 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ProccessorProfile {

}
