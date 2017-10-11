package br.unisinos.jgraphicscene.graphics.transformations;

import br.unisinos.jgraphicscene.utilities.Time;
import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * The TimeTransformation is a transformation class that uses the underlying
 * translation and rotation vectors to establish a rate for transforming
 * things overtime, i.e., the transformation matrix is generated based activate
 * the elapsed time.
 * <p></p>
 * Everytime the transformation matrix is gathered, a time delta is calculated,
 * multiplied by the rates in the vectors and applied in the matrix.
 */
public class TimeTransformation extends Transformation {
    private Vector3f initialTranslation;

    public TimeTransformation() {
        this(new Vector3f());
    }

    public TimeTransformation(Vector3f initialTranslation) {
        super(new Vector3f(), new Vector4f());
        this.initialTranslation = initialTranslation;
    }

    public Vector3f getInitialTranslation() {
        return initialTranslation;
    }

    public void setInitialTranslation(Vector3f initialTranslation) {
        this.initialTranslation = initialTranslation;
    }

    @Override
    public Vector3f getTranslation() {
        float delta = Time.secondsDelta();
        return new Vector3f(delta * this.translation.x + this.initialTranslation.x,
                             delta * this.translation.y + this.initialTranslation.y,
                             delta * this.translation.z + this.initialTranslation.z);
    }

    @Override
    public Vector4f getRotation() {
        return new Vector4f(new Vector3f(this.rotation.x, this.rotation.y, this.rotation.z).normalize(), Time.secondsDelta());
    }
}
