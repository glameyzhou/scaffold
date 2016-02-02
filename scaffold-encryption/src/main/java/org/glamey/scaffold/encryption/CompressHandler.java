package org.glamey.scaffold.encryption;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import com.google.common.io.Closer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 * @author by zhouyang.zhou.
 */
public class CompressHandler {
    private static final Logger logger = LoggerFactory.getLogger(CompressHandler.class);

    /**
     * Deflater 压缩
     *
     * @param content
     * @return
     */
    public static byte[] compress(String content) {
        Closer closer = Closer.create();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION, true);
        try {
            DeflaterOutputStream out = closer.register(new DeflaterOutputStream(byteArrayOutputStream, deflater));
            out.write(content.getBytes(Charsets.UTF_8.name()));
        } catch (Exception e) {
            logger.error(String.format("compress error:"), e);
        } finally {
            if (closer != null)
                try {
                    closer.close();
                } catch (Exception ex) {
                    logger.info(String.format("compress error,close the stream error:"), ex);
                }
        }
        deflater.end();
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * inflater 解压
     *
     * @param inputStream
     * @return
     */
    public static byte[] decompress(InputStream inputStream) {
        Closer closer = Closer.create();
        try {
            BufferedInputStream bufferInputStream = closer.register(new BufferedInputStream(new InflaterInputStream(inputStream, new Inflater(true))));
//			bufferInputStream = closer.register(new BufferedInputStream(new GZIPInputStream(inputStream)));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ByteStreams.copy(bufferInputStream, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            logger.error(String.format("decompress error:"), e);
        } finally {
            try {
                if (closer != null) {
                    closer.close();
                }
            } catch (Exception e1) {
                logger.error(String.format("decompress error,close the stream error"), e1);
            }
        }
        return null;
    }

    /**
     * 将输入流转化为byte数组
     *
     * @param inputStream
     * @return
     */
    public static byte[] toByteArray(InputStream inputStream) {
        try {
            return ByteStreams.toByteArray(inputStream);
        } catch (IOException e) {
            logger.error("change the InputStream to bytes error");
        }
        return null;
    }
}
