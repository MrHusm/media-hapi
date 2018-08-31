package com.yxsd.kanshu.service.impl;

import com.yxsd.kanshu.dao.IBaseDao;
import com.yxsd.kanshu.dao.IUserPayBookDao;
import com.yxsd.kanshu.service.IUserPayBookService;
import com.yxsd.kanshu.ucenter.model.UserPayBook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="userPayBookService")
public class UserPayBookServiceImpl extends BaseServiceImpl<UserPayBook, Long> implements IUserPayBookService {

    @Resource(name="userPayBookDao")
    private IUserPayBookDao userPayBookDao;

    @Override
    public IBaseDao<UserPayBook> getBaseDao() {
        return userPayBookDao;
    }

}
