package br.unisinos.jgraphicscene.utilities.io;

import br.unisinos.jgraphicscene.units.Color;

import java.io.File;


public class Loader {
    private File file;
    private Color color;

    public Loader(String path, Color color) {
        this(new File(path), color);
    }

    public Loader(File file, Color color) {
        this.file = file;
        this.color = color;
    }

//    public Composition load() {
//        try {
//            List<String> lines = FileUtils.readLines(file, Charset.defaultCharset());
//            List<Float> vertices = new ArrayList<>(lines.size());
//            List<Integer> indices = new ArrayList<>(lines.size());
//            List<Chunk> chunks = new ArrayList<>(lines.size());
//
//            Collections.addAll(vertices, 0f, 0f, 0f, 0f, 0f, 0f, 0f);
//
//            for (String line : lines) {
//                this.processLine(line, vertices, indices);
//            }
//
//            chunks.add(new Chunk(vertices.size(), Mode.GL_TRIANGLES, null, new Versor<>(() -> (START - System.currentTimeMillis()) / 500f).add(-0.34f, 0.4f, -0.3f)));
//
//            return new Composition(vertices, indices, chunks);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    private void processLine(String line, List<Float> vertices, List<Integer> indices) {
//        String[] splitLine = line.split("\\s+");
//        String[] coordinates = ArrayUtils.subarray(splitLine, 1, splitLine.length);
//
//        if (line.startsWith("v")) {
//            this.graspVertex(coordinates, color, vertices);
//        } else if (line.startsWith("f")) {
//            this.graspFragment(coordinates, indices);
//        }
//    }
//
//    private void graspVertex(String[] coordinates, Color color, List<Float> vertices) {
//        for (String coordinateString : coordinates) {
//            Float coordinate = Float.parseFloat(coordinateString);
//            vertices.add(coordinate);
//        }
//
//        color.graft(vertices);
//    }
//
//    private void graspFragment(String[] coordinates, List<Integer> indices) {
//        for (String coordinateString : coordinates) {
//            Integer coordinate = Integer.parseInt(coordinateString);
//            indices.add(coordinate);
//        }
//    }
}
