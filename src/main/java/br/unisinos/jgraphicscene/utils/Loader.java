package br.unisinos.jgraphicscene.utils;

import br.unisinos.jgraphicscene.graphics.Chunk;
import br.unisinos.jgraphicscene.graphics.Composition;
import br.unisinos.jgraphicscene.utils.constants.Drawing;
import jdk.internal.util.xml.impl.Input;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Loader {
    public static Composition loadObj(String path) {
        return loadObj(new File(path));
    }

    public static Composition loadObj(File file) {
        try {
            List<String> lines = FileUtils.readLines(file, Charset.defaultCharset());
            List<Float> vertices = new ArrayList<>(lines.size());
            List<Integer> indices = new ArrayList<>(lines.size());
            List<Chunk> chunks = new ArrayList<>(lines.size());

            for (String line : lines) {
                if (line.startsWith("#")) {
                    continue;
                }

                String[] components = line.split(" ");

                if (components.length > 1) {
                    switch (components[0]) {
                        case "v":
                            Loader.addAll(components, vertices, 1);
                            break;
                        case "f":
                            //Loader.addAll(components, indices, 1);
                            chunks.add(new Chunk(components.length - 1, Drawing.GL_TRIANGLES));
                            break;
                        default:
                            break;
                    }
                }
            }

            return new Composition(vertices, indices, chunks);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void addAll(String[] components, List<Float> list, int offset) {
        for (int i = offset; i < components.length; i++) {
            Float number = Float.parseFloat(components[i]);
            list.add(number);
        }
    }
}
