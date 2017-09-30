package br.unisinos.jgraphicscene.graphics.composer;

import br.unisinos.jgraphicscene.graphics.Transformation;

public final class Chunk {
    private int size;
    private int mode;
    private String shader;
    private Transformation transformation;

    public Chunk(int size, int mode) {
        this(size, mode, new Transformation());
    }

    public Chunk(int size, int mode, Transformation transformation) {
        this.size = size;
        this.mode = mode;
        this.transformation = transformation;
    }

    public Chunk(int size, int mode, String shader) {
        this.size = size;
        this.mode = mode;
        this.shader = shader;
    }

    public Chunk(int size, int mode, String shader, Transformation transformation) {
        this.size = size;
        this.mode = mode;
        this.shader = shader;
        this.transformation = transformation;
    }

    public int getSize() {
        return size;
    }

    public int getMode() {
        return mode;
    }

    public String getShader() {
        return shader;
    }

    public boolean hasShader() {
        return shader != null;
    }

    public Transformation getTransformation() {
        return transformation;
    }
}
