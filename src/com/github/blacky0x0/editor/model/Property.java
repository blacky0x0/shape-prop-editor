package com.github.blacky0x0.editor.model;

import java.util.HashMap;

/**
 * User: blacky
 * Date: 15.03.15
*/

/**
 * Description list of properties for shape classes
 * @see     com.github.blacky0x0.editor.model.Shape
 * @see     com.github.blacky0x0.editor.model.Rectangle
 * @see     com.github.blacky0x0.editor.model.Oval
 */
public enum Property {

    NAME    ("name",    String.class,  PropertyChain.NONE,   Rule.NO_RULE, "Name: "),
    X       ("x",       Integer.class, PropertyChain.NONE,   Rule.INTEGER_RULE, "X: "),
    Y       ("y",       Integer.class, PropertyChain.NONE,   Rule.INTEGER_RULE, "Y: "),
    WIDTH   ("width",   Integer.class, PropertyChain.NONE,   Rule.POSITIVE_INTEGER_RULE, "Width:    "),
    HEIGHT  ("height",  Integer.class, PropertyChain.NONE,   Rule.POSITIVE_INTEGER_RULE, "Height:   "),
    RIGHT   ("right",   Boolean.class, PropertyChain.NONE,   Rule.NO_RULE, ""),
    RADIUS_X("radiusX", Integer.class, PropertyChain.RADIUS, Rule.POSITIVE_INTEGER_RULE, "RadiusX: "),
    RADIUS_Y("radiusY", Integer.class, PropertyChain.RADIUS, Rule.POSITIVE_INTEGER_RULE, "RadiusY: ");

    private final String propertyName;
    private final Class  propertyClass;
    private final PropertyChain binding;
    private final Rule   rule;
    private final String printableName;

    private Property(final String propertyName, final Class propertyClass, final PropertyChain binding, final Rule rule, final String printableName) {
        this.propertyName = propertyName;
        this.propertyClass = propertyClass;
        this.binding = binding;
        this.rule = rule;
        this.printableName = printableName;
    }

    // Make an immutable map
    private static final HashMap<String, PropertyChain> PROPERTY_BINDING_MAP = new HashMap<String, PropertyChain>() {
        {
            for(Property item : Property.values())
                put(item.propertyName, item.binding);
        }
    };

    // Make an immutable map
    private static final HashMap<String, Rule> PROPERTY_RULE_MAP = new HashMap<String, Rule>() {
        {
            for(Property item : Property.values())
                put(item.propertyName, item.rule);
        }
    };

    // Make an immutable map
    private static final HashMap<String, Class> PROPERTY_CLASS_MAP = new HashMap<String, Class>() {
        {
            for(Property item : Property.values())
                put(item.propertyName, item.propertyClass);
        }
    };

    // Make an immutable map
    private static final HashMap<String, Property> PROPERTY_VALUES_MAP = new HashMap<String, Property>() {
        {
            for(Property item : Property.values())
                put(item.propertyName, item);
        }
    };

    // Make an immutable map
    private static final HashMap<String, String> PRINTABLE_VALUES_MAP = new HashMap<String, String>() {
        {
            for(Property item : Property.values())
                put(item.propertyName, item.printableName);
        }
    };

    private static final String[] OVAL_PROPERTIES_ORDER = {"name", "x", "y", "right", "radiusX", "radiusY"};
    private static final String[] RECTANGLE_PROPERTIES_ORDER = {"name", "x", "y", "width", "height"};
    private static final String[] EMPTY_PROPERTIES_ORDER = {};

    /**
     * Returns a properties order for a received shape name class
     * @param shapeType a string name of a class
     * @return a properties order of a shape
     */
    public static String[] getShapePropertiesOrder(String shapeType) {
        if (Rectangle.class.getSimpleName().equalsIgnoreCase(shapeType))
            return RECTANGLE_PROPERTIES_ORDER;

        if (Oval.class.getSimpleName().equalsIgnoreCase(shapeType))
            return OVAL_PROPERTIES_ORDER;

        return EMPTY_PROPERTIES_ORDER;
    }

    /**
     * Returns a Property for a received property name
     * @param propertyName a property of Shape class
     * @return a Property or null if a property not found
     */
    public static Property getPropertyByName(String propertyName) {

        if (PROPERTY_VALUES_MAP.get(propertyName) != null)
            return PROPERTY_VALUES_MAP.get(propertyName);

        return null;
    }

    /**
     * Returns a class for a received property name
     * @param propertyName a property of Shape class
     * @return a class or String.class if a property name not found
     */
    public static Class getPropertyClass(String propertyName) {

        if (PROPERTY_CLASS_MAP.get(propertyName) != null)
            return PROPERTY_CLASS_MAP.get(propertyName);

        return String.class;
    }

    /**
     * Returns a binding current property with others for a received property name
     * @param propertyName a property of Shape class
     * @return a binding or Binding.NONE if a property name not found
     */
    public static PropertyChain getPropertyBinding(String propertyName) {

        if (PROPERTY_BINDING_MAP.get(propertyName) != null)
            return PROPERTY_BINDING_MAP.get(propertyName);

        return PropertyChain.NONE;
    }

    /**
     * Returns a rule for a received property name
     * @param propertyName a property of Shape class
     * @return a rule or Rule.NO_RULE if a property name not found
     */
    public static Rule getPropertyRule(String propertyName) {

        if (PROPERTY_RULE_MAP.get(propertyName) != null)
            return PROPERTY_RULE_MAP.get(propertyName);

        return Rule.NO_RULE;
    }

    /**
     * Returns a printable name for a received property name
     * @param propertyName a property of Shape class
     * @return a printable name or received name if a printable name not found
     */
    public static String getPrintableName(String propertyName) {

        if (PRINTABLE_VALUES_MAP.get(propertyName) != null)
            return PRINTABLE_VALUES_MAP.get(propertyName);

        return propertyName;
    }

    @Override
    public String toString() {
        return printableName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Class getPropertyClass() {
        return propertyClass;
    }

    public String getPrintableName() {
        return printableName;
    }

    public enum Rule {
        NO_RULE(".*", ""),
        INTEGER_RULE("-?\\d*", "It must be a decimal number"),
        POSITIVE_INTEGER_RULE("\\d*", "Number must be greater or equal zero");

        Rule(String regExp, String errorMsg) {
            this.regExp = regExp;
            this.errorMsg = errorMsg;
        }

        private String regExp;
        private String errorMsg;

        public String getRegExp() {
            return regExp;
        }

        public String getErrorMsg() {
            return errorMsg;
        }
    }
}
