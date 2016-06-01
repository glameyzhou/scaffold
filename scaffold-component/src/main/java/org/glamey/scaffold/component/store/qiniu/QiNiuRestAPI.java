package org.glamey.scaffold.component.store.qiniu;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * 七牛云存储
 *
 * @author zhouyang.zhou.
 */
public class QiNiuRestAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(QiNiuRestAPI.class);
    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String bucketHash;
    private String serverDomain;

    private QiNiuRestAPI(Builder builder) {
        this.accessKey = builder.accessKey;
        this.secretKey = builder.secretKey;
        this.bucketName = builder.bucketName;
        this.bucketHash = builder.bucketHash;
        this.serverDomain = builder.serverDomain;
    }

    private Auth create() {
        Assert.notNull(accessKey);
        Assert.notNull(secretKey);
        return Auth.create(accessKey, secretKey);
    }

    private String getUpToken() {
        Assert.notNull(bucketName);
        return create().uploadToken(bucketName);
    }

    /**
     * 普通文件上传
     *
     * @param uploadFile 待上传的文件路径
     * @param fileName   文件名称
     * @return the cloud file url
     * @throws QiniuException exception
     */
    public String upload(String uploadFile, String fileName) throws QiniuException {
        Assert.notNull(uploadFile);
        Assert.notNull(fileName);
        UploadManager uploadManager = new UploadManager();
        Response res = uploadManager.put(uploadFile, fileName, getUpToken());
        LOGGER.info("upload file,uploadFile={}, fileName={}, response={}", uploadFile, fileName, res.bodyString());
        return generateFileUrl(fileName);
    }

    /**
     * @param bytes    bytes
     * @param fileName file name
     * @return the cloud file url
     * @throws QiniuException exception
     */
    public String upload(byte[] bytes, String fileName) throws QiniuException {
        Assert.notNull(bytes);
        Assert.notNull(fileName);
        UploadManager uploadManager = new UploadManager();
        Response res = uploadManager.put(bytes, fileName, getUpToken());
        LOGGER.info("upload file,fileName={}, response={}", fileName, res.bodyString());
        return generateFileUrl(fileName);
    }

    private String generateFileUrl(String fileName) {
        return new StringBuilder("http://").append(bucketHash).append(".").append(serverDomain).append("/").append(fileName).toString();
    }

    /**
     * 参数设置,初始化配置
     */
    public static class Builder {
        private String accessKey;
        private String secretKey;
        private String bucketName;
        private String bucketHash;
        private String serverDomain;

        public Builder auth(String accessKey, String secretKey) {
            Assert.state(isNotBlank(accessKey) && isNotBlank(secretKey));
            this.accessKey = accessKey;
            this.secretKey = secretKey;
            return this;
        }

        public Builder bucket(String bucketName, String bucketHash, String serverDomain) {
            Assert.state(isNotBlank(bucketName));
            this.bucketName = bucketName;
            this.bucketHash = bucketHash;
            this.serverDomain = serverDomain;
            return this;
        }

        public QiNiuRestAPI build() {
            return new QiNiuRestAPI(this);
        }
    }
}
