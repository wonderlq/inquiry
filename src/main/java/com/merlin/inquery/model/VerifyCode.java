package com.merlin.inquery.model;

/**
 * @author lq
 * @since 1.0.0
 * Created On 2019-02-15 10:28
 */
public class VerifyCode {

    private String word;
    private double confidence;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }
}
