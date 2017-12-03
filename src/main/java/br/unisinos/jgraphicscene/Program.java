package br.unisinos.jgraphicscene;

import br.unisinos.jgraphicscene.graphics.Lighting;
import br.unisinos.jgraphicscene.graphics.Scene;
import br.unisinos.jgraphicscene.graphics.opengl.Window;
import br.unisinos.jgraphicscene.graphics.transformations.Rotation;
import br.unisinos.jgraphicscene.graphics.transformations.Transformation;
import br.unisinos.jgraphicscene.obj.Obj;
import br.unisinos.jgraphicscene.utilities.constants.Colors;
import br.unisinos.jgraphicscene.utilities.io.ObjLoader;
import br.unisinos.jgraphicscene.utilities.io.SceneLoader;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Program {
    public static void umain(String[] args) {
        if (args.length == 0) {
            System.exit(1);
        }

        List<Scene> scenes = null;

        if (args[0].equals("-s")) {
            if (args.length >= 2) {
                scenes = SceneLoader.load(args[1]);
            } else {
                System.exit(2);
            }
        } else {
            scenes = Arrays.stream(args).map(ObjLoader::load).map(Scene::new).collect(Collectors.toList());
        }

        Window window = new Window("Graphic Scenes", 1024, 768);
        scenes.forEach(window.getDrawer()::add);
        window.open();
    }

    public static void main(String[] args) {
        Window window = new Window("Graphic Scenes", 1024, 768);

        Obj bunny = ObjLoader.load("src/main/resources/obj/bunny_normal.obj");

        Obj venus = ObjLoader.load("src/main/resources/obj/venus.obj");
        venus.setTransformation(new Transformation(new Vector3f(1, 1, 1), new Vector4f()));
        venus.setTransformation(new Rotation(1, 0, 1));

        Scene scene = new Scene(bunny, venus);
        scene.setLighting(new Lighting(2,2,2, Colors.WHITE));

        window.getDrawer().add(scene);

        window.open();
    }
}
