package br.unisinos.jgraphicscene.shapes.polygons;

import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.utilities.constants.Colors;
import org.joml.Vector3f;

public class Circle extends Ellipse {
    public Circle(float radius, int definition, Vector3f center, Color color) {
        super(radius, radius, definition, center, color);
    }

    public Circle(float radius, int definition, Vector3f center) {
        this(radius, definition, center, Colors.BLACK);
    }

    public Circle(float radius, int definition, Color color) {
        this(radius, definition, new Vector3f(), color);
    }

    public Circle(float radius, int definition) {
        this(radius, definition, Colors.BLACK);
    }
}
