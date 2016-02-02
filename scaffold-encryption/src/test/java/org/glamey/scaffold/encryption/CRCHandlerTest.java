package org.glamey.scaffold.encryption;

import com.google.common.base.Charsets;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author by zhouyang.zhou.
 */
public class CRCHandlerTest {

    @Test
    public void testCheck() throws Exception {
    }

    @Test
    public void testGetCRC() throws Exception {
        byte[] bytes = "数据数据数据数据数据数据数据数据数据数据数据数据数据数据数据".getBytes(Charsets.UTF_8.name());
        String crc = CRCHandler.getCRC(bytes);
        Assert.assertEquals("equals", "9d668bb8", crc);
    }
}