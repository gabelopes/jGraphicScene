package br.unisinos.jgraphicscene;

import br.unisinos.jgraphicscene.graphics.Lighting;
import br.unisinos.jgraphicscene.graphics.opengl.Window;
import br.unisinos.jgraphicscene.graphics.transformations.Plectromorph;
import br.unisinos.jgraphicscene.shapes.obj.Obj;
import br.unisinos.jgraphicscene.utilities.constants.Colors;
import br.unisinos.jgraphicscene.utilities.io.ObjLoader;

public class Program {
    public static void main(String[] args) {
        Window window = new Window("Graphic Scenes", 1024, 768);

        for (String file : args) {
            Obj obj = ObjLoader.load(file);

            if (obj != null) {
                obj.setTransformation(new Plectromorph(0.1f));
                obj.setLighting(new Lighting(1.2f, 1.0f, 2.0f));
                obj.setColor(Colors.RED);

                window.getDrawer().add(obj);
            }
        }

        window.open();
    }
}
