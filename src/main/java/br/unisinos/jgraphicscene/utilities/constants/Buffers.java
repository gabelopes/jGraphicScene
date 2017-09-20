package br.unisinos.jgraphicscene.utilities.constants;

public interface Buffers {
    int VERTEX = 0;
    int ELEMENT = 1;
    int GLOBAL_MATRICES = 2;
    int SIZE = Buffers.class.getFields().length - 1;
}
