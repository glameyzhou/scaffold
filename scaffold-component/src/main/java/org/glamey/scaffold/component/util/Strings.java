package org.glamey.scaffold.component.util;

import java.util.UUID;

/**
 * @author zhouyang.zhou.
 */
public class Strings {
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
