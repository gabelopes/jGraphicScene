package br.unisinos.jgraphicscene.shapes;

import br.unisinos.jgraphicscene.graphics.Drawable;
import br.unisinos.jgraphicscene.shapes.units.Vertex;

import java.util.List;

public interface Shape extends Drawable {
    List<Vertex> getVertices();
    int getDrawingMode();
}
