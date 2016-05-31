package org.glamey.scaffold.component.sms.huanxin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 环信参数内容
 *
 * @author zhouyang.zhou.
 */
@Component
public class HuanXinInfo {

    @Value("${huanxin.appKey}")
    private String appKey;

    @Value("${huanxin.orgName}")
    private String orgName;

    @Value("${huanxin.appName}")
    private String appName;

    @Value("${huanxin.clientId}")
    private String clientId;

    @Value("${huanxin.clientSecret}")
    private String clientSecret;

    @Value("${huanxin.allowed.registryAccounts}")
    private String allowedRegistryAccounts;


    @Value("${huanxin.baseUrl}")
    private String baseUrl;


    public String getAppKey() {
        return appKey;
    }

    public String getOrgName() {
        return orgName;
    }

    public String getAppName() {
        return appName;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getAllowedRegistryAccounts() {
        return allowedRegistryAccounts;
    }

    /**
     * 用户授权
     *
     * @return
     */
    public HuanXinRestAPI auth() {
        return new HuanXinRestAPI.Builder().url(getBaseUrl(), getOrgName(), getAppName()).auth(getClientId(), getClientSecret()).build();
    }
}
