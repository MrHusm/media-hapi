package com.yxsd.kanshu.service.impl;

import com.yxsd.kanshu.constant.RedisKeyConstants;
import com.yxsd.kanshu.dao.IBaseDao;
import com.yxsd.kanshu.dao.IUserHuaweiDao;
import com.yxsd.kanshu.service.IUserHuaweiService;
import com.yxsd.kanshu.ucenter.model.UserHuawei;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="userHuaweiService")
public class UserHuaweiServiceImpl extends BaseServiceImpl<UserHuawei, Long> implements IUserHuaweiService {

    @Resource(name="userHuaweiDao")
    private IUserHuaweiDao userHuaweiDao;

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,UserHuawei> masterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,UserHuawei> slaveRedisTemplate;

    @Override
    public IBaseDao<UserHuawei> getBaseDao() {
        return userHuaweiDao;
    }

    @Override
    public UserHuawei getUserHuaweiByUserId(Long userId) {
        String key = RedisKeyConstants.CACHE_USER_HUAWEI_ID_KEY + userId;
        UserHuawei userHuawei = slaveRedisTemplate.opsForValue().get(key);
        if(userHuawei == null){
            userHuawei = this.findUniqueByParams("userId",userId);
            if(userHuawei != null){
                masterRedisTemplate.opsForValue().set(key, userHuawei, 2, TimeUnit.DAYS);
            }
        }
        return userHuawei;
    }
}
