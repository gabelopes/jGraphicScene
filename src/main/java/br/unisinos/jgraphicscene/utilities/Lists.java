package br.unisinos.jgraphicscene.utilities;

import java.util.Collections;
import java.util.List;

public class Lists {
    public static float[] asFloatArray(List<? extends Float> list) {
        float[] array = new float[list.size()];

        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }

    public static int[] asIntegerArray(List<? extends Integer> list) {
        int[] array = new int[list.size()];

        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }
}
