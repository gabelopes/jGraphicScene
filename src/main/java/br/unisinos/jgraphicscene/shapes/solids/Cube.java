package br.unisinos.jgraphicscene.shapes.solids;

import br.unisinos.jgraphicscene.units.Color;
import org.joml.Vector3f;

public class Cube extends Parallelepiped {
    public Cube(float size, Color color) {
        this(size, color, new Vector3f());
    }

    public Cube(float size, Color color, Vector3f position) {
        super(size, size, size, color, position);
    }
}
