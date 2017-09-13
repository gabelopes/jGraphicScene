package br.unisinos.jgraphicscene.graphics;

import br.unisinos.jgraphicscene.shapes.units.Versor;

public class Chunk {
    private Versor<Float> translation, rotation;
    private int size;
    private int drawing;

    public Chunk(int size, int drawing) {
        this.size = size;
        this.drawing = drawing;
    }

    public Versor<Float> getTranslation() {
        return translation;
    }

    public void setTranslation(Versor<Float> translation) {
        this.translation = translation;
    }

    public Versor<Float> getRotation() {
        return rotation;
    }

    public void setRotation(Versor<Float> rotation) {
        this.rotation = rotation;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDrawing() {
        return drawing;
    }

    public void setDrawing(int drawing) {
        this.drawing = drawing;
    }
}
