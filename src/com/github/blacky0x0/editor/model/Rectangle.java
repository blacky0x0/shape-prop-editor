package com.github.blacky0x0.editor.model;


/**
 * User: blacky
 * Date: 15.03.15
 */
public class Rectangle extends Shape {

    protected Integer width = 0;
    protected Integer height = 0;

    public Rectangle() { super(); }

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
        propertyChangeSupport.firePropertyChange("width", this.width, this.width = width);
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        propertyChangeSupport.firePropertyChange("height", this.height, this.height = height);
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "name='" + name + '\'' +
                ", type='" + getClass().getSimpleName() + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                "} ";
    }

}
