package br.unisinos.jgraphicscene.shapes.units;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Vertex extends Point {
    private Color color;

    public Vertex(float x, float y, float z, Color color) {
        super(x, y, z);
        this.color = color;
    }

    public Vertex(float x, float y, Color color) {
        this(x, y, 0, color);
    }

    public Vertex(float x, float y, float z) {
        super(x, y, z);
    }

    public Vertex(float x, float y) {
        super(x, y);
    }

    public Vertex(float x, float y, float z, float r, float g, float b) {
        this(x, y, z, r, g, b, 1);
    }

    public Vertex(float x, float y, float z, float r, float g, float b, float a) {
        super(x, y, z);
        this.color = new Color(r, g, b, a);
    }

    public Vertex(float x, float y, float r, float g, float b) {
        this(x, y, 0, r, g, b);
    }

    public Color getColor() {
        return color;
    }

    public void grasp(List<Float> list) {
        Collections.addAll(list, x, y, z, color.red, color.green, color.blue, color.alpha);
    }
}
