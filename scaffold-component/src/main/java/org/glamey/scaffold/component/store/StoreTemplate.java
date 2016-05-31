package org.glamey.scaffold.component.store;

import java.io.File;
import java.io.IOException;

/**
 * @author zhouyang.zhou.
 */
public interface StoreTemplate {

    /**
     * @param uploadFile
     * @param fileName
     * @return
     * @throws Exception
     */
    String upload(File uploadFile, String fileName) throws IOException;

    /**
     * @param bytes
     * @param fileName
     * @return
     * @throws Exception
     */
    String upload(byte[] bytes, String fileName) throws IOException;
}
