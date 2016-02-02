package org.glamey.scaffold.web.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author by zhouyang.zhou.
 */
public class IpUtils {

    public static final String UNKNOWN = "unknown";
    public static final String X_REAL_IP = "X-Real-IP";
    public static final String X_FORWARDED_FOR = "x-forwarded-for";
    public static final String PROXY_CLIENT_IP = "Proxy-Client-IP";
    public static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";

    /**
     * 获取访问者的IP地址
     *
     * @param request
     * @return
     */
    public static String getClientRealIP(HttpServletRequest request) {
        String ip = request.getHeader(X_REAL_IP);
        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(UNKNOWN, ip)) {
            ip = request.getHeader(X_FORWARDED_FOR);
        }
        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(UNKNOWN, ip)) {
            ip = request.getHeader(PROXY_CLIENT_IP);
        }

        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(UNKNOWN, ip)) {
            ip = request.getHeader(WL_PROXY_CLIENT_IP);
        }

        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(UNKNOWN, ip)) {
            ip = request.getRemoteAddr();
        }

        return getValidIp(ip);
    }

    private static String getValidIp(String ip) {
        int pos = ip.lastIndexOf(',');
        if (pos >= 0) {
            ip = ip.substring(pos);
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }
}

