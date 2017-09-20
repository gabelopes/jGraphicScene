package br.unisinos.jgraphicscene.shapes.solids;

import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.units.Point;

public class Cube extends Parallelepiped {
    public Cube(float size, Color color) {
        this(size, color, new Point());
    }

    public Cube(float size, Color color, Point position) {
        super(size, size, size, color, position);
    }
}
