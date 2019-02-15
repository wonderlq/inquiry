package com.merlin.inquery.model.txocr;

import java.io.Serializable;
import java.util.List;

/**
 * @author lq
 * @since 1.0.0
 * Created On 2019-02-15 10:42
 */
public class TxOcrDataItemList implements Serializable {
        private String item;
        private String itemstring;
        private List<TxOcrDataItemCoord> itemcoord;
        private List<TxOcrDataItemWord> words;

        public String getItem() {
                return item;
        }

        public void setItem(String item) {
                this.item = item;
        }

        public String getItemstring() {
                return itemstring;
        }

        public void setItemstring(String itemstring) {
                this.itemstring = itemstring;
        }

        public List<TxOcrDataItemCoord> getItemcoord() {
                return itemcoord;
        }

        public void setItemcoord(List<TxOcrDataItemCoord> itemcoord) {
                this.itemcoord = itemcoord;
        }

        public List<TxOcrDataItemWord> getWords() {
                return words;
        }

        public void setWords(List<TxOcrDataItemWord> words) {
                this.words = words;
        }
}
