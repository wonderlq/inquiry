package com.merlin.inquery.facade;

import com.merlin.inquery.model.WfQueryModel;

/**
 * 对外接口
 * @author lq
 * @since 1.0.0
 * Created On 2019-02-14 17:05
 */
public interface QueryService{

    /**
     * 车辆违章查询
     * @param wfQueryModel
     */
    public void WfQuery(WfQueryModel wfQueryModel);
}
