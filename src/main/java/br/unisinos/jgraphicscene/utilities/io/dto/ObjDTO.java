package br.unisinos.jgraphicscene.utilities.io.dto;

import br.unisinos.jgraphicscene.graphics.transformations.Transformation;
import br.unisinos.jgraphicscene.obj.Obj;
import br.unisinos.jgraphicscene.utilities.Classes;
import br.unisinos.jgraphicscene.utilities.io.ObjLoader;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class ObjDTO implements DTO<Obj> {
    private String filename;
    private Float[] rotation;
    private float scale;
    private Float[] translation;

    private float rotationSpeed;
    private float scaleSpeed;
    private float translationSpeed;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Float[] getRotation() {
        return rotation;
    }

    public void setRotation(Float[] rotation) {
        this.rotation = rotation;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Float[] getTranslation() {
        return translation;
    }

    public void setTranslation(Float[] translation) {
        this.translation = translation;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public float getScaleSpeed() {
        return scaleSpeed;
    }

    public void setScaleSpeed(float scaleSpeed) {
        this.scaleSpeed = scaleSpeed;
    }

    public float getTranslationSpeed() {
        return translationSpeed;
    }

    public void setTranslationSpeed(float translationSpeed) {
        this.translationSpeed = translationSpeed;
    }

    public Obj transfer() {
        Obj obj = ObjLoader.load(filename);

        if (obj != null) {
            Vector4f rotation = Classes.instance(Vector4f.class, this.rotation);
            Vector3f translation = Classes.instance(Vector3f.class, this.translation);
            obj.setTransformation(new Transformation(translation, rotation, this.scale));
        }

        return obj;
    }
}
