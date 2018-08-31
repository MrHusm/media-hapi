package com.yxsd.kanshu.dao.impl;

import com.yxsd.kanshu.dao.IUserWeiboDao;
import com.yxsd.kanshu.ucenter.model.UserWeibo;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="userWeiboDao")
public class UserWeiboDaoImpl extends BaseDaoImpl<UserWeibo> implements IUserWeiboDao {
}
