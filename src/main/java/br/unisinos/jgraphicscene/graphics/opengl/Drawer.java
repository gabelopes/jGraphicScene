package br.unisinos.jgraphicscene.graphics.opengl;

import br.unisinos.jgraphicscene.graphics.Lighting;
import br.unisinos.jgraphicscene.graphics.Scene;
import br.unisinos.jgraphicscene.graphics.composer.Chunk;
import br.unisinos.jgraphicscene.graphics.composer.Composer;
import br.unisinos.jgraphicscene.obj.Material;
import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.utilities.OpenGL;
import br.unisinos.jgraphicscene.utilities.constants.Colors;
import br.unisinos.jgraphicscene.utilities.constants.Mode;
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

public class Drawer extends Ring<Scene> {
    private Shader shader;
    private Composer composer;

    private int hash;
    private IntBuffer buffers;

    public Drawer() {
        this(new ArrayList<>());
    }

    public Drawer(Scene... scenes) {
        this(new ArrayList<>());
        Collections.addAll(this.list, scenes);
    }

    public Drawer(List<Scene> scenes) {
        this.list = scenes;
        this.shader = new Shader("lighting");
        this.buffers = GLBuffers.newDirectIntBuffer(2);
    }

    public void initialize(GL4 gl) {
        Scene scene = this.get();

        this.composer = new Composer();

        if (scene != null) {
            this.hash = scene.hashCode();
            scene.draw(this.composer);
        }

        this.bindBuffers(gl);
        this.shader.initialize(gl);

        gl.glEnable(GL_DEPTH_TEST);
    }

    public void destroy(GL4 gl) {
        gl.glDeleteProgram(this.shader.getName());

        for (Chunk chunk : this.composer.getChunks()) {
            gl.glDeleteVertexArrays(1, chunk.getBufferVAO());
            gl.glDeleteVertexArrays(1, chunk.getBufferEBO());
        }

        gl.glDeleteBuffers(2, this.buffers);
    }

    private void bindBuffers(GL4 gl) {
        gl.glGenBuffers(2, this.buffers);

        this.bindVBO(gl);
        this.bindMatrices(gl);

        OpenGL.checkError(gl);
    }

    private void bindVBO(GL4 gl) {
        FloatBuffer vertexBuffer = GLBuffers.newDirectFloatBuffer(this.composer.getVertices());

        gl.glBindBuffer(GL_ARRAY_BUFFER, this.getVBO());
        gl.glBufferData(GL_ARRAY_BUFFER, vertexBuffer.capacity() * Float.BYTES, vertexBuffer, GL_STATIC_DRAW);
        gl.glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private void bindMatrices(GL4 gl) {
        gl.glBindBuffer(GL_UNIFORM_BUFFER, this.getMatrices());
        gl.glBufferData(GL_UNIFORM_BUFFER, 16 * Float.BYTES * 2, null, GL_STREAM_DRAW);
        gl.glBindBuffer(GL_UNIFORM_BUFFER, 0);

        gl.glBindBufferBase(GL_UNIFORM_BUFFER, Semantic.Uniform.GLOBAL_MATRICES, this.getMatrices());
    }

    private void bindChunk(GL4 gl, Chunk chunk) {
        if (!chunk.hasVAO()) {
            this.configureVAO(gl, chunk);
        }

        if (!chunk.hasEBO()) {
            this.configureEBO(gl, chunk);
        }

        gl.glBindVertexArray(chunk.getVAO());
        gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, chunk.getEBO());

        OpenGL.checkError(gl);
    }

    private void configureEBO(GL4 gl, Chunk chunk) {
        gl.glGenBuffers(1, chunk.getBufferEBO());

        IntBuffer elementBuffer = GLBuffers.newDirectIntBuffer(chunk.getElementsArray());

        gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, chunk.getEBO());
        gl.glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer.capacity() * Integer.BYTES, elementBuffer, GL_STATIC_DRAW);
        gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    private void configureVAO(GL4 gl, Chunk chunk) {
        gl.glGenVertexArrays(1, chunk.getBufferVAO());
        gl.glBindVertexArray(chunk.getVAO());
        OpenGL.checkError(gl);

        {
            gl.glBindBuffer(GL_ARRAY_BUFFER, this.getVBO());

            {
                int positionThreshold = 3;
                int normalThreshold = 3;
                int textureThreshold = 2;
                int stride = (positionThreshold + normalThreshold + textureThreshold) * Float.BYTES;
                int offset = 0;

                gl.glEnableVertexAttribArray(Semantic.Attribute.POSITION);
                gl.glVertexAttribPointer(Semantic.Attribute.POSITION, positionThreshold, GL_FLOAT, false, stride, offset);

                offset += positionThreshold * Float.BYTES;

                gl.glEnableVertexAttribArray(Semantic.Attribute.COLOR);
                gl.glVertexAttribPointer(Semantic.Attribute.COLOR, normalThreshold, GL_FLOAT, false, stride, offset);

                offset += normalThreshold * Float.BYTES;

                gl.glEnableVertexAttribArray(Semantic.Attribute.TEXCOORD);
                gl.glVertexAttribPointer(Semantic.Attribute.TEXCOORD, textureThreshold, GL_FLOAT, false, stride, offset);
            }

            gl.glBindBuffer(GL_ARRAY_BUFFER, 0);
        }

        gl.glBindVertexArray(0);

        OpenGL.checkError(gl);
    }

    private int getVBO() {
        return this.buffers.get(0);
    }

    private int getMatrices() {
        return this.buffers.get(1);
    }

    private boolean hasChanges() {
        Scene scene = this.get();
        return scene != null && scene.hashCode() != this.hash;
    }

    public void draw(GL4 gl, Camera camera, Color background) {
        if (this.hasChanges()) {
            this.initialize(gl);
        }

        gl.glClearBufferfv(GL_COLOR, 0, background.buffer());
        gl.glClearBufferfv(GL_DEPTH, 0, GLBuffers.newDirectFloatBuffer(1).put(0, 1f));

        gl.glUseProgram(this.shader.getName());

        this.configureCamera(gl, camera);
        this.configureLighting(gl, this.shader, this.get().getLighting());

        for (Chunk chunk : this.composer.getChunks()) {
            this.bindChunk(gl, chunk);
            this.configureChunk(gl, chunk);
            gl.glDrawElements(Mode.GL_TRIANGLES, chunk.getSize(), GL_UNSIGNED_INT, 0);
        }

        gl.glUseProgram(0);
        gl.glBindVertexArray(0);

        OpenGL.checkError(gl);
    }

    private void configureChunk(GL4 gl, Chunk chunk) {
        this.configureMaterial(gl, chunk.getMaterial());
        this.shader.setMatrix(gl,"model", chunk.getTransformation().getMatrix());
    }

    private void configureMaterial(GL4 gl, Material material) {

    }

    private void configureCamera(GL4 gl, Camera camera) {
        camera.updateDelta();

        this.shader.setMatrix(gl,"projection", camera.getProjection());
        this.shader.setMatrix(gl,"view", camera.getView());
        this.shader.setVector(gl, "viewPosition", camera.getPosition());
    }

    private void configureLighting(GL4 gl, Shader shader, Lighting lighting) {
        shader.setVector(gl, "lightColor", lighting.getColor());
        shader.setVector(gl, "lightPosition", lighting.getPosition());
    }
}
