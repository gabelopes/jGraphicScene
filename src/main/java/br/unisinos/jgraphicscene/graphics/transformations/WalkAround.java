package br.unisinos.jgraphicscene.graphics.transformations;

import br.unisinos.jgraphicscene.obj.Obj;
import br.unisinos.jgraphicscene.utilities.Time;
import org.joml.Vector3f;

import static br.unisinos.jgraphicscene.graphics.transformations.WalkAround.Coordinate.*;
import static br.unisinos.jgraphicscene.graphics.transformations.WalkAround.Plane.xOy;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class WalkAround extends Rotation {
    public enum Plane {
        xOy, xOz, yOz
    }

    public enum Coordinate {
        X, Y, Z
    }

    private Vector3f center;
    private Obj watch;
    private Plane plane;

    private float radius;
    private float inclination;
    private float translationSpeed;

    public WalkAround(Vector3f center, float radius, float inclination, float speed) {
        this(center, xOy, radius, inclination, speed);
    }

    public WalkAround(Vector3f center, Plane plane, float radius, float inclination, float speed) {
        this(center, plane, radius, inclination, speed, new Rotation(0, 0, 0));
    }

    public WalkAround(Vector3f center, Plane plane, float radius, float inclination, float speed, Rotation rotation) {
        super(rotation.rotation.x, rotation.rotation.y, rotation.rotation.z, rotation.rotation.w, rotation.rotationSpeed);
        this.center = center == null ? new Vector3f() : center;
        this.plane = plane == null ? xOy : plane;
        this.radius = radius;
        this.inclination = inclination;
        this.translationSpeed = speed;

        this.setTranslation(this.radius, 0, 0);
    }

    public void watch(Obj obj) {
        this.watch = obj;
    }

    private Coordinate translate(Coordinate coordinate) {
        switch (plane) {
            case xOz:
                return (new Coordinate[]{Z, X, Y})[coordinate.ordinal()];
            case yOz:
                return (new Coordinate[]{Y, Z, X})[coordinate.ordinal()];
            default:
                return coordinate;
        }
    }

    private float get(Coordinate coordinate) {
        switch (coordinate) {
            case X:
                return radius * (float) cos(inclination) * (float) cos(Time.secondsDelta() * translationSpeed);
            case Y:
                return radius * (float) sin(Time.secondsDelta() * translationSpeed);
            default: // Z
                return radius * (float) sin(inclination) * (float) cos(Time.secondsDelta() * translationSpeed);
        }
    }

    private float get(Vector3f vector, Coordinate coordinate) {
        switch (coordinate) {
            case X:
                return vector.x;
            case Y:
                return vector.y;
            default: // z
                return vector.z;
        }
    }

    private float getX() {
        return this.get(translate(X));
    }

    private float getY() {
        return this.get(translate(Y));
    }

    private float getZ() {
        return this.get(translate(Z));
    }

    private boolean isWatching() {
        return watch != null;
    }

    private float getX(Vector3f vector) {
        return this.get(vector, translate(X));
    }

    private float getY(Vector3f vector) {
        return this.get(vector, translate(Y));
    }

    private float getZ(Vector3f vector) {
        return this.get(vector, translate(Z));
    }

    private Vector3f getCenter() {
        if (isWatching()) {
            return watch.getTransformation().getTranslation();
        } else {
            return center;
        }
    }

    @Override
    public Vector3f getTranslation() {
        Vector3f center = this.getCenter(); // This translation doesn't work for a reason;
        return new Vector3f(getX() + getX(center), getY() + getY(center), getZ() + getZ(center));
    }
}
