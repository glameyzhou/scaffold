/**
 * @author zhouyang.zhou
 * <p>logback日志监听器，直接配置在web.xml中，随着web server启动的时候加载
 * <hr><blockquote><pre>
 *  {@code
 *  <context-param>
 *    <param-name>logbackConfigLocation</param-name>
 *    <param-value>classpath:log4j/logback.xml</param-value>
 *  </context-param>
 *  <listener>
 *    <listener-class>org.glamey.scaffold.http.logback.LogbackConfigListener</listener-class>
 *  </listener>
 *  }
 * </pre></blockquote><hr>
 */
package org.glamey.scaffold.web.logback;