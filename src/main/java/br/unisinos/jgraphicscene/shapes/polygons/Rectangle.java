package br.unisinos.jgraphicscene.shapes.polygons;

import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.units.Vertex;
import br.unisinos.jgraphicscene.utilities.constants.Colors;
import br.unisinos.jgraphicscene.utilities.constants.Mode;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Collections;

public class Rectangle extends Polygon {
    public enum Plane {
        xOy,
        xOz,
        yOz
    }

    /**
     *  d__c
     *  |_|
     * a  b
     */
    private Rectangle(Vertex a, Vertex b, Vertex c, Vertex d) {
        this.addVertices(a, b, c, d);
    }

    public Rectangle(float width, float height) {
        this(width, height, Colors.BLACK);
    }

    public Rectangle(float width, float height, Color color) {
        this(width, height, color, new Vector3f());
    }

    public Rectangle(float width, float height, Color color, Vector3f position) {
        this(
            new Vertex(position.x, position.y, color),
            new Vertex(width + position.x, position.y, color),
            new Vertex(width + position.x, height + position.y, color),
            new Vertex(position.x, height + position.y, color)
        );
    }

    public Rectangle(float width, float height, Color color, Vector3f position, Plane plane) {
        Vertex a, b, c, d;

        if (plane == Plane.xOy) {
            a = new Vertex(position.x, position.y, position.z, color);
            b = new Vertex(width + position.x, position.y, position.z, color);
            c = new Vertex(width + position.x, height + position.y, position.z, color);
            d = new Vertex(position.x, height + position.y, position.z, color);
        } else if (plane == Plane.xOz) {
            a = new Vertex(position.x, position.y, position.z, color);
            b = new Vertex(width + position.x, position.y, position.z, color);
            c = new Vertex(width + position.x, position.y, height + position.z, color);
            d = new Vertex(position.x, position.y, height + position.z, color);
        } else {
            a = new Vertex(position.x, position.y, position.z, color);
            b = new Vertex(position.x, width + position.y, position.z, color);
            c = new Vertex(position.x, width + position.y, height + position.z, color);
            d = new Vertex(position.x, position.y, height + position.z, color);
        }

        this.addVertices(a, b, c, d);
    }

    private void addVertices(Vertex a, Vertex b, Vertex c, Vertex d) {
        this.vertices = new ArrayList<>();
        Collections.addAll(this.vertices, a, b, c, a, d, c);
    }

    @Override
    public int getMode() {
        return Mode.GL_TRIANGLES;
    }
}
