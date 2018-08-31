package com.yxsd.kanshu.service;

import com.yxsd.kanshu.ucenter.model.UserVip;

/**
 * Created by hushengmeng on 2017/7/4.
 */
public interface IUserVipService extends IBaseService<UserVip,Long> {

    /**
     * 增加VIP天数
     * @param userId
     * @param days
     * @param channel
     * @return
     */
    public void addVip(Long userId,Integer days,Integer channel);
}
