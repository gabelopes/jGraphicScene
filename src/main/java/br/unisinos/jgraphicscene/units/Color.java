package br.unisinos.jgraphicscene.units;

import br.unisinos.jgraphicscene.decorators.Arrangeable;
import br.unisinos.jgraphicscene.decorators.Bufferable;
import com.jogamp.opengl.util.GLBuffers;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.nio.FloatBuffer;

public class Color implements Arrangeable<Float>, Bufferable<FloatBuffer> {
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
    public FloatBuffer getBuffer() {
        return GLBuffers.newDirectFloatBuffer(new float[] {red, green, blue, alpha});
    }

    @Override
    public Float[] arrange() {
        return new Float[] {red, green, blue, alpha};
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(red)
            .append(green)
            .append(blue)
            .append(alpha)
            .toHashCode();
    }
}
