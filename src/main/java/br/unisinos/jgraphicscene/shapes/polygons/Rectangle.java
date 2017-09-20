package br.unisinos.jgraphicscene.shapes.polygons;

import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.units.Point;
import br.unisinos.jgraphicscene.units.Vertex;
import br.unisinos.jgraphicscene.utilities.constants.Colors;
import br.unisinos.jgraphicscene.utilities.constants.Mode;

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
        this(width, height, color, new Point());
    }

    public Rectangle(float width, float height, Color color, Point position) {
        this(
            new Vertex(position.getX(), position.getY(), color),
            new Vertex(width + position.getX(), position.getY(), color),
            new Vertex(width + position.getX(), height + position.getY(), color),
            new Vertex(position.getX(), height + position.getY(), color)
        );
    }

    public Rectangle(float width, float height, Color color, Point position, Plane plane) {
        Vertex a, b, c, d;

        if (plane == Plane.xOy) {
            a = new Vertex(position.getX(), position.getY(), position.getZ(), color);
            b = new Vertex(width + position.getX(), position.getY(), position.getZ(), color);
            c = new Vertex(width + position.getX(), height + position.getY(), position.getZ(), color);
            d = new Vertex(position.getX(), height + position.getY(), position.getZ(), color);
        } else if (plane == Plane.xOz) {
            a = new Vertex(position.getX(), position.getY(), position.getZ(), color);
            b = new Vertex(width + position.getX(), position.getY(), position.getZ(), color);
            c = new Vertex(width + position.getX(), position.getY(), height + position.getZ(), color);
            d = new Vertex(position.getX(), position.getY(), height + position.getZ(), color);
        } else {
            a = new Vertex(position.getX(), position.getY(), position.getZ(), color);
            b = new Vertex(position.getX(), width + position.getY(), position.getZ(), color);
            c = new Vertex(position.getX(), width + position.getY(), height + position.getZ(), color);
            d = new Vertex(position.getX(), position.getY(), height + position.getZ(), color);
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
