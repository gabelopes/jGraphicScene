package br.unisinos.jgraphicscene.utilities.io.parsers;

import java.util.regex.Pattern;

public class RegularExpressions {
    // Units
    static final String UNSIGNED_INTEGER = "\\d+";
    static final String INTEGER = "[-+]?" + UNSIGNED_INTEGER;
    static final String DECIMAL = INTEGER + "(?:.\\d+)?(?:[Ee]" + INTEGER + ")?";
    static final String FACET = INTEGER + "/" + INTEGER + "/" + INTEGER + "|" + INTEGER + "/" + INTEGER + "|" + INTEGER + "//" + INTEGER + "|" + INTEGER;

    // Patterns
    static final Pattern VERTEX = vecd("v", 3, 1);
    static final Pattern TEXTURE = vecd("vt", 2, 1);
    static final Pattern NORMAL = vecd("vn", 3);
    static final Pattern FACE = vecf("f", 3);

    static final Pattern GROUP = id("g");
    static final Pattern MTLLIB = id("mtllib");
    static final Pattern USEMTL = id("usemtl");

    static final Pattern NEWMTL = id("newmtl");
    static final Pattern NS = scalard("Ns"); // Specular exponent
    static final Pattern D = scalard("d"); // Opacity
    static final Pattern TR = scalard("Tr"); // 1 - d
    static final Pattern ILLUM = scalarui("illum");
    static final Pattern KA = vecd("Ka", 3);
    static final Pattern KD = vecd("Kd", 3);
    static final Pattern KS = vecd("Ks", 3);
    static final Pattern MAP_KA = id("map_Ka");
    static final Pattern MAP_KD = id("map_Kd");
    static final Pattern MAP_KS = id("map_Ks");
    static final Pattern SHININESS = scalard("shininess");

    // Other
    static final Pattern COMMENT = Pattern.compile("^\\s*#.*$");

    private static Pattern vecd(String name, int components) {
        return vecd(name, components,0);
    }

    private static Pattern vecd(String name, int components, int optional) {
        return vec(name, DECIMAL, components, optional);
    }

    private static Pattern vecf(String name, int components) {
        return vec(name, FACET, components, 0);
    }

    private static Pattern vec(String name, String unit, int components, int optional) {
        StringBuilder pattern = new StringBuilder("^\\s*" + name);

        for (int i = 0; i < components; i++) {
            pattern.append("\\s+(").append(unit).append(")");
        }

        for (int i = 0; i < optional; i++) {
            pattern.append("(?:\\s+").append(unit).append(")?");
        }

        pattern.append("\\s*$");

        return Pattern.compile(pattern.toString());
    }

    private static Pattern id(String name) {
        return Pattern.compile("^\\s*" + name + "\\s+(\\S+?)\\s*$");
    }

    private static Pattern scalard(String name) {
        return vecd(name, 1);
    }

    private static Pattern scalarui(String name) {
        return vec(name, UNSIGNED_INTEGER, 1, 0);
    }
}
