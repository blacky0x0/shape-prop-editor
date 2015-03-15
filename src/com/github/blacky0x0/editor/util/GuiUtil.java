package com.github.blacky0x0.editor.util;


import java.awt.*;

/**
 * User: blacky
 * Date: 15.03.15
 */
public class GuiUtil {

    /**
     * Return a dimension of default screen
     * @return
     */
    public static Dimension getMainDisplayDimension() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode dm = gd.getDisplayMode();
        return new Dimension(dm.getWidth(), dm.getHeight());
    }

    /**
     * Compute center coordinates for window at display
     * @param display
     * @param window
     * @return
     */
    public static Point computeCenterPoint(Dimension display, Dimension window) {
        int x = (int)(display.getWidth() - window.getWidth()) / 2;
        int y = (int)(display.getHeight() - window.getHeight()) / 2;
        return new Point(x, y);
    }

    /**
     * Compute center coordinates for default window at display
     * @param window
     * @return
     */
    public static Point computeCenterPoint(Dimension window) {
        Dimension display = getMainDisplayDimension();
        int x = (int)(display.getWidth() - window.getWidth()) / 2;
        int y = (int)(display.getHeight() - window.getHeight()) / 2;
        return new Point(x, y);
    }


    /**
     * Compute center coordinates for default window at display
     * @param window
     * @return
     */
    public static Point computeCenterPoint(java.awt.Rectangle window) {
        Dimension display = getMainDisplayDimension();
        int x = (int)(display.getWidth() - window.getWidth()) / 2;
        int y = (int)(display.getHeight() - window.getHeight()) / 2;
        return new Point(x, y);
    }

    public static Point computeCenterPoint(org.eclipse.swt.graphics.Rectangle bounds) {
        Dimension display = getMainDisplayDimension();
        Dimension window = new Dimension(bounds.width, bounds.height);
        int x = (int)(display.getWidth() - window.getWidth()) / 2;
        int y = (int)(display.getHeight() - window.getHeight()) / 2;
        return new Point(x, y);
    }
}
