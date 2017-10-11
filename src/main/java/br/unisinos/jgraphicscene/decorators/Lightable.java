package br.unisinos.jgraphicscene.decorators;

import br.unisinos.jgraphicscene.graphics.Lighting;
import br.unisinos.jgraphicscene.shapes.Shape;
import br.unisinos.jgraphicscene.units.Color;

public interface Lightable {
    Lighting getLighting();
    boolean applyLighting();
    Color getColor();
}
