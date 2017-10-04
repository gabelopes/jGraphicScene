package br.unisinos.jgraphicscene.graphics.transformations;

import br.unisinos.jgraphicscene.utilities.constants.Axis;
import br.unisinos.jgraphicscene.utilities.constants.Morph;
import br.unisinos.jgraphicscene.utilities.structures.Switch;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static br.unisinos.jgraphicscene.utilities.constants.Axis.*;
import static br.unisinos.jgraphicscene.utilities.constants.Morph.*;

/**
 * The Plectromorph is a transformation class that handles the
 * transformation of things from keyboard strokes. It holds the
 * respective deltas for translation and rotation, and also makes
 * a speed factor available for each, so they can be independently
 * moved with different speed rates. Plectro (πλήκτρο) is Greek for key.
 * <p></p>
 * Plectromorph is a static movement class, and is used as the entry
 * point for the callbacks in Window, with respect to translation and
 * rotation.
 */
public class Plectromorph extends Transformation {
    private Matrix4f matrix;
    private Vector3f speeds;

    public Plectromorph() {
        this(new Vector3f(), new Vector4f());
    }

    public Plectromorph(float speed) {
        this(speed, speed, speed);
    }

    public Plectromorph(float scaleSpeed, float translationSpeed, float rotationSpeed) {
        this(new Vector3f(scaleSpeed, translationSpeed, rotationSpeed));
    }

    public Plectromorph(Vector3f speeds) {
        this(new Vector3f(), new Vector4f());
        this.speeds = speeds;
    }

    public Plectromorph(Vector3f translation, Vector4f rotation) {
        super(translation, rotation);
        this.matrix = super.getMatrix();
    }

    public void transform(Switch<Morph> types, Switch<Axis> axes, float factor) {
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
        Vector3f coordinates = this.getCoordinates(axes, factor * this.speeds.x, false);

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
