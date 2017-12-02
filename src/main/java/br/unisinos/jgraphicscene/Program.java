package br.unisinos.jgraphicscene;

import br.unisinos.jgraphicscene.decorators.Drawable;
import br.unisinos.jgraphicscene.graphics.Lighting;
import br.unisinos.jgraphicscene.graphics.Scene;
import br.unisinos.jgraphicscene.graphics.opengl.Window;
import br.unisinos.jgraphicscene.graphics.transformations.KeyboardTransformation;
import br.unisinos.jgraphicscene.graphics.transformations.TimeTransformation;
import br.unisinos.jgraphicscene.shapes.Shape;
import br.unisinos.jgraphicscene.shapes.obj.Obj;
import br.unisinos.jgraphicscene.shapes.polygons.Ellipse;
import br.unisinos.jgraphicscene.shapes.polygons.Triangle;
import br.unisinos.jgraphicscene.shapes.solids.Cube;
import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.units.Vertex;
import br.unisinos.jgraphicscene.utilities.constants.Colors;
import br.unisinos.jgraphicscene.utilities.io.ObjLoader;
import br.unisinos.jgraphicscene.utilities.io.SceneLoader;
import org.apache.commons.lang3.ArrayUtils;
import org.joml.Vector3f;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Program {
//    public static void main(String[] args) {
//        if (args.length == 0) {
//            System.exit(1);
//        }
//
//        List<?> drawables = null;
//
//        if (args[0].equals("-s")) {
//            if (args.length >= 2) {
//                drawables = SceneLoader.load(args[1]);
//            } else {
//                System.exit(2);
//            }
//        } else {
//            drawables = Arrays.stream(args).map(ObjLoader::load).collect(Collectors.toList());
//        }
//
//        Window window = new Window("Graphic Scenes", 1024, 768);
//        ((List<Drawable>)drawables).forEach(window.getDrawer()::add);
//        window.open();
//    }
    public static void main(String[] args) {
        Window window = new Window("Graphic Scenes", 1024, 768);

        Scene scene = new Scene(
            new Cube(0.4f, Colors.BLUE)
        );

        window.getDrawer().add(scene);

        window.open();
    }
}
