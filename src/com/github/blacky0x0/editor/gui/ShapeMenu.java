package com.github.blacky0x0.editor.gui;

import com.github.blacky0x0.editor.model.Oval;
import com.github.blacky0x0.editor.model.Rectangle;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

import java.util.logging.Logger;

/**
 * User: blacky
 * Date: 22.03.15
 */
public class ShapeMenu {
    protected final Logger logger = Logger.getLogger(getClass().getName());

    private Shell shell;
    private WritableList storage;
    private ViewShapeTable shapesTable;

    // The Menu hierarchy
    private Menu bar;
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


    public ShapeMenu(Shell shell, WritableList storage, ViewShapeTable shapesTable) {
        this.shell = shell;
        this.storage = storage;
        this.shapesTable = shapesTable;

        init();
    }

    private void init() {

        bar = new Menu (shell, SWT.BAR);

        shell.setLayout(new FillLayout(SWT.HORIZONTAL));
        shell.setMenuBar(bar);

        compositeFileItem = new MenuItem(bar, SWT.CASCADE);
        compositeFileItem.setText(FILE_ITEM_TEXT);

        compositeFileMenu = new Menu(shell, SWT.DROP_DOWN);
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

        createRectangle.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event e) {
                //RectangleTitleAreaDialog dialog = new RectangleTitleAreaDialog(shell);
                ShapeTitleAreaDialog<Rectangle> dialog = new ShapeTitleAreaDialog<>(shell, Rectangle.class);
                dialog.create();

                if (dialog.open() == org.eclipse.jface.window.Window.OK) {
                    // Get a shape from dialog
                    Rectangle rectangle = dialog.getShape();

                    storage.add(rectangle);

                    logger.info(dialog.getShape().toString());
                }

            }
        });



        createOval.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event e) {

                //OvalTitleAreaDialog dialog = new OvalTitleAreaDialog(shell);
                ShapeTitleAreaDialog<Oval> dialog = new ShapeTitleAreaDialog<>(shell, Oval.class);
                dialog.create();

                if (dialog.open() == org.eclipse.jface.window.Window.OK) {
                    // Get a shape from dialog
                    Oval oval = dialog.getShape();

                    storage.add(oval);

                    logger.info(dialog.getShape().toString());
                }

            }
        });

        removeShape.setAccelerator(REMOVE_SHAPE_ACCELERATOR);
        removeShape.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event e) {

               shapesTable.removeSelectedItemsWithConfirmation();

            }
        });


        compositeFileItem.setMenu(compositeFileMenu);

        new MenuItem(compositeFileMenu, SWT.SEPARATOR);

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
