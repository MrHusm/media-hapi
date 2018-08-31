package com.yxsd.kanshu.dao.impl;

import com.yxsd.kanshu.dao.IUserWelfareDao;
import com.yxsd.kanshu.model.UserWelfare;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="userWelfareDao")
public class UserWelfareDaoImpl extends BaseDaoImpl<UserWelfare> implements IUserWelfareDao {
}
