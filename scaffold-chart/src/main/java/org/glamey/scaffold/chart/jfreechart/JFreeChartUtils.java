package org.glamey.scaffold.chart.jfreechart;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author zhouyang.zhou
 */
public class JFreeChartUtils {

    /**
     * @param baseDir base dir
     * @param data    key=yyyy-MM-dd value=\\d+
     * @param desc    desc
     * @param width   width
     * @param height  height
     * @return the path
     */
    public static String buildLineImage(String baseDir, LinkedHashMap<String, Integer> data, String desc, int width, int height) {
        String dir = File.separator + "chart" + File.separator + DateFormatUtils.format(new Date(), "yyyy/MM/dd");
        String fullPath = baseDir + dir;
        File f = new File(fullPath);
        if (!f.exists())
            f.mkdirs();

        String fileName = UUID.randomUUID().toString() + ".jpg";
        String filePath = fullPath + File.separator + fileName;

        try {
            //初始化数据
            String chartTitle = "";//"近七日浏览量";
            String chartSeriesDesc = desc;//"近七日浏览量";//"日访问量曲线";
            String chartXdesc = "";//"日期";
            String chartYdesc = "";//"访问量";
            String timeFormat = "MM-dd";//yyyy-MM-dd
            String periodType = "DAY";
            int dateInterval = 1;
            TimeSeriesCollection dataset = new TimeSeriesCollection();
            TimeSeries timeSeries = new TimeSeries(desc, Day.class);
            Date now = new Date();
            /*for (int i = -6; i <= 0; i++) {
              timeSeries.add(new Day(DateUtils.addDays(now, i)), RandomUtils.nextInt(100000));
            }*/
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                timeSeries.add(new Day(DateUtils.parseDate(entry.getKey(), "yyyy-MM-dd")), entry.getValue());
            }
            dataset.addSeries(timeSeries);
            //生成图表
            JFreeChart chart = JFreeChartFactory.createLineChart(chartTitle, chartXdesc, chartYdesc,
                    periodType, dateInterval, timeFormat, dataset);
            //生成图片
            JFreeChartFactory.drawToOutputStream(filePath, chart, width, height);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dir + File.separator + fileName;
    }
}
