package br.unisinos.jgraphicscene.utilities.structures;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Dispatcher<K, E> {
    Map<K, List<Consumer<E>>> mappings;

    public Dispatcher() {
        this.mappings = new HashMap<>();
    }

    public void attach(K key, Consumer<E> function) {
        List<Consumer<E>> functions = this.mappings.get(key);

        if (functions == null) {
            functions = new LinkedList<>();
            functions.add(function);

            this.mappings.put(key, functions);
        } else {
            functions.add(function);
        }
    }

    public void remove(K key, Consumer<E> function) {
        List<Consumer<E>> functions = this.mappings.get(key);

        if (functions != null) {
            functions.remove(function);
        }
    }

    public void dispatch(K key, E event) {
        List<Consumer<E>> functions = this.mappings.get(key);

        if (functions != null) {
            functions.forEach(f -> f.accept(event));
        }
    }
}
