package com.github.blacky0x0.editor.model;

import java.util.HashMap;

/**
 * User: blacky
 * Date: 15.03.15
 */
public interface ShapeIF {
    Integer getX();
    Integer getY();
    String getName();

    HashMap<Property, Object> getProperties();
}
