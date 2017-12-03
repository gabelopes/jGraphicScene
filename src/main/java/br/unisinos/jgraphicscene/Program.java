package br.unisinos.jgraphicscene;

import br.unisinos.jgraphicscene.graphics.Scene;
import br.unisinos.jgraphicscene.graphics.opengl.Window;
import br.unisinos.jgraphicscene.utilities.io.SceneLoader;
import br.unisinos.jgraphicscene.utilities.io.parsers.ObjParser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {
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
            scenes = Arrays.stream(args).map(ObjParser::parse).map(Scene::new).collect(Collectors.toList());
        }

        Window window = new Window("Graphic Scenes", 1024, 768);
        scenes.forEach(window.getDrawer()::add);
        window.open();
    }
}
