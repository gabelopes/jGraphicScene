package br.unisinos.jgraphicscene.shapes.units;

import br.unisinos.jgraphicscene.utils.Bufferable;
import com.jogamp.opengl.util.GLBuffers;

import java.nio.FloatBuffer;

public class Color implements Vector<Float>, Bufferable<FloatBuffer> {
    float red, green, blue, alpha;

    public Color() {
        this(0, 0, 0);
    }

    public Color(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public Color(float red, float green, float blue) {
        this(red, green, blue, 1);
    }

    public float getRed() {
        return red;
    }

    public float getGreen() {
        return green;
    }

    public float getBlue() {
        return blue;
    }

    public float getAlpha() {
        return alpha;
    }

    @Override
    public String toString() {
        return "(" + red + ", " + green + ", " + blue + (alpha == 1 ? "" : ", " + alpha) + ")";
    }

    @Override
    public FloatBuffer buffer() {
        return GLBuffers.newDirectFloatBuffer(new float[] {this.red, this.green, this.blue, this.alpha});
    }

    @Override
    public Float[] getComponents() {
        return new Float[] {red, green, blue, alpha};
    }
}
