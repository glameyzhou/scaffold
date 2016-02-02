package org.glamey.scaffold.encryption;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author by zhouyang.zhou.
 */
public class BlowfishCBCTest {

    @Test
    public void testMain() throws Exception {
        Assert.assertEquals("glamey.zhou", new BlowFishHandler("glamey.zhou").decryptString(new BlowFishHandler("glamey.zhou").encryptString("glamey.zhou")));
    }
}