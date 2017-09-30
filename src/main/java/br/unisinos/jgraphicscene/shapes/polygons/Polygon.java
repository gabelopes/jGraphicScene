package br.unisinos.jgraphicscene.shapes.polygons;

import br.unisinos.jgraphicscene.shapes.Shape;
import br.unisinos.jgraphicscene.units.Vertex;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public abstract class Polygon extends Shape {
    protected List<Vertex> vertices;

    @Override
    public List<Vertex> getVertices() {
        return vertices;
    }

    @Override
    public int hashCode() {
        List<Vertex> vertices = this.getVertices();

        HashCodeBuilder builder = new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(vertices);

        for (Vertex vertex : vertices) {
            builder.append(vertex);
        }

        return builder.toHashCode();
    }
}
