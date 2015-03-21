package com.github.blacky0x0.editor.model;


/**
 * User: blacky
 * Date: 15.03.15
 */
public class Oval extends Shape {

    protected Integer radiusX = 0;
    protected Integer radiusY = 0;

    public Oval() { super(); }

    public Oval(Integer x, Integer y,
                     String name,
                     Integer radiusX, Integer radiusY) {
        super(x, y, name);

        this.radiusX = radiusX;
        this.radiusY = radiusY;
    }

    public Integer getRadiusX() {
        return radiusX;
    }

    public void setRadiusX(Integer radiusX) {
        propertyChangeSupport.firePropertyChange("radiusX", this.radiusX, this.radiusX = radiusX);
    }

    public Integer getRadiusY() {
        return radiusY;
    }

    public void setRadiusY(Integer radiusY) {
        propertyChangeSupport.firePropertyChange("radiusY", this.radiusY, this.radiusY = radiusY);
    }

    public boolean isCircle() {
        return radiusX.equals(radiusY);
    }

    @Override
    public String toString() {
        return "Oval{" +
                "name='" + name + '\'' +
                ", type='" + getClass().getSimpleName() + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", radiusX=" + radiusX +
                ", radiusY=" + radiusY +
                "} ";
    }
}
