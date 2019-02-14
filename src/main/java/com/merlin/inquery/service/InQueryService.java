package com.merlin.inquery.service;

import com.merlin.inquery.model.WfQueryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 违章查询
 *
 * @author lq
 * @since 1.0.0
 * Created On 2019-02-14 17:05
 */
@Service
public class InQueryService implements QueryService {

    private static final Logger logger = LoggerFactory.getLogger(InQueryService.class);

    @Autowired
    SendService sendService;

    @Override
    public void WfQuery(WfQueryModel wfQueryModel) {
        if (paramIllegalCheck(wfQueryModel)) {
            logger.error("param illegal, please check {}",wfQueryModel);
        }

        try {
            //set init cookie
            sendService.getIndexCookie(wfQueryModel);

            //get verify code and reset cookie
            sendService.getVerifyCode(wfQueryModel);

            //query
            sendService.getQueryResult(wfQueryModel);

        }catch (Exception e){
            logger.error("wf query exception ",e);
        }
    }


    /**
     * 参数检测
     *
     * @param wfQueryModel
     * @return true不通过，false 通过
     */
    private boolean paramIllegalCheck(WfQueryModel wfQueryModel) {
        return wfQueryModel.getDy() == null || wfQueryModel.getFdjh6w() == null || wfQueryModel.getHplex() == null || wfQueryModel.getCode() == null;
    }
}
