package com.yxsd.kanshu.service.impl;

import com.yxsd.kanshu.constant.RedisKeyConstants;
import com.yxsd.kanshu.dao.IBaseDao;
import com.yxsd.kanshu.dao.IQiandaoDao;
import com.yxsd.kanshu.model.Qiandao;
import com.yxsd.kanshu.service.IQiandaoService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by hushengmeng on 2018/1/9.
 */
@Service(value="qiandaoService")
public class QiandaoServiceImpl extends BaseServiceImpl<Qiandao, Long> implements IQiandaoService {

    @Resource(name="qiandaoDao")
    private IQiandaoDao qiandaoDao;

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,Qiandao> masterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,Qiandao> slaveRedisTemplate;

    @Override
    public IBaseDao<Qiandao> getBaseDao() {
        return qiandaoDao;
    }

    @Override
    public Qiandao getQiandao() {
        String key = RedisKeyConstants.CACHE_QIANDAO_KEY;
        Qiandao qiandao = slaveRedisTemplate.opsForValue().get(key);
        if(qiandao == null){
            List<Qiandao> list = this.findListByParamsObjs(null);
            if(CollectionUtils.isNotEmpty(list)){
                qiandao = list.get(0);
                masterRedisTemplate.opsForValue().set(key, qiandao, 5, TimeUnit.DAYS);
            }
        }
        return qiandao;
    }
}
