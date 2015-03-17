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

public class RectangleEditor {

	private final Display display = new Display ();

    // The application components
    private final Shell shell = new Shell (display);
    private Text txtName;
    private Text txtCoordinateY;
    private Text txtCoordinateX;
    private Text txtWidth;
    private Text txtHeight;
    
    
    private void init() {
        shell.setText("Create a rectangle");
        shell.setSize(272, 281);

        // Set a window at center the current screen
        Point locationPoint = GuiUtil.computeCenterPoint(shell.getBounds());
        shell.setLocation((int) locationPoint.getX(), (int) locationPoint.getY());
        shell.setLayout(null);
        
        Label lblName = new Label(shell, SWT.NONE);
        lblName.setBounds(23, 27, 45, 19);
        lblName.setText("Name:");
        
        Label lblX = new Label(shell, SWT.NONE);
        lblX.setBounds(55, 61, 13, 19);
        lblX.setText("X:");
        
        Label lblY = new Label(shell, SWT.NONE);
        lblY.setBounds(56, 94, 12, 19);
        lblY.setText("Y:");
        
        Label lblWidth = new Label(shell, SWT.NONE);
        lblWidth.setBounds(23, 126, 44, 19);
        lblWidth.setText("Width:");
        
        Label lblHeight = new Label(shell, SWT.NONE);
        lblHeight.setBounds(23, 159, 51, 19);
        lblHeight.setText("Height:");
        
        txtName = new Text(shell, SWT.BORDER);
        txtName.setBounds(88, 24, 146, 25);
        
        txtCoordinateX = new Text(shell, SWT.BORDER);
        txtCoordinateX.setBounds(88, 58, 146, 25);
        
        txtCoordinateY = new Text(shell, SWT.BORDER);
        txtCoordinateY.setBounds(88, 91, 146, 25);
        
        txtWidth = new Text(shell, SWT.BORDER);
        txtWidth.setBounds(88, 123, 146, 25);
        
        txtHeight = new Text(shell, SWT.BORDER);
        txtHeight.setBounds(88, 156, 146, 25);
        
        Button btnOk = new Button(shell, SWT.NONE);
        btnOk.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		System.out.println("OK button pressed");
        	}
        });
        btnOk.setBounds(88, 214, 68, 28);
        btnOk.setText("OK");
        
        Button btnCancel = new Button(shell, SWT.NONE);
        btnCancel.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		System.out.println("Cancel button pressed");
        	}
        });
        btnCancel.setBounds(162, 214, 68, 28);
        btnCancel.setText("Cancel");

        shell.open ();


        while (!shell.isDisposed ()) {
            if (!display.readAndDispatch ()) display.sleep ();
        }
        display.dispose ();
    }
    
	public static void main(String[] args) {
		new RectangleEditor().init();
	}
}
