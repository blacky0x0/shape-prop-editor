package com.github.blacky0x0.editor.model;

/**
 * User: blacky
 * Date: 15.03.15
 */
public enum Property {

    NAME("name", "Name: "),
    X("x", "X: "),
    Y("y", "Y: "),
    WIDTH("width", "Width: "),
    HEIGHT("height", "Height: "),
    RADIUS("radius", "Radius: "),
    RADIUS_X("radiusX", "RadiusX: "),
    RADIUS_Y("radiusY", "RadiusY: ");

    private String propertyName;
    private String printableName;

    private Property(String propertyName, String printableName) {
        this.propertyName = propertyName;
        this.printableName = printableName;
    }

    public static final String[] ovalPropertiesOrder = {"name", "x", "y", "radiusX", "radiusY"};
    public static final String[] rectanglePropertiesOrder = {"name", "x", "y", "width", "height"};
    public static final String[] emptyPropertiesOrder = {};

    public static String[] getShapePropertiesOrder(String shapeType) {
        if (Rectangle.class.getSimpleName().equalsIgnoreCase(shapeType))
            return rectanglePropertiesOrder;

        if (Oval.class.getSimpleName().equalsIgnoreCase(shapeType))
            return ovalPropertiesOrder;

        return emptyPropertiesOrder;
    }

    // move to HashMap?
    public static String getPrintableName(String propertyName) {

        if (NAME.propertyName.equals(propertyName))
            return NAME.printableName;

        if (X.propertyName.equals(propertyName))
            return X.printableName;

        if (Y.propertyName.equals(propertyName))
            return Y.printableName;

        if (WIDTH.propertyName.equals(propertyName))
            return WIDTH.printableName;

        if (HEIGHT.propertyName.equals(propertyName))
            return HEIGHT.printableName;

        if (RADIUS.propertyName.equals(propertyName))
            return RADIUS.printableName;

        if (RADIUS_X.propertyName.equals(propertyName))
            return RADIUS_X.printableName;

        if (RADIUS_Y.propertyName.equals(propertyName))
            return RADIUS_Y.printableName;

        return propertyName;
    }

    @Override
    public String toString() {
        return printableName;
    }
}
