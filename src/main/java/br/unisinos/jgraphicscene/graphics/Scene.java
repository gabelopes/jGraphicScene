package br.unisinos.jgraphicscene.graphics;

import br.unisinos.jgraphicscene.decorators.Drawable;
import br.unisinos.jgraphicscene.graphics.composer.Composer;
import br.unisinos.jgraphicscene.shapes.Shape;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Scene implements Drawable {
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
}