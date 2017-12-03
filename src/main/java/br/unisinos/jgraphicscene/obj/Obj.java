package br.unisinos.jgraphicscene.obj;

import br.unisinos.jgraphicscene.graphics.Lighting;
import br.unisinos.jgraphicscene.graphics.composer.Composer;
import br.unisinos.jgraphicscene.graphics.transformations.Transformation;
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

public class Obj {
    private List<Vector3f> vertices;
    private List<Vector3f> normals;
    private List<Vector3f> textures;

    private List<Face> faces;

    private Transformation transformation;
    private Color color;

    public Obj() {
        this(Colors.WHITE);
    }

    public Obj(Color color) {
        this(color, new Transformation());
    }

    public Obj(Color color, Transformation transformation) {
        this.vertices = new ArrayList<>();
        this.normals = new ArrayList<>();
        this.textures = new ArrayList<>();

        this.faces = new ArrayList<>();

        this.transformation = transformation;
        this.color = color;
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

    public List<Vertex> getVertices() {
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

    public void draw(Composer composer) {
        composer.add(this.getMode(), this.getVertices(), getTransformation(), getColor());
    }

    public int getMode() {
        return Mode.GL_TRIANGLES;
    }

    public Color getColor() {
        return this.color;
    }

    public Transformation getTransformation() {
        return transformation;
    }

    public void setTransformation(Transformation transformation) {
        this.transformation = transformation;
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
            .append(color);

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
