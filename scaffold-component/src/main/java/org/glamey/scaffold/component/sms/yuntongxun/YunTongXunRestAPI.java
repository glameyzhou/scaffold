package org.glamey.scaffold.component.sms.yuntongxun;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import okhttp3.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.glamey.scaffold.component.exception.ComponentException;
import org.glamey.scaffold.encryption.Base64Handler;
import org.glamey.scaffold.encryption.MD5Handler;
import org.glamey.scaffold.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.*;
import static org.glamey.scaffold.component.sms.yuntongxun.YunTongXunRestAPI.AccountType.ACCOUNT;
import static org.glamey.scaffold.component.sms.yuntongxun.YunTongXunRestAPI.AccountType.SUB_ACCOUNT;

/**
 * 云通信restAPI
 *
 * @author by zhouyang.zhou
 */
public final class YunTongXunRestAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(YunTongXunRestAPI.class);
    private String baseUrl;
    private String accountSid;
    private String accountToken;
    private String subAccountSid;
    private String subAccountToken;
    private String voipAccount;
    private String voipToken;
    private String appId;
    private String timeStamp;

    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient();

    private static final JsonMapper JSON_MAPPER = JsonMapper.create();

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");

    private YunTongXunRestAPI(Builder builder) {
        this.timeStamp = builder.TIME_STAMP;
        this.baseUrl = builder.BASE_URL;
        this.accountSid = defaultIfBlank(builder.ACCOUNT_SID, EMPTY);
        this.accountToken = defaultIfBlank(builder.ACCOUNT_TOKEN, EMPTY);
        this.subAccountSid = defaultIfBlank(builder.SUBACCOUNT_SID, EMPTY);
        this.subAccountToken = defaultIfBlank(builder.SUBACCOUNT_TOKEN, EMPTY);
        this.voipAccount = defaultIfBlank(builder.VOIP_ACCOUNT, EMPTY);
        this.voipToken = defaultIfBlank(builder.VOIP_TOKEN, EMPTY);
        this.appId = defaultIfBlank(builder.APP_ID, EMPTY);
    }

    /**
     * 主账户信息
     *
     * @return response
     * @throws IOException exception
     */
    public final Map<String, Object> accountInfo() throws IOException {
        Request request = new Request.Builder()
                .url(buildBaseUrl("/AccountInfo", ACCOUNT))
                .headers(buildHeader(0, ACCOUNT))
                .get()
                .build();

        return doRequest(request, String.format("主账户信息查询,accountSid=%s", accountSid));
    }

    /**
     * 创建子账户
     *
     * @param friendlyName friendlyName
     * @return response
     * @throws IOException IOException
     */
    public final Map<String, Object> subAccounts(String friendlyName) throws IOException {
        Assert.notNull(isNotBlank(friendlyName), "子账户不能为空");
        Map<String, Object> map = Maps.newHashMap();
        map.put("appId", appId);
        map.put("friendlyName", friendlyName);
        String requestBody = JSON_MAPPER.toJson(map);

        Request request = new Request.Builder()
                .url(buildBaseUrl("/SubAccounts", ACCOUNT))
                .post(RequestBody.create(MEDIA_TYPE_JSON, requestBody))
                .headers(buildHeader(requestBody.length(), ACCOUNT))
                .build();

        return doRequest(request, String.format("子账户创建异常,friendlyName=%s", friendlyName));
    }

    /**
     * 关闭子账户
     *
     * @param subAccountSid subAccountSid
     * @return response
     * @throws IOException IOException
     */
    public final Map<String, Object> closeSubAccount(String subAccountSid) throws IOException {
        Assert.notNull(isNotBlank(subAccountSid), "子账户SID不能为空");
        Map<String, Object> map = Maps.newHashMap();
        map.put("subAccountSid", subAccountSid);
        String requestBody = JSON_MAPPER.toJson(map);

        Request request = new Request.Builder()
                .url(buildBaseUrl("/CloseSubAccount", ACCOUNT))
                .post(RequestBody.create(MEDIA_TYPE_JSON, requestBody))
                .headers(buildHeader(requestBody.length(), ACCOUNT))
                .build();

        return doRequest(request, String.format("子账户关闭异常,subAccountSid=%s", subAccountSid));
    }

    /**
     * 子账户信息查询
     *
     * @param friendlyName friendlyName
     * @return response
     * @throws IOException exception
     */
    public final Map<String, Object> querySubAccountByName(String friendlyName) throws IOException {
        Assert.notNull(isNotBlank(friendlyName), "子账户名字不能为空");
        Map<String, Object> map = Maps.newHashMap();
        map.put("appId", appId);
        map.put("friendlyName", friendlyName);
        String requestBody = JSON_MAPPER.toJson(map);

        Request request = new Request.Builder()
                .url(buildBaseUrl("/QuerySubAccountByName", ACCOUNT))
                .post(RequestBody.create(MEDIA_TYPE_JSON, requestBody))
                .headers(buildHeader(requestBody.length(), ACCOUNT))
                .build();

        return doRequest(request, String.format("子账户信息查询,friendlyName=%s", friendlyName));
    }


    /**
     * 下发模板短信
     *
     * @param mobiles    the mobiles
     * @param templateId templteId
     * @param data       data
     * @return response
     * @throws IOException exception
     */
    public final Map<String, Object> smsTemplateSMS(String[] mobiles, String templateId, String[] data) throws IOException {
        Assert.state(mobiles != null && isNotBlank(templateId) && data != null, "参数错误");
        Map<String, Object> map = Maps.newHashMap();
        map.put("to", Joiner.on(",").join(Arrays.asList(mobiles)));
        map.put("appId", appId);
        map.put("templateId", "1");
        map.put("datas", data);
        String requestBody = JSON_MAPPER.toJson(map);

        Request request = new Request.Builder()
                .url(buildBaseUrl("/SMS/TemplateSMS", ACCOUNT))
                .post(RequestBody.create(MEDIA_TYPE_JSON, requestBody))
                .headers(buildHeader(requestBody.length(), ACCOUNT))
                .build();

        return doRequest(request, String.format("请求短信模板验证失败,mobiles=%s,templateId=%s,data=%s", mobiles, templateId, data));
    }

    /**
     * 下发语音验证码
     *
     * @param mobile     mobile
     * @param verifyCode verify code
     * @param displayNum display number
     * @param playTimes  play times
     * @return response
     * @throws IOException exception
     */
    public final Map<String, Object> callsVoiceVerify(String mobile, String verifyCode, String displayNum, String playTimes) throws IOException {
        Assert.state(isNotBlank(mobile) && isNotBlank(verifyCode), "参数错误");
        playTimes = defaultIfBlank(playTimes, "3");
        displayNum = defaultIfBlank(displayNum, "点点无限--面试官");
        Map<String, Object> map = Maps.newHashMap();
        map.put("appId", appId);
        map.put("verifyCode", verifyCode);
        map.put("to", mobile);
        map.put("displayNum", displayNum);
        map.put("playTimes", playTimes);
        String requestBody = JSON_MAPPER.toJson(map);

        Request request = new Request.Builder()
                .url(buildBaseUrl("/Calls/VoiceVerify", ACCOUNT))
                .post(RequestBody.create(MEDIA_TYPE_JSON, requestBody))
                .headers(buildHeader(requestBody.length(), ACCOUNT))
                .build();

        return doRequest(request, String.format("请求语音验证码失败,mobiles=%s,verifyCode=%s", mobile, verifyCode));
    }


    /**
     * 发送双向回拨请求
     *
     * @param from            必选参数 主叫电话号码
     * @param to              必选参数 被叫电话号码
     * @param customerSerNum  可选参数 被叫侧显示的客服号码，根据平台侧显号规则控制
     * @param fromSerNum      可选参数 主叫侧显示的号码，根据平台侧显号规则控制
     * @param promptTone      可选参数 第三方自定义回拨提示音
     * @param userData        可选参数 第三方私有数据
     * @param maxCallTime     可选参数 最大通话时长
     * @param hangupCdrUrl    可选参数 实时话单通知地址
     * @param alwaysPlay      可选参数 是否一直播放提示音
     * @param terminalDtmf    可选参数 用于终止播放提示音的按键
     * @param needBothCdr     可选参数 是否给主被叫发送话单
     * @param needRecord      可选参数 是否录音
     * @param countDownTime   可选参数 设置倒计时时间
     * @param countDownPrompt 可选参数 到达倒计时时间播放的提示音
     * @return response
     * @throws IOException exception
     */
    public final Map<String, Object> callsCallback(String from, String to,
                                                   String customerSerNum, String fromSerNum, String promptTone,
                                                   String alwaysPlay, String terminalDtmf, String userData,
                                                   String maxCallTime, String hangupCdrUrl, String needBothCdr,
                                                   String needRecord, String countDownTime, String countDownPrompt) throws IOException {
        Assert.state(isNotBlank(from) && isNotBlank(to), "主叫电话号码、被叫电话号码不能为空");
        Map<String, Object> map = Maps.newHashMap();
        map.put("from", from);
        map.put("to", to);
        if (isNotBlank(customerSerNum)) map.put("customerSerNum", customerSerNum);
        if (isNotBlank(fromSerNum)) map.put("fromSerNum", fromSerNum);
        if (isNotBlank(promptTone)) map.put("promptTone", promptTone);
        if (isNotBlank(alwaysPlay)) map.put("alwaysPlay", alwaysPlay);
        if (isNotBlank(terminalDtmf)) map.put("terminalDtmf", terminalDtmf);
        if (isNotBlank(userData)) map.put("userData", userData);
        if (isNotBlank(maxCallTime)) map.put("maxCallTime", maxCallTime);
        if (isNotBlank(hangupCdrUrl)) map.put("hangupCdrUrl", hangupCdrUrl);
        if (isNotBlank(needBothCdr)) map.put("needBothCdr", needBothCdr);
        if (isNotBlank(needRecord)) map.put("needRecord", needRecord);
        if (isNotBlank(countDownTime)) map.put("countDownTime", countDownTime);
        if (isNotBlank(countDownPrompt)) map.put("countDownPrompt", countDownPrompt);
        map.put("recordPoint", "0");
        String requestBody = JSON_MAPPER.toJson(map);

        Request request = new Request.Builder()
                .url(buildBaseUrl("/Calls/Callback", SUB_ACCOUNT))
                .post(RequestBody.create(MEDIA_TYPE_JSON, requestBody))
                .headers(buildHeader(requestBody.length(), SUB_ACCOUNT))
                .build();

        return doRequest(request, String.format("请求双向回拨异常,subAccountSid=%s", subAccountSid));
    }

    /**
     * 数据请求及其处理
     *
     * @param request request
     * @param message message
     * @return response
     * @throws IOException exception
     */
    private Map<String, Object> doRequest(Request request, String message) throws IOException {
        Response response = httpExecute(request);
        if (response.isSuccessful()) {
            String result = response.body().string();
            LOGGER.info("response {}, request={}", result, request.toString());
            return JSON_MAPPER.fromJson(result, new TypeReference<HashMap<String, Object>>() {
            });
        }
        throw new ComponentException(message);
    }

    private Response httpExecute(Request request) throws IOException {
        return HTTP_CLIENT.newCall(request).execute();
    }

    /**
     * 构建包含授权的请求头内容
     *
     * @param bodySize    the size of body
     * @param accountType account type
     * @return http headers
     */
    private Headers buildHeader(long bodySize, AccountType accountType) {
        StringBuffer buffer = new StringBuffer();
        if (accountType == ACCOUNT) {
            buffer.append(accountSid);
        } else if (accountType == SUB_ACCOUNT) {
            buffer.append(subAccountSid);
        }
        buffer.append(":");
        buffer.append(timeStamp);
        String authorization = Base64Handler.byteToString(buffer.toString().getBytes(Charsets.UTF_8));

        ImmutableMap<String, String> map = ImmutableMap.<String, String>builder()
                .put("Accept", "application/json")
                .put("Content-Type", "application/json;charset=utf-8")
                .put("Content-Length", String.valueOf(bodySize))
                .put("Authorization", authorization)
                .build();

        return Headers.of(map);
    }


    /**
     * 构建请求URL
     *
     * @param function    function
     * @param accountType account type
     * @return response
     */
    private String buildBaseUrl(String function, AccountType accountType) {
        StringBuffer buffer = new StringBuffer(baseUrl);
        buffer.append("/").append("2013-12-26");
        if (accountType == ACCOUNT) {
            buffer.append("/Accounts/").append(accountSid);
            buffer.append(function).append("?sig=");
            buffer.append(new MD5Handler().getMD5(
                    new StringBuffer(accountSid).append(accountToken).append(timeStamp).toString()
            ).toUpperCase());
        } else if (accountType == SUB_ACCOUNT) {
            buffer.append("/SubAccounts/").append(subAccountSid);
            buffer.append(function).append("?sig=");
            buffer.append(new MD5Handler().getMD5(
                    new StringBuffer(subAccountSid).append(subAccountToken).append(timeStamp).toString()
            ).toUpperCase());
        }
        return buffer.toString();
    }


    /**
     *
     */
    enum AccountType {
        ACCOUNT,
        SUB_ACCOUNT;
    }

    /**
     * 参数设置,初始化配置
     */
    public static class Builder {
        private String BASE_URL;
        private String ACCOUNT_SID;
        private String ACCOUNT_TOKEN;
        private String SUBACCOUNT_SID;
        private String SUBACCOUNT_TOKEN;
        private String VOIP_ACCOUNT;
        private String VOIP_TOKEN;
        private String APP_ID;
        private String TIME_STAMP;

        /**
         * @param baseUrl base url
         * @return the builder object
         */

        public Builder url(String baseUrl) {
            Assert.state(isNotBlank(baseUrl), "连接地址不能为空");
            this.BASE_URL = baseUrl;
            this.TIME_STAMP = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
            return this;
        }

        public Builder account(String accountSid, String accountAuth) {
            Assert.state(isNotBlank(accountSid) && isNotBlank(accountAuth), "主账号信息不能为空");
            this.ACCOUNT_SID = accountSid;
            this.ACCOUNT_TOKEN = accountAuth;
            return this;
        }

        public Builder subAccount(String subAccountSid, String subAccountAuth) {
            Assert.state(isNotBlank(subAccountSid) && isNotBlank(subAccountAuth), "子账号信息不能为空");
            this.SUBACCOUNT_SID = subAccountSid;
            this.SUBACCOUNT_TOKEN = subAccountAuth;
            return this;
        }

        public Builder app(String appId) {
            Assert.state(isNotBlank(appId), "APP_ID不能为空");
            this.APP_ID = appId;
            return this;
        }

        public Builder voIPAccount(String voIPAccount, String voIPPassword) {
            Assert.state(isNotBlank(voIPAccount) && isNotBlank(voIPAccount), "VOIP信息不能为空");
            this.VOIP_ACCOUNT = voIPAccount;
            this.VOIP_TOKEN = voIPPassword;
            return this;
        }

        public YunTongXunRestAPI build() {
            return new YunTongXunRestAPI(this);
        }
    }
}
