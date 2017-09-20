package br.unisinos.jgraphicscene.shapes.solids;

import br.unisinos.jgraphicscene.shapes.polygons.Rectangle;
import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.units.Point;
import br.unisinos.jgraphicscene.utilities.constants.Mode;

import java.util.ArrayList;
import java.util.Collections;

public class Parallelepiped extends Solid {

    public Parallelepiped(float width, float height, float depth, Color color, Point position) {
        this.polygons = new ArrayList<>();
        Collections.addAll(
            this.polygons,
            new Rectangle(depth, width, color, position, Rectangle.Plane.xOy),
            new Rectangle(depth, width, color, new Point(position.getX(), position.getY(), position.getZ() + height), Rectangle.Plane.xOy),
            new Rectangle(depth, height, color, position, Rectangle.Plane.xOz),
            new Rectangle(depth, height, color, new Point(position.getX(), position.getY() + width, position.getZ()), Rectangle.Plane.xOz),
            new Rectangle(width, height, color, position, Rectangle.Plane.yOz),
            new Rectangle(width, height, color, new Point(position.getX() + depth, position.getY(), position.getZ()), Rectangle.Plane.yOz)
        );
    }

    @Override
    public int getMode() {
        return Mode.GL_TRIANGLES;
    }
}
