package com.github.blacky0x0.editor.gui;

import com.github.blacky0x0.editor.model.Oval;
import com.github.blacky0x0.editor.model.Rectangle;
import com.github.blacky0x0.editor.model.Shape;
import com.github.blacky0x0.editor.repository.ListStorage;
import com.github.blacky0x0.editor.util.GuiUtil;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import java.util.logging.*;
import java.awt.*;

import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.custom.StyledText;

/**
 * User: blacky
 * Date: 15.03.15
 */
public class SimplePropertiesEditor {
    protected final Logger logger = Logger.getLogger(getClass().getName());

    // This in-memory storage contains all shapes
    private WritableList storage = new WritableList(ListStorage.getShapes(), Shape.class);

    private static final Display display = new Display ();

    // The application components
    private static final Shell shell = new Shell (display);

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

    // A sashform contains two tables
    private SashForm sashForm;
    // Two tables
    private ViewShapeTable shapesTable;

    // Vary properties forms
    private Composite propertyForm;
//    private RectangleForm rectangleForm;
//    private OvalForm ovalForm;
    private ShapeComposite<Rectangle> rectangleForm;
    private ShapeComposite<Oval> ovalForm;

    private Label lblSelectOneRow;

    public static void main (String [] args) {

        Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
            public void run() {
                //Shell shell = new Shell(display);

                SimplePropertiesEditor editor = new SimplePropertiesEditor();
                editor.init();

                // Set a window at center the current screen
                Point locationPoint = GuiUtil.computeCenterPoint(shell.getBounds());
                shell.setLocation((int) locationPoint.getX(), (int) locationPoint.getY());

                //shell.pack();
                shell.setSize(600, 400);
                shell.open();
                while (!shell.isDisposed()) {
                    if (!display.readAndDispatch())
                        display.sleep();
                }
            }
        });

        display.dispose();
    }

    private void init() {

        initMenu();

        initControls();

    }

    public void hidePropertiesForm() {
        ovalForm.setVisible(false);
        rectangleForm.setVisible(false);
        lblSelectOneRow.setVisible(true);
    }

    public void updateForm(Rectangle rectangle) {
        ovalForm.setVisible(false);
//        rectangleForm.updateForm(rectangle);
        rectangleForm.setVisible(true);
        lblSelectOneRow.setVisible(false);
    }

    public void updateForm(Oval oval) {
        rectangleForm.setVisible(false);
//        ovalForm.updateForm(oval);
        ovalForm.setVisible(true);
        lblSelectOneRow.setVisible(false);
    }

    private void initControls() {
        
        sashForm = new SashForm(shell, SWT.NONE);

        // create a table with shapes
        shapesTable = new ViewShapeTable(this, shell, sashForm, storage);

        // composite form to show properties
        propertyForm = new Composite(sashForm, SWT.NONE);
        propertyForm.setVisible(true);
        propertyForm.setLayout(null);

//        rectangleForm = new RectangleForm(propertyForm, SWT.NONE);
        rectangleForm = new ShapeComposite<>(propertyForm, SWT.NONE, Rectangle.class);
        rectangleForm.setBounds(0, 0, 190, 270);
//        ovalForm = new OvalForm(propertyForm, SWT.NONE);
        ovalForm = new ShapeComposite<>(propertyForm, SWT.NONE, Oval.class);
        ovalForm.setBounds(0, 0, 190, 270);

        // hide forms if no selection in the table
        rectangleForm.setVisible(false);
        ovalForm.setVisible(false);
        
        lblSelectOneRow = new Label(propertyForm, SWT.NONE);
        lblSelectOneRow.setBounds(10, 23, 204, 29);
        lblSelectOneRow.setText("Whether a row is selected?");

        sashForm.setWeights(new int[] {7,4});

    }

    private void initMenu() {
        shell.setLayout(new FillLayout(SWT.HORIZONTAL));
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

        createRectangle.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event e) {
                //RectangleTitleAreaDialog dialog = new RectangleTitleAreaDialog(shell);
                ShapeTitleAreaDialog<Rectangle> dialog = new ShapeTitleAreaDialog<>(shell, Rectangle.class);
                dialog.create();

                if (dialog.open() == org.eclipse.jface.window.Window.OK) {
                    // Get a shape from dialog
                    Rectangle rectangle = dialog.getShape();

                    //storage.add(rectangle);

                    //logger.info(dialog.getShape().toString());
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

                    //storage.add(oval);

                    //logger.info(dialog.getShape().toString());
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
