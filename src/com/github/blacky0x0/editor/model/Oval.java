package com.github.blacky0x0.editor.model;


/**
 * User: blacky
 * Date: 15.03.15
 */
public class Oval extends Shape {

    protected Integer radiusX = 0;
    protected Integer radiusY = 0;
    protected Boolean right = false;    // is it a right figure?

    public Oval() { this(0, 0, "", 0, 0); }

    public Oval(Integer x, Integer y,
                     String name,
                     Integer radiusX, Integer radiusY) {
        super(x, y, name);

        this.radiusX = radiusX;
        this.radiusY = radiusY;
    }

    public Boolean isRight() {
        return right;
    }

    public Boolean getRight() {
        return right;
    }

    public void setRight(Boolean right) {
        propertyChangeSupport.firePropertyChange("right", this.right, this.right = right);
        if (isRight()) {
            propertyChangeSupport.firePropertyChange("radiusX", this.radiusX, this.radiusX = radiusX);
            propertyChangeSupport.firePropertyChange("radiusY", this.radiusY, this.radiusY = radiusX);
        }
    }

    public Integer getRadiusX() {
        return radiusX;
    }

    public void setRadiusX(Integer radiusX) {
        propertyChangeSupport.firePropertyChange("radiusX", this.radiusX, this.radiusX = radiusX);
        if (isRight())
            propertyChangeSupport.firePropertyChange("radiusY", this.radiusY, this.radiusY = radiusX);
    }

    public Integer getRadiusY() {
        return radiusY;
    }

    public void setRadiusY(Integer radiusY) {
        propertyChangeSupport.firePropertyChange("radiusY", this.radiusY, this.radiusY = radiusY);
        if (isRight())
            propertyChangeSupport.firePropertyChange("radiusX", this.radiusX, this.radiusX = radiusY);
    }

    @Override
    public String toString() {
        return "Oval{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", radiusX=" + radiusX +
                ", radiusY=" + radiusY +
                "} ";
    }
}
