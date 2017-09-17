package br.unisinos.jgraphicscene.shapes.polygons;

import br.unisinos.jgraphicscene.shapes.units.Color;
import br.unisinos.jgraphicscene.shapes.units.Point;
import br.unisinos.jgraphicscene.shapes.units.Vertex;
import br.unisinos.jgraphicscene.utils.constants.Colors;
import br.unisinos.jgraphicscene.utils.constants.Drawing;

import java.util.Collections;

public class Rectangle extends Polygon {

    public Rectangle(float width, float height) {
        this(width, height, Colors.BLACK);
    }

    public Rectangle(float width, float height, Color color) {
        this(width, height, color, new Point());
    }

    public Rectangle(float width, float height, Color color, Point position) {
        this.vertices = new Vertex[6];
        this.generateVertices(width, height, color, position);
    }

    private void generateVertices(float width, float height, Color color, Point position) {
        this.vertices[0] = new Vertex(position.getX(), position.getY(), color);
        this.vertices[1] = new Vertex(width + position.getX(), position.getY(), color);
        this.vertices[2] = new Vertex(width + position.getX(), height + position.getY(), color);

        this.vertices[3] = new Vertex(position.getX(), position.getY(), color);
        this.vertices[4] = new Vertex(position.getX(), height + position.getY(), color);
        this.vertices[5] = new Vertex(width + position.getX(), height + position.getY(), color);
    }

    @Override
    public int getDrawingMode() {
        return Drawing.GL_TRIANGLES;
    }
}
