package br.unisinos.jgraphicscene;

import br.unisinos.jgraphicscene.graphics.Scene;
import br.unisinos.jgraphicscene.graphics.opengl.Window;
import br.unisinos.jgraphicscene.graphics.transformations.Translation;
import br.unisinos.jgraphicscene.graphics.transformations.WalkAround;
import br.unisinos.jgraphicscene.obj.Obj;
import br.unisinos.jgraphicscene.utilities.io.SceneLoader;
import br.unisinos.jgraphicscene.utilities.io.parsers.ObjParser;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Program {
    public static void umain(String[] args) {
        if (args.length == 0) {
            System.exit(1);
        }

        System.out.println("Loading...");

        new Thread(() -> {
            List<Scene> scenes = new ArrayList<>();

            if (args[0].equals("-s")) {
                if (args.length >= 2) {
                    scenes = SceneLoader.load(args[1]);
                } else {
                    System.err.println("No scene file provided!");
                    System.exit(2);
                }
            } else {
                scenes = Arrays.stream(args).map(ObjParser::parse).map(Scene::new).collect(Collectors.toList());
            }

            System.out.println("Finished loading. Opening...");

            Program.open(scenes);
        }).start();
    }

    private static void open(List<Scene> scenes) {
        Window window = new Window("Graphic Scenes", 1024, 768);
        scenes.forEach(window.getDrawer()::add);
        window.open();
    }

    public static void main(String[] args) {
        Obj sun = ObjParser.parse("D:\\Workspace\\jGraphicScene\\out\\artifacts\\bola\\bola.obj");
        //sun.setTransformation(new Translation(1, 0, 0, 0.1f));

        Obj earth = ObjParser.parse("D:\\Workspace\\jGraphicScene\\out\\artifacts\\bola\\bola.obj");
        WalkAround earthMovement = new WalkAround(sun, 5, (float) Math.PI / 4, 1);
        earthMovement.setScale(0.4f);
        earth.setTransformation(earthMovement);

        Obj moon = ObjParser.parse("D:\\Workspace\\jGraphicScene\\out\\artifacts\\bola\\bola.obj");
        WalkAround moonMovement = new WalkAround(earth, 3, (float) Math.PI / 4, 1);
        moonMovement.setScale(0.1f);
        moon.setTransformation(moonMovement);

        Scene scene = new Scene(sun, earth, moon);
        Window window = new Window("Graphic Scenes", 1024, 768);
        window.getDrawer().add(scene);
        window.open();
    }
}
