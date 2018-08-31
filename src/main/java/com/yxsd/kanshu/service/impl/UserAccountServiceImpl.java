package com.yxsd.kanshu.service.impl;

import com.yxsd.kanshu.dao.IBaseDao;
import com.yxsd.kanshu.dao.IUserAccountDao;
import com.yxsd.kanshu.service.IUserAccountService;
import com.yxsd.kanshu.ucenter.model.UserAccount;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="userAccountService")
public class UserAccountServiceImpl extends BaseServiceImpl<UserAccount, Long> implements IUserAccountService {

    @Resource(name="userAccountDao")
    private IUserAccountDao userAccountDao;

    @Override
    public IBaseDao<UserAccount> getBaseDao() {
        return userAccountDao;
    }
}
