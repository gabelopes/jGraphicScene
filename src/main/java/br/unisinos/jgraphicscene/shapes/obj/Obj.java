package br.unisinos.jgraphicscene.shapes.obj;

import br.unisinos.jgraphicscene.graphics.composer.Chunk;
import br.unisinos.jgraphicscene.graphics.composer.Composer;
import br.unisinos.jgraphicscene.shapes.Shape;
import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.units.Point;
import br.unisinos.jgraphicscene.units.Vertex;
import br.unisinos.jgraphicscene.utilities.Lists;
import br.unisinos.jgraphicscene.utilities.constants.Colors;
import br.unisinos.jgraphicscene.utilities.constants.Mode;

import java.util.ArrayList;
import java.util.List;

public class Obj extends Shape {
    private List<Point> geometricVertices;
    private List<Point> vertexNormals;
    private List<Point> textureCoordinates;

    private List<Face> faces;

    private Color color;

    public Obj() {
        this(Colors.WHITE);
    }

    public Obj(Color color) {
        this.geometricVertices = new ArrayList<>();
        this.vertexNormals = new ArrayList<>();
        this.textureCoordinates = new ArrayList<>();

        this.faces = new ArrayList<>();

        this.color = color;
    }

    public void addVertex(float... coordinates) {
        this.geometricVertices.add(new Point(coordinates));
    }

    public void addNormal(float... coordinates) {
        this.vertexNormals.add(new Point(coordinates));
    }

    public void addTextureCoordinate(float... coordinates) {
        this.textureCoordinates.add(new Point(coordinates));
    }

    public void addFace(int[] vertex, int[] texture, int[] normal) {
        this.faces.add(new Face(new Point(vertex), new Point(texture), new Point(normal)));
    }

    @Override
    public List<Vertex> getVertices() {
        List<Vertex> vertices = new ArrayList<>(this.geometricVertices.size());

        for (Point point : this.geometricVertices) {
            vertices.add(new Vertex(point.getX(), point.getY(), point.getZ(), this.color));
        }

        return vertices;
    }

    private List<Integer> getVertexElements() {
        List<Integer> elements = new ArrayList<>(this.faces.size() * 3);

        for (Face face : this.faces) {
            Point vertex = face.getVertex();
            elements.add((int) vertex.getX());
            elements.add((int) vertex.getY());
            elements.add((int) vertex.getZ());
        }

        return elements;
    }

    @Override
    public void draw(Composer composer) {
        Chunk chunk = new Chunk(this.geometricVertices.size(), this.getMode(), this.getTransformation());
        composer.add(this.getVertices(), this.getVertexElements(), chunk);
    }

    @Override
    public int getMode() {
        return Mode.GL_TRIANGLES;
    }
}
