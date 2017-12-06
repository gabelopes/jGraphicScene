package br.unisinos.jgraphicscene.obj;

import br.unisinos.jgraphicscene.graphics.Material;
import br.unisinos.jgraphicscene.graphics.composer.Composer;
import br.unisinos.jgraphicscene.graphics.transformations.Transformation;
import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.units.Vertex;
import br.unisinos.jgraphicscene.utilities.constants.Colors;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Obj {
    public static final String DEFAULT_GROUP = "@default";

    private List<Vector3f> vertices;
    private List<Vector2f> textures;
    private List<Vector3f> normals;

    private List<Group> groups;
    private List<Material> materials;

    private Transformation transformation;

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

        this.groups = new ArrayList<>();
        this.materials = new ArrayList<>();

        this.transformation = transformation;
    }

    public void addVertex(float x, float y, float z) {
        this.vertices.add(new Vector3f(x, y, z));
    }

    public void addNormal(float x, float y, float z) {
        this.normals.add(new Vector3f(x, y, z));
    }

    public void addTexture(float s, float t) {
        this.textures.add(new Vector2f(s, t));
    }

    public void addGroup(Group group) {
        this.groups.add(group);
    }

    public void addMaterial(Material material) {
        this.materials.add(material);
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public Map<Material, List<Vertex>> getGroups() {
        Map<Material, List<Vertex>> groups = new HashMap<>();

        for (Group group : this.groups) {
            List<Vertex> vertices = new ArrayList<>(group.getFaces().size() * 3);

            for (Face face : group.getFaces()) {
                Vector3i geometricVertices = face.getVertex();
                Vector3i normalVertices = face.getNormal();
                Vector3i textureVertices = face.getTexture();

                Vector3f vertexA = this.vertices.get(geometricVertices.x - 1);
                Vector3f vertexB = this.vertices.get(geometricVertices.y - 1);
                Vector3f vertexC = this.vertices.get(geometricVertices.z - 1);

                Vector3f normalA = this.normals.get(normalVertices.x - 1);
                Vector3f normalB = this.normals.get(normalVertices.y - 1);
                Vector3f normalC = this.normals.get(normalVertices.z - 1);

                Vector2f textureA = this.textures.get(textureVertices.x - 1);
                Vector2f textureB = this.textures.get(textureVertices.y - 1);
                Vector2f textureC = this.textures.get(textureVertices.z - 1);

                vertices.add(Vertex.from(vertexA, normalA, textureA));
                vertices.add(Vertex.from(vertexB, normalB, textureB));
                vertices.add(Vertex.from(vertexC, normalC, textureC));
            }

            groups.put(this.getMaterial(group.getMaterial()), vertices);
        }

        return groups;
    }

    private Material getMaterial(String material) {
        return this.materials.stream().filter(m -> m.getName().equals(material)).findFirst().orElse(null);
    }

    public void draw(Composer composer) {
        composer.add(this.getGroups(), getTransformation());
    }

    public Transformation getTransformation() {
        return transformation;
    }

    public void setTransformation(Transformation transformation) {
        this.transformation = transformation;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(vertices)
            .append(normals)
            .append(textures)
            .append(groups);

        for (Vector3f vertex : this.vertices) {
            builder.append(vertex);
        }

        for (Vector3f vertex : this.normals) {
            builder.append(vertex);
        }

        for (Vector2f vertex : this.textures) {
            builder.append(vertex);
        }

        for (Group group : this.groups) {
            builder.append(group);
        }

        return builder.toHashCode();
    }
}
