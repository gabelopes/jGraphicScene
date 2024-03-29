package br.unisinos.jgraphicscene.graphics.transformations;

import com.jogamp.opengl.util.GLBuffers;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.nio.FloatBuffer;

public class Transformation {
    protected Vector3f translation;
    protected Vector4f rotation;

    public Transformation() {
        this(new Vector3f(), new Vector4f());
    }

    public Transformation(Vector3f translation, Vector4f rotation) {
        this.translation = translation;
        this.rotation = rotation;
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public Transformation setTranslation(Vector3f translation) {
        this.translation = translation;
        return this;
    }

    public Transformation setTranslation(float x, float y, float z) {
        this.translation = new Vector3f(x, y, z);
        return this;
    }

    public Vector4f getRotation() {
        return rotation;
    }

    public Transformation setRotation(Vector4f rotation) {
        this.rotation = rotation;
        return this;
    }

    public Transformation setRotation(float x, float y, float z) {
        return this.setRotation(0f, x, y, z);
    }

    public Transformation setRotation(float angle, float x, float y, float z) {
        this.rotation = new Vector4f(x, y, z, angle);
        return this;
    }

    public Matrix4f getMatrix() {
        Vector3f translation = this.getTranslation();
        Vector4f rotation = this.getRotation();
        Matrix4f matrix = new Matrix4f();

        matrix.identity();

        matrix.translate(translation.x, translation.y, translation.z);
        matrix.rotate(rotation.w, rotation.x, rotation.y, rotation.z);

        return matrix;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(this.getTranslation())
            .append(this.getRotation())
            .toHashCode();
    }
}
