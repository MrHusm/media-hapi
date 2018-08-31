package com.yxsd.kanshu.service;

import com.yxsd.kanshu.ucenter.model.UserWb;
import com.yxsd.kanshu.ucenter.model.UserWeibo;

/**
 * Created by hushengmeng on 2017/7/4.
 */
public interface IUserWeiboService extends IBaseService<UserWeibo,Long> {
    /**
     * 根据用户id获取绑定的微博信息
     * @param userId
     * @return
     */
    public UserWeibo getUserWeiboByUserId(Long userId);

    /**
     * 保存微博信息
     * @param userWb
     * @param userId
     * @return
     */
    public UserWeibo saveUserWeibo(UserWb userWb, Long userId);
}
