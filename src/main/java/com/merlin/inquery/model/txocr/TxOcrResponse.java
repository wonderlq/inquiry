package com.merlin.inquery.model.txocr;

import java.io.Serializable;

/**
 * @link https://ai.qq.com/doc/ocrgeneralocr.shtml
 * tx ocr 返回结果bean
 * @author lq
 * @since 1.0.0
 * Created On 2019-02-15 10:42
 */
public class TxOcrResponse implements Serializable {

    private int ret;
    private String msg;
    private TxOcrDataResponse data;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public TxOcrDataResponse getData() {
        return data;
    }

    public void setData(TxOcrDataResponse data) {
        this.data = data;
    }
}
