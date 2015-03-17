package com.github.blacky0x0.editor.gui;

import java.awt.Point;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.github.blacky0x0.editor.util.GuiUtil;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class OvalEditor {

	private final Display display = new Display ();

    // The application components
    private final Shell shape = new Shell (display);
    private Text txtName;
    private Text txtCoordinateY;
    private Text txtCoordinateX;
    private Text txtRadiusX;
    private Text txtRadiusY;
    
    
    private void init() {
        shape.setText("Create an oval");
        shape.setSize(272, 313);

        // Set a window at center the current screen
        Point locationPoint = GuiUtil.computeCenterPoint(shape.getBounds());
        shape.setLocation((int) locationPoint.getX(), (int) locationPoint.getY());
        shape.setLayout(null);
        
        Label lblName = new Label(shape, SWT.NONE);
        lblName.setBounds(23, 27, 45, 19);
        lblName.setText("Name:");
        
        Label lblX = new Label(shape, SWT.NONE);
        lblX.setBounds(55, 61, 13, 19);
        lblX.setText("X:");
        
        Label lblY = new Label(shape, SWT.NONE);
        lblY.setBounds(56, 94, 12, 19);
        lblY.setText("Y:");
        
        Label lblRadiusX = new Label(shape, SWT.NONE);
        lblRadiusX.setBounds(10, 154, 57, 19);
        lblRadiusX.setText("RadiusX:");
        
        Label lblRadiusY = new Label(shape, SWT.NONE);
        lblRadiusY.setBounds(10, 187, 64, 19);
        lblRadiusY.setText("RadiusY:");
        
        txtName = new Text(shape, SWT.BORDER);
        txtName.setBounds(88, 24, 146, 25);
        
        txtCoordinateX = new Text(shape, SWT.BORDER);
        txtCoordinateX.setBounds(88, 58, 146, 25);
        
        txtCoordinateY = new Text(shape, SWT.BORDER);
        txtCoordinateY.setBounds(88, 91, 146, 25);
        
        txtRadiusX = new Text(shape, SWT.BORDER);
        txtRadiusX.setBounds(88, 151, 146, 25);
        
        txtRadiusY = new Text(shape, SWT.BORDER);
        txtRadiusY.setBounds(88, 184, 146, 25);
        
        Button btnOk = new Button(shape, SWT.NONE);
        btnOk.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		System.out.println("OK button pressed");
        	}
        });
        btnOk.setBounds(92, 235, 68, 28);
        btnOk.setText("OK");
        
        Button btnCancel = new Button(shape, SWT.NONE);
        btnCancel.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		System.out.println("Cancel button pressed");
        	}
        });
        btnCancel.setBounds(166, 235, 68, 28);
        btnCancel.setText("Cancel");
        
        Button btnCheckButton = new Button(shape, SWT.CHECK);
        btnCheckButton.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		System.out.println("Checkbox value was changed");
        	}
        });
        btnCheckButton.setBounds(88, 119, 127, 26);
        btnCheckButton.setText("it's a circle?");

        shape.open ();


        while (!shape.isDisposed ()) {
            if (!display.readAndDispatch ()) display.sleep ();
        }
        display.dispose ();
    }
    
	public static void main(String[] args) {
		new OvalEditor().init();
	}
}
