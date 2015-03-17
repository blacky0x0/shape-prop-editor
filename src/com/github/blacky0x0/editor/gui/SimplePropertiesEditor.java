package com.github.blacky0x0.editor.gui;

import com.github.blacky0x0.editor.model.Oval;
import com.github.blacky0x0.editor.model.Property;
import com.github.blacky0x0.editor.model.Rectangle;
import com.github.blacky0x0.editor.model.Shape;
import com.github.blacky0x0.editor.repository.ListStorage;
import com.github.blacky0x0.editor.util.GuiUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import java.awt.*;
import java.util.Map;

/**
 * User: blacky
 * Date: 15.03.15
 */
public class SimplePropertiesEditor {

    // This in-memory storage contains all shapes
    private ListStorage storage = new ListStorage();

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

    // A sashform contains two tables
    private SashForm sashForm;
    // Two tables
    private Table shapesTable;
    private Table propertiesTable;

    public static void main (String [] args) {
        SimplePropertiesEditor editor = new SimplePropertiesEditor();
        editor.init();
    }

    public void init() {

        initMenu();

        initData();

        initTables();

        finishInit();

    }

    private void initTables() {
        
        sashForm = new SashForm(shell, SWT.NONE);

        shapesTable = new Table (sashForm, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
        shapesTable.setHeaderVisible(true);

        propertiesTable = new Table(sashForm, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
        propertiesTable.setHeaderVisible(true);

        sashForm.setWeights(new int[]{3, 2});

        // init a table with shapes
        String[] titles = {"Name", "Type", "X", "Y"};

        TableColumn columnName = new TableColumn (shapesTable, SWT.NONE);
        columnName.setText ("Name");
        columnName.setWidth(80);

        TableColumn columnType = new TableColumn (shapesTable, SWT.NONE);
        columnType.setText ("Type");
        columnType.setWidth(80);

        TableColumn columnX = new TableColumn (shapesTable, SWT.NONE);
        columnX.setText ("X");
        columnX.setWidth(60);

        TableColumn columnY = new TableColumn (shapesTable, SWT.NONE);
        columnY.setText ("Y");
        columnY.setWidth(60);

//         for (int i=0; i<titles.length; i++) {
//            TableColumn column = new TableColumn (shapesTable, SWT.NONE);
//            column.setText (titles [i]);
//         }

        for (Shape shape : storage.getList()) {
            TableItem item = new TableItem (shapesTable, SWT.NONE);

            item.setText (0, shape.getName());
            item.setText (1, shape.getClass().getSimpleName());
            item.setText (2, shape.getX().toString());
            item.setText (3, shape.getY().toString());
        }

        for (int i = 0; i < shapesTable.getColumnCount(); i++) {
            shapesTable.getColumn(i).pack();
        }

        // set two columns for a table with properties
        TableColumn columnProperty = new TableColumn (propertiesTable, SWT.NONE);
        columnProperty.setText ("Property");
        columnProperty.setWidth(80);

        TableColumn columnValue = new TableColumn (propertiesTable, SWT.NONE);
        columnValue.setText ("Value");
        columnValue.setWidth(80);

        // fill a table with properties
        Shape selectedShape = storage.getList().get(0);

        TableItem itemName = new TableItem (propertiesTable, SWT.NONE);
        itemName.setText (0, "Name");
        itemName.setText (1, selectedShape.getName());

        TableItem itemType = new TableItem (propertiesTable, SWT.NONE);
        itemType.setText (0, "Type");
        itemType.setText (1, selectedShape.getClass().getSimpleName());

        TableItem itemX = new TableItem (propertiesTable, SWT.NONE);
        itemX.setText (0, "X");
        itemX.setText (1, selectedShape.getX().toString());

        TableItem itemY = new TableItem (propertiesTable, SWT.NONE);
        itemY.setText (0, "Y");
        itemY.setText (1, selectedShape.getY().toString());

        for (Map.Entry<Property, Object> property : storage.getList().get(0).getProperties().entrySet()) {
            TableItem item = new TableItem (propertiesTable, SWT.NONE);

            item.setText (0, property.getKey().toString());
            item.setText (1, property.getValue().toString());
        }

        for (int i = 0; i < propertiesTable.getColumnCount(); i++) {
            propertiesTable.getColumn(i).pack();
        }
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

        storage.add(new Rectangle(1, 2, "rectangle #01", 10, 20));
        storage.add(new Rectangle(3, 4, "rectangle #02", 30, 40));
        storage.add(new Oval(5, 6, "oval #03", 50, 60));
        storage.add(new Oval(7, 8, "cycle #04", 80));

    }

    public void initMenu() {
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
                System.out.println("Make a rectangle shape");
            }
        });

        createOval.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event e) {
                System.out.println("Make an oval shape");
            }
        });

        removeShape.setAccelerator(REMOVE_SHAPE_ACCELERATOR);
        removeShape.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event e) {
                System.out.println("Selected shapes were removed");
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
