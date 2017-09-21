package br.unisinos.jgraphicscene.shapes.obj;

import br.unisinos.jgraphicscene.units.Point;

class Face {
    private Point vertex;
    private Point texture;
    private Point normal;

    public Face(Point vertex) {
        this(vertex, new Point(), new Point());
    }

    public Face(Point vertex, Point texture, Point normal) {
        this.vertex = vertex;
        this.texture = texture;
        this.normal = normal;
    }

    public Point getVertex() {
        return vertex;
    }

    public Point getNormal() {
        return normal;
    }

    public Point getTexture() {
        return texture;
    }
}
