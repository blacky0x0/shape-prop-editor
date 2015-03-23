package com.github.blacky0x0.editor.model;

/**
 * User: blacky
 * Date: 23.03.15
 */
public enum Type {

    OVAL      ("Oval"),
    RECTANGLE ("Rectangle");

    private Type (String name) {
        this.name = name;
    }

    private String name;


    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
