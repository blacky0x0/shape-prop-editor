package com.github.blacky0x0.editor.repository;

import com.github.blacky0x0.editor.model.Oval;
import com.github.blacky0x0.editor.model.Rectangle;
import com.github.blacky0x0.editor.model.Shape;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * User: blacky
 * Date: 17.03.15
 */
public class ListStorage {
    List<Shape> list = new LinkedList<>();

    public static List<Shape> getShapes() {
        List<Shape> shapes = new ArrayList<Shape>();
        Shape shape = new Oval(-30, -40, "circle #01", 50, 60);
        shapes.add(shape);
        shape = new Oval(-20, -10, "circle #02", 70, 80);
        shapes.add(shape);
        shape = new Rectangle(10, 20, "#01", 100, 100);
        shapes.add(shape);
        shape = new Rectangle(30, 40, "#02", 200, 200);
        shapes.add(shape);
        shape = new Rectangle(50, 60, "#03", 300, 300);
        shapes.add(shape);
        return shapes;
    }

    public ListStorage() {}

    public ListStorage(List<Shape> list) {
        this.list = list;
    }

    public List<Shape> getList() {
        return list;
    }

    public void add(Shape shape) {
        list.add(shape);
    }

    public void remove(Shape shape) {
        list.remove(shape);
    }

    public int size() {
        return list.size();
    }
}
