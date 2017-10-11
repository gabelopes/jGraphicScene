package br.unisinos.jgraphicscene.graphics.composer;

import br.unisinos.jgraphicscene.graphics.transformations.Transformation;

public final class Chunk {
    private int size;
    private int mode;

    public Chunk(int size, int mode) {
        this.size = size;
        this.mode = mode;
    }

    public int getSize() {
        return size;
    }

    public int getMode() {
        return mode;
    }
}
