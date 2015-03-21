package com.github.blacky0x0.editor.gui;

import com.github.blacky0x0.editor.model.Oval;
import com.github.blacky0x0.editor.model.Rectangle;
import com.github.blacky0x0.editor.model.Shape;
import com.github.blacky0x0.editor.util.GuiUtil;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;

import java.awt.Point;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

/**
 * User: blacky
 * Date: 21.03.15
 */
public class ShapeComposite<T extends Shape> extends Composite {
    protected static HashMap<String, Pair> controls = new HashMap<>();
    protected Class<T> type;
//    protected Button applyBtn;
    protected T shape;  // TODO: make binding a shape with controls

    public ShapeComposite(Composite parent, int style, Class<T> type) {
        super(parent, style);
        this.type = type;

        init();
    }

    public Class<T> getType() {
        return type;
    }

    public T getShape() {
        return shape;
    }

    public void setShape(T shape) {
        this.shape = shape;
    }

    public void init() {
        GridLayout layout = new GridLayout(2, false);
        setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        setLayout(layout);

        // Make controls for fields of a class
        makeControls(getType().getSuperclass()); // ? extends Shape
        makeControls(getType());    // Shape

        new Label(this, SWT.NONE);  // just for alignment
//        applyBtn = new Button(this, SWT.NONE);
//        applyBtn.setText("Apply changes");
    }

    protected void makeControls(Class clazz) {
        // Make controls for class fields
        for (Field field : clazz.getDeclaredFields()) {
            // Skip synthetic fields
            if (field.isSynthetic())
                continue;

            // Don't make controls for fields with 'transient' modifier
            if ((field.getModifiers() & Modifier.TRANSIENT) == 0)
            {
                Label label = new Label(this, SWT.NONE);
                label.setText(field.getName() + ": ");

                GridData data = new GridData();
                data.grabExcessHorizontalSpace = true;
                data.horizontalAlignment = GridData.FILL;

                Text text = new Text(this, SWT.BORDER);
                text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
                text.setText(field.getName());
                text.setLayoutData(data);

                // save controls in a map
                controls.put(field.getName(), new Pair(label, text));
            }
        }
    }

    public static class Pair {
        private Label label;
        private Control control;

        public Pair(Label label, Control control) {
            this.label = label;
            this.control = control;
        }

        public Label getLabel() {
            return label;
        }

        public void setLabel(Label label) {
            this.label = label;
        }

        public Control getControl() {
            return control;
        }

        public void setControl(Control control) {
            this.control = control;
        }
    }


    public static void main (String [] args) {
        final Display display = new Display ();

        Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
            public void run() {
                Shell shell = new Shell(display);

                shell.setLayout(new GridLayout(2, false));

                ShapeComposite<Oval> shapeComposite = new ShapeComposite<>(shell, SWT.NONE, Oval.class);
                //shapeComposite.init();

                shell.pack();

                // Set a window at center the current screen
                Point locationPoint = GuiUtil.computeCenterPoint(shell.getBounds());
                shell.setLocation((int) locationPoint.getX(), (int) locationPoint.getY());

                shell.open();
                while (!shell.isDisposed()) {
                    if (!display.readAndDispatch())
                        display.sleep();
                }
            }
        });

        display.dispose();
    }
}
