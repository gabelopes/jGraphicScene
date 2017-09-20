package br.unisinos.jgraphicscene.units;

import com.jogamp.opengl.util.GLBuffers;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.nio.FloatBuffer;

public class Vertex extends Point {
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
        super(x, y);
    }

    public Vertex(float x, float y, float z, float r, float g, float b) {
        this(x, y, z, r, g, b, 1);
    }

    public Vertex(float x, float y, float z, float r, float g, float b, float a) {
        super(x, y, z);
        this.color = new Color(r, g, b, a);
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

    @Override
    public Float[] arrange() {
        return new Float[] {x, y, z, color.red, color.green, color.blue, color.alpha};
    }

    @Override
    public FloatBuffer getBuffer() {
        return GLBuffers.newDirectFloatBuffer(new float[] {x, y, z, color.red, color.green, color.blue, color.alpha});
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
}
