package com.yxsd.kanshu.service;

import com.yxsd.kanshu.model.Qiandao;

/**
 * Created by hushengmeng on 2018/1/9.
 */
public interface IQiandaoService extends IBaseService<Qiandao,Long> {

    /**
     * 获取签到信息
     * @return
     */
    public Qiandao getQiandao();
}
