package br.unisinos.jgraphicscene.utilities.io.parsers;

import br.unisinos.jgraphicscene.obj.Group;
import br.unisinos.jgraphicscene.obj.Obj;
import org.joml.Vector3i;

import static br.unisinos.jgraphicscene.utilities.io.parsers.RegularExpressions.*;

public class ObjParser extends Parser<Obj> {
    private Obj obj;
    private Group currentGroup;

    private ObjParser(String filename) {
        super(filename);
        this.configure();
        this.obj = new Obj();
    }

    private void configure() {
        this
            .handle(VERTEX, this::addVertex)
            .handle(TEXTURE, this::addTexture)
            .handle(NORMAL, this::addNormal)
            .handle(FACE, this::addFace)
            .handle(GROUP, this::createGroup)
            .handle(MTLLIB, this::addMaterials)
            .handle(USEMTL, this::useMaterial)
            .ignore(COMMENT)
        ;
    }

    @Override
    protected Obj retrieve() {
        return this.obj;
    }

    private void createGroup(String[] args) {
        this.createGroup(args[0]);
    }

    private void createGroup(String name) {
        this.currentGroup = new Group(name);
        this.obj.addGroup(this.currentGroup);
    }

    private void addVertex(String[] args) {
        float[] coordinates = this.toFloatArray(args);
        this.obj.addVertex(coordinates[0], coordinates[1], coordinates[2]);
    }

    private void addNormal(String[] args) {
        float[] coordinates = this.toFloatArray(args);
        this.obj.addNormal(coordinates[0], coordinates[1], coordinates[2]);
    }

    private void addTexture(String[] args) {
        float[] coordinates = this.toFloatArray(args);
        this.obj.addTexture(coordinates[0], coordinates[1]);
    }

    private void addMaterials(String[] args) {
        String mtllib = args[0];
        String filename = this.resolvePath(mtllib);
        this.obj.setMaterials(MaterialParser.parse(filename));
    }

    private void useMaterial(String[] args) {
        this.currentGroup.setMaterial(args[0]);
    }

    private void addFace(String[] facets) {
        if (this.currentGroup == null) {
            this.createGroup(Obj.DEFAULT_GROUP);
        }

        int[] x = parseFacet(facets[0]);
        int[] y = parseFacet(facets[1]);
        int[] z = parseFacet(facets[2]);

        this.currentGroup.addFace(
            new Vector3i(x[0], y[0], z[0]),
            new Vector3i(x[1], y[1], z[1]),
            new Vector3i(x[2], y[2], z[2])
        );
    }

    private int[] parseFacet(String facet) {
        String[] indices = facet.split("/");

        return this.toIntArray(indices);
    }

    public static Obj parse(String filename) {
        return new ObjParser(filename).parse();
    }
}
