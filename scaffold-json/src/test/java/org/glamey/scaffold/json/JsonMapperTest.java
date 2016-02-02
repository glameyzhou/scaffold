package org.glamey.scaffold.json;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author by zhouyang.zhou.
 */
public class JsonMapperTest {

    @Test
    public void testToJson() throws Exception {
        System.out.println(JsonMapper.create().toJson("Hello MianshiOK..."));
    }
}