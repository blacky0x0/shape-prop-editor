package com.github.blacky0x0.editor.gui;

import com.github.blacky0x0.editor.model.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.*;

import java.util.logging.Logger;

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

        setLayout(new FormLayout());

        txtName = new Text(this, SWT.BORDER);
        FormData fd_txtName = new FormData();
        fd_txtName.top = new FormAttachment(0, 19);
        fd_txtName.right = new FormAttachment(100, -10);
        txtName.setLayoutData(fd_txtName);

        txtType = new Text(this, SWT.BORDER);
        txtType.setEnabled(false);
        txtType.setEditable(false);
        FormData fd_txtType = new FormData();
        fd_txtType.right = new FormAttachment(100, -10);
        txtType.setLayoutData(fd_txtType);

        Label lblName = new Label(this, SWT.NONE);
        fd_txtName.left = new FormAttachment(lblName, 6);
        FormData fd_lblName = new FormData();
        fd_lblName.top = new FormAttachment(0, 22);
        fd_lblName.right = new FormAttachment(100, -119);
        lblName.setLayoutData(fd_lblName);
        lblName.setText("Name:");

        Label lblType = new Label(this, SWT.NONE);
        fd_txtType.left = new FormAttachment(lblType, 6);
        FormData fd_lblType = new FormData();
        fd_lblType.right = new FormAttachment(lblName, 0, SWT.RIGHT);
        lblType.setLayoutData(fd_lblType);
        lblType.setText("Type:");

        Label lblX = new Label(this, SWT.NONE);
        fd_lblType.bottom = new FormAttachment(lblX, -13);
        FormData fd_lblX = new FormData();
        fd_lblX.right = new FormAttachment(lblName, 0, SWT.RIGHT);
        lblX.setLayoutData(fd_lblX);
        lblX.setText("X:");

        Label lblY = new Label(this, SWT.NONE);
        FormData fd_lblY = new FormData();
        fd_lblY.right = new FormAttachment(lblName, 0, SWT.RIGHT);
        lblY.setLayoutData(fd_lblY);
        lblY.setText("Y:");

        Label lblWidth = new Label(this, SWT.NONE);
        fd_lblY.bottom = new FormAttachment(lblWidth, -9);
        FormData fd_lblWidth = new FormData();
        fd_lblWidth.right = new FormAttachment(lblName, 0, SWT.RIGHT);
        fd_lblWidth.top = new FormAttachment(0, 147);
        lblWidth.setLayoutData(fd_lblWidth);
        lblWidth.setText("Width:");

        Label lblHeight = new Label(this, SWT.NONE);
        FormData fd_lblHeight = new FormData();
        fd_lblHeight.right = new FormAttachment(lblName, 0, SWT.RIGHT);
        fd_lblHeight.top = new FormAttachment(lblWidth, 18);
        lblHeight.setLayoutData(fd_lblHeight);
        lblHeight.setText("Height:");

        spinnerX = new Spinner(this, SWT.BORDER);
        fd_txtType.bottom = new FormAttachment(spinnerX, -6);
        fd_lblX.top = new FormAttachment(0, 87);
        FormData fd_spinnerX = new FormData();
        fd_spinnerX.top = new FormAttachment(0, 82);
        fd_spinnerX.right = new FormAttachment(txtName, 0, SWT.RIGHT);
        fd_spinnerX.left = new FormAttachment(lblX, 6);
        spinnerX.setLayoutData(fd_spinnerX);

        spinnerX.setMinimum(Integer.MIN_VALUE);
        spinnerX.setMaximum(Integer.MAX_VALUE);
        spinnerX.setSelection(50);
        spinnerX.setIncrement(1);
        spinnerX.setPageIncrement(50);

        spinnerY = new Spinner(this, SWT.BORDER);
        fd_spinnerX.bottom = new FormAttachment(spinnerY, -9);
        FormData fd_spinnerY = new FormData();
        fd_spinnerY.left = new FormAttachment(txtName, 0, SWT.LEFT);
        fd_spinnerY.right = new FormAttachment(100, -10);
        spinnerY.setLayoutData(fd_spinnerY);

        spinnerY.setMinimum(Integer.MIN_VALUE);
        spinnerY.setMaximum(Integer.MAX_VALUE);
        spinnerY.setSelection(50);
        spinnerY.setIncrement(1);
        spinnerY.setPageIncrement(50);

        spinnerWidth = new Spinner(this, SWT.BORDER);
        fd_spinnerY.bottom = new FormAttachment(spinnerWidth, -6);
        FormData fd_spinnerWidth = new FormData();
        fd_spinnerWidth.top = new FormAttachment(0, 147);
        fd_spinnerWidth.left = new FormAttachment(txtName, 0, SWT.LEFT);
        fd_spinnerWidth.right = new FormAttachment(100, -10);
        spinnerWidth.setLayoutData(fd_spinnerWidth);

        spinnerWidth.setMinimum(0);
        spinnerWidth.setMaximum(Integer.MAX_VALUE);
        spinnerWidth.setSelection(10);
        spinnerWidth.setIncrement(1);
        spinnerWidth.setPageIncrement(10);

        spinnerHeight = new Spinner(this, SWT.BORDER);
        FormData fd_spinnerHeight = new FormData();
        fd_spinnerHeight.top = new FormAttachment(spinnerWidth, 7);
        fd_spinnerHeight.right = new FormAttachment(txtName, 0, SWT.RIGHT);
        fd_spinnerHeight.left = new FormAttachment(lblHeight, 6);
        spinnerHeight.setLayoutData(fd_spinnerHeight);

        spinnerHeight.setMinimum(0);
        spinnerHeight.setMaximum(Integer.MAX_VALUE);
        spinnerHeight.setSelection(10);
        spinnerHeight.setIncrement(1);
        spinnerHeight.setPageIncrement(10);


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
        FormData fd_btnApplyChanges = new FormData();
        fd_btnApplyChanges.top = new FormAttachment(lblHeight, 26);
        fd_btnApplyChanges.right = new FormAttachment(txtName, 0, SWT.RIGHT);
        btnApplyChanges.setLayoutData(fd_btnApplyChanges);
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
