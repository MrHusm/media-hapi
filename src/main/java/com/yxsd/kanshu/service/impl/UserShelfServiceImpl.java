package com.yxsd.kanshu.service.impl;


import com.yxsd.kanshu.dao.IBaseDao;
import com.yxsd.kanshu.dao.IUserShelfDao;
import com.yxsd.kanshu.service.IUserShelfService;
import com.yxsd.kanshu.ucenter.model.UserShelf;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="userShelfService")
public class UserShelfServiceImpl extends BaseServiceImpl<UserShelf, Long> implements IUserShelfService {

    @Resource(name="userShelfDao")
    private IUserShelfDao userShelfDao;

    @Override
    public IBaseDao<UserShelf> getBaseDao() {
        return userShelfDao;
    }

}
