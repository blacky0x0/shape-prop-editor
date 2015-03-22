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
    private static final Shell shell = new Shell (display);

    // The application components
    private ShapeMenu menu;
    // A sashform contains a shapesTable & one of the properties form
    private SashForm sashForm;
    private ViewShapeTable shapesTable;
    // Vary properties forms
    private Composite propertyForm;
    private ShapeComposite<Rectangle> rectangleForm;
    private ShapeComposite<Oval> ovalForm;
    private Label lblSelectOneRow;

    public static void main (String [] args) {

        Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
            public void run() {
                SimplePropertiesEditor editor = new SimplePropertiesEditor();
                editor.init();

                // Set a window at center the current screen
                // TODO: refactor with a Display toolkit
                Point locationPoint = GuiUtil.computeCenterPoint(shell.getBounds());
                shell.setLocation((int) locationPoint.getX(), (int) locationPoint.getY());

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

        menu = new ShapeMenu(shell, storage, shapesTable);

        initControls();

    }

    public void hidePropertiesForm() {
        ovalForm.setVisible(false);
        rectangleForm.setVisible(false);
        lblSelectOneRow.setVisible(true);
    }

    public void updateForm(Rectangle rectangle) {
        ovalForm.setVisible(false);
        rectangleForm.setShape(rectangle);
        rectangleForm.setVisible(true);
        lblSelectOneRow.setVisible(false);
    }

    public void updateForm(Oval oval) {
        rectangleForm.setVisible(false);
        ovalForm.setShape(oval);
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

        rectangleForm = new ShapeComposite<>(propertyForm, SWT.NONE, Rectangle.class);
        rectangleForm.setBounds(0, 0, 190, 270);

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

}
