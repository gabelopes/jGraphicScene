package br.unisinos.jgraphicscene.graphics;

import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

public class Composition {
    private float[] vertices;
    private int[] elements;
    private List<Chunk> chunks;

    public Composition(List<Float> vertices, List<Integer> elements, List<Chunk> chunks) {
        this.vertices = ArrayUtils.toPrimitive(vertices.toArray(new Float[0]));
        this.elements = ArrayUtils.toPrimitive(elements.toArray(new Integer[0]));
        this.chunks = chunks;
    }

    public float[] getVertices() {
        return this.vertices;
    }

    public int[] getElements() {
        return elements;
    }

    public List<Chunk> getChunks() {
        return chunks;
    }
}
