package org.glamey.scaffold.component.sms.yuntongxun;

import org.apache.commons.lang3.RandomStringUtils;
import org.glamey.scaffold.BaseSpringJunit;
import org.glamey.scaffold.component.sms.SmsTemplate;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author zhouyang.zhou.
 */
public class YunTongXunSmsTemplateTest extends BaseSpringJunit {

    @Resource
    private SmsTemplate yunTongXunSmsTemplate;


    //    @Test(expected = ComponentException.class)
    @Test
    public void testSendSmsContentOfText() throws Exception {
        yunTongXunSmsTemplate.sendSmsContentOfText(new String[]{"15801357001"}, "1", new String[]{RandomStringUtils.random(4, "0123456789"), "5"});
    }


    //    @Test(expected = ComponentException.class)
    @Test
    public void testSendSmsContentOfVoice() throws Exception {
        yunTongXunSmsTemplate.sendSmsContentOfVoice("15801357001", RandomStringUtils.random(4, "0123456789"), null, null);
    }

}