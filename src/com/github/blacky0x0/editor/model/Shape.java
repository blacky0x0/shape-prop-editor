package com.github.blacky0x0.editor.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * User: blacky
 * Date: 15.03.15
 */
public abstract class Shape { //implements ShapeIF {
    protected Integer x;
    protected Integer y;
    protected String name;
    protected PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    protected Shape(Integer x, Integer y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public void addPropertyChangeListener(String propertyName,
                                          PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        propertyChangeSupport.firePropertyChange("x", this.x, this.x = x);
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        propertyChangeSupport.firePropertyChange("y", this.y, this.y = y);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        propertyChangeSupport.firePropertyChange("name", this.name, this.name = name);
    }

    @Override
    public String toString() {
        return "Shape{" +
                "name='" + name + '\'' +
                ", type='" + getClass().getSimpleName() + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
