package br.unisinos.jgraphicscene.graphics.transformations;

import br.unisinos.jgraphicscene.utilities.Time;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Rotation extends Transformation {
    protected float rotationSpeed;

    public Rotation(float x, float y, float z) {
        this(new Vector4f(x, y, z, 0), 1);
    }

    public Rotation(float x, float y, float z, float rotationSpeed) {
        this(new Vector4f(x, y, z, 0), rotationSpeed);
    }

    public Rotation(float x, float y, float z, float initialAngle, float rotationSpeed) {
        this(new Vector4f(x, y, z, initialAngle), rotationSpeed);
    }

    public Rotation(Vector4f configuration, float rotationSpeed) {
        super(configuration);
        this.rotationSpeed = rotationSpeed;
    }

    @Override
    public Vector4f getRotation() {
        return new Vector4f(
            new Vector3f(this.rotation.x, this.rotation.y, this.rotation.z).normalize(),
            rotationSpeed * Time.secondsDelta()
        );
    }
}
