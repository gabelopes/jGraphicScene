package br.unisinos.jgraphicscene.shapes.polygons;

import br.unisinos.jgraphicscene.shapes.units.Color;
import br.unisinos.jgraphicscene.shapes.units.Point;
import br.unisinos.jgraphicscene.shapes.units.Vertex;
import br.unisinos.jgraphicscene.utils.constants.Colors;
import br.unisinos.jgraphicscene.utils.constants.Drawing;

import java.util.List;

public class Ellipse extends Polygon {
    private float width, height;
    private int definition;
    private Point center;
    private Color color;

    public Ellipse(float width, float height, int definition, Point center, Color color) {
        this.width = width;
        this.height = height;
        this.definition = definition;
        this.center = center;
        this.color = color;
    }

    public Ellipse(float width, float height, int definition, Color color) {
        this(width, height, definition, new Point(), color);
    }

    public Ellipse(float width, float height, int definition, Point center) {
        this(width, height, definition, center, Colors.BLACK);
    }

    public Ellipse(float width, float height, int definition) {
        this(width, height, definition, Colors.BLACK);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getDefinition() {
        return definition;
    }

    public Point getCenter() {
        return center;
    }

    public Color getColor() {
        return color;
    }

    private void generateVertices() {
        this.vertices = new Vertex[definition + 1 + 1]; // definition + center + cycle vertex
        this.vertices[0] = new Vertex(this.center.getX(), this.center.getY(), this.color);

        double angleQuotient = 2 * Math.PI / this.definition;

        for (int i = 0; i <= this.definition; i++) {
            double angle = i * angleQuotient;

            this.vertices[i + 1] = new Vertex((float)(this.width * Math.cos(angle) + this.center.getX()), (float)(this.height * Math.sin(angle) + this.center.getY()), this.color);
        }
    }

    @Override
    public List<Vertex> getVertices() {
        if (this.vertices == null) {
            this.generateVertices();
        }

        return super.getVertices();
    }

    @Override
    public int getDrawingMode() {
        return Drawing.GL_TRIANGLE_FAN;
    }
}
