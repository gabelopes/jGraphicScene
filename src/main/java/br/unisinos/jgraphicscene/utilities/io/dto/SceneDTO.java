package br.unisinos.jgraphicscene.utilities.io.dto;

import br.unisinos.jgraphicscene.graphics.Lighting;
import br.unisinos.jgraphicscene.graphics.Scene;
import br.unisinos.jgraphicscene.obj.Obj;
import br.unisinos.jgraphicscene.utilities.Classes;
import com.jsoniter.annotation.JsonObject;
import org.joml.Vector3f;

import java.util.List;
import java.util.stream.Collectors;

@JsonObject
public class SceneDTO implements DTO<Scene> {
    private String name;
    private List<ObjDTO> objs;
    private Float[] light;

    public Scene transfer() {
        List<Obj> objs = this.objs.stream().map(ObjDTO::transfer).collect(Collectors.toList());
        Lighting lighting = new Lighting(Classes.instance(Vector3f.class, light));

        return new Scene(name, objs, lighting);
    }
}
