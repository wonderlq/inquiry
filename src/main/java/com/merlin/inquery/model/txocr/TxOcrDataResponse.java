package com.merlin.inquery.model.txocr;

import java.io.Serializable;
import java.util.List;

/**
 *
 *
 * @author lq
 * @since 1.0.0
 * Created On 2019-02-15 10:42
 */
public class TxOcrDataResponse implements Serializable {

    private List<TxOcrDataItemList> item_list;

    public List<TxOcrDataItemList> getItem_list() {
        return item_list;
    }

    public void setItem_list(List<TxOcrDataItemList> item_list) {
        this.item_list = item_list;
    }
}
