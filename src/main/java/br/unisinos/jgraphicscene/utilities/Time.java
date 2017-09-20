package br.unisinos.jgraphicscene.utilities;

public class Time {
    private static final long START = System.currentTimeMillis();

    public static long getDelta() {
        return System.currentTimeMillis() - START;
    }
}
