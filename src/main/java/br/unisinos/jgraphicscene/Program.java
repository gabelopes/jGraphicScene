package br.unisinos.jgraphicscene;

import br.unisinos.jgraphicscene.graphics.Scene;
import br.unisinos.jgraphicscene.graphics.opengl.Display;
import br.unisinos.jgraphicscene.shapes.polygons.Circle;
import br.unisinos.jgraphicscene.shapes.polygons.Rectangle;
import br.unisinos.jgraphicscene.shapes.polygons.Triangle;
import br.unisinos.jgraphicscene.shapes.units.Vertex;
import br.unisinos.jgraphicscene.utils.constants.Colors;

public class Program {
    public static void main(String[] args) {
        Display display = new Display("Graphic Scenes", 1024, 768);

        Triangle triangle = new Triangle(new Vertex(0, 0.5f, Colors.RED), new Vertex(0.5f, -0.5f, Colors.GREEN), new Vertex(-0.5f, -0.5f, Colors.BLUE));
        Rectangle rectangle = new Rectangle(0.5f, 0.7f, Colors.RED);
        Circle circle = new Circle(0.5f, 200, Colors.BLUE);

        display.add(triangle);
        display.add(rectangle);
        display.add(circle);

        display.open();
    }
}
