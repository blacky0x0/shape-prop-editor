package com.github.blacky0x0.editor.gui;

import com.github.blacky0x0.editor.util.GuiUtil;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

import java.awt.Point;

/**
 * User: blacky
 * Date: 15.03.15
 */
public class SimplePropertiesEditor {
    public static void main (String [] args) {
        Display display = new Display ();
        Shell shell = new Shell (display);
        Menu bar = new Menu (shell, SWT.BAR);

        shell.setMenuBar(bar);

        MenuItem fileItem = new MenuItem (bar, SWT.CASCADE);
        fileItem.setText("&File");

        Menu submenu = new Menu (shell, SWT.DROP_DOWN);
        fileItem.setMenu(submenu);

        MenuItem exit = new MenuItem (submenu, SWT.PUSH);

        exit.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event e) {
                System.exit(0);
            }
        });
        exit.setText("E&xit\tCtrl+X");
        exit.setAccelerator(SWT.MOD1 + 'X');
        shell.setSize(200, 200);

        // Set a window at center the current screen
        Point locationPoint = GuiUtil.computeCenterPoint(shell.getBounds());
        shell.setLocation((int) locationPoint.getX(), (int) locationPoint.getY());

        shell.open ();


        while (!shell.isDisposed ()) {
            if (!display.readAndDispatch ()) display.sleep ();
        }
        display.dispose ();
    }



}
