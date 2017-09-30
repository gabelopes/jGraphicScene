package br.unisinos.jgraphicscene.graphics.opengl;

import br.unisinos.jgraphicscene.decorators.Drawable;
import br.unisinos.jgraphicscene.graphics.composer.Chunk;
import br.unisinos.jgraphicscene.graphics.composer.Composer;
import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.utilities.OpenGL;
import br.unisinos.jgraphicscene.utilities.constants.Buffers;
import br.unisinos.jgraphicscene.utilities.constants.Semantic;
import br.unisinos.jgraphicscene.utilities.io.Shader;
import br.unisinos.jgraphicscene.utilities.structures.Ring;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.util.GLBuffers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.jogamp.opengl.GL.GL_ARRAY_BUFFER;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import static com.jogamp.opengl.GL.GL_ELEMENT_ARRAY_BUFFER;
import static com.jogamp.opengl.GL.GL_FLOAT;
import static com.jogamp.opengl.GL.GL_STATIC_DRAW;
import static com.jogamp.opengl.GL.GL_UNSIGNED_INT;
import static com.jogamp.opengl.GL2ES2.GL_STREAM_DRAW;
import static com.jogamp.opengl.GL2ES3.*;

public class Drawer extends Ring<Drawable> {
    private Shader defaultShader;

    private IntBuffer buffers;
    private IntBuffer VAO;

    private int lastHashCode;

    private Composer composer;

    public Drawer() {
        this(new Shader());
    }

    public Drawer(Shader defaultShader) {
        this(defaultShader, new ArrayList<>());
    }

    public Drawer(Drawable... drawables) {
        this(new Shader(), drawables);
    }

    public Drawer(Shader defaultShader, Drawable... drawables) {
        this.list = new ArrayList<>();
        Collections.addAll(this.list, drawables);

        this.defaultShader = defaultShader;
        this.buffers = GLBuffers.newDirectIntBuffer(Buffers.SIZE);
        this.VAO = GLBuffers.newDirectIntBuffer(1);
    }

    public Drawer(List<Drawable> drawables) {
        this(new Shader(), drawables);
    }

    public Drawer(Shader defaultShader, List<Drawable> drawables) {
        this.list = drawables;
        this.defaultShader = defaultShader;
        this.buffers = GLBuffers.newDirectIntBuffer(Buffers.SIZE);
        this.VAO = GLBuffers.newDirectIntBuffer(1);
    }

    public void initialize(GL4 gl) {
        Drawable drawable = this.get();

        this.lastHashCode = drawable.hashCode();

        this.composer = new Composer();
        drawable.draw(this.composer);

        this.bindBuffers(gl);
        this.initializeVAO(gl);

        this.defaultShader.initialize(gl);

        gl.glEnable(GL_DEPTH_TEST);
    }

    public void destroy(GL4 gl) {
        gl.glDeleteProgram(this.defaultShader.getName());
        gl.glDeleteVertexArrays(1, this.VAO);
        gl.glDeleteBuffers(Buffers.SIZE, this.buffers);
    }

    private void bindBuffers(GL4 gl) {
        FloatBuffer vertexBuffer = GLBuffers.newDirectFloatBuffer(this.composer.getVertices());
        IntBuffer elementBuffer = GLBuffers.newDirectIntBuffer(this.composer.getElements());

        gl.glGenBuffers(Buffers.SIZE, this.buffers);

        gl.glBindBuffer(GL_ARRAY_BUFFER, this.buffers.get(Buffers.VERTEX));
        gl.glBufferData(GL_ARRAY_BUFFER, vertexBuffer.capacity() * Float.BYTES, vertexBuffer, GL_STATIC_DRAW);
        gl.glBindBuffer(GL_ARRAY_BUFFER, 0);

        gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.buffers.get(Buffers.ELEMENT));
        gl.glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer.capacity() * Integer.BYTES, elementBuffer, GL_STATIC_DRAW);
        gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        gl.glBindBuffer(GL_UNIFORM_BUFFER, buffers.get(Buffers.GLOBAL_MATRICES));
        gl.glBufferData(GL_UNIFORM_BUFFER, 16 * Float.BYTES * 2, null, GL_STREAM_DRAW);
        gl.glBindBuffer(GL_UNIFORM_BUFFER, 0);

        gl.glBindBufferBase(GL_UNIFORM_BUFFER, Semantic.Uniform.GLOBAL_MATRICES, this.buffers.get(Buffers.GLOBAL_MATRICES));

        OpenGL.checkError(gl);
    }

    private void initializeVAO(GL4 gl) {
        gl.glGenVertexArrays(1, this.VAO);
        gl.glBindVertexArray(this.VAO.get(0));

        {
            gl.glBindBuffer(GL_ARRAY_BUFFER, this.buffers.get(Buffers.VERTEX));

            {
                int positionThreshold = 3;
                int colorThreshold = 4;
                int stride = (positionThreshold + colorThreshold) * Float.BYTES;
                int offset = 0;

                gl.glEnableVertexAttribArray(Semantic.Attribute.POSITION);
                gl.glVertexAttribPointer(Semantic.Attribute.POSITION, positionThreshold, GL_FLOAT, false, stride, offset);

                offset = positionThreshold * Float.BYTES;

                gl.glEnableVertexAttribArray(Semantic.Attribute.COLOR);
                gl.glVertexAttribPointer(Semantic.Attribute.COLOR, colorThreshold, GL_FLOAT, false, stride, offset);
            }

            gl.glBindBuffer(GL_ARRAY_BUFFER, 0);
            gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.buffers.get(Buffers.ELEMENT));
        }

        gl.glBindVertexArray(0);

        OpenGL.checkError(gl);
    }

    private boolean hasChanges() {
        return this.get().hashCode() != this.lastHashCode;
    }

    public void draw(GL4 gl, Camera camera, Color background) {
        if (this.hasChanges()) {
            this.initialize(gl);
        }

        camera.updateDelta();

        gl.glClearBufferfv(GL_COLOR, 0, background.getBuffer());
        gl.glClearBufferfv(GL_DEPTH, 0, GLBuffers.newDirectFloatBuffer(1).put(0, 1f));

        gl.glUseProgram(this.defaultShader.getName());
        gl.glBindVertexArray(this.VAO.get(0));

        this.defaultShader.setMatrix(gl,"projection", camera.getProjection());
        this.defaultShader.setMatrix(gl,"view", camera.getView());

        int offset = 0;

        for (Chunk chunk : this.composer.getChunks()) {
            Shader shader = this.defaultShader;

            if (chunk.hasShader()) {
                shader = new Shader(chunk.getShader());
                shader.initialize(gl);

                gl.glUseProgram(shader.getName());

                shader.setMatrix(gl,"projection", camera.getProjection());
                shader.setMatrix(gl,"view", camera.getView());
            }

            shader.setMatrix(gl, "model", chunk.getTransformation().getMatrix());

            gl.glDrawElements(chunk.getMode(), this.composer.getElements().length, GL_UNSIGNED_INT, offset);

            offset += chunk.getSize();
        }

        gl.glUseProgram(0);
        gl.glBindVertexArray(0);

        OpenGL.checkError(gl);
    }
}
