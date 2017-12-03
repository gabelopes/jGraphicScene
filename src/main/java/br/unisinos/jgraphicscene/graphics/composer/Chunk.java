package br.unisinos.jgraphicscene.graphics.composer;

import br.unisinos.jgraphicscene.graphics.Lighting;
import br.unisinos.jgraphicscene.graphics.transformations.Transformation;
import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.utilities.constants.Colors;

public final class Chunk {
    private int size;
    private int mode;
    private Transformation transformation;
    private Color color;

    Chunk(int size, int mode) {
        this(size, mode, new Transformation());
    }

    Chunk(int size, int mode, Transformation transformation) {
        this(size, mode, transformation, Colors.WHITE);
    }

    Chunk(int size, int mode, Color color) {
        this(size, mode, new Transformation(), color);
    }

    Chunk(int size, int mode, Transformation transformation, Color color) {
        this.size = size;
        this.mode = mode;
        this.transformation = transformation;
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public int getMode() {
        return mode;
    }

    public Transformation getTransformation() {
        return transformation;
    }

    public Color getColor() {
        return color;
    }
}
