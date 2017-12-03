package br.unisinos.jgraphicscene.utilities.io.dto;

import br.unisinos.jgraphicscene.graphics.Lighting;
import br.unisinos.jgraphicscene.graphics.Scene;
import br.unisinos.jgraphicscene.obj.Obj;
import com.jsoniter.annotation.JsonObject;

import java.util.List;
import java.util.stream.Collectors;

@JsonObject
public class SceneDTO implements DTO<Scene> {
    private List<ObjDTO> objs;
    private LightingDTO lighting;

    public List<ObjDTO> getObjs() {
        return objs;
    }

    public void setObjs(List<ObjDTO> objs) {
        this.objs = objs;
    }

    public LightingDTO getLighting() {
        return lighting;
    }

    public void setLighting(LightingDTO lighting) {
        this.lighting = lighting;
    }

    public Scene transfer() {
        List<Obj> objs = this.objs.stream().map(ObjDTO::transfer).collect(Collectors.toList());
        Lighting lighting = this.lighting.transfer();
        return new Scene(objs, lighting);
    }
}
