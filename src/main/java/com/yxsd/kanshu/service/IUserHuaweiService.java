package com.yxsd.kanshu.service;

import com.yxsd.kanshu.ucenter.model.UserHuawei;

/**
 * Created by hushengmeng on 2017/7/4.
 */
public interface IUserHuaweiService extends IBaseService<UserHuawei,Long> {

    /**
     * 根据用户id获取绑定的华为账号信息
     * @param userId
     * @return
     */
    public UserHuawei getUserHuaweiByUserId(Long userId);
}
