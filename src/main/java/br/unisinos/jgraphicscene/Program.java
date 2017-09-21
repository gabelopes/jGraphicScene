package br.unisinos.jgraphicscene;

import br.unisinos.jgraphicscene.graphics.opengl.Drawer;
import br.unisinos.jgraphicscene.graphics.opengl.Window;
import br.unisinos.jgraphicscene.shapes.obj.Obj;
import br.unisinos.jgraphicscene.shapes.solids.Cube;
import br.unisinos.jgraphicscene.utilities.Time;
import br.unisinos.jgraphicscene.utilities.constants.Colors;
import br.unisinos.jgraphicscene.utilities.io.ObjLoader;

public class Program {
    public static void main(String[] args) {
        Window window = new Window("Graphic Scenes", 1024, 768);

        Cube cubeA = new Cube(0.5f, Colors.BLUE);
        cubeA.getTransformation().setRotation(() -> Time.getDelta() / 1000f, -0.34f, 0.4f, -0.3f);

        Cube cubeB = new Cube(0.3f, Colors.WHITE);
        cubeB.getTransformation().setRotation(() -> Time.getDelta() / 500f, 0.5f, 0.1f, 0.3f);

        window.getDrawer().add(cubeA);
        window.getDrawer().add(cubeB);

        for (String file : args) {
            Obj obj = ObjLoader.load(file);

            if (obj != null) {
                window.getDrawer().add(obj);
            }
        }

        window.open();
    }
}
