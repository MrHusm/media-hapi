package com.yxsd.kanshu.service.impl;

import com.yxsd.kanshu.constant.RedisKeyConstants;
import com.yxsd.kanshu.dao.IBaseDao;
import com.yxsd.kanshu.dao.IUserWelfareDao;
import com.yxsd.kanshu.model.UserWelfare;
import com.yxsd.kanshu.service.IUserWelfareService;
import com.yxsd.kanshu.utils.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by hushengmeng on 2018/1/9.
 */
@Service(value="userWelfareService")
public class UserWelfareServiceImpl extends BaseServiceImpl<UserWelfare, Long> implements IUserWelfareService {

    @Resource(name="userWelfareDao")
    private IUserWelfareDao userWelfareDao;

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,UserWelfare> masterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,UserWelfare> slaveRedisTemplate;

    @Override
    public IBaseDao<UserWelfare> getBaseDao() {
        return userWelfareDao;
    }

    @Override
    public UserWelfare getUserWelfare() {
        String key = RedisKeyConstants.CACHE_NEW_USER_WELFARE_KEY;
        UserWelfare userWelfare = slaveRedisTemplate.opsForValue().get(key);
        if(userWelfare == null){
            List<UserWelfare> list = this.findListByParamsObjs(null);
            if(CollectionUtils.isNotEmpty(list)){
                userWelfare = list.get(0);
                masterRedisTemplate.opsForValue().set(key, userWelfare, 5, TimeUnit.DAYS);
            }
        }
        return userWelfare;
    }
}
