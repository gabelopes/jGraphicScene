package br.unisinos.jgraphicscene.utilities.io.dto.transformations;

import br.unisinos.jgraphicscene.graphics.transformations.Rotation;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class RotationDTO implements TransformDTO<Rotation> {
    private float x;
    private float y;
    private float z;
    private float angle;
    private float speed;

    @Override
    public Rotation transfer() {
        return new Rotation(x, y, z, angle, speed);
    }

    @Override
    public Rotation transferForConfiguration(Vector3f translation, Vector4f rotation, float scale) {
        Rotation result = new Rotation(x, y, z, angle, speed);
        result.setScale(scale);
        result.setTranslation(translation);

        return result;
    }
}
