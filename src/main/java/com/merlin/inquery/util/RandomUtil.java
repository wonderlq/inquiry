package com.merlin.inquery.util;

/**
 * 随机数
 * @author lq
 * @since 1.0.0
 * Created On 2019-02-15 10:36
 */
public class RandomUtil {


    /**
     * 简单实现 ，后面优化
     * @return
     */
    public static String randomStr() {
        return String.valueOf(System.nanoTime());
    }
}
