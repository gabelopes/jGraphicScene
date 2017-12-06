package br.unisinos.jgraphicscene.graphics.opengl;

import br.unisinos.jgraphicscene.graphics.Material;
import br.unisinos.jgraphicscene.graphics.Scene;
import br.unisinos.jgraphicscene.graphics.Texture;
import br.unisinos.jgraphicscene.graphics.composer.Chunk;
import br.unisinos.jgraphicscene.graphics.composer.Composer;
import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.utilities.OpenGL;
import br.unisinos.jgraphicscene.utilities.constants.Mode;
import br.unisinos.jgraphicscene.utilities.constants.Semantic;
import br.unisinos.jgraphicscene.utilities.structures.Ring;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.util.GLBuffers;
import com.jogamp.opengl.util.texture.TextureData;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.*;
import java.util.stream.Collectors;

import static br.unisinos.jgraphicscene.utilities.constants.Semantic.Attribute.NORMAL;
import static br.unisinos.jgraphicscene.utilities.constants.Semantic.Attribute.POSITION;
import static br.unisinos.jgraphicscene.utilities.constants.Semantic.Attribute.TEXCOORD;
import static com.jogamp.opengl.GL.GL_ARRAY_BUFFER;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import static com.jogamp.opengl.GL.GL_ELEMENT_ARRAY_BUFFER;
import static com.jogamp.opengl.GL.GL_FLOAT;
import static com.jogamp.opengl.GL.GL_STATIC_DRAW;
import static com.jogamp.opengl.GL.GL_UNSIGNED_INT;
import static com.jogamp.opengl.GL2ES2.GL_STREAM_DRAW;
import static com.jogamp.opengl.GL2ES3.*;
import static com.jogamp.opengl.GL2ES3.GL_FRONT_AND_BACK;
import static com.jogamp.opengl.GL2ES3.GL_LINEAR;
import static com.jogamp.opengl.GL2ES3.GL_LINEAR_MIPMAP_LINEAR;
import static com.jogamp.opengl.GL2ES3.GL_REPEAT;
import static com.jogamp.opengl.GL2ES3.GL_TEXTURE0;
import static com.jogamp.opengl.GL2ES3.GL_TEXTURE1;
import static com.jogamp.opengl.GL2ES3.GL_TEXTURE_2D;
import static com.jogamp.opengl.GL2ES3.GL_TEXTURE_MAG_FILTER;
import static com.jogamp.opengl.GL2ES3.GL_TEXTURE_MIN_FILTER;
import static com.jogamp.opengl.GL2ES3.GL_TEXTURE_WRAP_S;
import static com.jogamp.opengl.GL2ES3.GL_TEXTURE_WRAP_T;
import static com.jogamp.opengl.GL2ES3.GL_UNSIGNED_BYTE;
import static com.jogamp.opengl.GL2GL3.GL_FILL;

public class Drawer extends Ring<Scene> {
    private Shader shader;
    private Composer composer;

    private int hash;
    private IntBuffer buffers;

