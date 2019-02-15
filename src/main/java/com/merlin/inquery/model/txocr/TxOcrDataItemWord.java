package com.merlin.inquery.model.txocr;

import java.io.Serializable;

/**
 * @author lq
 * @since 1.0.0
 * Created On 2019-02-15 11:08
 */
public class TxOcrDataItemWord implements Serializable {

    private String character;
    private double confidence;

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }
}
