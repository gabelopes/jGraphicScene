package br.unisinos.jgraphicscene;

import br.unisinos.jgraphicscene.graphics.opengl.Window;
import br.unisinos.jgraphicscene.shapes.solids.Cube;
import br.unisinos.jgraphicscene.utilities.Time;
import br.unisinos.jgraphicscene.utilities.constants.Colors;

public class Program {
    public static void main(String[] args) {
        Window window = new Window("Graphic Scenes", 1024, 768);

        Cube cube = new Cube(0.5f, Colors.BLUE);
        cube.getTransformation().setRotation(() -> Time.getDelta() / 500f, -0.34f, 0.4f, -0.3f);

        Cube cube2 = new Cube(0.3f, Colors.WHITE);
        cube2.getTransformation().setRotation(() -> Time.getDelta() / 1000f, 0.5f, 0.1f, 0.3f);

        window.getDrawer().add(cube);
        window.getDrawer().add(cube2);

//        Composition composition = new Loader(args[0], Colors.BLUE).load();
//
//        Obj obj = new Obj(composition);
//        obj.getTransformation().setRotation(() -> (Time.getDelta()) / 1000f, 1, 0.4f, -0.3f);
//
//        window.add(obj);

        window.open();
    }
}
