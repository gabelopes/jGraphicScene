package br.unisinos.jgraphicscene.graphics.composer;

import br.unisinos.jgraphicscene.graphics.transformations.Transformation;
import br.unisinos.jgraphicscene.obj.Material;
import br.unisinos.jgraphicscene.utilities.Lists;
import com.jogamp.opengl.util.GLBuffers;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public final class Chunk {
    private int size;

    private List<Integer> elements;

    private Transformation transformation;
    private Material material;

    private IntBuffer bufferVAO;
    private IntBuffer bufferEBO;

    public Chunk(int size, List<Integer> elements, Material material, Transformation transformation) {
        this.size = size;
        this.elements = elements;
        this.material = material;
        this.transformation = transformation == null ? new Transformation() : transformation;
        this.bufferVAO = GLBuffers.newDirectIntBuffer(1);
        this.bufferEBO = GLBuffers.newDirectIntBuffer(1);
    }

    public int getSize() {
        return size;
    }

    public Transformation getTransformation() {
        return transformation;
    }

    public Material getMaterial() {
        return material;
    }

    public List<Integer> getElements() {
        return elements;
    }

    public int[] getElementsArray() {
        return Lists.asIntegerArray(this.elements);
    }

    public IntBuffer getBufferVAO() {
        return bufferVAO;
    }

    public IntBuffer getBufferEBO() {
        return bufferEBO;
    }

    public boolean hasVAO() {
        return this.bufferVAO.get(0) > 0;
    }

    public boolean hasEBO() {
        return this.bufferEBO.get(0) > 0;
    }

    public int getVAO() {
        return this.bufferVAO.get(0);
    }

    public int getEBO() {
        return this.bufferEBO.get(0);
    }
}
