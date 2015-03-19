package com.github.blacky0x0.editor.gui;

import com.github.blacky0x0.editor.model.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;

import java.util.logging.Logger;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

/**
 * User: blacky
 * Date: 19.03.15
 */
public class RectangleForm extends Composite {
    protected final Logger logger = Logger.getLogger(getClass().getName());

    // Components of composite element
    protected Text txtName;
    protected Text txtType;
    protected Spinner spinnerX;
    protected Spinner spinnerY;
    protected Spinner spinnerWidth;
    protected Spinner spinnerHeight;

    // Shape to modify
    protected Rectangle currentShape;

    public RectangleForm(Composite parent, int style) {
        super(parent, style);

        init();
    }

    protected void init() {
        setLayout(new GridLayout(2, false));
        Label lblName = new Label(this, SWT.NONE);
        lblName.setText("Name:   ");
        //lblName.setSize(lblName.getSize().x, lblName.getSize().y + 100);

        txtName = new Text(this, SWT.BORDER);
        txtName.setText("Rectangle");
        txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

        Label lblType = new Label(this, SWT.NONE);
        lblType.setText("Type:");

        txtType = new Text(this, SWT.BORDER);
        txtType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        txtType.setEnabled(false);
        txtType.setEditable(false);

        Label lblX = new Label(this, SWT.NONE);
        lblX.setText("X:");

        spinnerX = new Spinner(this, SWT.BORDER);
        spinnerX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        spinnerX.setMinimum(Integer.MIN_VALUE);
        spinnerX.setMaximum(Integer.MAX_VALUE);
        spinnerX.setSelection(50);
        spinnerX.setIncrement(1);
        spinnerX.setPageIncrement(50);

        Label lblY = new Label(this, SWT.NONE);
        lblY.setText("Y:");

        spinnerY = new Spinner(this, SWT.BORDER);
        spinnerY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        spinnerY.setMinimum(Integer.MIN_VALUE);
        spinnerY.setMaximum(Integer.MAX_VALUE);
        spinnerY.setSelection(50);
        spinnerY.setIncrement(1);
        spinnerY.setPageIncrement(50);

        Label lblWidth = new Label(this, SWT.NONE);
        lblWidth.setText("Width:");

        spinnerWidth = new Spinner(this, SWT.BORDER);
        spinnerWidth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        spinnerWidth.setMinimum(0);
        spinnerWidth.setMaximum(Integer.MAX_VALUE);
        spinnerWidth.setSelection(10);
        spinnerWidth.setIncrement(1);
        spinnerWidth.setPageIncrement(10);

        Label lblHeight = new Label(this, SWT.NONE);
        lblHeight.setText("Height:");

        spinnerHeight = new Spinner(this, SWT.BORDER);
        spinnerHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        spinnerHeight.setMinimum(0);
        spinnerHeight.setMaximum(Integer.MAX_VALUE);
        spinnerHeight.setSelection(10);
        spinnerHeight.setIncrement(1);
        spinnerHeight.setPageIncrement(10);

        new Label(this, SWT.NONE);
        new Label(this, SWT.NONE);
        new Label(this, SWT.NONE);
        new Label(this, SWT.NONE);
        new Label(this, SWT.NONE);


        Button btnApplyChanges = new Button(this, SWT.NONE);
        btnApplyChanges.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                try
                {
                    currentShape.setName(txtName.getText());
                    currentShape.setX(Integer.parseInt(spinnerX.getText()));
                    currentShape.setY(Integer.parseInt(spinnerY.getText()));
                    currentShape.setWidth(Integer.parseInt(spinnerWidth.getText()));
                    currentShape.setHeight(Integer.parseInt(spinnerHeight.getText()));
                }
                catch(NumberFormatException nfe)
                {
                    logger.warning("Can't parse integer values"
                            .concat(nfe.getCause().toString()));
                }
            }
        });

        btnApplyChanges.setText("Apply changes");
    }

    public void updateForm(Rectangle rectangle) {
        currentShape = rectangle;

        txtName.setText(rectangle.getName());
        txtType.setText(rectangle.getClass().getSimpleName());
        spinnerX.setSelection(rectangle.getX());
        spinnerY.setSelection(rectangle.getY());
        spinnerWidth.setSelection(rectangle.getWidth());
        spinnerHeight.setSelection(rectangle.getHeight());
    }
}
