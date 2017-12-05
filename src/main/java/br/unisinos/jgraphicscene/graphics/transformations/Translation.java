package br.unisinos.jgraphicscene.graphics.transformations;

import br.unisinos.jgraphicscene.utilities.Time;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Translation extends Transformation {
    protected float speed;

    public Translation(float x, float y, float z) {
        this(x, y, z, 1);
    }

    public Translation(float x, float y, float z, float speed) {
        this(new Vector3f(x, y, z), speed);
    }

    public Translation(Vector3f configuration, float speed) {
        super(configuration);
        this.speed = speed;
    }

    @Override
    public Vector3f getTranslation() {
        float delta = speed * Time.secondsDelta();
        return new Vector3f(this.translation.x * delta, this.translation.y * delta, this.translation.z * delta);
    }
}
