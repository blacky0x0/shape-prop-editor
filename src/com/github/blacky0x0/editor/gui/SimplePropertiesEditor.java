package com.github.blacky0x0.editor.gui;

import com.github.blacky0x0.editor.model.Rectangle;
import com.github.blacky0x0.editor.model.Shape;
import com.github.blacky0x0.editor.util.GuiUtil;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

import java.awt.Point;
import java.util.HashSet;

/**
 * User: blacky
 * Date: 15.03.15
 */
public class SimplePropertiesEditor {

    private final Display display = new Display ();

    // The application components
    private final Shell shell = new Shell (display);

    // The Menu hierarchy
    private final Menu bar = new Menu (shell, SWT.BAR);
    private MenuItem compositeFileItem;
    private final String FILE_ITEM_TEXT = "&File";
    private Menu compositeFileMenu;
    private MenuItem modelSubItem;
    private Menu modelItemSubMenu;
    private final String MODEL_SUB_ITEM_TEXT = "&Model";
    private MenuItem createRectangle;
    private final String CREATE_RECTANGLE_TEXT = "Create &Rectangle";
    private MenuItem createOval;
    private final String CREATE_OVAL_TEXT = "Create &Oval";
    private MenuItem removeShape;
    private final String REMOVE_SHAPE_TEXT = "Remove";
    private final int REMOVE_SHAPE_ACCELERATOR = SWT.MOD1 + 'D';
    private MenuItem exit;
    private final String EXIT_TEXT = "E&xit";
    private final int EXIT_ACCELERATOR = SWT.MOD1 + 'X';


    public static void main (String [] args) {
        SimplePropertiesEditor editor = new SimplePropertiesEditor();
        editor.init();
    }

    public void init() {

        initMenu();

        initData();

        finishInit();

    }

    private void finishInit() {
        shell.setSize(600, 400);

        // Set a window at center the current screen
        Point locationPoint = GuiUtil.computeCenterPoint(shell.getBounds());
        shell.setLocation((int) locationPoint.getX(), (int) locationPoint.getY());

        shell.open ();


        while (!shell.isDisposed ()) {
            if (!display.readAndDispatch ()) display.sleep ();
        }
        display.dispose ();
    }

    private void initData() {
        // This in-memory storage contains all shapes
        HashSet<Shape> storage = new HashSet<>();

        storage.add(new Rectangle(1, 2, "r", 10, 20));
        storage.add(new Rectangle(1, 2, "r" ,10, 20));
        storage.add(new Rectangle(1, 2, "r" ,10, 20));

//        List list = new List (shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
//        //for (shape : storage)
//        list.add("" + "123");//shape.toString());
//        list.add("" + "12343");//shape.toString());
    }

    public void initMenu() {
        shell.setMenuBar(bar);

        compositeFileItem = new MenuItem (bar, SWT.CASCADE);
        compositeFileItem.setText(FILE_ITEM_TEXT);

        compositeFileMenu = new Menu (shell, SWT.DROP_DOWN);
        compositeFileItem.setMenu(compositeFileMenu);

        modelSubItem = new MenuItem (compositeFileMenu, SWT.CASCADE);
        modelSubItem.setText(MODEL_SUB_ITEM_TEXT);

        modelItemSubMenu = new Menu (shell, SWT.DROP_DOWN);
        modelSubItem.setMenu(modelItemSubMenu);

        createRectangle = new MenuItem (modelItemSubMenu, SWT.PUSH);
        createOval = new MenuItem (modelItemSubMenu, SWT.PUSH);
        removeShape = new MenuItem (modelItemSubMenu, SWT.PUSH);

        createRectangle.setText(CREATE_RECTANGLE_TEXT);
        createOval.setText(CREATE_OVAL_TEXT);
        removeShape.setText(REMOVE_SHAPE_TEXT);

        removeShape.setAccelerator(REMOVE_SHAPE_ACCELERATOR);
        removeShape.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event e) {
                System.out.println("Selected shapes were removed");
            }
        });

        compositeFileItem.setMenu(compositeFileMenu);

        exit = new MenuItem (compositeFileMenu, SWT.PUSH);

        exit.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event e) {
                System.exit(0);
            }
        });
        exit.setText(EXIT_TEXT);
        exit.setAccelerator(EXIT_ACCELERATOR);
    }

}
