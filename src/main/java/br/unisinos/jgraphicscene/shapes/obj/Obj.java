package br.unisinos.jgraphicscene.shapes.obj;

import br.unisinos.jgraphicscene.decorators.Drawable;
import br.unisinos.jgraphicscene.decorators.Transformable;
import br.unisinos.jgraphicscene.graphics.Transformation;
import br.unisinos.jgraphicscene.graphics.composer.Composer;
import br.unisinos.jgraphicscene.shapes.solids.Solid;
import br.unisinos.jgraphicscene.units.Vertex;
import br.unisinos.jgraphicscene.utilities.constants.Mode;

public class Obj extends Solid {


    public Obj(String file) {

    }

    public void addFace() {

    }

    public void addVertex(Vertex vertex) {

    }

    public void addNormal() {

    }

    public void addTextureCoordinates() {

    }

    @Override
    public void draw(Composer composer) {

    }

    @Override
    public int getMode() {
        return Mode.GL_TRIANGLES;
    }
}
