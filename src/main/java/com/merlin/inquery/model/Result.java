package com.merlin.inquery.model;

import java.io.Serializable;

/**
 * @author lq
 * @since 1.0.0
 * Created On 2019-02-14 18:18
 */
public class Result implements Serializable {

    private String msg;
    private String code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
