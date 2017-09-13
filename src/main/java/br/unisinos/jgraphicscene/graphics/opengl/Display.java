package br.unisinos.jgraphicscene.graphics.opengl;

import br.unisinos.jgraphicscene.graphics.Composition;
import br.unisinos.jgraphicscene.graphics.Chunk;
import br.unisinos.jgraphicscene.graphics.Drawable;
import br.unisinos.jgraphicscene.shapes.units.Color;
import br.unisinos.jgraphicscene.utils.constants.Buffers;
import br.unisinos.jgraphicscene.utils.Shader;
import br.unisinos.jgraphicscene.utils.constants.Colors;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.GLBuffers;
import br.unisinos.jgraphicscene.utils.constants.Semantic;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static com.jogamp.opengl.GL.GL_ARRAY_BUFFER;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import static com.jogamp.opengl.GL.GL_ELEMENT_ARRAY_BUFFER;
import static com.jogamp.opengl.GL.GL_FLOAT;
import static com.jogamp.opengl.GL.GL_STATIC_DRAW;
import static com.jogamp.opengl.GL2ES3.*;

public class Display implements GLEventListener, KeyListener {
    private GLWindow window;
    private Animator animator;

    private IntBuffer buffers;
    private IntBuffer vertexArray;

    private String title;
    private int width, height;
    private Color background;

    private List<Drawable> drawables;
    private int currentDrawable;
    private Composition currentComposition;

    private Shader shader;

    private boolean modified;

    public Display(String title, int width, int height) {
        this(title, width, height, Colors.TEAL, new ArrayList<>(), new Shader());
    }

    public Display(String title, int width, int height, Color background) {
        this(title, width, height, background, new ArrayList<>(), new Shader());
    }

    public Display(String title, int width, int height, Color background, List<Drawable> drawables, Shader shader) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.background = background;
        this.drawables = drawables;
        this.shader = shader;
        this.currentDrawable = drawables.size() > 0 ? 0 : -1;
        this.buffers = GLBuffers.newDirectIntBuffer(Buffers.SIZE);
        this.vertexArray = GLBuffers.newDirectIntBuffer(1);
    }

    public int add(Drawable drawable) {
        this.drawables.add(drawable);

        if (this.currentDrawable == -1) {
            this.currentDrawable++;
        }

        return this.drawables.size() - 1;
    }

    public void next() {
        if (this.drawables.size() > 0) {
            this.currentDrawable = (this.currentDrawable + 1) % this.drawables.size();
        }

        this.modify();
    }

    public void previous() {
        if (this.drawables.size() > 0) {
            this.currentDrawable = this.currentDrawable - 1;

            if (this.currentDrawable == -1) {
                this.currentDrawable = this.drawables.size() - 1;
            }
        }

        this.modify();
    }

    private void modify() {
        this.modified = true;
    }

    private Drawable getCurrentDrawable() {
        if (this.drawables.size() > 0 && this.currentDrawable >= 0) {
            return this.drawables.get(this.currentDrawable);
        }

        return null;
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public void open() {
        GLProfile glProfile = GLProfile.get(GLProfile.GL4);
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);

        this.window = GLWindow.create(glCapabilities);

        this.window.setTitle(this.title);
        this.window.setSize(this.width, this.height);

        this.window.setVisible(true);

        this.window.addGLEventListener(this);
        this.window.addKeyListener(this);

        this.animator = new Animator(this.window);
        this.animator.start();

        this.window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDestroyed(WindowEvent e) {
                animator.stop();
                System.exit(1);
            }
        });
    }

    public void close() {
        new Thread(() -> this.window.destroy()).start();
    }

    @Override
    public void keyPressed(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                this.close();
                break;
            case KeyEvent.VK_LEFT:
                this.previous();
                break;
            case KeyEvent.VK_RIGHT:
                this.next();
            default:
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        return;
    }

    private void bindBuffers(GL4 gl, Composition composition) {
        FloatBuffer vertexBuffer = GLBuffers.newDirectFloatBuffer(composition.getVertices());
        IntBuffer elementBuffer = GLBuffers.newDirectIntBuffer(composition.getElements());

        gl.glGenBuffers(Buffers.SIZE, this.buffers);

        gl.glBindBuffer(GL_ARRAY_BUFFER, this.buffers.get(Buffers.VERTEX));
        gl.glBufferData(GL_ARRAY_BUFFER, vertexBuffer.capacity() * Float.BYTES, vertexBuffer, GL_STATIC_DRAW);
        gl.glBindBuffer(GL_ARRAY_BUFFER, 0);

        gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.buffers.get(Buffers.ELEMENT));
        gl.glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer.capacity() * Integer.BYTES, elementBuffer, GL_STATIC_DRAW);
        gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        checkError(gl, "Buffer binding");
    }

    private void initVertexArray(GL4 gl) {
        gl.glGenVertexArrays(1, this.vertexArray);
        gl.glBindVertexArray(this.vertexArray.get(0));

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
            gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, buffers.get(Buffers.ELEMENT));
        }

        gl.glBindVertexArray(0);

        checkError(gl, "VAO initialization");
    }

    @Override
    public void init(GLAutoDrawable autoDrawable) {
        Drawable drawable = this.getCurrentDrawable();

        if (drawable != null) {
            this.currentComposition = drawable.compose();
            GL4 gl = autoDrawable.getGL().getGL4();

            bindBuffers(gl, this.currentComposition);
            initVertexArray(gl);
            this.shader.init(gl);

            gl.glEnable(GL_DEPTH_TEST);
        }
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();

        gl.glDeleteProgram(shader.getId());
        gl.glDeleteVertexArrays(1, this.vertexArray);
        gl.glDeleteBuffers(Buffers.SIZE, this.buffers);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        if (this.modified) {
            this.init(drawable);
            this.modified = false;
        }

        GL4 gl = drawable.getGL().getGL4();


        gl.glClearBufferfv(GL_COLOR, 0, background.buffer());
        gl.glClearBufferfv(GL_DEPTH, 0, GLBuffers.newDirectFloatBuffer(1).put(0, 1f));

        gl.glUseProgram(this.shader.getId());
        gl.glBindVertexArray(this.vertexArray.get(0));

        int offset = 0;

        for (Chunk chunk : this.currentComposition.getChunks()) {
            gl.glDrawElements(chunk.getDrawing(), this.currentComposition.getElements().length, GL_UNSIGNED_INT, offset);
            offset += chunk.getSize();
        }

        gl.glUseProgram(0);
        gl.glBindVertexArray(0);

        checkError(gl, "Display");
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        return;
    }

    private void checkError(GL gl, String location) {
        int error = gl.glGetError();

        if (error != GL_NO_ERROR) {
            String errorType = "UNKNOWN";

            switch (error) {
                case GL_INVALID_ENUM:
                    errorType = "GL_INVALID_ENUM";
                    break;
                case GL_INVALID_VALUE:
                    errorType = "GL_INVALID_VALUE";
                    break;
                case GL_INVALID_OPERATION:
                    errorType = "GL_INVALID_OPERATION";
                    break;
                case GL_INVALID_FRAMEBUFFER_OPERATION:
                    errorType = "GL_INVALID_FRAMEBUFFER_OPERATION";
                    break;
                case GL_OUT_OF_MEMORY:
                    errorType = "GL_OUT_OF_MEMORY";
                    break;
                default:
            }

            throw new Error("OpenGL Error(" + errorType + "): " + location);
        }
    }
}
