package br.unisinos.jgraphicscene.units;

import com.jogamp.opengl.util.GLBuffers;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joml.Vector3f;

import java.nio.FloatBuffer;

public class Vertex extends Vector3f {
    private Color color;

    public Vertex(float x, float y, float z, Color color) {
        super(x, y, z);
        this.color = color;
    }

    public Vertex(float x, float y, Color color) {
        this(x, y, 0, color);
    }

    public Vertex(float x, float y, float z) {
        super(x, y, z);
    }

    public Vertex(float x, float y) {
        this(x, y, 0);
    }

    public Vertex(float x, float y, float z, float r, float g, float b) {
        super(x, y, z);
        this.color = new Color(r, g, b);
    }

    public Vertex(float x, float y, float r, float g, float b) {
        this(x, y, 0, r, g, b);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float[] get() {
        return new float[] {this.x, this.y, this.z, this.color.x, this.color.y, this.color.z};
    }

    public FloatBuffer buffer() {
        return GLBuffers.newDirectFloatBuffer(this.get());
    }

    @Override
    public String toString() {
        return super.toString() + " rgb" + color.toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(color)
            .toHashCode();
    }

    public static Vertex from(Vector3f position, Vector3f color) {
        return new Vertex(position.x, position.y, position.z, color.x, color.y, color.z);
    }
}