    private Map<String, IntBuffer> textureBuffers;

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
        this.textureBuffers = new HashMap<>();
    }

    public void initialize(GL4 gl) {
        Scene scene = this.get();

        this.composer = new Composer();

        if (scene != null) {
            this.hash = scene.hashCode();
            scene.draw(this.composer);

        }

        this.shader.initialize(gl);
        this.bindBuffers(gl);

        gl.glEnable(GL_DEPTH_TEST);
    }

    public void destroy(GL4 gl) {
        gl.glDeleteProgram(this.shader.getName());

        for (Chunk chunk : this.composer.getChunks()) {
            gl.glDeleteVertexArrays(1, chunk.getBufferVAO());
            gl.glDeleteVertexArrays(1, chunk.getBufferEBO());
        }

        this.unloadTextures(gl);
        gl.glDeleteBuffers(2, this.buffers);
    }

    private void bindBuffers(GL4 gl) {
        gl.glGenBuffers(2, this.buffers);

        this.bindVBO(gl);

        OpenGL.checkError(gl);
    }

    private void bindVBO(GL4 gl) {
        FloatBuffer vertexBuffer = GLBuffers.newDirectFloatBuffer(this.composer.getVertices());

        gl.glBindBuffer(GL_ARRAY_BUFFER, this.getVBO());
        gl.glBufferData(GL_ARRAY_BUFFER, vertexBuffer.capacity() * Float.BYTES, vertexBuffer, GL_STATIC_DRAW);
        gl.glBindBuffer(GL_ARRAY_BUFFER, 0);
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

                gl.glVertexAttribPointer(POSITION, positionThreshold, GL_FLOAT, false, stride, offset);
                gl.glEnableVertexAttribArray(POSITION);

                offset += positionThreshold * Float.BYTES;

                gl.glVertexAttribPointer(NORMAL, normalThreshold, GL_FLOAT, false, stride, offset);
                gl.glEnableVertexAttribArray(NORMAL);

                offset += normalThreshold * Float.BYTES;

                gl.glVertexAttribPointer(TEXCOORD, textureThreshold, GL_FLOAT, false, stride, offset);
                gl.glEnableVertexAttribArray(TEXCOORD);
            }

            gl.glBindBuffer(GL_ARRAY_BUFFER, 0);
        }

        gl.glBindVertexArray(0);

        OpenGL.checkError(gl);
    }

    private int getVBO() {
        return this.buffers.get(0);
    }

    private boolean hasChanges() {
        Scene scene = this.get();
        return scene != null && scene.hashCode() != this.hash;
    }

    public void draw(GL4 gl, Camera camera) {
        if (this.hasChanges()) {
            this.initialize(gl);
        }

        this.clearBuffers(gl, this.get().getBackground());

        gl.glUseProgram(this.shader.getName());

        this.setupCamera(gl, camera);
        this.setupLighting(gl);

        for (Chunk chunk : this.composer.getChunks()) {
            this.setupChunk(gl, chunk);
            this.bindChunk(gl, chunk);
            this.bindTextures(gl, chunk.getMaterial());

            gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL); // todo Is this needed?
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
    }

    private void setupMaterial(GL4 gl, Material material) {
        this.shader.setVector(gl, "light.ambient", material.getAmbientColor());
        this.shader.setVector(gl, "light.diffuse", material.getDiffuseColor());
        this.shader.setVector(gl, "light.specular", material.getSpecularColor());

        this.shader.setInt(gl, "material.diffuse", 0);
        this.shader.setInt(gl, "material.specular", 1);

        this.shader.setFloat(gl, "material.shininess", material.getShininess());
    }

    private void bindTextures(GL4 gl, Material material) {
        this.loadTextures(gl, material);

        if (material.getDiffuseMap() != null) {
            gl.glActiveTexture(GL_TEXTURE0);
            gl.glBindTexture(GL_TEXTURE_2D, material.getDiffuseMap().getId());
        }

        if (material.getSpecularMap() != null) {
            gl.glActiveTexture(GL_TEXTURE1);
            gl.glBindTexture(GL_TEXTURE_2D, material.getSpecularMap().getId());
        }
    }

    /**
     * Load textures guarantees no texture will be loaded twice by filtering null textures or data-null textures.
     * After loading a texture into the buffers, it then nullify its data, to free-up memory and prevent reloading.
     */
    private void loadTextures(GL4 gl, Material material) {
        List<Texture> textures = material.getTextures().stream().filter(t -> t != null && !t.isBound()).collect(Collectors.toList());

        IntBuffer textureIds = GLBuffers.newDirectIntBuffer(textures.size());
        gl.glGenTextures(textures.size(), textureIds);

        for (int i = 0; i < textures.size(); i++) {
            int id = textureIds.get(i);
            Texture texture = textures.get(i);
            TextureData data = texture.read(gl);
            Buffer buffer = data.getBuffer();

            gl.glBindTexture(GL_TEXTURE_2D, id);
            gl.glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
            gl.glTexImage2D(GL_TEXTURE_2D, 0, data.getInternalFormat(), data.getWidth(), data.getHeight(), data.getBorder(), data.getInternalFormat(), GL_UNSIGNED_BYTE, buffer);
            gl.glGenerateMipmap(GL_TEXTURE_2D);

            gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
            gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
            gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

            data.destroy();
            texture.setId(id);
        }

        this.textureBuffers.put(material.getName(), textureIds);
    }

    private void unloadTextures(GL4 gl) {
        for (IntBuffer buffer : this.textureBuffers.values()) {
            gl.glDeleteTextures(buffer.capacity(), buffer);
        }

        this.textureBuffers.clear();
    }
}
