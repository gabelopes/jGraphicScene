package br.unisinos.jgraphicscene.utilities.io.dto.transformations;

import br.unisinos.jgraphicscene.graphics.transformations.Rotation;
import br.unisinos.jgraphicscene.utilities.io.dto.DTO;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class RotationDTO implements DTO<Rotation> {
    private float x;
    private float y;
    private float z;
    private float angle;
    private float speed;

    @Override
    public Rotation transfer() {
        return new Rotation(x, y, z, angle, speed);
    }
}
