package com.yxsd.kanshu.service.impl;

import com.yxsd.kanshu.dao.IBaseDao;
import com.yxsd.kanshu.dao.IUserPayChapterDao;
import com.yxsd.kanshu.service.IUserPayChapterService;
import com.yxsd.kanshu.ucenter.model.UserPayChapter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="userPayChapterService")
public class UserPayChapterServiceImpl extends BaseServiceImpl<UserPayChapter, Long> implements IUserPayChapterService {

    @Resource(name="userPayChapterDao")
    private IUserPayChapterDao userPayChapterDao;

    @Override
    public IBaseDao<UserPayChapter> getBaseDao() {
        return userPayChapterDao;
    }

}
