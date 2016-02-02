package org.glamey.scaffold.encryption;

import java.util.zip.CRC32;

/**
 * @author by zhouyang.zhou.
 */
public class CRCHandler {
    private static final java.util.zip.CRC32 CRC32 = new CRC32();

    /**
     * 通过CRC校验数据包是否完整
     *
     * @param target 目标数据
     * @param expect 期望值
     * @return boolean结果
     */
    public static boolean check(byte[] target, String expect) {
        if (expect == null)
            return false;
        return expect.equals(getCRC(target));
    }

    /**
     * 生成CRC标识
     *
     * @param target 目标数组
     * @return crc标识字符
     */
    public static String getCRC(byte[] target) {
        CRC32.reset();
        CRC32.update(target);
        return Long.toHexString(CRC32.getValue());
    }
}
