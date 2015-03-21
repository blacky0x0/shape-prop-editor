package com.github.blacky0x0.editor.gui;

import com.github.blacky0x0.editor.model.Oval;
import com.github.blacky0x0.editor.model.Property;
import com.github.blacky0x0.editor.model.Rectangle;
import com.github.blacky0x0.editor.model.Shape;
import com.github.blacky0x0.editor.util.GuiUtil;
import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.swt.WidgetProperties;
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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

/**
 * User: blacky
 * Date: 21.03.15
 */
public class ShapeComposite<T extends Shape> extends Composite {
    protected final Logger logger = Logger.getLogger(getClass().getName());

    protected static HashMap<String, Pair> controls = new HashMap<>();
    protected Class<T> type;
//    protected Button applyBtn;
    protected T shape;
    protected WritableValue bindedShape = new WritableValue();
    private AtomicBoolean isValidState = new AtomicBoolean(true);

    public ShapeComposite(Composite parent, int style, Class<T> type) {
        super(parent, style);
        this.type = type;

        init();

        bindValues();
    }

    /**
     * Return true if all values of fields in valid state.
     * For example: x must be an integer number,
     * width must be greater or equals zero
     * @return
     */
    public boolean isValidState() {
        return isValidState.get();
    }

    private void bindValues() {
        DataBindingContext context = new DataBindingContext();

        IObservableValue target = WidgetProperties.text(SWT.Modify).observe(controls.get("name").getControl());
        IObservableValue model = BeanProperties.value("name").observeDetail(bindedShape);
        context.bindValue(target, model);

        // Bind the x including a validator
        target = WidgetProperties.text(SWT.Modify).observe(controls.get("x").getControl());
        model = BeanProperties.value(Rectangle.class, "x").observeDetail(bindedShape);

        // add an validator so that field value can only be a number
        IValidator validator = new IValidator() {
            @Override
            public IStatus validate(Object value) {
                if (value instanceof Integer) {
                    String s = String.valueOf(value);
                    if (s.matches("-?\\d*")) {
                        //isValidState.set(true);
                        return ValidationStatus.ok();
                    }
                }
                //isValidState.set(false);
                return ValidationStatus.error("Not a number");
            }
        };

        UpdateValueStrategy strategy = new UpdateValueStrategy();
        //strategy.setBeforeSetValidator(validator);
        strategy.setAfterConvertValidator(validator);

        Binding bindValue = context.bindValue(target, model, strategy, null);

        // add some decorations
        ControlDecorationSupport.create(bindValue, SWT.TOP | SWT.LEFT);

        target = WidgetProperties.text(SWT.Modify).observe(controls.get("y").getControl());
        model = BeanProperties.value(Rectangle.class, "y").observeDetail(bindedShape);
        strategy.setAfterConvertValidator(validator);
        bindValue = context.bindValue(target, model, strategy, null);
        ControlDecorationSupport.create(bindValue, SWT.TOP | SWT.LEFT);




        shape = newInstance();
        bindedShape.setValue(shape);
    }

    public T newInstance() {
        try { return type.newInstance(); }
        catch (Exception e) { e.printStackTrace(); }

        return null;
    }

    public Class<T> getType() {
        return type;
    }

    public T getShape() {
        return shape;
    }

    public void setShape(T shape) {
        this.shape = shape;
        bindedShape.setValue(shape);
    }

    public void init() {
        GridLayout layout = new GridLayout(2, false);
        setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        setLayout(layout);

        // Make controls for fields of a class
        //makeControls(getType().getSuperclass()); // ? extends Shape
        makeControls(getType());    // Shape

    }

    protected void makeControls(Class clazz) {

        String[] propertiesOrder = Property.getShapePropertiesOrder(clazz.getSimpleName());

        if (propertiesOrder.length != 0)
        {
            for (String propertyName : propertiesOrder) {
                Label label = new Label(this, SWT.NONE);
                label.setText(Property.getPrintableName(propertyName));

                GridData data = new GridData();
                data.grabExcessHorizontalSpace = true;
                data.horizontalAlignment = GridData.FILL;

                Text text = new Text(this, SWT.BORDER);
                text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
                text.setText(propertyName);
                text.setLayoutData(data);

                // save controls in a map
                controls.put(propertyName, new Pair(label, text));
            }
        }
        else
        {
            logger.warning("No properties order was found for this type of shape: "
                    .concat(clazz.getName())
                    .concat(" in ")
                    .concat(Property.class.getName()));
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
