package com.merlin.inquery.model.txocr;

import java.io.Serializable;

/**
 * @author lq
 * @since 1.0.0
 * Created On 2019-02-15 11:08
 */
public class TxOcrDataItemCoord implements Serializable {

   private int x;
   private int y;
   private int width;
   private int height;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
