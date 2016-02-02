package org.glamey.scaffold.encryption;

import com.google.common.base.Preconditions;
import org.apache.commons.codec.binary.Base64;

/**
 * <p>base64工具类
 *
 * @author zhouyang.zhou
 */
public class Base64Handler {

    /**
     * @param bytes the to string bytes
     * @return string
     */
    public static String byteToString(byte[] bytes) {
        Preconditions.checkArgument(bytes != null, "待解码的数据为空");
        return Base64.encodeBase64String(bytes);
    }

    /**
     * @param string the to bytes string
     * @return byte[]
     */
    public static byte[] stringToByte(String string) {
        Preconditions.checkNotNull(string, "待编码的数据为空");
        return Base64.decodeBase64(string);
    }

}
