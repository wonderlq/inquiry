package com.merlin.inquery.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lq
 * @since 1.0.0
 * Created On 2019-02-14 17:00
 */
public class WfQueryModel implements Serializable {
    private String hplex; // 号牌类型 01 大型汽车，02 小型汽车
    private String dy; //地域， 省份简写 京、鄂、川等
    private String code; // 号牌码 如42219
    private String fdjh6w; // 发动机后6位
    private String verifyCode; //验证码


    private Map<String,String> cookies = new HashMap<>();

    public String getHplex() {
        return hplex;
    }

    public void setHplex(String hplex) {
        this.hplex = hplex;
    }

    public String getDy() {
        return dy;
    }

    public void setDy(String dy) {
        this.dy = dy;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFdjh6w() {
        return fdjh6w;
    }

    public void setFdjh6w(String fdjh6w) {
        this.fdjh6w = fdjh6w;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    @Override
    public String toString() {
        return "WfQueryModel{" + "hplex='" + hplex + '\'' + ", dy='" + dy + '\'' + ", code='" + code + '\'' + ", fdjh6w='" + fdjh6w + '\'' + ", verifyCode='" + verifyCode + '\'' + '}';
    }
}
