package com.github.blacky0x0.editor.model;

/**
 * User: blacky
 * Date: 15.03.15
 */
public abstract class Shape { //implements ShapeIF {
    protected Integer x;
    protected Integer y;
    protected String name;

    protected Shape(Integer x, Integer y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }
}
