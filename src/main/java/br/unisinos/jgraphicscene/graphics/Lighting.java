package br.unisinos.jgraphicscene.graphics;

import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.utilities.constants.Colors;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joml.Vector3f;

public class Lighting {
    private Vector3f position;
    private Color color;

    public Lighting() {
        this(new Vector3f());
    }

    public Lighting(Vector3f position) {
        this(position, Colors.WHITE);
    }

    public Lighting(Vector3f position, Color color) {
        this.position = position;
        this.color = color;
    }

    public Lighting(float x, float y, float z, Color color) {
        this(new Vector3f(x, y, z), color);
    }

    public Lighting(float x, float y, float z, float r, float g, float b) {
        this(new Vector3f(x, y, z), new Color(r, g, b));
    }

    public Lighting(float x, float y, float z) {
        this(new Vector3f(x, y, z), Colors.WHITE);
    }

    public Vector3f getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(position)
            .append(color)
            .toHashCode();
    }
}
