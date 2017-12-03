package br.unisinos.jgraphicscene.obj;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joml.Vector3i;

import java.util.LinkedList;
import java.util.List;

public class Group {
    private String name;
    private String material;

    private List<Face> faces;

    public Group(String name) {
        this(name, null);
    }

    public Group(String name, String material) {
        this.name = name;
        this.material = material;
        this.faces = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public List<Face> getFaces() {
        return faces;
    }

    public void setFaces(List<Face> faces) {
        this.faces = faces;
    }

    public void addFace(Vector3i vertex, Vector3i texture, Vector3i normal) {
        this.faces.add(new Face(vertex, texture, normal));
    }

    @Override
    public String toString() {
        return this.name + " usemtl(" + this.material + ")";
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder =  new HashCodeBuilder(17, 37)
            .append(getName())
            .append(getMaterial())
            .append(getFaces());

        for (Face face : this.getFaces()) {
            builder.append(face);
        }

        return builder.toHashCode();
    }
}
