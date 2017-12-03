package br.unisinos.jgraphicscene.graphics.composer;

import br.unisinos.jgraphicscene.graphics.Lighting;
import br.unisinos.jgraphicscene.graphics.transformations.Transformation;
import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.units.Vertex;
import br.unisinos.jgraphicscene.utilities.Lists;
import br.unisinos.jgraphicscene.utilities.constants.Mode;

import java.util.*;

public class Composer {
    private Map<Vertex, Integer> vertices;
    private List<Integer> elements;
    private List<Chunk> chunks;

    public Composer() {
        this.vertices = new LinkedHashMap<>();
        this.elements = new ArrayList<>();
        this.chunks = new LinkedList<>();
    }

    public void add(int mode, Vertex... vertices) {
        for (Vertex vertex : vertices) {
            Integer element = this.getElement(vertex);
            this.elements.add(element);
        }

        this.chunks.add(new Chunk(vertices.length, mode));
    }

    public void add(int mode, List<Vertex> vertices) {
        for (Vertex vertex : vertices) {
            Integer element = this.getElement(vertex);
            this.elements.add(element);
        }

        this.chunks.add(new Chunk(vertices.size(), mode));
    }

    public void add(int mode, List<Vertex> vertices, Transformation transformation) {
        for (Vertex vertex : vertices) {
            Integer element = this.getElement(vertex);
            this.elements.add(element);
        }

        this.chunks.add(new Chunk(vertices.size(), mode, transformation));
    }

    public void add(int mode, List<Vertex> vertices, Transformation transformation, Color color) {
        for (Vertex vertex : vertices) {
            Integer element = this.getElement(vertex);
            this.elements.add(element);
        }

        this.chunks.add(new Chunk(vertices.size(), mode, transformation, color));
    }

    private Integer getElement(Vertex vertex) {
        Integer element = this.vertices.get(vertex);

        if (element == null) {
            element = this.vertices.size();
            this.vertices.put(vertex, element);
        }

        return element;
    }

    public float[] getVertices() {
        List<Float> vertices = new ArrayList<>();

        for (Vertex vertex : this.vertices.keySet()) {
            for (float component : vertex.get()) {
                vertices.add(component);
            }
        }

        return Lists.asFloatArray(vertices);
    }

    public int[] getElements() {
        return Lists.asIntegerArray(this.elements);
    }

    public List<Chunk> getChunks() {
        return this.chunks;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
