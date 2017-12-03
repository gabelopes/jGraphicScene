package br.unisinos.jgraphicscene.utilities.io;

import br.unisinos.jgraphicscene.obj.Obj;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ObjLoader {
    private static final String INTEGER = "-?\\d+";
    private static final String DECIMAL = INTEGER + "(?:.\\d+)?(?:[Ee]" + INTEGER + ")?";
    private static final String FACET = INTEGER + "/" + INTEGER + "/" + INTEGER + "|" + INTEGER + "/" + INTEGER + "|" + INTEGER + "//" + INTEGER + "|" + INTEGER;

    private static final Pattern VERTEX = Pattern.compile("^\\s*v\\s+(" + DECIMAL + ")\\s+(" + DECIMAL + ")\\s+(" + DECIMAL + ")(?:\\s+(" + DECIMAL + "))?\\s*$");
    private static final Pattern TEXTURE = Pattern.compile("^\\s*vt\\s+(" + DECIMAL + ")\\s+(" + DECIMAL + ")(?:\\s+(" + DECIMAL + "))?\\s*$");
    private static final Pattern NORMAL = Pattern.compile("^\\s*vn\\s+(" + DECIMAL + ")\\s+(" + DECIMAL + ")\\s+(" + DECIMAL + ")\\s*$");

    private static final Pattern FACE = Pattern.compile("^\\s*f\\s+(" + FACET + ")\\s+(" + FACET + ")\\s+(" + FACET + ")\\s*$");

    public static Obj load(String file) {
        try {
            List<String> lines = FileUtils.readLines(new File(file), Charset.defaultCharset());
            Obj obj = new Obj();

            for (String line : lines) {
                ObjLoader.processLine(line, obj);
            }

            return obj;
        } catch (IOException e) {
            e.printStackTrace(System.err);
            return null;
        }
    }

    private static void processLine(String line, Obj obj) {
        Matcher matcher;

        if ((matcher = VERTEX.matcher(line)).matches()) {
            ObjLoader.addVertex(ObjLoader.getGroups(matcher), obj);
        } else  if ((matcher = TEXTURE.matcher(line)).matches()) {
            ObjLoader.addTextureCoordinate(ObjLoader.getGroups(matcher), obj);
        } else if ((matcher = NORMAL.matcher(line)).matches()) {
            ObjLoader.addNormal(ObjLoader.getGroups(matcher), obj);
        } else if ((matcher = FACE.matcher(line)).matches()) {
            ObjLoader.addFace(ObjLoader.getGroups(matcher), obj);
        } else {
            return;
        }
    }

    private static void addVertex(String[] coordinates, Obj obj) {
        obj.addVertex(ObjLoader.parseCoordinates(coordinates));
    }

    private static void addNormal(String[] coordinates, Obj obj) {
        obj.addNormal(ObjLoader.parseCoordinates(coordinates));
    }

    private static void addTextureCoordinate(String[] coordinates, Obj obj) {
        obj.addTextureCoordinate(ObjLoader.parseCoordinates(coordinates));
    }

    private static void addFace(String[] facets, Obj obj) {
        if (facets.length >= 3) {
            int[] a = parseFacet(facets[0]);
            int[] b = parseFacet(facets[1]);
            int[] c = parseFacet(facets[2]);

            obj.addFace(
                new int[] {a[0], b[0], c[0]},
                new int[] {a[1], b[1], c[1]},
                new int[] {a[2], b[2], c[2]}
            );
        }
    }

    private static String[] getGroups(Matcher matcher) {
        String[] groups = new String[matcher.groupCount()];

        for (int i = 0; i < matcher.groupCount(); i++) {
            groups[i] = matcher.group(i + 1);
        }

        return groups;
    }

    private static float[] parseCoordinates(String[] coordinates) {
        float[] parsedCoordinates = new float[coordinates.length];

        for (int i = 0; i < coordinates.length; i++) {
            try {
                parsedCoordinates[i] = Float.parseFloat(coordinates[i]);
            } catch (Exception e) {
                parsedCoordinates[i] = 0;
            }
        }

        return parsedCoordinates;
    }

    private static int[] parseIndices(String[] indices) {
        int[] parsedIndices = new int[3];

        for (int i = 0; i < 3; i++) {
            try {
                parsedIndices[i] = Integer.parseInt(indices[i]);
            } catch (Exception e) {
                parsedIndices[i] = 0;
            }
        }

        return parsedIndices;
    }

    private static int[] parseFacet(String facet) {
        String[] indices = facet.split("/");

        return ObjLoader.parseIndices(indices);
    }
}
