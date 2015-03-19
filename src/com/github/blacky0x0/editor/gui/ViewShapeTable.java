package com.github.blacky0x0.editor.gui;

import com.github.blacky0x0.editor.model.Rectangle;
import com.github.blacky0x0.editor.model.Shape;
import com.github.blacky0x0.editor.repository.ListStorage;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.part.ViewPart;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * User: blacky
 * Date: 19.03.15
 */
public class ViewShapeTable extends ViewPart {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private Shell shell;
    private TableViewer viewer;
    private WritableList storage;
    private Composite parent;


    public ViewShapeTable(final Shell shell, final Composite parent, final WritableList storage) {
        this.shell = shell;
        this.parent = parent;
        this.storage = storage;

        createPartControl(parent);
    }

    @Override
    public void createPartControl(Composite parent) {
        parent.setLayout(new GridLayout(1, false));
        GridData gd = new GridData();
        gd.grabExcessHorizontalSpace = true;

        // Define the viewer
        viewer = new TableViewer(parent);
        viewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
        column.getColumn().setWidth(100);
        column.getColumn().setText("Name");

        column = new TableViewerColumn(viewer, SWT.NONE);
        column.getColumn().setWidth(100);
        column.getColumn().setText("Type");

        column = new TableViewerColumn(viewer, SWT.NONE);
        column.getColumn().setWidth(80);
        column.getColumn().setText("X");

        column = new TableViewerColumn(viewer, SWT.NONE);
        column.getColumn().setWidth(80);
        column.getColumn().setText("Y");

        viewer.getTable().setHeaderVisible(true);

        // now lets bind the values
        // No extra label provider / content provider / setInput required
        ViewerSupport.bind(viewer, storage, BeanProperties.values(new String[] { "name", "type", "x", "y" }));

        // TODO: make handler for a type
    }

    @Override
    public void setFocus() {
        viewer.getControl().setFocus();
    }

    /**
     * Returns selected shapes
     * @return a list of shapes
     */
    public List<Shape> getSelectedItems() {

        if (!viewer.getSelection().isEmpty())
            return ((IStructuredSelection) viewer.getSelection()).toList();

        return Collections.emptyList();
    }

    /**
     * Returns a string with where items placed line by line
     * @param list of items
     * @return formatted string
     */
    private String getFormattedString(List list) {
        StringBuilder sb = new StringBuilder();
        for (Object item : list) {
            sb.append("\t\t");
            sb.append(item);
            sb.append("\n");
        }
        return sb.toString();
    }

    public void removeSelectedItemsSilently() {
        if (!viewer.getSelection().isEmpty())
        {
            List<Shape> selectedShapes = getSelectedItems();

            storage.removeAll(selectedShapes);
            logger.info(
                    "Selected shapes have been removed:\n"
                            .concat(getFormattedString(selectedShapes)));
        }
    }

    /**
     * Selected items will be removed after user's confirmation from a dialog.
     * If there are not selected items then user will be informed about this.
     */
    public void removeSelectedItemsWithConfirmation() {

        // Are there any selected rows in the table?
        if (viewer.getSelection().isEmpty())
        {
            MessageDialog.openInformation(shell,
                    "Removing selected items from the table",
                    "There are no selected items in the table");
            return;
        }

        if (!viewer.getSelection().isEmpty())
        {
            List<Shape> selectedShapes = getSelectedItems();

            // User must confirm removing items
            MessageDialog dialog = new MessageDialog(shell,
                    "Removing selected items from the table", null,
                    "Next items will be removed:\n"
                            .concat(getFormattedString(selectedShapes)),
                    MessageDialog.QUESTION,
                    new String[] { "OK", "Cancel" }, 1);

            // Cancel => 1; OK => 0; Simple close => -1
            if (dialog.open() == 0)
            {
                removeSelectedItemsSilently();
            }
            else
            {
                // Do nothing
                logger.info("Canceling operation");
            }
        }
    }

    /**
     * Only for testing purposes
     */
    private void addTestButtons() {
        // The following buttons are there to test the binding
        Button delete = new Button(parent, SWT.PUSH);
        delete.setText("Delete");
        delete.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (!viewer.getSelection().isEmpty()) {
                    removeSelectedItemsWithConfirmation();
                    //removeSelectedItemsSilently();
                }
            }
        });

        Button add = new Button(parent, SWT.PUSH);
        add.setText("Add");
        add.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {

                Shape shape =
                        new Rectangle(
                                (int) (Math.random() * 10),
                                (int) (Math.random() * 20),
                                "#" + (int)(Math.random() * 100),
                                (int) (Math.random() * 1000),
                                (int) (Math.random() * 1000)
                        );

                storage.add(shape);
            }
        });

        Button change = new Button(parent, SWT.PUSH);
        change.setText("Switch X / Y");
        change.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (!viewer.getSelection().isEmpty()) {
                    IStructuredSelection selection = (IStructuredSelection) viewer
                            .getSelection();
                    Shape shape = (Shape) selection.getFirstElement();

                    Integer temp = shape.getY();
                    shape.setY(shape.getX());
                    shape.setX(temp);
                }
            }
        });
    }

    public static void main(String[] args) {

        final Display display = new Display();

        Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
            public void run() {
                Shell shell = new Shell(display);

                ViewShapeTable table = new ViewShapeTable(
                        shell,
                        shell,
                        new WritableList(ListStorage.getShapes(), Shape.class));

                table.addTestButtons();

                shell.pack();
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
