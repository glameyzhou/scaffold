package org.glamey.scaffold.chart.jfreechart;

import com.google.common.collect.Maps;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.util.Date;
import java.util.LinkedHashMap;

/**
 * @author by zhouyang.zhou.
 */
public class JFreeChartUtilsTest {

    @Test
    public void testBuildLineImage() throws Exception {
        LinkedHashMap<String, Integer> data = Maps.newLinkedHashMap();
        Date now = new Date();
        for (int i = -6; i <= 0; i++) {
            data.put(DateFormatUtils.format(DateUtils.addDays(now, i), "yyyy-MM-dd"), RandomUtils.nextInt(9));
        }
        System.out.println(JFreeChartUtils.buildLineImage("/Users/zhouyangzhou/work/temp", data, "近日期浏览量", 400, 200));
    }
}