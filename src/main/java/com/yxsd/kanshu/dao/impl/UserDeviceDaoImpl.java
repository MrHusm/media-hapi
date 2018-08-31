package com.yxsd.kanshu.dao.impl;

import com.yxsd.kanshu.dao.IUserDeviceDao;
import com.yxsd.kanshu.model.UserDevice;
import org.springframework.stereotype.Repository;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Repository(value="userDeviceDao")
public class UserDeviceDaoImpl extends BaseDaoImpl<UserDevice> implements IUserDeviceDao {
}
