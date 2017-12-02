package br.unisinos.jgraphicscene.graphics;

import br.unisinos.jgraphicscene.decorators.Drawable;
import br.unisinos.jgraphicscene.decorators.Lightable;
import br.unisinos.jgraphicscene.graphics.composer.Composer;
import br.unisinos.jgraphicscene.shapes.Shape;
import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.utilities.constants.Colors;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Scene implements Drawable, Lightable {
    private List<Shape> shapes;
    private Lighting globalLighting;

    public Scene(List<Shape> shapes) {
        this.shapes = shapes;
    }

    public Scene(Shape... shapes) {
        this.shapes = new LinkedList<>();
        Collections.addAll(this.shapes, shapes);
    }

    public Scene() {
        this(new LinkedList<>());
    }

    public Scene(List<Shape> shapes, Lighting globalLighting) {
        this.shapes = shapes;
        this.globalLighting = globalLighting;
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
    public Lighting getLighting() {
        return globalLighting;
    }

    @Override
    public boolean applyLighting() {
        return false;
    }

    @Override
    public Color getColor() {
        return Colors.WHITE;
    }
}