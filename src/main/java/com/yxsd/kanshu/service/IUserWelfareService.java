package com.yxsd.kanshu.service;

import com.yxsd.kanshu.model.UserWelfare;

/**
 * Created by hushengmeng on 2018/1/9.
 */
public interface IUserWelfareService extends IBaseService<UserWelfare,Long> {

    /**
     * 获取新用户福利类型
     * @return
     */
    public UserWelfare getUserWelfare();
}
