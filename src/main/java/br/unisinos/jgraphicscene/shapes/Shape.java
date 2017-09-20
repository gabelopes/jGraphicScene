package br.unisinos.jgraphicscene.shapes;

import br.unisinos.jgraphicscene.decorators.Drawable;
import br.unisinos.jgraphicscene.decorators.Transformable;
import br.unisinos.jgraphicscene.graphics.Transformation;
import br.unisinos.jgraphicscene.graphics.composer.Composer;
import br.unisinos.jgraphicscene.units.Vertex;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public abstract class Shape implements Drawable, Transformable {
    protected Transformation transformation;

    public Shape() {
        this.transformation = new Transformation();
    }

    public Shape(Transformation transformation) {
        this.transformation = transformation;
    }

    public abstract int getMode();
    public abstract List<Vertex> getVertices();

    @Override
    public Transformation getTransformation() {
        return transformation;
    }

    public void setTransformation(Transformation transformation) {
        this.transformation = transformation;
    }

    @Override
    public void draw(Composer composer) {
        composer.add(this.getMode(), this.getTransformation(), this.getVertices());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Shape)) return false;

        Shape shape = (Shape) o;

        return new EqualsBuilder()
                .append(transformation, shape.transformation)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(transformation)
            .toHashCode();
    }
}
