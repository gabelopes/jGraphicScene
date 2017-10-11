package br.unisinos.jgraphicscene.shapes.polygons;

import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.units.Vertex;
import br.unisinos.jgraphicscene.utilities.constants.Colors;
import br.unisinos.jgraphicscene.utilities.constants.Mode;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Ellipse extends Polygon {
    private float width, height;
    private int definition;
    private Vector3f center;
    private Color color;

    public Ellipse(float width, float height, int definition, Vector3f center, Color color) {
        this.width = width;
        this.height = height;
        this.definition = definition;
        this.center = center;
        this.color = color;
    }

    public Ellipse(float width, float height, int definition, Color color) {
        this(width, height, definition, new Vector3f(), color);
    }

    public Ellipse(float width, float height, int definition, Vector3f center) {
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

    public Vector3f getCenter() {
        return center;
    }

    public Color getColor() {
        return color;
    }

    private void generateVertices() {
        this.vertices = new ArrayList<>(); // definition + center + cycle vertex
        this.vertices.add(new Vertex(this.center.x, this.center.y, this.color));

        double angleQuotient = 2 * Math.PI / this.definition;

        for (int i = 0; i <= this.definition; i++) {
            double angle = i * angleQuotient;

            this.vertices.add(new Vertex((float) (this.width * Math.cos(angle) + this.center.x), (float) (this.height * Math.sin(angle) + this.center.y), this.color));
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
    public int getMode() {
        return Mode.GL_TRIANGLE_FAN;
    }
}
