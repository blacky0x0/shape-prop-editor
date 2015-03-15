package com.github.blacky0x0.editor.model;

import java.util.HashMap;

/**
 * User: blacky
 * Date: 15.03.15
 */
public class Oval extends Shape {

    // TODO: collection must be unmodifiable
    protected HashMap<Property, Object> properties = new HashMap<>();
    protected boolean isCircle = false;

    public Oval(Integer x, Integer y,
                     String name,
                     Integer radiusX, Integer radiusY,
                     boolean isCircle) {
        super(x, y, name);
        this.isCircle = isCircle;
        properties.put(Property.RADIUS_X, radiusX);
        properties.put(Property.RADIUS_Y, radiusY);

    }

    public HashMap<Property, Object> getProperties() {
        return properties;
    }

    public boolean isCircle() {
        return isCircle;
    }
}
