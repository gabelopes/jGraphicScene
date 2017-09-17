package br.unisinos.jgraphicscene.shapes.polygons;

import br.unisinos.jgraphicscene.graphics.Chunk;
import br.unisinos.jgraphicscene.graphics.Composition;
import br.unisinos.jgraphicscene.shapes.Shape;
import br.unisinos.jgraphicscene.shapes.units.Vertex;

import java.util.*;

public abstract class Polygon implements Shape {
    protected Vertex[] vertices;

    @Override
    public List<Vertex> getVertices() {
        return Arrays.asList(vertices);
    }

    @Override
    public Composition compose() {
        Map<Vertex, Integer> map = new LinkedHashMap<>();
        List<Integer> elements = new ArrayList<>();
        List<Chunk> chunks = new ArrayList<>();

        for (Vertex vertex : this.getVertices()) {
            elements.add(this.getIndex(vertex, map));
        }

        chunks.add(new Chunk(this.getVertices().size(), this.getDrawingMode()));

        return new Composition(getVertices(map), elements, chunks);
    }

    private List<Float> getVertices(Map<Vertex, Integer> map) {
        List<Float> vertices = new ArrayList<>();

        for (Vertex vertex : map.keySet()) {
            vertex.grasp(vertices);
        }

        return vertices;
    }

    private int getIndex(Vertex vertex, Map<Vertex, Integer> vertexMap) {
        Integer index = vertexMap.get(vertex);

        if (index == null) {
            index = vertexMap.size();
            vertexMap.put(vertex, index);
        }

        return index;
    }
}
