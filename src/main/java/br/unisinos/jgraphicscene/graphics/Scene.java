package br.unisinos.jgraphicscene.graphics;

import br.unisinos.jgraphicscene.graphics.composer.Composer;
import br.unisinos.jgraphicscene.obj.Obj;
import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.utilities.constants.Colors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Scene {
    private List<Obj> objs;
    private Lighting lighting;

    public Scene(List<Obj> objs) {
        this(objs, new Lighting());
    }

    public Scene(Obj... objs) {
        this(new ArrayList<>());
        Collections.addAll(this.objs, objs);
    }

    public Scene() {
        this(new LinkedList<>());
    }

    public Scene(List<Obj> objs, Lighting lighting) {
        this.objs = objs;
        this.lighting = lighting;
    }

    public int add(Obj shape) {
        this.objs.add(shape);
        return this.objs.size() - 1;
    }

    public void draw(Composer composer) {
        for (Obj shape : this.objs) {
            shape.draw(composer);
        }
    }

    public Lighting getLighting() {
        return lighting;
    }

    public Color getColor() {
        return Colors.WHITE;
    }

    public void setLighting(Lighting lighting) {
        this.lighting = lighting;
    }
}