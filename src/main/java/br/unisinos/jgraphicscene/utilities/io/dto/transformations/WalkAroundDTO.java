package br.unisinos.jgraphicscene.utilities.io.dto.transformations;

import br.unisinos.jgraphicscene.graphics.transformations.WalkAround;
import br.unisinos.jgraphicscene.obj.Obj;
import br.unisinos.jgraphicscene.utilities.Classes;
import br.unisinos.jgraphicscene.utilities.io.dto.DTO;
import br.unisinos.jgraphicscene.utilities.pools.ObjPool;
import org.joml.Vector3f;

public class WalkAroundDTO implements DTO<WalkAround> {
    private Float[] center;
    private WalkAround.Plane plane;
    private String watch;

    private float radius;
    private float inclination;
    private float speed;

    private RotationDTO rotation;

    @Override
    public WalkAround transfer() {
        Vector3f center = Classes.instance(Vector3f.class, this.center);

        if (rotation == null) {
            rotation = new RotationDTO();
        }

        WalkAround walkAround = new WalkAround(center, plane, radius, inclination, speed, rotation.transfer());

        if (watch != null) {
            Obj obj = ObjPool.get(watch);
            walkAround.watch(obj);
        }

        return walkAround;
    }
}
