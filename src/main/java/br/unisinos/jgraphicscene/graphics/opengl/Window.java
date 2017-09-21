package br.unisinos.jgraphicscene.graphics.opengl;

import br.unisinos.jgraphicscene.shapes.solids.Cube;
import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.utilities.constants.Colors;
import br.unisinos.jgraphicscene.utilities.constants.Movement;
import br.unisinos.jgraphicscene.utilities.io.Shader;
import br.unisinos.jgraphicscene.utilities.structures.Dispatcher;
import com.jogamp.newt.event.*;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.Animator;
import org.joml.Vector3f;

public class Window implements GLEventListener, KeyListener, MouseListener {
    private static final Vector3f INITIAL_POSITION = new Vector3f(0, 0, 3);

    private String title;

    private int width;
    private int height;

    private Color background;

    private GLWindow window;
    private Animator animator;

    private Camera camera;
    private Drawer drawer;

    private Dispatcher<Short, KeyEvent> keyEvents;

    public Window(String title, int width, int height) {
        this(title, width, height, Colors.PETROL);
    }

    public Window(String title, int width, int height, Color background) {
        this(title, width, height, background, new Drawer());
    }

    public Window(String title, int width, int height, String shader) {
        this(title, width, height, Colors.PETROL, new Drawer(new Shader(shader)));
    }

    public Window(String title, int width, int height, Color background, String shader) {
        this(title, width, height, background, new Drawer(new Shader(shader)));
    }


    public Window(String title, int width, int height, Drawer drawer) {
        this(title, width, height, Colors.PETROL, drawer);
    }

    public Window(String title, int width, int height, Color background, Drawer drawer) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.background = background;

        this.camera = new Camera(this, INITIAL_POSITION);
        this.drawer = drawer == null ? new Drawer() : drawer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.window.setTitle(this.title);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        this.window.setSize(this.width, this.height);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        this.window.setSize(this.width, this.height);
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public Drawer getDrawer() {
        return drawer;
    }

    public void open() {
        if (this.window != null) {
            return;
        }

        GLProfile glProfile = GLProfile.get(GLProfile.GL4);
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);

        this.window = GLWindow.create(glCapabilities);

        this.window.setTitle(this.title);
        this.window.setSize(this.width, this.height);

        this.window.setVisible(true);

        this.window.addGLEventListener(this);
        this.window.addKeyListener(this);
        this.window.addMouseListener(this);

        this.animator = new Animator(this.window);
        this.animator.start();

        this.window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDestroyed(WindowEvent e) {
                animator.stop();
            }
        });
    }

    public void close() {
        new Thread(() -> this.window.destroy()).start();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        this.initDispatchers();
        this.drawer.initialize(drawable.getGL().getGL4());
    }

    public void initDispatchers() {
        keyEvents = new Dispatcher<>();

        keyEvents.attach(KeyEvent.VK_ESCAPE, e -> this.close());

        keyEvents.attach(KeyEvent.VK_LEFT, e -> this.getDrawer().previous());
        keyEvents.attach(KeyEvent.VK_RIGHT, e -> this.getDrawer().next());

        keyEvents.attach(KeyEvent.VK_W, e -> this.camera.processKeyboard(Movement.FORWARD));
        keyEvents.attach(KeyEvent.VK_A, e -> this.camera.processKeyboard(Movement.LEFT));
        keyEvents.attach(KeyEvent.VK_S, e -> this.camera.processKeyboard(Movement.BACK));
        keyEvents.attach(KeyEvent.VK_D, e -> this.camera.processKeyboard(Movement.RIGHT));

        keyEvents.attach(KeyEvent.VK_SPACE, e -> this.camera.reset(INITIAL_POSITION));
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        this.getDrawer().draw(drawable.getGL().getGL4(), this.camera, this.background);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        return;
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        this.drawer.destroy(drawable.getGL().getGL4());
        this.window = null;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        keyEvents.dispatch(event.getKeyCode(), event);
    }

    @Override
    public void mouseMoved(MouseEvent event) {
        this.camera.processMovement(event.getX(), event.getY(), true);
    }

    @Override
    public void mouseWheelMoved(MouseEvent event) {
        this.camera.processScroll(event.getRotation()[1]);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }
}
