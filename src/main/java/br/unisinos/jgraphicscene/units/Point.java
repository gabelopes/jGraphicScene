package br.unisinos.jgraphicscene.units;

import br.unisinos.jgraphicscene.decorators.Arrangeable;
import br.unisinos.jgraphicscene.decorators.Bufferable;
import com.jogamp.opengl.util.GLBuffers;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.nio.FloatBuffer;

public class Point implements Arrangeable<Float>, Bufferable<FloatBuffer> {
    float x, y, z;

    public Point() {
        this(0, 0);
    }

    public Point(float x, float y) {
        this(x, y, 0);
    }

    public Point(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(float... coordinates) {
        this.x = coordinates.length >= 1 ? coordinates[0] : 0;
        this.y = coordinates.length >= 2 ? coordinates[1] : 0;
        this.z = coordinates.length >= 3 ? coordinates[2] : 0;
    }

    public Point(int... coordinates) {
        this.x = coordinates.length >= 1 ? coordinates[0] : 0;
        this.y = coordinates.length >= 2 ? coordinates[1] : 0;
        this.z = coordinates.length >= 3 ? coordinates[2] : 0;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + (z == 0 ? "" : ", " + z) + ")";
    }

    @Override
    public FloatBuffer getBuffer() {
        return GLBuffers.newDirectFloatBuffer(new float[] {x, y, z});
    }

    @Override
    public Float[] arrange() {
        return new Float[] {x, y, z};
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(x)
            .append(y)
            .append(z)
            .toHashCode();
    }
}
