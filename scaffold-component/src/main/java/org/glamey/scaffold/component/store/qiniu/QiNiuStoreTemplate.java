package org.glamey.scaffold.component.store.qiniu;

import com.google.common.io.Files;
import org.apache.commons.io.FilenameUtils;
import org.glamey.scaffold.component.store.StoreTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * @author zhouyang.zhou.
 */

@Component
public class QiNiuStoreTemplate implements StoreTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(QiNiuStoreTemplate.class);

    @Resource
    private QiNiuInfo qiNiuInfo;

    @Override
    public String upload(File uploadFile, String fileName) throws IOException {
        Assert.notNull(uploadFile);
        Assert.notNull(fileName);
        QiNiuRestAPI restAPI = qiNiuInfo.authBucket();
        String fileUrl = restAPI.upload(Files.toByteArray(uploadFile), FilenameUtils.getName(fileName));
        LOGGER.info("upload the file success, return fileUrl={}", fileUrl);
        return fileUrl;
    }

    @Override
    public String upload(byte[] bytes, String fileName) throws IOException {
        Assert.notNull(bytes);
        Assert.notNull(fileName);
        QiNiuRestAPI restAPI = qiNiuInfo.authBucket();
        String fileUrl = restAPI.upload(bytes, FilenameUtils.getName(fileName));
        LOGGER.info("upload the file success, return fileUrl={}", fileUrl);
        return fileUrl;
    }
}
