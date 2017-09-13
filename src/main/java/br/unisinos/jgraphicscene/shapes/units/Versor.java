package br.unisinos.jgraphicscene.shapes.units;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class Versor<T> {
    List<Supplier<T>> components;

    public Versor(List<Supplier<T>> components) {
        this.components = components;
    }

    public Versor(T... components) {
        this.components = new ArrayList<>();

        for (T component : components) {
            this.add(component);
        }
    }

    public Versor() {
        this(new LinkedList<>());
    }

    public void add(T component) {
        this.components.add(() -> component);
    }

    public void add(Supplier<T> component) {
        this.components.add(component);
    }

    public T get(int index) {
        return this.components.get(index).get();
    }

    public int size() {
        return components.size();
    }

    public List<Supplier<T>> getComponents() {
        return components;
    }
}
