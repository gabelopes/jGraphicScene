package br.unisinos.jgraphicscene.graphics;

import com.jogamp.opengl.util.texture.TextureIO;

import java.io.File;

public class Texture {
    private com.jogamp.opengl.util.texture.Texture texture;
    private String filename;

    public Texture(String filename) {
        this.filename = filename;
    }

    public void read() {
        try {
            texture = TextureIO.newTexture(new File(this.filename), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public com.jogamp.opengl.util.texture.Texture getTexture() {
        return texture;
    }

    public boolean isBound() {
        return texture != null;
    }
}
