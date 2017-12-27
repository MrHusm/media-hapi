package com.yxsd.kanshu.utils;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: dongzhanjun
 * Date: 2016/3/4
 * Time: 18:49
 * To change this template use File | Settings | File Templates.
 */
public class GenerateUUID {
    /**
     * 生成UUID.
     * @return UUID
     */
    public static String generateUUID() {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();
        uuidStr = uuidStr.replaceAll("-", "");
        return uuidStr;
    }
}
