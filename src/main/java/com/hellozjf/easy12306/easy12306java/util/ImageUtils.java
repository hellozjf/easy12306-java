package com.hellozjf.easy12306.easy12306java.util;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * @author Jingfeng Zhou
 */
@Slf4j
public class ImageUtils {

    /**
     * 将图片转化为字符串
     * @param data
     * @return
     */
    public static String encode(byte[] data) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    public static byte[] decode(String data) {
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            return decoder.decodeBuffer(data);
        } catch (IOException e) {
            log.error("e = {}", e);
            return null;
        }
    }
}
