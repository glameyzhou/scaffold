package org.glamey.scaffold.component.sms;

import java.io.IOException;

/**
 * 短信相关的内容
 *
 * @author zhouyang.zhou.
 */
public interface SmsTemplate {

    /**
     * 发送短信
     *
     * @param mobiles    手机号码,支持多个号码
     * @param templateId 模板ID
     * @param data       模板统配内容集合
     * @throws IOException exception
     */
    void sendSmsContentOfText(String[] mobiles, String templateId, String[] data) throws IOException;


    /**
     * 下发语音验证码
     *
     * @param mobile     mobile
     * @param verifyCode verify code
     * @param displayNum display number
     * @param playTimes  play times
     * @throws IOException exception
     */
    void sendSmsContentOfVoice(String mobile, String verifyCode, String displayNum, String playTimes) throws IOException;

    /**
     * 短信厂商
     *
     * @return the sms manufacturer
     */
    SmsManufacturer manufacturer();
}