package com.github.blacky0x0.editor.gui;

import com.github.blacky0x0.editor.model.Rectangle;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.logging.Logger;

/**
 * Dialog for creation new rectangle shapes
 * User: blacky
 * Date: 18.03.15
 */
public class RectangleTitleAreaDialog extends TitleAreaDialog {
    protected final Logger logger = Logger.getLogger(getClass().getName());

    // TODO: refactor -> extract code to general class
    private Text txtName;
    private Spinner spinnerX;
    private Spinner spinnerY;
    private Spinner spinnerWidth;
    private Spinner spinnerHeight;

    private Rectangle rectangle;

    public RectangleTitleAreaDialog(Shell parentShell) {
        super(parentShell);
    }

    public static void main(String[] args) {
        Display display = new Display ();
        Shell shell = new Shell (display);

        // Sample using
        RectangleTitleAreaDialog dialog = new RectangleTitleAreaDialog(shell);
        dialog.create();
        if (dialog.open() == Window.OK) {
            // Get a rectangle from dialog
            System.out.println(dialog.getRectangle());
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
        createWidth(container);
        createHeight(container);

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

    private void createWidth(Composite container) {
        Label label = new Label(container, SWT.NONE);
        label.setText("Width:");

        GridData data = new GridData();
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        spinnerWidth = new Spinner(container, SWT.BORDER);
        spinnerWidth.setLayoutData(data);

        spinnerWidth.setMinimum(0);
        spinnerWidth.setMaximum(Integer.MAX_VALUE);
        spinnerWidth.setSelection(10);
        spinnerWidth.setIncrement(1);
        spinnerWidth.setPageIncrement(10);
    }

    private void createHeight(Composite container) {
        Label label = new Label(container, SWT.NONE);
        label.setText("Height:");

        GridData data = new GridData();
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        spinnerHeight = new Spinner(container, SWT.BORDER);
        spinnerHeight.setLayoutData(data);

        spinnerHeight.setMinimum(0);
        spinnerHeight.setMaximum(Integer.MAX_VALUE);
        spinnerHeight.setSelection(10);
        spinnerHeight.setIncrement(1);
        spinnerHeight.setPageIncrement(10);
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
            rectangle = new Rectangle(
                    Integer.parseInt(spinnerX.getText()),
                    Integer.parseInt(spinnerY.getText()),
                    txtName.getText(),
                    Integer.parseInt(spinnerWidth.getText()),
                    Integer.parseInt(spinnerHeight.getText())
            );
        }
        catch(NumberFormatException nfe)
        {
            logger.severe("Can't create a rectangle. Something is going wrong!\n"
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
        newShell.setText("Create a rectangle");
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

}
