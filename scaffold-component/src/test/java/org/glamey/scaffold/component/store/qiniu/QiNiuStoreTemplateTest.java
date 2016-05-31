package org.glamey.scaffold.component.store.qiniu;

import com.google.common.io.Files;
import org.glamey.scaffold.component.store.StoreTemplate;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.File;

import static org.junit.Assert.*;

/**
 * @author zhouyang.zhou.
 */
public class QiNiuStoreTemplateTest {
    @Resource
    private StoreTemplate qiNiuStoreTemplate;

    @Test
    public void uploadFile() throws Exception {
        File uploadFile = new File("C:\\tmp\\dept.json");
        qiNiuStoreTemplate.upload(uploadFile, uploadFile.getName());
    }

    @Test
    public void uploadFileBytes() throws Exception {
        File uploadFile = new File("C:\\tmp\\qcache\\.gitignore");
        qiNiuStoreTemplate.upload(Files.toByteArray(uploadFile), uploadFile.getName());
    }


}