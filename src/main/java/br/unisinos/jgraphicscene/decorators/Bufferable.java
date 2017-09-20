package br.unisinos.jgraphicscene.decorators;

import java.nio.Buffer;

public interface Bufferable<T extends Buffer> {
    T getBuffer();
}
