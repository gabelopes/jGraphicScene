package br.unisinos.jgraphicscene.utilities;

import com.jogamp.opengl.util.GLBuffers;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.nio.ByteBuffer;

public class Images {
    public static ByteBuffer asByteBuffer(BufferedImage bufferedImage) {
        byte[] pixelData = new byte[0];
        DataBuffer dataBuffer = bufferedImage.getRaster().getDataBuffer();

        if (dataBuffer instanceof DataBufferByte) {
            pixelData = ((DataBufferByte) dataBuffer).getData();
        }

        return GLBuffers.newDirectByteBuffer(pixelData);
    }
}
