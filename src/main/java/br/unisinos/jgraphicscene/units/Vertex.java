package br.unisinos.jgraphicscene.units;

import com.jogamp.opengl.util.GLBuffers;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.nio.FloatBuffer;

public class Vertex extends Vector3f {
    private Vector3f normal;
    private Vector2f texture;

    public Vertex(float x, float y, float z, Vector3f normal, Vector2f texture) {
        super(x, y, z);
        this.normal = normal;
        this.texture = texture;
    }

    public Vector3f getNormal() {
        return normal;
    }

    public void setNormal(Vector3f normal) {
        this.normal = normal;
    }

    public float[] get() {
        return new float[] {this.x, this.y, this.z, this.normal.x, this.normal.y, this.normal.z, this.texture.x, this.texture.y};
    }

    public FloatBuffer buffer() {
        return GLBuffers.newDirectFloatBuffer(this.get());
    }

    @Override
    public String toString() {
        return super.toString() + " n=" + normal.toString() + " t=" + texture.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;

        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(getNormal(), vertex.getNormal())
            .append(texture, vertex.texture)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(normal)
            .append(texture)
            .toHashCode();
    }

    public static Vertex from(Vector3f position, Vector3f normal, Vector2f texture) {
        return new Vertex(position.x, position.y, position.z, normal, texture);
    }
}
