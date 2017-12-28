package com.yxsd.kanshu.dao.impl;

import com.yxsd.kanshu.dao.IUserShelfDao;
import com.yxsd.kanshu.ucenter.model.UserShelf;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="userShelfDao")
public class UserShelfDaoImpl extends BaseDaoImpl<UserShelf> implements IUserShelfDao {
}
