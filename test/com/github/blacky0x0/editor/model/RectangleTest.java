package com.github.blacky0x0.editor.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * User: blacky
 * Date: 15.03.15
 */
public class RectangleTest {


    @Test
    public void testGetProperties() throws Exception {
        int x = 1;
        int y = 5;
        int width = 10;
        int height = 20;
        String name = "new rectangle";

        Rectangle rectangle = new Rectangle(x, y, name, width, height);

        Assert.assertEquals(2, rectangle.getProperties().size());

        Assert.assertEquals(width, rectangle.getProperties().get(Property.WIDTH));
        Assert.assertEquals(height, rectangle.getProperties().get(Property.HEIGHT));
    }

    @Test (expected = Exception.class)
    public void testChangeProperties() throws Exception {
        int x = 1;
        int y = 5;
        int width = 10;
        int height = 20;
        String name = "new rectangle";

        Rectangle rectangle = new Rectangle(x, y, name, width, height);

        // Expect an exception. Collection must be unmodifiable
        rectangle.getProperties().clear();
    }
}
