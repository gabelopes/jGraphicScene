package br.unisinos.jgraphicscene;

import br.unisinos.jgraphicscene.graphics.Scene;
import br.unisinos.jgraphicscene.graphics.opengl.Window;
import br.unisinos.jgraphicscene.shapes.obj.Obj;
import br.unisinos.jgraphicscene.shapes.polygons.Ellipse;
import br.unisinos.jgraphicscene.shapes.polygons.Triangle;
import br.unisinos.jgraphicscene.shapes.solids.Cube;
import br.unisinos.jgraphicscene.units.Point;
import br.unisinos.jgraphicscene.units.Vertex;
import br.unisinos.jgraphicscene.utilities.Time;
import br.unisinos.jgraphicscene.utilities.constants.Colors;
import br.unisinos.jgraphicscene.utilities.io.ObjLoader;

public class Program {
    public static void main(String[] args) {
        Window window = new Window("Graphic Scenes", 1024, 768);

        Ellipse ellipse = new Ellipse(0.5f, 0.3f, 200, Colors.RED);
        Triangle triangle = new Triangle(new Vertex(0, 0.5f, Colors.RED), new Vertex(-0.5f, -0.5f, Colors.GREEN), new Vertex(0.5f, -0.5f, Colors.BLUE));

        Triangle rotatingTriangle = new Triangle(new Vertex(0, 0.5f, Colors.RED), new Vertex(-0.5f, -0.5f, Colors.TEAL), new Vertex(0.5f, -0.5f, Colors.BLUE));
        rotatingTriangle.getTransformation().setRotation(Time::secondsDelta, -0.3f, 0.5f, 0);
        // First Scene
        Triangle a = new Triangle(new Vertex(-0.75f, 0.75f, Colors.RED), new Vertex(-0.25f, 0.75f, Colors.GREEN), new Vertex(-0.5f, 0.25f, Colors.BLUE));
        Triangle b = new Triangle(new Vertex(0.75f, -0.75f, Colors.RED), new Vertex(0.25f, -0.75f, Colors.GREEN), new Vertex(0.5f, -0.25f, Colors.BLUE));

        Scene sceneA = new Scene(a, b);
        // Second Scene
        Triangle c = new Triangle(new Vertex(-0.75f, 0.75f, Colors.RED), new Vertex(-0.25f, 0.75f, Colors.GREEN), new Vertex(-0.5f, 0.25f, Colors.BLUE));
        Triangle d = new Triangle(new Vertex(0.75f, -0.75f, Colors.RED), new Vertex(0.25f, -0.75f, Colors.GREEN), new Vertex(0.5f, -0.25f, Colors.BLUE));
        Triangle e = new Triangle(new Vertex(0, 0.2f, Colors.WHITE), new Vertex(-0.2f, -0.2f, Colors.GRAY), new Vertex(0.2f, -0.2f, Colors.GRAY));

        e.getTransformation().setRotation(Time::secondsDelta, 1, 1, 0.2f);

        Scene sceneB = new Scene(c, d, e);
        // Third Scene (3D Rotating Cubes)
        Cube cubeA = new Cube(0.1f, Colors.GREEN);
        cubeA.getTransformation().setRotation(Time::secondsDelta, -0.3f, 0.5f, 1);
        Cube cubeB = new Cube(0.2f, Colors.BLUE, new Point(0.5f, 0.5f, 0.5f));
        cubeB.getTransformation().setRotation(Time::secondsDelta, 1f, 0.5f, -0.7f);

        Scene sceneC = new Scene(cubeA, cubeB);

        window.getDrawer().add(ellipse, triangle, sceneA, rotatingTriangle, sceneB, sceneC);

        for (String file : args) {
            Obj obj = ObjLoader.load(file);

            if (obj != null) {
                window.getDrawer().add(obj);
            }
        }

        window.open();
    }
}
