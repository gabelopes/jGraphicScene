package br.unisinos.jgraphicscene.utilities.io;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.math.Matrix4;
import com.jogamp.opengl.util.GLBuffers;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;
import org.joml.Matrix4f;

import java.nio.FloatBuffer;

import static com.jogamp.opengl.GL2ES2.GL_FRAGMENT_SHADER;
import static com.jogamp.opengl.GL2ES2.GL_VERTEX_SHADER;

public class Shader {
    private static final String SHADERS_FOLDER = "shaders";

    private int id;
    private String vertex, fragment;

    public Shader() {
        this( "default");
    }

    public Shader(String name) {
        this(name, name);
    }

    public Shader(String vertex, String fragment) {
        this.vertex = vertex;
        this.fragment = fragment;
    }

    public void initialize(GL4 gl) {
        ShaderCode vertexShader = ShaderCode.create(gl, GL_VERTEX_SHADER, this.getClass(), SHADERS_FOLDER, null, this.vertex, "vert", null, true);
        ShaderCode fragmentShader = ShaderCode.create(gl, GL_FRAGMENT_SHADER, this.getClass(), SHADERS_FOLDER, null, this.fragment, "frag", null, true);

        ShaderProgram shaderProgram = new ShaderProgram();

        shaderProgram.add(vertexShader);
        shaderProgram.add(fragmentShader);

        shaderProgram.init(gl);

        this.id = shaderProgram.program();

        shaderProgram.link(gl, System.err);
    }

    public void setMatrix(GL4 gl, String name, Matrix4 matrix) {
        this.setMatrix(gl, name, GLBuffers.newDirectFloatBuffer(matrix.getMatrix()));
    }

    public void setMatrix(GL4 gl, String name, Matrix4f matrix) {
        this.setMatrix(gl, name, matrix.get(GLBuffers.newDirectFloatBuffer(16)));
    }

    private void setMatrix(GL4 gl, String name, FloatBuffer matrix) {
        int matrixId = gl.glGetUniformLocation(this.getName(), name);
        gl.glUniformMatrix4fv(matrixId, 1, false, matrix);
    }

    public int getName() {
        return id;
    }
}
