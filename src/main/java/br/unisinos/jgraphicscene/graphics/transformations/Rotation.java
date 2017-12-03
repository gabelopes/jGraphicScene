package br.unisinos.jgraphicscene.graphics.transformations;

import br.unisinos.jgraphicscene.utilities.Time;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Rotation extends Transformation {
    protected float speed;

    public Rotation(float x, float y, float z) {
        this(new Vector4f(x, y, z, 0), 1);
    }

    public Rotation(float x, float y, float z, float speed) {
        this(new Vector4f(x, y, z, 0), speed);
    }

    public Rotation(float x, float y, float z, float initialAngle, float speed) {
        this(new Vector4f(x, y, z, initialAngle), speed);
    }

    public Rotation(Vector4f configuration, float speed) {
        super(new Vector3f(), configuration);
        this.speed = speed;
    }

    @Override
    public Vector4f getRotation() {
        return new Vector4f(
            new Vector3f(this.rotation.x, this.rotation.y, this.rotation.z).normalize(),
            speed * Time.secondsDelta()
        );
    }
}
