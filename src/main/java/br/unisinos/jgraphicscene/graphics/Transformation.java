package br.unisinos.jgraphicscene.graphics;

import br.unisinos.jgraphicscene.decorators.Bufferable;
import br.unisinos.jgraphicscene.units.Versor;
import com.jogamp.opengl.math.Matrix4;
import com.jogamp.opengl.util.GLBuffers;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.nio.FloatBuffer;
import java.util.function.Supplier;

public class Transformation implements Bufferable<FloatBuffer> {
    private Versor<Float> translation;
    private Versor<Float> rotation;

    public Transformation() {
        this(new Versor<>(0f, 0f, 0f), new Versor<>(0f, 0f, 0f, 0f));
    }

    public Transformation(Versor<Float> translation, Versor<Float> rotation) {
        this.translation = translation;
        this.rotation = rotation;
    }

    public Versor<Float> getTranslation() {
        return translation;
    }

    public void setTranslation(Versor<Float> translation) {
        this.translation = translation;
    }

    public Versor<Float> getRotation() {
        return rotation;
    }

    public void setRotation(Versor<Float> rotation) {
        this.rotation = rotation;
    }

    public void setRotation(Supplier<Float> angle, float x, float y, float z) {
        this.rotation = new Versor<>(angle).add(x, y, z);
    }

    public void setRotation(float angle, float x, float y, float z) {
        this.rotation = new Versor<>(angle, x, y, z);
    }

    public Matrix4 getMatrix() {
        Matrix4 matrix = new Matrix4();

        matrix.loadIdentity();

        matrix.translate(translation.get(0, 0f), translation.get(1, 0f), translation.get(2, 0f));
        matrix.rotate(rotation.get(0, 0f), rotation.get(1, 0f), rotation.get(2, 0f), rotation.get(3, 0f));

        return matrix;
    }

    @Override
    public FloatBuffer getBuffer() {
        return GLBuffers.newDirectFloatBuffer(this.getMatrix().getMatrix());
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(translation)
            .append(rotation)
            .toHashCode();
    }
}
