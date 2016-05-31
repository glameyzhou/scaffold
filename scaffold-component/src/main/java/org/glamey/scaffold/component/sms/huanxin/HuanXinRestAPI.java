package org.glamey.scaffold.component.sms.huanxin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import okhttp3.*;
import org.glamey.scaffold.component.exception.ComponentException;
import org.glamey.scaffold.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.*;

/**
 * 环信restAPI
 *
 * @author by zhouyang.zhou
 */
public final class HuanXinRestAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(HuanXinRestAPI.class);
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";
    private String baseUrl;
    private String clientId;
    private String clientSecret;

    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient();

    private static final JsonMapper JSON_MAPPER = JsonMapper.create();

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");

    private HuanXinRestAPI(Builder builder) {
        this.baseUrl = builder.BASE_URL;
        this.clientId = defaultIfBlank(builder.CLIENT_ID, EMPTY);
        this.clientSecret = defaultIfBlank(builder.CLIENT_SECRET, EMPTY);
    }


    /**
     * 获取token
     * 接口限流说明: 同一个IP每秒最多可调用30次, 超过的部分会返回429或503错误, 所以在调用程序中, 如果碰到了这样的错误, 需要稍微暂停一下并且重试。如果该限流控制不满足需求，请联系商务经理开放更高的权限。
     *
     * @return
     */
    public final Map<String, Object> token() throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + "/token")
                .headers(Headers.of(ImmutableMap.of(CONTENT_TYPE, APPLICATION_JSON)))
                .post(RequestBody.create(MEDIA_TYPE_JSON, JSON_MAPPER.toJson(ImmutableMap.of("grant_type", "client_credentials", "client_id", clientId, "client_secret", clientSecret))))
                .build();
        return doRequest(request, String.format("获取token异常"));
    }

    /**
     * 获取账户信息
     *
     * @return
     */
    public final Map<String, Object> accountInfo(String accountId) throws IOException {
        Assert.notNull(isNotBlank(accountId), "账户ID不能为空");
        Request request = new Request.Builder()
                .url(baseUrl + "/users/" + accountId)
                .headers(buildTokenHeader())
                .get()
                .build();
        return doRequest(request, String.format("查询账户信息失败,accountId=%s", accountId));
    }

    /**
     * 创建账户
     *
     * @return
     */
    public final Map<String, Object> createAccount(String accountId, String password, String nickName) throws IOException {
        Assert.notNull(isNotBlank(accountId) && isNotBlank(password), "账户信息不能为空");
        Map<String, Object> map = Maps.newHashMap();
        map.put("username", accountId);
        map.put("password", password);
        map.put("nickname", defaultIfBlank(nickName, accountId));
        String requestBody = JSON_MAPPER.toJson(map);

        Request request = new Request.Builder()
                .url(baseUrl + "/users")
                .headers(buildTokenHeader())
                .post(RequestBody.create(MEDIA_TYPE_JSON, requestBody))
                .build();
        return doRequest(request, String.format("创建账户失败,accountId=%s, password=%s,nickname=%s", accountId, password, nickName));
    }

    /**
     * 修改用户昵称
     *
     * @param accountId
     * @param nickName
     * @return
     * @throws IOException
     */
    public final Map<String, Object> updateNickName(String accountId, String nickName) throws IOException {
        Assert.notNull(isNotBlank(accountId) && isNotBlank(nickName), "用户账户ID和昵称不能为空");
        Map<String, Object> map = Maps.newHashMap();
        map.put("nickname", nickName);
        String requestBody = JSON_MAPPER.toJson(map);

        Request request = new Request.Builder()
                .url(baseUrl + "/users/" + accountId)
                .headers(buildTokenHeader())
                .put(RequestBody.create(MEDIA_TYPE_JSON, requestBody))
                .build();
        return doRequest(request, String.format("修改用户昵称失败,accountId=%s, nickname=%s", accountId, nickName));
    }

    /**
     * 数据请求及其处理
     *
     * @param request
     * @param message
     * @return
     * @throws IOException
     */
    private Map<String, Object> doRequest(Request request, String message) throws IOException {
        Response response = httpExecute(request);
        String result = response.body().string();
        LOGGER.info("response {}, request={}", result, request.toString());
        HashMap<String, Object> resultMap = JSON_MAPPER.fromJson(result, new TypeReference<HashMap<String, Object>>() {
        });
        if (response.isSuccessful()) {
            return resultMap;
        }
        throw new ComponentException(response.code(), (String) resultMap.get("error_description"));
    }

    private Response httpExecute(Request request) throws IOException {
        return HTTP_CLIENT.newCall(request).execute();
    }

    private Headers buildTokenHeader() throws IOException {
        //TODO 暂时使用每次获取token，后续修改为定时器，定时刷新token
        String token = HuanXinConstants.TOKEN;
        if (isBlank(token)) {
            Map<String, Object> tokenMap = token();
            token = (String) tokenMap.get("access_token");
        }
        ImmutableMap<String, String> map = ImmutableMap.<String, String>builder()
                .put(CONTENT_TYPE, APPLICATION_JSON)
                .put("Authorization", "Bearer " + token)
                .build();

        return Headers.of(map);
    }

    /**
     * 参数设置,初始化配置
     */
    public static class Builder {
        private String BASE_URL;
        private String APP_KEY;
        private String ORG_NAME;
        private String APP_NAME;
        private String CLIENT_ID;
        private String CLIENT_SECRET;

        public Builder url(String baseUrl, String orgName, String appName) {
            Assert.state(isNotBlank(baseUrl) && isNotBlank(orgName) && isNotBlank(appName), "应用信息不能为空");
            this.BASE_URL = new StringBuffer(baseUrl).append("/").append(orgName).append("/").append(appName).toString();
            this.APP_KEY = orgName + "#" + appName;
            this.ORG_NAME = orgName;
            this.APP_NAME = appName;
            return this;
        }

        public Builder auth(String clientId, String clientSecret) {
            Assert.state(isNotBlank(clientId) && isNotBlank(clientSecret), "APP密钥不能为空");
            this.CLIENT_ID = clientId;
            this.CLIENT_SECRET = clientSecret;
            return this;
        }

        public HuanXinRestAPI build() {
            return new HuanXinRestAPI(this);
        }
    }
}
