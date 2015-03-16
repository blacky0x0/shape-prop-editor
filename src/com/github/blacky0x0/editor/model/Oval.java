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
                Integer radius) {
        super(x, y, name);
        this.isCircle = true;
        properties.put(Property.RADIUS, radius);
    }

    public Oval(Integer x, Integer y,
                     String name,
                     Integer radiusX, Integer radiusY) {
        super(x, y, name);

        if (radiusX.equals(radiusY))
        {
            this.isCircle = true;
            properties.put(Property.RADIUS, radiusX);
        }
        else
        {
            properties.put(Property.RADIUS_X, radiusX);
            properties.put(Property.RADIUS_Y, radiusY);
        }
    }

    public HashMap<Property, Object> getProperties() {
        return properties;
    }

    public boolean isCircle() {
        return isCircle;
    }
}
