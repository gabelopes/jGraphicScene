package br.unisinos.jgraphicscene.graphics.opengl;

import br.unisinos.jgraphicscene.graphics.Scene;
import br.unisinos.jgraphicscene.graphics.composer.Chunk;
import br.unisinos.jgraphicscene.graphics.composer.Composer;
import br.unisinos.jgraphicscene.obj.Material;
import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.utilities.OpenGL;
import br.unisinos.jgraphicscene.utilities.constants.Mode;
import br.unisinos.jgraphicscene.utilities.constants.Semantic;
import br.unisinos.jgraphicscene.utilities.io.Shader;
import br.unisinos.jgraphicscene.utilities.structures.Ring;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.util.GLBuffers;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.File;
import java.io.IOException;
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
        this.shader = new Shader("materials");
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
        this.loadTextures(gl);

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

    private void loadTextures(GL4 gl) {
        for (Chunk chunk : this.composer.getChunks()) {
            for (String map : chunk.getMaterial().getMaps()) {
                if (map == null) {
                    continue;
                }

                try {
                    Texture texture = TextureIO.newTexture(new File(map), false);

                    texture.setTexParameteri(gl, GL_TEXTURE_WRAP_S, GL_REPEAT);
                    texture.setTexParameteri(gl, GL_TEXTURE_WRAP_T, GL_REPEAT);
                    texture.setTexParameteri(gl, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
                    texture.setTexParameteri(gl, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

                    chunk.getTextures().put(map, texture);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
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

        IntBuffer elementBuffer = GLBuffers.newDirectIntBuffer(chunk.getElements());

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

                gl.glEnableVertexAttribArray(Semantic.Attribute.NORMAL);
                gl.glVertexAttribPointer(Semantic.Attribute.NORMAL, normalThreshold, GL_FLOAT, false, stride, offset);

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

        this.clearBuffers(gl, background);

        gl.glUseProgram(this.shader.getName());

        this.setupCamera(gl, camera);
        this.setupLighting(gl);

        for (Chunk chunk : this.composer.getChunks()) {
            this.bindChunk(gl, chunk);
            this.setupChunk(gl, chunk);
            gl.glDrawElements(Mode.GL_TRIANGLES, chunk.getSize(), GL_UNSIGNED_INT, 0);
        }

        gl.glUseProgram(0);
        gl.glBindVertexArray(0);

        OpenGL.checkError(gl);
    }

    private void clearBuffers(GL4 gl, Color background) {
        gl.glClearBufferfv(GL_COLOR, 0, background.buffer());
        gl.glClearBufferfv(GL_DEPTH, 0, GLBuffers.newDirectFloatBuffer(1).put(0, 1f));
    }

    private void setupCamera(GL4 gl, Camera camera) {
        camera.updateDelta();

        this.shader.setMatrix(gl,"projection", camera.getProjection());
        this.shader.setMatrix(gl,"view", camera.getView());
        this.shader.setVector(gl, "viewPosition", camera.getPosition());
    }

    private void setupLighting(GL4 gl) {
        this.shader.setVector(gl, "light.position", this.get().getLighting().getPosition());
    }

    private void setupChunk(GL4 gl, Chunk chunk) {
        this.setupMaterial(gl, chunk.getMaterial());
        this.shader.setMatrix(gl,"model", chunk.getTransformation().getMatrix());
        this.bindTextures(gl, chunk);
    }

    private void setupMaterial(GL4 gl, Material material) {
        this.shader.setVector(gl, "light.ambient", material.getAmbientColor());
        this.shader.setVector(gl, "light.diffuse", material.getDiffuseColor());
        this.shader.setVector(gl, "light.specular", material.getSpecularColor());

        this.shader.setInt(gl, "material.diffuse", 0);
        this.shader.setInt(gl, "material.specular", 1);

        this.shader.setFloat(gl, "material.shininess", material.getShininess());
    }

    private void bindTextures(GL4 gl, Chunk chunk) {
//        Texture ambientMap = chunk.getTexture(chunk.getMaterial().getAmbientMap());
//
//        if (ambientMap != null) {
//            ambientMap.enable(gl);
//        }

        Texture diffuseMap = chunk.getTexture(chunk.getMaterial().getDiffuseMap());

        if (diffuseMap != null) {
            diffuseMap.enable(gl);
            gl.glActiveTexture(GL_TEXTURE0);
            diffuseMap.bind(gl);
        }

        Texture specularMap = chunk.getTexture(chunk.getMaterial().getSpecularMap());

        if (specularMap != null) {
            specularMap.enable(gl);
            gl.glActiveTexture(GL_TEXTURE1);
            specularMap.bind(gl);
        }

    }
}
