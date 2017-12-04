package br.unisinos.jgraphicscene.graphics.composer;

import br.unisinos.jgraphicscene.graphics.transformations.Transformation;
import br.unisinos.jgraphicscene.obj.Material;
import br.unisinos.jgraphicscene.units.Vertex;
import br.unisinos.jgraphicscene.utilities.Lists;

import java.util.*;

import static java.util.Map.Entry;

public class Composer {
    private Map<Vertex, Integer> vertices;
    private List<Chunk> chunks;

    public Composer() {
        this.vertices = new LinkedHashMap<>();
        this.chunks = new LinkedList<>();
    }

    public void add(Map<Material, List<Vertex>> groups, Transformation transformation) {
        for (Entry<Material, List<Vertex>> group : groups.entrySet()) {
            Material material = group.getKey();
            List<Vertex> vertices = group.getValue();

            List<Integer> elements = new ArrayList<>(vertices.size());

            for (Vertex vertex : vertices) {
                Integer element = this.getElement(vertex);
                elements.add(element);
            }

            Chunk chunk = new Chunk(vertices.size(), elements, material, transformation);
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

    public List<Chunk> getChunks() {
        return this.chunks;
    }
}
