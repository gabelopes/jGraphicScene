package br.unisinos.jgraphicscene.graphics;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.File;

public class Texture {
    private Integer id;
    private String filename;

    public Texture(String filename) {
        this.filename = filename;
    }

    public TextureData read(GL4 gl) {
        try {
            return TextureIO.newTextureData(gl.getGLProfile(), new File(this.filename), false, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isBound() {
        return id != null;
    }
}
