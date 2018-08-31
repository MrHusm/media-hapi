package com.yxsd.kanshu.service.impl;

import com.yxsd.kanshu.dao.IBaseDao;
import com.yxsd.kanshu.dao.IUserVipDao;
import com.yxsd.kanshu.service.IUserVipService;
import com.yxsd.kanshu.ucenter.model.UserVip;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="userVipService")
public class UserVipServiceImpl extends BaseServiceImpl<UserVip, Long> implements IUserVipService {

    @Resource(name="userVipDao")
    private IUserVipDao userVipDao;

    @Override
    public IBaseDao<UserVip> getBaseDao() {
        return userVipDao;
    }

    @Override
    public void addVip(Long userId, Integer days,Integer channel) {
        UserVip userVip = findUniqueByParams("userId",userId);
        Calendar calendar = Calendar.getInstance();
        if(userVip != null){
            Date now = new Date();
            if(now.getTime() > userVip.getEndDate().getTime()){
                calendar.setTime(now);
            }else{
                calendar.setTime(userVip.getEndDate());
            }
            calendar.add(Calendar.DAY_OF_MONTH, days);
            userVip.setEndDate(calendar.getTime());
            userVip.setUpdateDate(new Date());
            update(userVip);
        }else{
            userVip = new UserVip();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, days);
            userVip.setEndDate(calendar.getTime());
            userVip.setUserId(userId);
            userVip.setUpdateDate(new Date());
            userVip.setCreateDate(new Date());
            if(channel !=null){
                userVip.setChannel(channel);
            }
            save(userVip);
        }
    }
}
