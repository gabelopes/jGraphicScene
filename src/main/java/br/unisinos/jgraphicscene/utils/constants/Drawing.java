package br.unisinos.jgraphicscene.utils.constants;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public interface Drawing {
    int GL_POINTS = GL.GL_POINTS;
    int GL_LINES = GL.GL_LINES;
    int GL_LINE_LOOP = GL.GL_LINE_LOOP;
    int GL_LINE_STRIP = GL.GL_LINE_STRIP;
    int GL_TRIANGLES = GL.GL_TRIANGLES;
    int GL_TRIANGLE_STRIP = GL.GL_TRIANGLE_STRIP;
    int GL_TRIANGLE_FAN = GL.GL_TRIANGLE_FAN;
    int GL_QUADS = GL2.GL_QUADS;
    int GL_QUAD_STRIP = GL2.GL_QUAD_STRIP;
}
