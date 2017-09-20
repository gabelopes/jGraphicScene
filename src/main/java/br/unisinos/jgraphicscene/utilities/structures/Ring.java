package br.unisinos.jgraphicscene.utilities.structures;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Ring<T> implements Iterator<T> {
    private int index;
    protected List<T> list;

    public Ring() {
        this(new LinkedList<>());
    }

    public Ring(T[] array) {
        this(new LinkedList<>());
        Collections.addAll(list, array);
    }

    public Ring(List<T> list) {
        this.list = list == null ? new LinkedList<>() : list;
    }

    public void add(T element) {
        this.list.add(element);
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    public T next() {
        if (list.size() > 0) {
            index = (index + 1) % list.size();
        }

        return get();
    }

    public T get() {
        return this.get(index);
    }

    public T get(int index) {
        if (index >= 0 && index < list.size()) {
            return list.get(index);
        }

        return null;
    }

    @Override
    public void remove() {
        this.remove(index);
    }

    public void remove(int index) {
        if (index >= 0 && index < list.size()) {
            list.remove(index);

            if (index == this.index) {
                this.index--;
            }
        }
    }

    public T previous() {
        if (index > 0) {
            index--;
        } else {
            index = list.size() - 1;
        }

        return get();
    }
}
