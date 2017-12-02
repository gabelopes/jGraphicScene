package br.unisinos.jgraphicscene.utilities.io.dto;

import br.unisinos.jgraphicscene.graphics.Lighting;
import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.utilities.Classes;
import org.joml.Vector3f;

public class LightingDTO implements DTO<Lighting> {
    private Float[] position;
    private Float[] color;

    public Float[] getPosition() {
        return position;
    }

    public void setPosition(Float[] position) {
        this.position = position;
    }

    public Float[] getColor() {
        return color;
    }

    public void setColor(Float[] color) {
        this.color = color;
    }

    public Lighting transfer() {
        return new Lighting(
            Classes.instance(Vector3f.class, position),
            Classes.instance(Color.class, color)
        );
    }
}
