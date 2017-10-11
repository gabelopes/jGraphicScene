package br.unisinos.jgraphicscene.utilities;

public class Time {
    private static final long START = System.currentTimeMillis();

    public static long delta() {
        return System.currentTimeMillis() - START;
    }

    public static float secondsDelta() {
        return (System.currentTimeMillis() - START) / 1000f;
    }
}
