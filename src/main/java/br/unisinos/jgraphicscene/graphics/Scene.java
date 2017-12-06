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
    private String name;
    private List<Obj> objs;
    private Lighting lighting;
    private Color background;

    public Scene(String name, List<Obj> objs) {
        this(name, objs, new Lighting());
    }

    public Scene(String name, Obj... objs) {
        this(name, new ArrayList<>());
        Collections.addAll(this.objs, objs);
    }

    public Scene(String name) {
        this(name, new LinkedList<>());
    }

    public Scene(String name, List<Obj> objs, Lighting lighting) {
        this(name, objs, lighting, Colors.PETROL);
    }

    public Scene(String name, List<Obj> objs, Lighting lighting, Color background) {
        this.name = name;
        this.objs = objs;
        this.lighting = lighting;
        this.background = background;
    }

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
        this(null, objs, lighting);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Lighting getLighting() {
        return lighting;
    }

    public void setLighting(Lighting lighting) {
        this.lighting = lighting;
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }
}