package org.glamey.scaffold.web.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * http session操作
 *
 * @author zhouyang.zhou
 */
public class Sessions {

    /**
     * 根据名称获取session数据对象
     *
     * @param request     {@link HttpServletRequest}
     * @param sessionName session name
     * @param <T>         the return object type
     * @return session object
     */
    public static <T> T getSession(HttpServletRequest request, String sessionName) {
        Object o = request.getSession().getAttribute(sessionName);
        return o == null ? null : (T) o;
    }

    /**
     * 向服务端设置session数据
     *
     * @param request     {@link HttpServletRequest}
     * @param sessionName session name
     * @param t           object type 对应的value
     * @param <T>         object type
     */
    public static <T> void setSession(HttpServletRequest request, String sessionName, T t) {
        request.getSession().setAttribute(sessionName, t);
    }

    /**
     * 移除指定的session对象
     *
     * @param request     {@link HttpServletRequest}
     * @param sessionName session name
     */
    public static void removeAttribute(HttpServletRequest request, String sessionName) {
        HttpSession session = request.getSession();
        session.removeAttribute(sessionName);
    }

    /**
     * 所有的session失效
     *
     * @param request {@link HttpServletRequest}
     */
    public static void invalidate(HttpServletRequest request) {
        request.getSession().invalidate();
    }
}
