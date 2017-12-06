package br.unisinos.jgraphicscene.utilities.io.dto.transformations;

import br.unisinos.jgraphicscene.graphics.transformations.Transformation;
import br.unisinos.jgraphicscene.utilities.Classes;
import br.unisinos.jgraphicscene.utilities.io.dto.DTO;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class TransformationDTO implements DTO<Transformation> {
    private Float[] translation;
    private Float[] rotation;
    private float scale;

    @Override
    public Transformation transfer() {
        Vector3f translation = Classes.instance(Vector3f.class, this.translation);
        Vector4f rotation = Classes.instance(Vector4f.class, this.rotation);

        return new Transformation(translation, rotation, scale);
    }
}
