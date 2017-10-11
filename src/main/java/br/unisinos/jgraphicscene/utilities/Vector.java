package br.unisinos.jgraphicscene.utilities;

import com.jogamp.opengl.util.GLBuffers;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class Vector {
    public static Vector3f from(float... coordinates) {
        return new Vector3f(GLBuffers.newDirectFloatBuffer(coordinates));
    }

    public static Vector3i from(int... coordinates) {
        return new Vector3i(GLBuffers.newDirectIntBuffer(coordinates));
    }
}
