package br.unisinos.jgraphicscene.shapes.units;

import br.unisinos.jgraphicscene.utils.Bufferable;
import com.jogamp.opengl.util.GLBuffers;

import java.nio.FloatBuffer;

public class Point implements Bufferable<FloatBuffer> {
    float x, y, z;

    public Point() {
        this(0, 0);
    }

    public Point(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(float x, float y) {
        this(x, y, 0);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + (z == 0 ? "" : ", " + z) + ")";
    }

    @Override
    public FloatBuffer buffer() {
        return GLBuffers.newDirectFloatBuffer(new float[] {this.x, this.y, this.z});
    }
}
