package com.github.blacky0x0.editor.model;

import java.util.HashMap;

/**
 * User: blacky
 * Date: 15.03.15
 */
public class Rectangle extends Shape {

    protected Integer width;
    protected Integer height;

    public Rectangle(Integer x, Integer y,
                     String name,
                     Integer width, Integer height) {
        super(x, y, name);
        this.width = width;
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

}
