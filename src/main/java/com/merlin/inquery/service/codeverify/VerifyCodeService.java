package com.merlin.inquery.service.codeverify;

import com.merlin.inquery.model.VerifyCode;

import java.util.List;

/**
 * 调用外部ocr统一父类
 * @author lq
 * @since 1.0.0
 * Created On 2019-02-15 10:27
 */
public interface VerifyCodeService {

    public List<VerifyCode> verifyCode(byte[] img) throws Exception;
}
