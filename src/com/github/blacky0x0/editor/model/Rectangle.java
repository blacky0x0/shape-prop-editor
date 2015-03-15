package com.github.blacky0x0.editor.model;

import java.util.HashMap;

/**
 * User: blacky
 * Date: 15.03.15
 */
public class Rectangle extends Shape {

    // TODO: collection must be unmodifiable
    protected HashMap<Property, Object> properties = new HashMap<>();

    public Rectangle(Integer x, Integer y,
                     String name,
                     Integer width, Integer height) {
        super(x, y, name);
        properties.put(Property.WIDTH, width);
        properties.put(Property.HEIGHT, height);
    }

    public HashMap<Property, Object> getProperties() {
        return properties;
    }
}
