package br.unisinos.jgraphicscene.graphics.composer;

import br.unisinos.jgraphicscene.graphics.Lighting;
import br.unisinos.jgraphicscene.graphics.transformations.Transformation;
import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.utilities.constants.Colors;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public final class Chunk {
    private int size;
    private Transformation transformation;
    private List<Integer> elements;
    private Color color;

    private IntBuffer VAO;
    private IntBuffer EBO;


    public Chunk(int size, Transformation transformation) {
        this(size, transformation, new ArrayList<>());
    }

    public Chunk(int size, Transformation transformation, Color color) {
        this(size, transformation, new ArrayList<>(), color);
    }

    public Chunk(int size, Transformation transformation, List<Integer> elements) {
        this(size, transformation, elements, Colors.WHITE);
    }

    public Chunk(int size, Transformation transformation, List<Integer> elements, Color color) {
        this.size = size;
        this.transformation = transformation == null ? new Transformation() : transformation;
        this.color = color == null ? Colors.WHITE : color;
        this.elements = elements;
    }

    public int getSize() {
        return size;
    }

    public Transformation getTransformation() {
        return transformation;
    }

    public Color getColor() {
        return color;
    }
}
