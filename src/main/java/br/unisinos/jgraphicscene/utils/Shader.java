package br.unisinos.jgraphicscene.utils;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;

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

    public void init(GL3 gl) {
        ShaderCode vertexShader = ShaderCode.create(gl, GL_VERTEX_SHADER, this.getClass(), SHADERS_FOLDER, null, this.vertex, "vert", null, true);
        ShaderCode fragmentShader = ShaderCode.create(gl, GL_FRAGMENT_SHADER, this.getClass(), SHADERS_FOLDER, null, this.fragment, "frag", null, true);

        ShaderProgram shaderProgram = new ShaderProgram();

        shaderProgram.add(vertexShader);
        shaderProgram.add(fragmentShader);

        shaderProgram.init(gl);

        this.id = shaderProgram.program();

        shaderProgram.link(gl, System.err);
    }

    public int getId() {
        return id;
    }
}
