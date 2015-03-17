package com.github.blacky0x0.editor.model;

import java.util.HashMap;

/**
 * User: blacky
 * Date: 15.03.15
 */
public class Oval extends Shape {

    protected Integer radiusX;
    protected Integer radiusY;

    protected boolean isCircle = false;

    public Oval(Integer x, Integer y,
                     String name,
                     Integer radiusX, Integer radiusY) {
        super(x, y, name);

        this.isCircle = true;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
    }

    public Integer getRadiusX() {
        return radiusX;
    }

    public void setRadiusX(Integer radiusX) {
        this.radiusX = radiusX;
    }

    public Integer getRadiusY() {
        return radiusY;
    }

    public void setRadiusY(Integer radiusY) {
        this.radiusY = radiusY;
    }

    public boolean isCircle() {
        return isCircle;
    }

    public void setCircle(boolean isCircle) {
        this.isCircle = isCircle;
    }
}
