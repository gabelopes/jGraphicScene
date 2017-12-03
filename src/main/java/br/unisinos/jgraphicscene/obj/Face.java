package br.unisinos.jgraphicscene.obj;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joml.Vector3i;

class Face {
    private Vector3i vertex;
    private Vector3i texture;
    private Vector3i normal;

    public Face(Vector3i vertex) {
        this(vertex, new Vector3i(), new Vector3i());
    }

    public Face(Vector3i vertex, Vector3i texture, Vector3i normal) {
        this.vertex = vertex;
        this.texture = texture;
        this.normal = normal;
    }

    public Vector3i getVertex() {
        return vertex;
    }

    public Vector3i getNormal() {
        return normal;
    }

    public Vector3i getTexture() {
        return texture;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(vertex)
            .append(texture)
            .append(normal)
            .toHashCode();
    }
}
