package br.unisinos.jgraphicscene.utils;

import java.nio.Buffer;

public interface Bufferable<T extends Buffer> {
    T buffer();
}
