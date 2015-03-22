package com.github.blacky0x0.editor.model;

/**
 * User: blacky
 * Date: 22.03.15
 */
public enum PropertyChain {

    NONE   (""),
    RADIUS ("Radius: ", "radiusX", "radiusY");

    private String newPrintableName;
    private String[] propertyNames;

    PropertyChain(String newPrintableName, String ... propertyNames) {
        this.propertyNames = propertyNames;
        this.newPrintableName = newPrintableName;
    }

    public String getNewPrintableName() {
        return newPrintableName;
    }

    public String[] getPropertyNames() {
        return propertyNames;
    }

}
