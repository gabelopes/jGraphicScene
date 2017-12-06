package br.unisinos.jgraphicscene.utilities.io.dto;

import br.unisinos.jgraphicscene.graphics.Material;
import br.unisinos.jgraphicscene.graphics.transformations.Transformation;
import br.unisinos.jgraphicscene.obj.Obj;
import br.unisinos.jgraphicscene.utilities.Classes;
import br.unisinos.jgraphicscene.utilities.io.parsers.ObjParser;
import br.unisinos.jgraphicscene.utilities.pools.ObjPool;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static java.util.Map.Entry;

public class ObjDTO implements DTO<Obj> {
    private String id;

    private String filename;

    private Float[] translation;
    private Float[] rotation;
    private float scale;

    private TransformationDTO transformation;

    private Map<String, MaterialDTO> materials;

    private void replaceMaterials(Obj obj) {
        obj.getMaterials().removeIf(material -> materials.containsKey(material.getName()));

        for (Entry<String, MaterialDTO> entry : materials.entrySet()) {
            String name = entry.getKey();
            MaterialDTO material = entry.getValue();

            obj.addMaterial(material.transferForConfiguration(name));
        }
    }

    public Obj transfer() {
        Obj obj = ObjParser.parse(filename);

        if (obj != null) {
            Vector4f rotation = Classes.instance(Vector4f.class, this.rotation);
            Vector3f translation = Classes.instance(Vector3f.class, this.translation);

            if (transformation != null) {
                obj.setTransformation(this.transformation.transferForConfiguration(translation, rotation, scale));
            } else {
                obj.setTransformation(new Transformation(translation, rotation, scale));
            }

            if (materials != null) {
                this.replaceMaterials(obj);
            }

            if (id != null) {
                ObjPool.register(id, obj);
            }
        }

        return obj;
    }
}
