package org.glamey.scaffold.web.http;

import com.google.common.base.Strings;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

import javax.servlet.http.HttpServletRequest;

/**
 * http 请求参数获取
 *
 * @author zhouyang.zhou
 */
public class Parameters {

    public static String getString(HttpServletRequest request, String fieldName) {
        return request.getParameter(fieldName);
    }

    public static String getString(HttpServletRequest request, String fieldName, String defaultValue) {
        String result = getString(request, fieldName);
        return Strings.isNullOrEmpty(result) ? defaultValue : result;
    }

    public static Integer getInt(HttpServletRequest request, String fieldName) {
        String s = request.getParameter(fieldName);
        return Strings.isNullOrEmpty(s) ? null : Ints.tryParse(s);
    }

    public static Integer getInt(HttpServletRequest request, String fieldName, Integer defaultValue) {
        Integer i = getInt(request, fieldName);
        return i == null ? defaultValue : i;
    }

    public static Long getLong(HttpServletRequest request, String fieldName) {
        String l = request.getParameter(fieldName);
        return Strings.isNullOrEmpty(l) ? null : Longs.tryParse(l);
    }

    public static Long getDouble(HttpServletRequest request, String fieldName, Long defaultValue) {
        Long l = getLong(request, fieldName);
        return l == null ? defaultValue : l;
    }

    public static Float getFloat(HttpServletRequest request, String fieldName) {
        String f = request.getParameter(fieldName);
        return Strings.isNullOrEmpty(f) ? null : Floats.tryParse(f);
    }

    public static Float getFloat(HttpServletRequest request, String fieldName, Float defaultValue) {
        Float f = getFloat(request, fieldName);
        return f == null ? defaultValue : f;
    }

    public static String[] getStrings(HttpServletRequest request, String fieldName) {
        String[] values = request.getParameterValues(fieldName);
        return values;
    }

    public static Integer[] getInts(HttpServletRequest request, String fieldName) {
        String[] values = request.getParameterValues(fieldName);
        if (values == null) {
            return null;
        }
        int len = values.length;
        Integer[] ints = new Integer[len];
        for (int i = 0; i < len; i++) {
            ints[i] = Ints.tryParse(values[i]);
        }
        return ints;
    }

    public static Double[] getDoubles(HttpServletRequest request, String fieldName) {
        String[] values = request.getParameterValues(fieldName);
        if (values == null) {
            return null;
        }
        int len = values.length;
        Double[] doubles = new Double[len];
        for (int i = 0; i < len; i++) {
            doubles[i] = Doubles.tryParse(values[i]);
        }
        return doubles;
    }
}
