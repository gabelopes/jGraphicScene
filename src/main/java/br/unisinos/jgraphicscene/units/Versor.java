package br.unisinos.jgraphicscene.units;

import br.unisinos.jgraphicscene.decorators.Arrangeable;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class Versor<T> implements Arrangeable<T> {
    List<Supplier<T>> components;

    public Versor(Supplier<T>... components) {
        this.components = new ArrayList<>();
        this.add(components);
    }

    public Versor(List<Supplier<T>> components) {
        this.components = components;
    }

    public Versor(T... components) {
        this.components = new ArrayList<>();
        this.add(components);
    }

    public Versor() {
        this(new LinkedList<>());
    }

    public Versor<T> add(T... components) {
        for (T component : components) {
            this.add(component);
        }

        return this;
    }

    public Versor<T> add(Supplier<T>...  components) {
        Collections.addAll(this.components, components);
        return this;
    }

    public Versor<T> add(T component) {
        this.getComponents().add(() -> component);
        return this;
    }

    public Versor<T> add(Supplier<T> component) {
        this.getComponents().add(component);
        return this;
    }

    public T get(int index) {
        if (index < 0 || index >= this.getComponents().size()) {
            return null;
        }

        return this.getComponents().get(index).get();
    }

    public T get(int index, T or) {
        T component = this.get(index);

        return component == null ? or : component;
    }

    public int size() {
        return components.size();
    }

    public List<Supplier<T>> getComponents() {
        return components;
    }

    public T[] arrange() {
        T[] components = (T[]) new Object[this.getComponents().size()];

        for (int i = 0; i < this.getComponents().size(); i++) {
            components[i] = this.get(i);
        }

        return components;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder(17, 37)
            .append(components);

        for (Supplier<T> component : components) {
            builder.append(component);
        }

        return builder.toHashCode();
    }
}
