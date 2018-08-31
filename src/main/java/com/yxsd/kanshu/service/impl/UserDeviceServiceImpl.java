package com.yxsd.kanshu.service.impl;

import com.yxsd.kanshu.dao.IBaseDao;
import com.yxsd.kanshu.dao.IUserDeviceDao;
import com.yxsd.kanshu.model.UserDevice;
import com.yxsd.kanshu.service.IUserDeviceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="userDeviceService")
public class UserDeviceServiceImpl extends BaseServiceImpl<UserDevice, Long> implements IUserDeviceService {

    @Resource(name="userDeviceDao")
    private IUserDeviceDao userDeviceDao;

    @Override
    public IBaseDao<UserDevice> getBaseDao() {
        return userDeviceDao;
    }
}
