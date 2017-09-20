package br.unisinos.jgraphicscene.shapes.polygons;

import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.units.Point;
import br.unisinos.jgraphicscene.utilities.constants.Colors;

public class Circle extends Ellipse {
    public Circle(float radius, int definition, Point center, Color color) {
        super(radius, radius, definition, center, color);
    }

    public Circle(float radius, int definition, Point center) {
        this(radius, definition, center, Colors.BLACK);
    }

    public Circle(float radius, int definition, Color color) {
        this(radius, definition, new Point(), color);
    }

    public Circle(float radius, int definition) {
        this(radius, definition, Colors.BLACK);
    }
}
