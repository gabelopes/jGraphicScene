package br.unisinos.jgraphicscene.graphics.transformations;

import br.unisinos.jgraphicscene.utilities.constants.Axis;
import br.unisinos.jgraphicscene.utilities.constants.TransformationType;
import br.unisinos.jgraphicscene.utilities.structures.Switch;
import org.joml.AxisAngle4f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static br.unisinos.jgraphicscene.utilities.constants.Axis.*;
import static br.unisinos.jgraphicscene.utilities.constants.TransformationType.*;

/**
 * The KeyboardTransformation is a transformation class that handles the
 * transformation of things from keyboard strokes. It holds the
 * respective deltas for translation, rotation and scale, and also makes
 * a speed factor available for each, so they can be independently
 * moved with different speed rates.
 * <p></p>
 * KeyboardTransformation is a static movement class, and is used as the entry
 * point for the callbacks in Window, with respect to translation and
 * rotation.
 */
public class KeyboardTransformation extends Transformation {
    private Matrix4f matrix;
    private Vector3f speeds;
    private float scale;

    public KeyboardTransformation() {
        this(new Vector3f(), new Vector4f());
    }

    public KeyboardTransformation(float speed) {
        this(speed, speed, speed);
    }

    public KeyboardTransformation(float scaleSpeed, float translationSpeed, float rotationSpeed) {
        this(new Vector3f(scaleSpeed, translationSpeed, rotationSpeed));
    }

    public KeyboardTransformation(Vector3f speeds) {
        this(new Vector3f(), new Vector4f(), 1, speeds);
    }

    public KeyboardTransformation(Vector3f translation, Vector4f rotation) {
        this(translation, rotation, 1, new Vector3f(1, 1, 1));
    }

    public KeyboardTransformation(Vector3f translation, Vector4f rotation, float scale, Vector3f speeds) {
        super(translation, rotation);
        this.speeds = speeds;
        this.scale = scale;
        this.matrix = super.getMatrix();
    }

    public void transform(Switch<TransformationType> types, Switch<Axis> axes, float factor) {
        if (axes.any()) {
            if (types.isActive(SCALE)) {
                this.scale(axes, factor);
            }

            if (types.isActive(TRANSLATE)) {
                this.translate(axes, factor);
            }

            if (types.isActive(ROTATE)) {
                this.rotate(axes, factor);
            }
        }
    }

    public void scale(float factor) {
        this.matrix.scale(factor);
    }

    public void scale(Switch<Axis> axes, float factor) {
        Vector3f translation = new Vector3f();
        AxisAngle4f rotation = new AxisAngle4f();

        this.matrix.getTranslation(translation);
        this.matrix.getRotation(rotation);

        this.matrix = super.getMatrix();
        this.matrix.translate(translation);
        this.matrix.rotate(rotation);

        this.scale += factor * this.speeds.x;

        if (this.scale <= 0) {
            this.scale = this.speeds.x;
        }

        Vector3f coordinates = this.getCoordinates(axes, this.scale, false);

        this.matrix.scale(coordinates.x, coordinates.y, coordinates.z);
    }

    public void translate(Switch<Axis> axes, float factor) {
        Vector3f coordinates = this.getCoordinates(axes, factor * this.speeds.y, false);

        this.matrix.translate(coordinates.x, coordinates.y, coordinates.z);
    }

    public void rotate(Switch<Axis> axes, float factor) {
        Vector3f coordinates = this.getCoordinates(axes, 1, true);

        this.matrix.rotate(factor * this.speeds.z, coordinates.x, coordinates.y, coordinates.z);
    }

    private Vector3f getCoordinates(Switch<Axis> axes, float factor, boolean normalize) {
        float x = axes.isActive(X) ? factor : 0;
        float y = axes.isActive(Y) ? factor : 0;
        float z = axes.isActive(Z) ? factor : 0;
        Vector3f coordinates = new Vector3f(x, y, z);

        if (normalize) {
            return coordinates.normalize();
        } else {
            return coordinates;
        }
    }

    @Override
    public Matrix4f getMatrix() {
        return this.matrix;
    }
}
