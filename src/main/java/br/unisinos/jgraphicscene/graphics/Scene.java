package br.unisinos.jgraphicscene.graphics;

import br.unisinos.jgraphicscene.shapes.Shape;
import br.unisinos.jgraphicscene.shapes.units.Vertex;

import java.util.*;

public class Scene implements Drawable {
    private List<Shape> shapes;

    public Scene(List<Shape> shapes) {
        this.shapes = shapes;
    }

    public Scene(Shape... shapes) {
        this(Arrays.asList(shapes));
    }

    public Scene() {
        this(new ArrayList<>());
    }

    public int add(Shape shape) {
        this.shapes.add(shape);
        return this.shapes.size() - 1;
    }

    @Override
    public Composition compose() {
        Map<Vertex, Integer> map = new HashMap<>();
        List<Integer> elements = new ArrayList<>();
        List<Chunk> chunks = new ArrayList<>();

        for (Shape shape : shapes) {
            for (Vertex vertex : shape.getVertices()) {
                elements.add(this.getIndex(vertex, map));
            }

            chunks.add(new Chunk(shape.getVertices().size(), shape.getDrawingMode()));
        }

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
