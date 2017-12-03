package br.unisinos.jgraphicscene.graphics.composer;

import br.unisinos.jgraphicscene.graphics.transformations.Transformation;
import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.units.Vertex;
import br.unisinos.jgraphicscene.utilities.Lists;

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

    public void add(List<Vertex> vertices) {
        this.add(vertices, null);
    }

    public void add(List<Vertex> vertices, Transformation transformation) {
        this.add(vertices, transformation, null);
    }

    public void add(List<Vertex> vertices, Transformation transformation, Color color) {
        Chunk chunk = new Chunk(vertices.size(), transformation, color);
        this.chunks.add(chunk);

        for (Vertex vertex : vertices) {
            Integer element = this.getElement(vertex);
            this.elements.add(element);
        }
    }

    public void addGrouped(List<List<Vertex>> groupedVertices, Transformation transformation) {
        for (List<Vertex> vertices : groupedVertices) {
            List<Integer> elements = new ArrayList<>(vertices.size());

            for (Vertex vertex : vertices) {
                Integer element = this.getElement(vertex);
                elements.add(element);
                this.elements.add(element);
            }

            Chunk chunk = new Chunk(vertices.size(), transformation, elements);
            this.chunks.add(chunk);
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
