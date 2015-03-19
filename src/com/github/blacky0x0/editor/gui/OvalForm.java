package com.github.blacky0x0.editor.gui;

import com.github.blacky0x0.editor.model.Oval;
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
public class OvalForm extends Composite {
    protected final Logger logger = Logger.getLogger(getClass().getName());

    // Components of composite element
    protected Text txtName;
    protected Text txtType;
    protected Spinner spinnerX;
    protected Spinner spinnerY;
    private Button  isCircle;
    private Spinner spinnerRadiusX;
    private Spinner spinnerRadiusY;

    private Label labelRadiusX;
    private Label labelRadiusY;

    // Shape to modify
    protected Oval currentShape;

    public OvalForm(Composite parent, int style) {
        super(parent, style);

        init();
    }

    protected void init() {
        setLayout(new GridLayout(2, false));
        
        Label lblName = new Label(this, SWT.NONE);
        lblName.setText("Name:");

        txtName = new Text(this, SWT.BORDER);
        txtName.setText("Oval");
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
        spinnerX.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
        spinnerX.setMinimum(Integer.MIN_VALUE);
        spinnerX.setMaximum(Integer.MAX_VALUE);
        spinnerX.setSelection(50);
        spinnerX.setIncrement(1);
        spinnerX.setPageIncrement(50);

        Label lblY = new Label(this, SWT.CENTER);
        lblY.setText("Y:");

        spinnerY = new Spinner(this, SWT.BORDER);
        spinnerY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        
        spinnerY.setMinimum(Integer.MIN_VALUE);
        spinnerY.setMaximum(Integer.MAX_VALUE);
        spinnerY.setSelection(50);
        spinnerY.setIncrement(1);
        spinnerY.setPageIncrement(50);
        new Label(this, SWT.NONE);
        
        isCircle = new Button (this, SWT.CHECK);
        isCircle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        isCircle.setText ("is it circle?");
        isCircle.setSelection(false);
        
        isCircle.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                // change state of a checkbox
                isCircle.setSelection(isCircle.getSelection());
                initControlsRightState();
            }
        });
        
        labelRadiusX = new Label(this, SWT.NONE);
        labelRadiusX.setText("RadiusX:");
                                
        spinnerRadiusX = new Spinner(this, SWT.BORDER);        
        spinnerRadiusX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        spinnerRadiusX.setMinimum(0);
        spinnerRadiusX.setMaximum(Integer.MAX_VALUE);
        spinnerRadiusX.setSelection(10);
        spinnerRadiusX.setIncrement(1);
        spinnerRadiusX.setPageIncrement(10);
                        
                        
        labelRadiusY = new Label(this, SWT.NONE);
        labelRadiusY.setText("RadiusY:");
                
        spinnerRadiusY = new Spinner(this, SWT.BORDER);
        spinnerRadiusY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

        spinnerRadiusY.setMinimum(0);
        spinnerRadiusY.setMaximum(Integer.MAX_VALUE);
        spinnerRadiusY.setSelection(10);
        spinnerRadiusY.setIncrement(1);
        spinnerRadiusY.setPageIncrement(10);
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
                    currentShape.setRadiusX(Integer.parseInt(spinnerRadiusX.getText()));

                    // is it circle?
                    if (isCircle.getSelection())
                        currentShape.setRadiusY(Integer.parseInt(spinnerRadiusX.getText()));
                    else
                        currentShape.setRadiusY(Integer.parseInt(spinnerRadiusY.getText()));
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

    private void initControlsRightState() {
        if (isCircle.getSelection())
        {
            labelRadiusX.setText("Radius:");
            labelRadiusY.setVisible(false);
            spinnerRadiusY.setVisible(false);
            spinnerRadiusY.setSelection(spinnerRadiusX.getSelection());
        }
        else
        {
            labelRadiusX.setText("RadiusX:");
            labelRadiusY.setVisible(true);
            spinnerRadiusY.setVisible(true);
        }
    }

    public void updateForm(Oval oval) {
        currentShape = oval;

        txtName.setText(oval.getName());
        txtType.setText(oval.getClass().getSimpleName());
        spinnerX.setSelection(oval.getX());
        spinnerY.setSelection(oval.getY());
        spinnerRadiusX.setSelection(oval.getRadiusX());
        spinnerRadiusY.setSelection(oval.getRadiusY());

        if (oval.getRadiusX().equals(oval.getRadiusY()))
            isCircle.setSelection(true);
        else
            isCircle.setSelection(false);
         initControlsRightState();
    }
}
