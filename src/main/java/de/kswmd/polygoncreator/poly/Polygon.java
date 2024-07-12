/*
 * Copyright 2024 Kai.
 *
 */
package de.kswmd.polygoncreator.poly;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kai
 */
public class Polygon {

    private final List<Point> vertices = new ArrayList<>();
    
    public Polygon() {
    }

    public void addVertex(Point p) {
        this.vertices.add(p);
    }

    public void removeVertex(Point p) {
        this.vertices.remove(p);
    }

    public boolean hasVertices() {
        return !vertices.isEmpty();
    }

    public boolean isPolygon() {
        return vertices.size() >= 3;
    }

    public void clear() {
        vertices.clear();
    }

    public void removeLastVertex() {
        if (hasVertices()) {
            vertices.remove(vertices.size() - 1);
        }
    }

    public int size() {
        return vertices.size();
    }

    public Point get(int i) {
        return vertices.get(i);
    }
}
