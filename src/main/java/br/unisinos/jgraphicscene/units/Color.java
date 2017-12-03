package br.unisinos.jgraphicscene.units;

import com.jogamp.opengl.util.GLBuffers;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joml.Vector3f;

import java.nio.FloatBuffer;

public class Color extends Vector3f {
    public Color() {
        this(0, 0, 0);
    }

    public Color(float red, float green, float blue) {
        this.x = red;
        this.y = green;
        this.z = blue;
    }

    public float getRed() {
        return this.x;
    }

    public float getGreen() {
        return this.y;
    }

    public float getBlue() {
        return this.z;
    }

    public FloatBuffer buffer() {
        return GLBuffers.newDirectFloatBuffer(new float[] {this.x, this.y, this.z});
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(this.x)
            .append(this.y)
            .append(this.z)
            .toHashCode();
    }
}
