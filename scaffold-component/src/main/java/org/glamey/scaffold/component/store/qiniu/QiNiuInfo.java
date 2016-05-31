package org.glamey.scaffold.component.store.qiniu;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhouyang.zhou.
 */

@Component
public class QiNiuInfo {
    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("${qiniu.secretKey}")
    private String secretKey;
    @Value("${qiniu.bucketName}")
    private String bucketName;
    @Value("${qiniu.bucketHash}")
    private String bucketHash;
    @Value("${qiniu.serverDomain}")
    private String serverDomain;

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getBucketHash() {
        return bucketHash;
    }

    public String getServerDomain() {
        return serverDomain;
    }

    /**
     * 用户授权
     *
     * @return
     * @see #authBucket()
     */
    public QiNiuRestAPI auth() {
        return new QiNiuRestAPI.Builder().auth(getAccessKey(), getSecretKey()).build();
    }

    /**
     * 用户授权，并且执行存储的空间
     *
     * @return
     * @see #auth()
     */
    public QiNiuRestAPI authBucket() {
        return new QiNiuRestAPI.Builder().auth(getAccessKey(), getSecretKey()).bucket(getBucketName(), getBucketHash(), getServerDomain()).build();
    }
}
