package com.github.blacky0x0.editor.gui;

import com.github.blacky0x0.editor.model.*;
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
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

/**
 * User: blacky
 * Date: 21.03.15
 */
public class ShapeComposite<T extends Shape> extends Composite {
    protected final Logger logger = Logger.getLogger(getClass().getName());

    protected HashMap<String, Pair> controls = new HashMap<>();
    protected Class<T> type;
    protected T shape;
    protected WritableValue bindedShape = new WritableValue();

    protected PropertyChain bindedPropertyChain = null;
    protected Property booleanProperty = null;

    public ShapeComposite(Composite parent, int style, Class<T> type) {
        super(parent, style);
        this.type = type;

        init();

        findChainedValues();

        bindValues();
    }

    /**
     * Binds controls with a shape value
     */
    private void bindValues() {
        DataBindingContext context = new DataBindingContext();

        String[] propertiesOrder = Property.getShapePropertiesOrder(getType().getSimpleName());

        for (final String propertyName : propertiesOrder) {

            Control targetControl = controls.get(propertyName).getControl();
            IObservableValue target;

            if (targetControl instanceof Button)
            {
                target = WidgetProperties.selection().observe(targetControl);

                final Button button = (Button) targetControl;
                button.addListener(SWT.Selection, new Listener() {
                    @Override
                    public void handleEvent(Event event) {
                        updateChainedControlsVisibility();
                    }
                });
            }
            else
                target = WidgetProperties.text(SWT.Modify).observe(targetControl);

            IObservableValue model = BeanProperties.value(getType(), propertyName).observeDetail(bindedShape);

            if (Property.getPropertyRule(propertyName) == Property.Rule.NO_RULE)
            {
                context.bindValue(target, model);
                continue;
            }

            // add a validator so that field value can only be a number
            IValidator validator = new IValidator() {
                @Override
                public IStatus validate(Object value) {
                    if (value instanceof Integer) {
                        String s = String.valueOf(value);

                        if (s.matches(Property.getPropertyRule(propertyName).getRegExp())) {
                            return ValidationStatus.ok();
                        }
                    }
                    return ValidationStatus
                            .error(Property.getPropertyRule(propertyName).getErrorMsg());
                }
            };

            UpdateValueStrategy strategy = new UpdateValueStrategy();
            strategy.setBeforeSetValidator(validator);

            Binding bindValue = context.bindValue(target, model, strategy, null);

            // add some decorations
            ControlDecorationSupport.create(bindValue, SWT.TOP | SWT.LEFT);
        }

        if (propertiesOrder.length == 0)
        {
            logger.warning("No properties order was found for this type of shape: "
                    .concat(getType().getName())
                    .concat(" in ")
                    .concat(Property.class.getName()));
        }

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

    /**
     * Returns a model of this composite element
     * @return a shape
     */
    public T getShape() {
        return shape;
    }

    /**
     * Updates model of this composite element
     * @param shape
     */
    public void setShape(T shape) {
        this.shape = shape;
        bindedShape.setValue(shape);

        // updates state of controls (look for hidden controls)
        updateChainedControlsVisibility();
    }

    /**
     * This method must be called to set visibility of controls to correct state.
     * It's useful if form contains checkboxes.
     */
    protected void updateChainedControlsVisibility() {

        if (bindedPropertyChain == null || booleanProperty == null)
            return;

        Button button = ((Button)controls.get(booleanProperty.getPropertyName()).getControl());

        if (button.getSelection())
        {
            // set new text to the first label
            // and hide other chained controls
            setChainedControlsVisibility(false);
        }
        else
        {
            // set old text back to the first label
            // and hide other chained controls
            setChainedControlsVisibility(true);
        }
    }

    /**
     *
     * @param visibility true - chained controls are shown;
     * false - only one pair of controls will be shown
     */
    protected void setChainedControlsVisibility(boolean visibility) {
        for (int i = 0; i < bindedPropertyChain.getPropertyNames().length; i++) {
            Pair pair = controls.get(bindedPropertyChain.getPropertyNames()[i]);

            if (i == 0)
            {
                if (visibility)
                {
                    String name = bindedPropertyChain.getPropertyNames()[i];
                    pair.getLabel().setText(Property.getPrintableName(name));
                }
                else {
                    pair.getLabel().setText(bindedPropertyChain.getNewPrintableName());
                }
                continue;
            }

            pair.getLabel().setVisible(visibility);
            pair.getControl().setVisible(visibility);
        }
    }

    /**
     * Looks for boolean value & chained properties for current type of shape.
     * For example: Oval has all of this, but Rectangle not
     */
    protected void findChainedValues() {
        String[] propertiesOrder = Property.getShapePropertiesOrder(getType().getSimpleName());

        for (String propertyName : propertiesOrder) {
            // many properties can be chained with one boolean property
            if (Property.getPropertyBinding(propertyName) != PropertyChain.NONE)
                bindedPropertyChain = Property.getPropertyBinding(propertyName);

            // it may be only one boolean property
            if (Property.getPropertyClass(propertyName).equals(Boolean.class))
                booleanProperty = Property.getPropertyByName(propertyName);
        }
    }

    protected void init() {
        GridLayout layout = new GridLayout(2, false);
        setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        setLayout(layout);

        // Make controls for fields of a class
        makeControls();
    }

    protected void makeControls() {

        String[] propertiesOrder = Property.getShapePropertiesOrder(getType().getSimpleName());

        for (String propertyName : propertiesOrder) {
            Label label = new Label(this, SWT.NONE);
            label.setText(Property.getPrintableName(propertyName));

            GridData data = new GridData();
            data.grabExcessHorizontalSpace = true;
            data.horizontalAlignment = GridData.FILL;

            // Make a special case for checkbox button
            if (Property.getPropertyClass(propertyName).equals(Boolean.class))
            {
                Button isRightShape = new Button (this, SWT.CHECK);
                isRightShape.setText("is a right shape?");
                isRightShape.setSelection(false);
                isRightShape.setLayoutData(data);

                controls.put(propertyName, new Pair(label, isRightShape));
                continue;
            }

            Text text = new Text(this, SWT.BORDER);
            text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
            text.setText(propertyName);
            text.setLayoutData(data);

            // save controls in a map
            controls.put(propertyName, new Pair(label, text));
        }

        if (propertiesOrder.length == 0)
        {
            logger.warning("No properties order was found for this type of shape: "
                    .concat(getType().getName())
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
