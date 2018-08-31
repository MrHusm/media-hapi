package com.yxsd.kanshu.service.impl;

import com.yxsd.kanshu.dao.IBaseDao;
import com.yxsd.kanshu.dao.IUserQiandaoDao;
import com.yxsd.kanshu.model.UserQiandao;
import com.yxsd.kanshu.service.IUserQiandaoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="userQiandaoService")
public class UserQiandaoServiceImpl extends BaseServiceImpl<UserQiandao, Long> implements IUserQiandaoService {

    @Resource(name="userQiandaoDao")
    private IUserQiandaoDao userQiandaoDao;

    @Override
    public IBaseDao<UserQiandao> getBaseDao() {
        return userQiandaoDao;
    }
}
