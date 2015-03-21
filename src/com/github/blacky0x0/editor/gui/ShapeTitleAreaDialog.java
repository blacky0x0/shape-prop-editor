package com.github.blacky0x0.editor.gui;

import com.github.blacky0x0.editor.model.Oval;
import com.github.blacky0x0.editor.model.Rectangle;
import com.github.blacky0x0.editor.model.Shape;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;

import java.util.logging.Logger;


/**
 * User: blacky
 * Date: 21.03.15
 */
public class ShapeTitleAreaDialog<T extends Shape> extends TitleAreaDialog {
    protected final Logger logger = Logger.getLogger(getClass().getName());
    protected Class<T> type;
    protected ShapeComposite<T> shapeComposite;

    public ShapeTitleAreaDialog(Shell parentShell, Class<T> type) {
        super(parentShell);
        this.type = type;
    }

    public Class<T> getType() {
        return type;
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

        shapeComposite = new ShapeComposite<>(area, SWT.NONE, getType());

        return area;
    }

    @Override
    protected boolean isResizable() {
        return true;
    }

    @Override
    protected void okPressed() {
        super.okPressed();
    }

    @Override
    protected Point getInitialSize() {
        return new Point(350, 350);
    }

    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Creating: ".concat(getType().getSimpleName()));
    }

    public T getShape() {
        return shapeComposite.getShape();
    }


    public static void main(String[] args) {
        Display display = new Display ();
        Shell shell = new Shell (display);

        // Sample using
        ShapeTitleAreaDialog<Rectangle> dialog = new ShapeTitleAreaDialog<>(shell, Rectangle.class);
        dialog.create();

        if (dialog.open() == Window.OK) {
            // Get a shape from dialog
            System.out.println(dialog.getShape());
        }
    }
}
