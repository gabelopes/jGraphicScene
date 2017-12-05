package br.unisinos.jgraphicscene.utilities.io.dto;

import br.unisinos.jgraphicscene.graphics.transformations.Transformation;
import com.jsoniter.any.Any;

import java.util.Map;

public class TransformationDTO implements DTO<Transformation> {
    private String clazz;
    private Map<String, Any> properties;

    @Override
    public Transformation transfer() {
        return null;
    }
}
