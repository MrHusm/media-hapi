package com.yxsd.kanshu.dao.impl;

import com.yxsd.kanshu.dao.IUserWeixinDao;
import com.yxsd.kanshu.ucenter.model.UserWeixin;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="userWeixinDao")
public class UserWeixinDaoImpl extends BaseDaoImpl<UserWeixin> implements IUserWeixinDao {
}
