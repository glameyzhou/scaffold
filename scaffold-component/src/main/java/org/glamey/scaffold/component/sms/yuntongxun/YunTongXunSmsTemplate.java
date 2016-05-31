package org.glamey.scaffold.component.sms.yuntongxun;

import org.apache.commons.lang3.StringUtils;
import org.glamey.scaffold.component.exception.ComponentException;
import org.glamey.scaffold.component.sms.SmsManufacturer;
import org.glamey.scaffold.component.sms.SmsTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.defaultIfBlank;
import static org.glamey.scaffold.component.sms.SmsManufacturer.YUNTONGXUN;

/**
 * @author zhouyang.zhou
 */

@Service
public class YunTongXunSmsTemplate implements SmsTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(YunTongXunSmsTemplate.class);

    @Resource
    private YunTongXunInfo yunTongXunInfo;


    @Override
    public void sendSmsContentOfText(String[] mobiles, String templateId, String[] data) throws IOException {
        Assert.notNull(mobiles, "手机号不能为空");
        Assert.notNull(data, "模板内容不能为空");
        templateId = defaultIfBlank(templateId, yunTongXunInfo.getTemplateId());

        LOGGER.info("mobiles={}, templateId={}, data={}", mobiles, templateId, data);

        YunTongXunRestAPI api = yunTongXunInfo.initAPIAccount();
        Map<String, Object> resultMap = api.smsTemplateSMS(mobiles, templateId, data);
        String statusCode = (String) resultMap.get("statusCode");
        if (!StringUtils.equals(statusCode, "000000")) {
            throw new ComponentException((String) resultMap.get("statusMsg"));
        }
    }

    @Override
    public void sendSmsContentOfVoice(String mobile, String verifyCode, String displayNum, String playTimes) throws IOException {
        Assert.notNull(mobile, "手机号不能为空");
        Assert.notNull(verifyCode, "验证码不能为空");

        LOGGER.info("mobile={}, verifyCode={}, displayNum={}, playTimes={}", mobile, verifyCode, displayNum, playTimes);

        YunTongXunRestAPI api = yunTongXunInfo.initAPIAccount();
        Map<String, Object> resultMap = api.callsVoiceVerify(mobile, verifyCode, displayNum, playTimes);
        String statusCode = (String) resultMap.get("statusCode");
        if (!StringUtils.equals(statusCode, "000000")) {
            throw new ComponentException((String) resultMap.get("statusMsg"));
        }
    }

    @Override
    public SmsManufacturer manufacturer() {
        return YUNTONGXUN;
    }
}
