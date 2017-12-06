package br.unisinos.jgraphicscene.utilities.io.dto.transformations;

import br.unisinos.jgraphicscene.utilities.io.dto.DTO;
import org.joml.Vector3f;
import org.joml.Vector4f;

public interface TransformDTO<T> extends DTO<T> {
    T transferForConfiguration(Vector3f translation, Vector4f rotation, float scale);
}
