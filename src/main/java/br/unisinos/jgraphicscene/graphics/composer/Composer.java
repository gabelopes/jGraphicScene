package br.unisinos.jgraphicscene.graphics.composer;

import br.unisinos.jgraphicscene.graphics.transformations.Transformation;
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

        this.chunks.add(new Chunk(this.elements.size(), mode));
    }

    public void add(List<Vertex> vertices, List<Integer> elements, Chunk chunk, int indexOffset) {
        Map<Integer, Integer> mappings = new LinkedHashMap<>();

        for (int i = 0; i < vertices.size(); i++) {
            Vertex vertex = vertices.get(i);
            Integer element = this.vertices.get(vertex);

            if (element == null) {
                int size = this.vertices.size();
                this.vertices.put(vertex, size);
                mappings.put(i + indexOffset, size);
            } else {
                mappings.put(i + indexOffset, element);
            }
        }

        this.addMappedElements(elements, mappings);
        this.chunks.add(chunk);
    }

    public void add(List<Vertex> vertices, List<Integer> elements, int mode, int indexOffset) {
        this.add(vertices, elements, new Chunk(elements.size(), mode), indexOffset);
    }

    public void add(List<Vertex> vertices, List<Integer> elements, int mode) {
        this.add(vertices, elements, mode, 0);
    }

    public void add(List<Vertex> vertices, List<Integer> elements, Chunk chunk) {
        this.add(vertices, elements, chunk, 0);
    }

    private void addMappedElements(List<Integer> elements, Map<Integer, Integer> mappings) {
        for (int element : elements) {
            this.elements.add(mappings.get(element));
        }
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
