package br.unisinos.jgraphicscene.shapes.polygons;

import br.unisinos.jgraphicscene.graphics.Composition;
import br.unisinos.jgraphicscene.shapes.units.Vertex;
import br.unisinos.jgraphicscene.utils.constants.Drawing;
import jdk.nashorn.internal.ir.Block;

import java.util.ArrayList;
import java.util.List;

public class Triangle extends Polygon {
    public Triangle(Vertex a, Vertex b, Vertex c) {
        this.vertices = new Vertex[] {a, b, c};
    }

    @Override
    public int getDrawingMode() {
        return Drawing.GL_TRIANGLES;
    }
}
