package br.unisinos.jgraphicscene;

import br.unisinos.jgraphicscene.graphics.Lighting;
import br.unisinos.jgraphicscene.graphics.Scene;
import br.unisinos.jgraphicscene.graphics.opengl.Window;
import br.unisinos.jgraphicscene.graphics.transformations.KeyboardTransformation;
import br.unisinos.jgraphicscene.graphics.transformations.TimeTransformation;
import br.unisinos.jgraphicscene.shapes.obj.Obj;
import br.unisinos.jgraphicscene.shapes.polygons.Ellipse;
import br.unisinos.jgraphicscene.shapes.polygons.Triangle;
import br.unisinos.jgraphicscene.shapes.solids.Cube;
import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.units.Vertex;
import br.unisinos.jgraphicscene.utilities.constants.Colors;
import br.unisinos.jgraphicscene.utilities.io.ObjLoader;
import org.joml.Vector3f;

public class Program {
    public static void main(String[] args) {
        Window window = new Window("Graphic Scenes", 1024, 768);

//        Ellipse ellipse = new Ellipse(0.5f, 0.3f, 200, Colors.RED);
//        Triangle triangle = new Triangle(new Vertex(0, 0.5f, Colors.RED), new Vertex(-0.5f, -0.5f, Colors.GREEN), new Vertex(0.5f, -0.5f, Colors.BLUE));
//
//        Triangle rotatingTriangle = new Triangle(new Vertex(0, 0.5f, Colors.RED), new Vertex(-0.5f, -0.5f, Colors.TEAL), new Vertex(0.5f, -0.5f, Colors.BLUE));
//        rotatingTriangle.setTransformation(new TimeTransformation().setRotation(-0.3f, 0.5f, 0));
//        // First Scene
//        Triangle a = new Triangle(new Vertex(-0.75f, 0.75f, Colors.RED), new Vertex(-0.25f, 0.75f, Colors.GREEN), new Vertex(-0.5f, 0.25f, Colors.BLUE));
//        Triangle b = new Triangle(new Vertex(0.75f, -0.75f, Colors.RED), new Vertex(0.25f, -0.75f, Colors.GREEN), new Vertex(0.5f, -0.25f, Colors.BLUE));
//
//        Scene sceneA = new Scene(a, b);
//
//        // Third Scene (3D Rotating Cubes)
//        Cube cubeA = new Cube(0.1f, Colors.GREEN);
//        Cube cubeB = new Cube(0.2f, Colors.BLUE, new Vector3f(0.5f, 0.5f, 0.5f));
//
//        Scene sceneC = new Scene(cubeA, cubeB);
//
//        window.getDrawer().add(ellipse, triangle, sceneA, rotatingTriangle, sceneC);

        for (String file : args) {
            Obj obj = ObjLoader.load(file);

            if (obj != null) {
                obj.setTransformation(new KeyboardTransformation(0.01f, 0.1f, 0.1f));
                obj.setLighting(new Lighting(1.2f, 1.0f, 2.0f));
                obj.setColor(new Color(1, 0.5f, 0.31f));

                window.getDrawer().add(obj);
            }
        }

        window.open();
    }
}
