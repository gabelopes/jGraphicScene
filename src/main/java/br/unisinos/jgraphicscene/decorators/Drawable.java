package br.unisinos.jgraphicscene.decorators;

import br.unisinos.jgraphicscene.graphics.composer.Composer;

public interface Drawable extends Transformable {
    void draw(Composer composer);
}
