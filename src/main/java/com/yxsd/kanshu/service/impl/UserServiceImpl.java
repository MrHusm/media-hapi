package com.yxsd.kanshu.service.impl;

import com.yxsd.kanshu.dao.IBaseDao;
import com.yxsd.kanshu.dao.IUserDao;
import com.yxsd.kanshu.service.IUserService;
import com.yxsd.kanshu.ucenter.model.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="userService")
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements IUserService {

    @Resource(name="userDao")
    private IUserDao userDao;


    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,User> masterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,User> slaveRedisTemplate;

    @Override
    public IBaseDao<User> getBaseDao() {
        return userDao;
    }
}
