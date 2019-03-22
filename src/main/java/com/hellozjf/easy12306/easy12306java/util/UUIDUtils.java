package com.hellozjf.easy12306.easy12306java.util;

import java.util.UUID;

/**
 * @author hellozjf
 */
public class UUIDUtils {

    /**
     * 生成没有-的UUID字符串
     * @return
     */
    public static String genId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
