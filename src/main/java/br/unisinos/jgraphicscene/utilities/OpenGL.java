package br.unisinos.jgraphicscene.utilities;

import com.jogamp.opengl.GL;

import static com.jogamp.opengl.GL.*;

public class OpenGL {
    public static void checkError(GL gl) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String location = "Unknown location";

        if (stackTrace.length > 2) {
            location = stackTrace[2].getClassName() + "." + stackTrace[2].getMethodName();
        }

        OpenGL.checkError(gl, location);
    }

    public static void checkError(GL gl, String location) {
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
