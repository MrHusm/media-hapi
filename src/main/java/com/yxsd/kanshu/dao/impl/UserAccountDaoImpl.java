package com.yxsd.kanshu.dao.impl;

import com.yxsd.kanshu.dao.IUserAccountDao;
import com.yxsd.kanshu.ucenter.model.UserAccount;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="userAccountDao")
public class UserAccountDaoImpl extends BaseDaoImpl<UserAccount> implements IUserAccountDao {
}
