package com.yxsd.kanshu.dao.impl;

import com.yxsd.kanshu.dao.IUserPayBookDao;
import com.yxsd.kanshu.ucenter.model.UserPayBook;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="userPayBookDao")
public class UserPayBookDaoImpl extends BaseDaoImpl<UserPayBook> implements IUserPayBookDao {
}
