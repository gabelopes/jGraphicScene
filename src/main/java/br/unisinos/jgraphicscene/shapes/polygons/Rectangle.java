package br.unisinos.jgraphicscene.shapes.polygons;

import br.unisinos.jgraphicscene.shapes.units.Color;
import br.unisinos.jgraphicscene.shapes.units.Point;
import br.unisinos.jgraphicscene.shapes.units.Vertex;
import br.unisinos.jgraphicscene.utils.constants.Colors;
import br.unisinos.jgraphicscene.utils.constants.Drawing;

public class Rectangle extends Polygon {

    public Rectangle(float width, float height) {
        this(width, height, Colors.BLACK);
    }

    public Rectangle(float width, float height, Color color) {
        this(width, height, color, new Point());
    }

    public Rectangle(float width, float height, Color color, Point position) {
        this.vertices = new Vertex[4];
        this.generateVertices(width, height, color, position);
    }

    public Rectangle(Vertex a, Vertex b, Vertex c, Vertex d) {
        this.vertices = new Vertex[] {a, b, c, d};
    }

    private void generateVertices(float width, float height, Color color, Point position) {
        this.vertices[0] = new Vertex(position.getX(), position.getY(), color);
        this.vertices[1] = new Vertex(width + position.getX(), position.getY(), color);
        this.vertices[2] = new Vertex(width + position.getX(), height + position.getY(), color);
        this.vertices[3] = new Vertex(position.getX(), height + position.getY(), color);
    }

    @Override
    public int getDrawingMode() {
        return Drawing.GL_QUADS;
    }
}
