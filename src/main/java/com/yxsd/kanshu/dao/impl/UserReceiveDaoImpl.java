package com.yxsd.kanshu.dao.impl;

import com.yxsd.kanshu.dao.IUserReceiveDao;
import com.yxsd.kanshu.ucenter.model.UserReceive;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="userReceiveDao")
public class UserReceiveDaoImpl extends BaseDaoImpl<UserReceive> implements IUserReceiveDao {
}
