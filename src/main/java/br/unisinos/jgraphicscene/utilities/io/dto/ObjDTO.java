package br.unisinos.jgraphicscene.utilities.io.dto;

import br.unisinos.jgraphicscene.graphics.transformations.Transformation;
import br.unisinos.jgraphicscene.obj.Obj;
import br.unisinos.jgraphicscene.utilities.Classes;
import br.unisinos.jgraphicscene.utilities.io.parsers.ObjParser;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class ObjDTO implements DTO<Obj> {
    private String id;
    private String filename;
    private Float[] translation;
    private Float[] rotation;
    private float scale;

    public Obj transfer() {
        Obj obj = ObjParser.parse(filename);

        if (obj != null) {
            Vector4f rotation = Classes.instance(Vector4f.class, this.rotation);
            Vector3f translation = Classes.instance(Vector3f.class, this.translation);
            obj.setTransformation(new Transformation(translation, rotation, this.scale));
        }

        return obj;
    }
}
