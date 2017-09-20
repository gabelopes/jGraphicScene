package br.unisinos.jgraphicscene.shapes.solids;

import br.unisinos.jgraphicscene.shapes.Shape;
import br.unisinos.jgraphicscene.shapes.polygons.Polygon;
import br.unisinos.jgraphicscene.units.Vertex;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.LinkedList;
import java.util.List;

public abstract class Solid extends Shape {
    protected List<Polygon> polygons;

    @Override
    public List<Vertex> getVertices() {
        List<Vertex> vertices = new LinkedList<>();

        for (Polygon polygon : this.polygons) {
            vertices.addAll(polygon.getVertices());
        }

        return vertices;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(polygons);

        for (Polygon polygon : polygons) {
            builder.append(polygon);
        }

        return builder.toHashCode();
    }
}
