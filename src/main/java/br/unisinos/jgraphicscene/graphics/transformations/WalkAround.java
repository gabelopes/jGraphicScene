package br.unisinos.jgraphicscene.graphics.transformations;

import br.unisinos.jgraphicscene.obj.Obj;
import br.unisinos.jgraphicscene.utilities.Time;
import org.joml.Vector3f;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class WalkAround extends Transformation {
    public enum Plane {
        XØY,
        XØZ,
        YØZ
    }

    private Obj obj;
    private float radius;
    private float inclination;
    private float speed;

    public WalkAround(Obj obj, float radius, float inclination, float speed) {
        this.obj = obj;
        this.radius = radius;
        this.inclination = inclination;
        this.speed = speed;

        this.setTranslation(this.radius, 0, 0);
    }

    private float getX(float reference) {
        return radius * (float) cos(inclination) * (float) cos(Time.secondsDelta() * speed) + reference;
    }

    private float getY(float reference) {
        return radius * (float) sin(Time.secondsDelta() * speed) + reference;
    }

    private float getZ(float reference) {
        return radius * (float) sin(inclination) * (float) cos(Time.secondsDelta() * speed) + reference;
    }

    @Override
    public Vector3f getTranslation() {
        Vector3f center = obj.getTransformation().getTranslation();
        return new Vector3f(getX(center.x), getY(center.y), getZ(center.z));
    }
}
