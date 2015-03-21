package com.github.blacky0x0.editor.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * User: blacky
 * Date: 15.03.15
 */
public abstract class Shape { //implements ShapeIF {
    protected Integer x = 0;
    protected Integer y = 0;
    protected String name = "";
    protected String type = getClass().getSimpleName();
    protected transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    protected Shape() { this(0, 0, ""); }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        propertyChangeSupport.firePropertyChange("type", this.type, this.type = type);
    }

    @Override
    public String toString() {
        return "Shape{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
