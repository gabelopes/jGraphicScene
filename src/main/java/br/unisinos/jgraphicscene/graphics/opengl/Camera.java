package br.unisinos.jgraphicscene.graphics.opengl;

import br.unisinos.jgraphicscene.utilities.Time;
import br.unisinos.jgraphicscene.utilities.constants.Movement;
import com.jogamp.opengl.math.Matrix4;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
    private static final float YAW = -90;
    private static final float PITCH = 0;
    private static final float SPEED = 2.5f;
    private static final float SENSITIVITY = 0.1f;
    private static final float ZOOM = 45;

    private static final Vector3f FRONT = new Vector3f(0, 0, -1);
    private static final Vector3f GLOBAL_UP = new Vector3f(0, 1, 0);

    private Vector3f position;

    private Vector3f front;
    private Vector3f up;
    private Vector3f right;

    private Vector3f globalUp;

    private float yaw;
    private float pitch;

    private float speed;
    private float sensitivity;
    private float zoom;

    private float delta;
    private float frame;

    private float mouseX;
    private float mouseY;

    private Window window;

    public Camera(Window window) {
        this(window, new Vector3f());
    }

    public Camera(Window window, Vector3f position) {
        this.window = window;
        this.reset(position, GLOBAL_UP);
    }

    public Camera(Window window, Vector3f position, Vector3f globalUp) {
        this.window = window;
        this.reset(position, globalUp);
    }

    public void reset() {
        this.reset(new Vector3f(), GLOBAL_UP);
    }

    public void reset(Vector3f position) {
        this.reset(position, GLOBAL_UP);
    }

    public void reset(Vector3f position, Vector3f globalUp) {
        this.position = position == null ? new Vector3f() : position;
        this.front = FRONT;
        this.up = new Vector3f();
        this.right = new Vector3f();

        this.globalUp = globalUp == null ? new Vector3f() : globalUp;

        this.yaw = YAW;
        this.pitch = PITCH;

        this.speed = SPEED;
        this.sensitivity = SENSITIVITY;
        this.zoom = ZOOM;

        this.mouseX = this.window.getWidth() / 2;
        this.mouseY = this.window.getHeight() / 2;

        this.update();
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getFront() {
        return front;
    }

    public Vector3f getUp() {
        return up;
    }

    public Vector3f getRight() {
        return right;
    }

    public Vector3f getGlobalUp() {
        return globalUp;
    }

    public void setGlobalUp(Vector3f globalUp) {
        this.globalUp = globalUp;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(float sensitivity) {
        this.sensitivity = sensitivity;
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    public Matrix4f getView() {
        return new Matrix4f().lookAt(position, position.add(front, new Vector3f()), up);
    }

    public Matrix4 getProjection() {
        Matrix4 projection = new Matrix4();
        projection.loadIdentity();

        projection.makePerspective((float) Math.toRadians(this.zoom), (float) this.window.getWidth() / this.window.getHeight(), 0.1f, 100);

        return projection;
    }

    public void processKeyboard(Movement direction) {
        float velocity = this.speed * this.delta;

        if (direction == Movement.FORWARD) {
            this.position.add(this.front.mul(velocity, new Vector3f()));
        } else if (direction == Movement.BACK) {
            this.position.sub(this.front.mul(velocity, new Vector3f()));
        } else if (direction == Movement.RIGHT) {
            this.position.add(this.right.mul(velocity, new Vector3f()));
        } else {
            this.position.sub(this.right.mul(velocity, new Vector3f()));
        }
    }

    public void processMovement(float x, float y, boolean restrain) {
        this.yaw += (x - mouseX) * this.sensitivity;
        this.pitch += (mouseY - y) * this.sensitivity;

        this.mouseX = x;
        this.mouseY = y;

        if (restrain) {
            if (this.pitch > 89) {
                this.pitch = 89;
            } else if (this.pitch < -89) {
                this.pitch = -89;
            }
        }

        this.update();
    }

    void processScroll(float rotation) {
        if (this.zoom >= 1 && this.zoom <= 45) {
            this.zoom -= rotation;
        }

        if (this.zoom <= 1) {
            this.zoom = 1;
        } else if (this.zoom >= 45) {
            this.zoom = 45;
        }
    }

    public void updateDelta() {
        float currentFrame = Time.delta() / 1000f;

        this.delta = currentFrame - frame;
        this.frame = currentFrame;
    }

    private void update() {
        this.front.set(
           (float) (Math.cos(Math.toRadians(this.yaw)) * Math.cos(Math.toRadians(this.pitch))),
           (float) Math.sin(Math.toRadians(this.pitch)),
           (float) (Math.sin(Math.toRadians(this.yaw)) * Math.cos(Math.toRadians(this.pitch)))
       ).normalize();

        this.front.cross(this.globalUp, this.right).normalize();
        this.right.cross(this.front, this.up).normalize();
    }
}
