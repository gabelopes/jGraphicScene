package br.unisinos.jgraphicscene.graphics;

import br.unisinos.jgraphicscene.decorators.Drawable;
import br.unisinos.jgraphicscene.decorators.Lightable;
import br.unisinos.jgraphicscene.decorators.Transformable;
import br.unisinos.jgraphicscene.graphics.composer.Composer;
import br.unisinos.jgraphicscene.graphics.transformations.Transformation;
import br.unisinos.jgraphicscene.shapes.Shape;
import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.utilities.constants.Colors;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Scene implements Drawable, Transformable, Lightable {
    private List<Shape> shapes;
    private Transformation transformation;

    public Scene(List<Shape> shapes) {
        this.shapes = shapes;
        this.transformation = new Transformation();
    }

    public Scene(Shape... shapes) {
        this.shapes = new LinkedList<>();
        Collections.addAll(this.shapes, shapes);
        this.transformation = new Transformation();
    }

    public Scene() {
        this(new LinkedList<>());
    }

    public int add(Shape shape) {
        this.shapes.add(shape);
        return this.shapes.size() - 1;
    }

    @Override
    public void draw(Composer composer) {
        for (Shape shape : this.shapes) {
            shape.draw(composer);
        }
    }

    @Override
    public Transformation getTransformation() {
        return this.transformation;
    }

    public void setTransformation(Transformation transformation) {
        this.transformation = transformation;
    }

    @Override
    public Lighting getLighting() {
        return null;
    }

    @Override
    public boolean applyLighting() {
        return false;
    }

    @Override
    public Color getColor() {
        return Colors.BLACK;
    }
}