package br.unisinos.jgraphicscene;

import br.unisinos.jgraphicscene.graphics.Scene;
import br.unisinos.jgraphicscene.graphics.opengl.Window;
import br.unisinos.jgraphicscene.graphics.transformations.WalkAround;
import br.unisinos.jgraphicscene.obj.Obj;
import br.unisinos.jgraphicscene.utilities.io.SceneLoader;
import br.unisinos.jgraphicscene.utilities.io.parsers.ObjParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static br.unisinos.jgraphicscene.graphics.transformations.WalkAround.Plane.xOz;

public class Program {
    public static void main(String[] args) {
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
}
