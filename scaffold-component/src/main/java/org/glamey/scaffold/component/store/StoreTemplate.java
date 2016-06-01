package org.glamey.scaffold.component.store;

import java.io.File;
import java.io.IOException;

/**
 * @author zhouyang.zhou.
 */
public interface StoreTemplate {

    /**
     * upload the file to cloud
     *
     * @param uploadFile the upload file
     * @param fileName   file name
     * @return cloud file url
     * @throws IOException exception
     */
    String upload(File uploadFile, String fileName) throws IOException;

    /**
     * @param bytes    the file bytes
     * @param fileName file name
     * @return the cloud file url
     * @throws IOException exception
     */
    String upload(byte[] bytes, String fileName) throws IOException;
}
