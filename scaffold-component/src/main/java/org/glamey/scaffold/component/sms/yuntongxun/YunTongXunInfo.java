package org.glamey.scaffold.component.sms.yuntongxun;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhouyang.zhou.
 */
@Component
public class YunTongXunInfo {
    @Value("${yuntongxun.account.sid}")
    private String accountSid;

    @Value("${yuntongxun.auth.token}")
    private String authToken;

    @Value("${yuntongxun.app.id}")
    private String appId;

    @Value("${yuntongxun.base.url}")
    private String baseUrl;

    @Value("${yuntongxun.template.id}")
    private String templateId;

    @Value("${yuntongxun.allowed.registrySubAccounts}")
    private String allowedRegistrySubAccounts;

    public String getAccountSid() {
        return accountSid;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getAppId() {
        return appId;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getTemplateId() {
        return templateId;
    }

    public String getAllowedRegistrySubAccounts() {
        return allowedRegistrySubAccounts;
    }

    /**
     * 初始化API
     *
     * @return the restAPI
     */
    public YunTongXunRestAPI initAPIAccount() {
        return new YunTongXunRestAPI.Builder().url(getBaseUrl()).account(getAccountSid(), getAuthToken()).app(getAppId()).build();
    }

    /**
     * @param subAccountSid   subAccountSid
     * @param subAccountToken subAccountToken
     * @return yuntongxun restAPI
     */
    public YunTongXunRestAPI initAPISubAccount(String subAccountSid, String subAccountToken) {
        return new YunTongXunRestAPI.Builder().url(getBaseUrl()).account(getAccountSid(), getAuthToken()).subAccount(subAccountSid, subAccountToken).app(getAppId()).build();
    }
}
