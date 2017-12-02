package br.unisinos.jgraphicscene.shapes.obj;

import br.unisinos.jgraphicscene.decorators.Lightable;
import br.unisinos.jgraphicscene.graphics.Lighting;
import br.unisinos.jgraphicscene.graphics.composer.Chunk;
import br.unisinos.jgraphicscene.graphics.composer.Composer;
import br.unisinos.jgraphicscene.shapes.Shape;
import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.units.Vertex;
import br.unisinos.jgraphicscene.utilities.Vectors;
import br.unisinos.jgraphicscene.utilities.constants.Colors;
import br.unisinos.jgraphicscene.utilities.constants.Mode;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.List;

public class Obj extends Shape implements Lightable {
    private List<Vector3f> vertices;
    private List<Vector3f> normals;
    private List<Vector3f> textures;

    private List<Face> faces;

    private Color color;
    private Lighting lighting;

    public Obj() {
        this(Colors.WHITE);
    }

    public Obj(Color color) {
        this(color, new Lighting());
    }

    public Obj(Color color, Lighting lighting) {
        this.vertices = new ArrayList<>();
        this.normals = new ArrayList<>();
        this.textures = new ArrayList<>();

        this.faces = new ArrayList<>();

        this.color = color;
        this.lighting = lighting;
    }

    public void addVertex(float... coordinates) {
        this.vertices.add(Vectors.from(coordinates));
    }

    public void addNormal(float... coordinates) {
        this.normals.add(Vectors.from(coordinates));
    }

    public void addTextureCoordinate(float... coordinates) {
        this.textures.add(Vectors.from(coordinates));
    }

    public void addFace(int[] vertex, int[] texture, int[] normal) {
        this.faces.add(new Face(Vectors.from(vertex), Vectors.from(texture), Vectors.from(normal)));
    }

    @Override
    public List<Vertex> getVertices() {
        if (this.applyLighting()) {
            return this.getLightableVertices();
        } else {
            return this.getDefaultVertices();
        }
    }

    private List<Vertex> getDefaultVertices() {
        List<Vertex> vertices = new ArrayList<>(this.vertices.size());

        for (Vector3f vertex : this.vertices) {
            vertices.add(new Vertex(vertex.x, vertex.y, vertex.z, this.color));
        }

        return vertices;
    }

    private List<Vertex> getLightableVertices() {
        List<Vertex> vertices = new ArrayList<>(this.vertices.size());

        for (Face face : this.faces) {
            Vector3i geometricVertices = face.getVertex();
            Vector3i normalVertices = face.getNormal();

            Vector3f vertexA = this.vertices.get(geometricVertices.x - 1);
            Vector3f vertexB = this.vertices.get(geometricVertices.y - 1);
            Vector3f vertexC = this.vertices.get(geometricVertices.z - 1);

            Vector3f normalA = this.normals.get(normalVertices.x - 1);
            Vector3f normalB = this.normals.get(normalVertices.y - 1);
            Vector3f normalC = this.normals.get(normalVertices.z - 1);

            vertices.add(Vertex.from(vertexA, normalA));
            vertices.add(Vertex.from(vertexB, normalB));
            vertices.add(Vertex.from(vertexC, normalC));
        }

        return vertices;
    }

    private List<Integer> getElements() {
        List<Integer> elements = new ArrayList<>(this.faces.size() * 3);

        for (Face face : this.faces) {
            Vector3i vertex = face.getVertex();

            elements.add(vertex.x);
            elements.add(vertex.y);
            elements.add(vertex.z);
        }

        return elements;
    }

    @Override
    public void draw(Composer composer) {
        List<Integer> elements = this.getElements();
        Chunk chunk = new Chunk(elements.size(), this.getMode());

        if (this.applyLighting()) {
            composer.add(this.getMode(), this.getLightableVertices());
        } else {
            composer.add(this.getVertices(), elements, chunk, 1);

        }
    }

    @Override
    public int getMode() {
        return Mode.GL_TRIANGLES;
    }

    @Override
    public Lighting getLighting() {
        return this.lighting;
    }

    @Override
    public boolean applyLighting() {
        return this.lighting != null && this.normals.size() > 0;
    }

    public void setLighting(Lighting lighting) {
        this.lighting = lighting;
    }

    public void setLighting(Vector3f position, Color color) {
        this.setLighting(new Lighting(position, color));
    }

    public void setLighting(float x, float y, float z, float r, float g, float b) {
        this.setLighting(new Vector3f(x, y, z), new Color(r, g, b));
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(vertices)
            .append(normals)
            .append(textures)
            .append(faces)
            .append(color)
            .append(lighting);

        for (Vector3f vertex : this.vertices) {
            builder.append(vertex);
        }

        for (Vector3f vertex : this.normals) {
            builder.append(vertex);
        }

        for (Vector3f vertex : this.textures) {
            builder.append(vertex);
        }

        for (Face face : this.faces) {
            builder.append(face);
        }

        return builder.toHashCode();
    }
}
