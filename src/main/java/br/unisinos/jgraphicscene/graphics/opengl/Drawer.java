package br.unisinos.jgraphicscene.graphics.opengl;

import br.unisinos.jgraphicscene.decorators.Drawable;
import br.unisinos.jgraphicscene.decorators.Lightable;
import br.unisinos.jgraphicscene.decorators.Transformable;
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
    private IntBuffer lightingVAO;

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

        this.composer = new Composer();

        if (drawable != null) {
            this.lastHashCode = drawable.hashCode();
            drawable.draw(this.composer);
        }

        this.bindBuffers(gl);
        this.initializeVAO(gl);

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
                int colorThreshold = 3;
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
        Drawable drawable = this.get();
        return drawable != null && drawable.hashCode() != this.lastHashCode;
    }

    public void draw(GL4 gl, Camera camera, Color background) {
        if (this.hasChanges()) {
            this.initialize(gl);
        }

        Shader shader = this.isLightable() ? new Shader("lighting") : this.defaultShader;
        shader.initialize(gl);

        camera.updateDelta();

        gl.glClearBufferfv(GL_COLOR, 0, background.buffer());
        gl.glClearBufferfv(GL_DEPTH, 0, GLBuffers.newDirectFloatBuffer(1).put(0, 1f));

        gl.glUseProgram(shader.getName());
        gl.glBindVertexArray(this.VAO.get(0));

        this.configureShader(gl, shader, camera);

        int offset = 0;

        for (Chunk chunk : this.composer.getChunks()) {
            gl.glDrawElements(chunk.getMode(), chunk.getSize(), GL_UNSIGNED_INT, offset);
            offset += chunk.getSize();
        }

        gl.glUseProgram(0);
        gl.glBindVertexArray(0);

        OpenGL.checkError(gl);
    }

    private void configureShader(GL4 gl, Shader shader, Camera camera) {
        shader.setMatrix(gl,"projection", camera.getProjection());
        shader.setMatrix(gl,"view", camera.getView());

        if (this.get() instanceof Transformable) {
            shader.setMatrix(gl,"model", ((Transformable) this.get()).getTransformation().getMatrix());
        }

        if (this.isLightable()) {
            Lightable drawable = (Lightable) this.get();
            shader.setVector(gl, "objectColor", drawable.getColor());
            shader.setVector(gl, "lightColor", drawable.getLighting().getColor());
            shader.setVector(gl, "lightPosition", drawable.getLighting().getPosition());
            shader.setVector(gl, "viewPosition", camera.getPosition());
        }
    }

    private boolean isLightable() {
        return this.get() instanceof Lightable && ((Lightable) this.get()).applyLighting();
    }
}
