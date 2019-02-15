package com.merlin.inquery.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author lq
 * @since 1.0.0
 * Created On 2019-02-15 13:52
 */
public class MD5util {
    public static String md5(String value) {
        return DigestUtils.md5Hex(value);
    }
}
