package br.unisinos.jgraphicscene.shapes.polygons;

import br.unisinos.jgraphicscene.units.Vertex;
import br.unisinos.jgraphicscene.utilities.constants.Mode;

import java.util.ArrayList;
import java.util.Collections;

public class Triangle extends Polygon {
    public Triangle(Vertex a, Vertex b, Vertex c) {
        this.vertices = new ArrayList<>(3);
        Collections.addAll(this.vertices, a, b, c);
    }

    @Override
    public int getMode() {
        return Mode.GL_TRIANGLES;
    }
}
