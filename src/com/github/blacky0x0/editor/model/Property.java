package com.github.blacky0x0.editor.model;

/**
 * User: blacky
 * Date: 15.03.15
 */
public enum Property {

    WIDTH("Width"),
    HEIGHT("Height"),
    RADIUS_X("RadiusX"),
    RADIUS_Y("RadiusY");

    private String name;

    private Property(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
