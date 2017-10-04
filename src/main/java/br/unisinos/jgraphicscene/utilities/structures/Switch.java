package br.unisinos.jgraphicscene.utilities.structures;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Switch<E extends Enum> {
    private Map<E, Byte> map;
    private byte modifiers;
    private byte all;

    public Switch(E... values) {
        this.map = new LinkedHashMap<>();
        this.modifiers = 0;
        this.all = 0;

        for (int i = 0; i < values.length; i++) {
            byte modifier = (byte) Math.pow(2, i);

            this.all |= modifier;
            this.map.put(values[i], modifier);
        }
    }

    public void handle(E value) {
        byte modifier = this.map.get(value);

        if (isActive(value)) {
            deactivate(modifier);
        } else {
            activate(modifier);
        }
    }

    public void set(E value) {
        this.set(this.map.get(value));
    }

    public void set(E... values) {
        byte modifier = 0;

        for (E value : values) {
            modifier |= this.map.get(value);
        }

        this.set(modifier);
    }

    private void set(byte modifier) {
        this.modifiers = modifier;
    }

    public void activate(E value) {
        this.activate(this.map.get(value));
    }

    private void activate(byte modifier) {
        this.modifiers |= modifier;
    }

    private void activateAll() {
        this.modifiers = this.all;
    }

    public void deactivateAll() {
        this.modifiers = 0;
    }

    public void deactivate(E value) {
        this.deactivate(this.map.get(value));
    }

    private void deactivate(byte modifier) {
        this.modifiers &= ~modifier;
    }

    public boolean isActive(E value) {
        return this.isActive(this.map.get(value));
    }

    private boolean isActive(byte modifier) {
        return (this.modifiers & modifier) != 0;
    }

    public boolean any() {
        return this.modifiers != 0;
    }

    public List<E> getModifiers() {
        List<E> modifiers = new ArrayList<>(this.map.size());

        for (E enumerator : this.map.keySet()) {
            if (this.isActive(enumerator)) {
                modifiers.add(enumerator);
            }
        }

        return modifiers;
    }

    @Override
    public String toString() {
        return map.keySet().toString();
    }
}
