package br.unisinos.jgraphicscene.utilities.io.dto;

import br.unisinos.jgraphicscene.graphics.transformations.Transformation;
import br.unisinos.jgraphicscene.utilities.io.dto.transformations.TransformDTO;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class TransformationDTO implements DTO<Transformation> {
    private String name;
    private Any properties;

    private Vector3f translation;
    private Vector4f rotation;
    private Float scale;

    public Transformation transferForConfiguration(Vector3f translation, Vector4f rotation, Float scale) {
        this.translation = translation;
        this.rotation = rotation;
        this.scale = scale;

        return transfer();
    }

    @Override
    public Transformation transfer() {
        try {
            Class<TransformDTO> clazz = (Class<TransformDTO>) Class.forName(this.getClass().getPackage().getName() + ".transformations." + name + "DTO");
            Transformation transformation = (Transformation) JsonIterator.deserialize(properties.toString(), clazz).transferForConfiguration(translation, rotation, scale);

            return transformation;
        } catch (Exception e) {
            return null;
        }
    }
}
