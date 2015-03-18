package com.github.blacky0x0.editor.gui;

import com.github.blacky0x0.editor.model.Oval;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.logging.Logger;

/**
 * Dialog for creation new oval shapes
 * User: blacky
 * Date: 18.03.15
 */
public class OvalTitleAreaDialog extends TitleAreaDialog {
    protected final Logger logger = Logger.getLogger(getClass().getName());

    // TODO: refactor with JFace binding
    private Text    txtName;
    private Spinner spinnerX;
    private Spinner spinnerY;
    private Button  isCircle;
    private Spinner spinnerRadiusX;
    private Spinner spinnerRadiusY;

    private Label labelRadiusX;
    private Label labelRadiusY;

    private Oval oval;

    public OvalTitleAreaDialog(Shell parentShell) {
        super(parentShell);
    }

    public static void main(String[] args) {
        Display display = new Display ();
        Shell shell = new Shell (display);

        // Sample using
        OvalTitleAreaDialog dialog = new OvalTitleAreaDialog(shell);
        dialog.create();
        if (dialog.open() == Window.OK) {
            // Get a oval from dialog
            System.out.println(dialog.getOval());
        }
    }
    @Override
    public void create() {
        super.create();
        //setTitle("Title");
        setMessage("Please fill in all required fields", IMessageProvider.INFORMATION);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite area = (Composite) super.createDialogArea(parent);

        Composite container = new Composite(area, SWT.NONE);
        container.setLayoutData(new GridData(GridData.FILL_BOTH));

        GridLayout layout = new GridLayout(2, false);
        container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        container.setLayout(layout);

        createName(container);
        createCoordinateX(container);
        createCoordinateY(container);
        createIsCircle(container);
        createRadiusX(container);
        createRadiusY(container);

        return area;
    }

    private void createName(Composite container) {
        Label label = new Label(container, SWT.NONE);
        label.setText("Name:");

        GridData dataFirstName = new GridData();
        dataFirstName.grabExcessHorizontalSpace = true;
        dataFirstName.horizontalAlignment = GridData.FILL;

        txtName = new Text(container, SWT.BORDER);
        txtName.setLayoutData(dataFirstName);
    }

    private void createCoordinateX(Composite container) {
        Label label = new Label(container, SWT.NONE);
        label.setText("X:");

        GridData data = new GridData();
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        spinnerX = new Spinner(container, SWT.BORDER);
        spinnerX.setLayoutData(data);

        spinnerX.setMinimum(Integer.MIN_VALUE);
        spinnerX.setMaximum(Integer.MAX_VALUE);
        spinnerX.setSelection(50);
        spinnerX.setIncrement(1);
        spinnerX.setPageIncrement(50);
    }

    private void createCoordinateY(Composite container) {
        Label label = new Label(container, SWT.NONE);
        label.setText("Y:");

        GridData data = new GridData();
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        spinnerY = new Spinner(container, SWT.BORDER);
        spinnerY.setLayoutData(data);

        spinnerY.setMinimum(Integer.MIN_VALUE);
        spinnerY.setMaximum(Integer.MAX_VALUE);
        spinnerY.setSelection(50);
        spinnerY.setIncrement(1);
        spinnerY.setPageIncrement(50);
    }

    private void createIsCircle(Composite container) {
        Label label = new Label(container, SWT.NONE);
        label.setText("");

        GridData data = new GridData();
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        isCircle = new Button (container, SWT.CHECK);
        isCircle.setText ("is it circle?");
        isCircle.setSelection (false);

        isCircle.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent (Event event) {

                // TODO: Implement hide/show the spinnerRadiusY

                if (isCircle.getSelection())
                {
                    labelRadiusX.setText("Radius:");
                    labelRadiusY.setText("");
                }
                else
                {
                    labelRadiusX.setText("RadiusX:");
                    labelRadiusY.setText("RadiusY");
                }

            }
        });

    }

    private void createRadiusX(Composite container) {
        labelRadiusX = new Label(container, SWT.NONE);
        labelRadiusX.setText("RadiusX:");

        GridData data = new GridData();
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        spinnerRadiusX = new Spinner(container, SWT.BORDER);
        spinnerRadiusX.setLayoutData(data);

        spinnerRadiusX.setMinimum(0);
        spinnerRadiusX.setMaximum(Integer.MAX_VALUE);
        spinnerRadiusX.setSelection(10);
        spinnerRadiusX.setIncrement(1);
        spinnerRadiusX.setPageIncrement(10);
    }

    private void createRadiusY(Composite container) {
        labelRadiusY = new Label(container, SWT.NONE);
        labelRadiusY.setText("RadiusY:");

        //compositeY = new Composite(container, SWT.BORDER);

        GridData dataY = new GridData();
        dataY.grabExcessHorizontalSpace = true;
        dataY.horizontalAlignment = GridData.FILL;
        spinnerRadiusY = new Spinner(container, SWT.BORDER);
        spinnerRadiusY.setLayoutData(dataY);

        spinnerRadiusY.setMinimum(0);
        spinnerRadiusY.setMaximum(Integer.MAX_VALUE);
        spinnerRadiusY.setSelection(15);
        spinnerRadiusY.setIncrement(1);
        spinnerRadiusY.setPageIncrement(10);
    }

    @Override
    protected boolean isResizable() {
        return true;
    }

    // save content of the Text fields because they get disposed
    // as soon as the Dialog closes
    private void saveInput() {
        try
        {
            oval = new Oval(
                    Integer.parseInt(spinnerX.getText()),
                    Integer.parseInt(spinnerY.getText()),
                    txtName.getText(),
                    Integer.parseInt(spinnerRadiusX.getText()),
                    Integer.parseInt(spinnerRadiusY.getText())
            );
        }
        catch(NumberFormatException nfe)
        {
            logger.severe("Can't create an oval. Something is going wrong!\n"
                    .concat(nfe.getCause().getMessage()));
        }
    }

    @Override
    protected void okPressed() {
        saveInput();
        super.okPressed();
    }

    @Override
    protected Point getInitialSize() {
        return new Point(350, 350);
    }

    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Create an oval");
    }

    public Oval getOval() {
        return oval;
    }


}
