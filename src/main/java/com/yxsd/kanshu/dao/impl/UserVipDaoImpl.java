package com.yxsd.kanshu.dao.impl;

import com.yxsd.kanshu.dao.IUserVipDao;
import com.yxsd.kanshu.ucenter.model.UserVip;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="userVipDao")
public class UserVipDaoImpl extends BaseDaoImpl<UserVip> implements IUserVipDao {
}
