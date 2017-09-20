package br.unisinos.jgraphicscene.graphics.composer;

import br.unisinos.jgraphicscene.graphics.Transformation;

public final class Chunk {
    private int size;
    private int mode;
    private Transformation transformation;

    public Chunk(int size, int mode) {
        this(size, mode, null);
    }

    public Chunk(int size, int mode, Transformation transformation) {
        this.size = size;
        this.mode = mode;
        this.transformation = transformation;
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
}
