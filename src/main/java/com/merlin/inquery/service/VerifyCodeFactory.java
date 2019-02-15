package com.merlin.inquery.service;

import com.merlin.inquery.model.VerifyCode;
import com.merlin.inquery.service.codeverify.TxOcrVerifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 封装获取验证码服务，验证码处理逻辑在这里
 *
 * @author lq
 * @since 1.0.0
 * Created On 2019-02-15 10:49
 */

@Service
public class VerifyCodeFactory {
    private static final Logger logger = LoggerFactory.getLogger(VerifyCodeFactory.class);

    @Autowired
    TxOcrVerifyService txOcrVerifyService;


    public String getCode(byte[] img) {
        StringBuilder s = new StringBuilder();

        try {
            List<VerifyCode> verifyCodes = txOcrVerifyService.verifyCode(img);
            if (CollectionUtils.isEmpty(verifyCodes)) {
                return null;
            }
            for (VerifyCode verifyCode : verifyCodes) {
                if (verifyCode.getConfidence() > 0.4) {
                    s.append(verifyCode.getWord());
                } else {
                    s.append("@");
                }
            }
        } catch (Exception e) {
            logger.error("get code exception", e);
        }
        return s.toString();
    }
}
