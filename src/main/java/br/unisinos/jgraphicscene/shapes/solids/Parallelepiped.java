package br.unisinos.jgraphicscene.shapes.solids;

import br.unisinos.jgraphicscene.shapes.polygons.Rectangle;
import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.utilities.constants.Mode;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Collections;

public class Parallelepiped extends Solid {

    public Parallelepiped(float width, float height, float depth, Color color, Vector3f position) {
        this.polygons = new ArrayList<>();
        Collections.addAll(
            this.polygons,
            new Rectangle(depth, width, color, position, Rectangle.Plane.xOy),
            new Rectangle(depth, width, color, new Vector3f(position.x, position.y, position.z + height), Rectangle.Plane.xOy),
            new Rectangle(depth, height, color, position, Rectangle.Plane.xOz),
            new Rectangle(depth, height, color, new Vector3f(position.x, position.y + width, position.z), Rectangle.Plane.xOz),
            new Rectangle(width, height, color, position, Rectangle.Plane.yOz),
            new Rectangle(width, height, color, new Vector3f(position.x + depth, position.y, position.z), Rectangle.Plane.yOz)
        );
    }

    @Override
    public int getMode() {
        return Mode.GL_TRIANGLES;
    }
}
