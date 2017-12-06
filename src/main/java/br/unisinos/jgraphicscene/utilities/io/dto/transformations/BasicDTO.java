package br.unisinos.jgraphicscene.utilities.io.dto.transformations;

import br.unisinos.jgraphicscene.graphics.transformations.Transformation;
import br.unisinos.jgraphicscene.utilities.Classes;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class BasicDTO implements TransformDTO<Transformation> {
    private Float[] translation;
    private Float[] rotation;
    private Float scale;

    @Override
    public Transformation transfer() {
        Vector3f translation = Classes.instance(Vector3f.class, this.translation);
        Vector4f rotation = Classes.instance(Vector4f.class, this.rotation);

        return new Transformation(translation, rotation, scale);
    }

    @Override
    public Transformation transferForConfiguration(Vector3f translation, Vector4f rotation, float scale) {
        if (this.translation != null) {
            translation = Classes.instance(Vector3f.class, this.translation);
        }

        if (this.rotation != null) {
            rotation = Classes.instance(Vector4f.class, this.rotation);
        }

        if (this.scale != null) {
            scale = this.scale;
        }

        return new Transformation(translation, rotation, scale);
    }
}
