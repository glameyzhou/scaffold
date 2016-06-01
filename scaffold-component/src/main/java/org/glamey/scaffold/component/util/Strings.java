package org.glamey.scaffold.component.util;

import java.util.UUID;

/**
 * @author zhouyang.zhou.
 */
public class Strings {
    /**
     * @return uuid
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
