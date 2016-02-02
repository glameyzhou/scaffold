package org.glamey.scaffold.chart.jfreechart;


import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.*;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.util.Rotation;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 网站摘录
 *
 * @author zhouyang.zhou
 */

public class JFreeChartFactory {

    public static final String fontsName = "宋体";//"simsunb";

    public static JFreeChart createPieChart(DefaultPieDataset dataset, String title, boolean is3D) {
        JFreeChart chart = null;
        if (is3D) {
            chart = ChartFactory.createPieChart3D(title, // 图表标题
                    dataset, // 数据集
                    true, // 是否显示图例
                    true, // 是否显示工具提示
                    true // 是否生成URL
            );
        } else {
            chart = ChartFactory.createPieChart(title, // 图表标题
                    dataset, // 数据集
                    true, // 是否显示图例
                    true, // 是否显示工具提示
                    true // 是否生成URL
            );
        }
// 设置标题字体==为了防止中文乱码：必须设置字体
        chart.setTitle(new TextTitle(title, new Font("黑体", Font.ITALIC, 22)));
// 设置图例的字体==为了防止中文乱码：必须设置字体
        chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 12));
// 获取饼图的Plot对象(实际图表)
        PiePlot plot = (PiePlot) chart.getPlot();
// 图形边框颜色
        plot.setBaseSectionOutlinePaint(Color.GRAY);
// 图形边框粗细
        plot.setBaseSectionOutlineStroke(new BasicStroke(0.0f));
// 设置饼状图的绘制方向，可以按顺时针方向绘制，也可以按逆时针方向绘制
        plot.setDirection(Rotation.ANTICLOCKWISE);
// 设置绘制角度(图形旋转角度)
        plot.setStartAngle(70);
// 设置突出显示的数据块
// plot.setExplodePercent("One", 0.1D);
// 设置背景色透明度
        plot.setBackgroundAlpha(0.7F);
// 设置前景色透明度
        plot.setForegroundAlpha(0.65F);
// 设置区块标签的字体==为了防止中文乱码：必须设置字体
        plot.setLabelFont(new Font("宋体", Font.PLAIN, 12));
// 扇区分离显示,对3D图不起效
        if (is3D)
            plot.setExplodePercent(dataset.getKey(3), 0.1D);
// 图例显示百分比:自定义方式，{0} 表示选项， {1} 表示数值， {2} 表示所占比例 ,小数点后两位
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0}:{1}\r\n({2})", NumberFormat.getNumberInstance(),
                new DecimalFormat("0.00%")));
// 图例显示百分比
// plot.setLegendLabelGenerator(new
// StandardPieSectionLabelGenerator("{0}={1}({2})"));
// 指定显示的饼图为：圆形(true) 还是椭圆形(false)
        plot.setCircular(true);
// 没有数据的时候显示的内容
        plot.setNoDataMessage("找不到可用数据...");
// 设置鼠标悬停提示
        plot.setToolTipGenerator(new StandardPieToolTipGenerator());
// 设置热点链接
// plot.setURLGenerator(new StandardPieURLGenerator("detail.jsp"));

        return chart;
    }

    public static JFreeChart createBarChart(CategoryDataset dataset,
                                            String title, String x, String y, boolean is3D) {
        JFreeChart chart = null;
        if (is3D) {
            chart = ChartFactory.createBarChart3D( // 3D柱状图
// JFreeChart chart = ChartFactory.createLineChart3D( //3D折线图
                    title, // 图表的标题
                    x, // 目录轴的显示标签
                    y, // 数值轴的显示标签
                    dataset, // 数据集
                    PlotOrientation.VERTICAL, // 图表方式：V垂直;H水平
                    true, // 是否显示图例
                    false, // 是否显示工具提示
                    false // 是否生成URL
            );
        } else {
            chart = ChartFactory.createBarChart( // 柱状图
// JFreeChart chart = ChartFactory.createLineChart3D( //3D折线图
                    title, // 图表的标题
                    x, // 目录轴的显示标签
                    y, // 数值轴的显示标签
                    dataset, // 数据集
                    PlotOrientation.VERTICAL, // 图表方式：V垂直;H水平
                    true, // 是否显示图例
                    false, // 是否显示工具提示
                    false // 是否生成URL
            );
        }

// ===============为了防止中文乱码：必须设置字体
        chart.setTitle(new TextTitle(title, new Font("黑体", Font.ITALIC, 22)));

        LegendTitle legend = chart.getLegend(); // 获取图例
        legend.setItemFont(new Font("宋体", Font.BOLD, 12)); // 设置图例的字体，防止中文乱码

        CategoryPlot plot = (CategoryPlot) chart.getPlot(); // 获取柱图的Plot对象(实际图表)
// 设置柱图背景色（注意，系统取色的时候要使用16位的模式来查看颜色编码，这样比较准确）
        plot.setBackgroundPaint(new Color(255, 255, 204));
        plot.setForegroundAlpha(0.65F); // 设置前景色透明度

// 设置横虚线可见
        plot.setRangeGridlinesVisible(true);
// 虚线色彩
        plot.setRangeGridlinePaint(Color.gray);

        CategoryAxis h = plot.getDomainAxis(); // 获取x轴
        h.setMaximumCategoryLabelWidthRatio(1.0f);// 横轴上的 Lable 是否完整显示
        h.setLabelFont(new Font("宋体", Font.BOLD, 12));// 设置字体，防止中文乱码
        h.setTickLabelFont(new Font("宋体", Font.BOLD, 12));// 轴数值
// h.setCategoryLabelPositions(CategoryLabelPositions.UP_45);//45度倾斜

        plot.getRangeAxis().setLabelFont(new Font("宋体", Font.BOLD, 12)); // Y轴设置字体，防止中文乱码

// 柱图的呈现器
        BarRenderer renderer = new BarRenderer();
// 设置柱子宽度
// renderer.setMaximumBarWidth(0.05);
// 设置柱子高度
// renderer.setMinimumBarLength(0.2);
// 设置柱子边框颜色
        renderer.setBaseOutlinePaint(Color.BLACK);
// 设置柱子边框可见
        renderer.setDrawBarOutline(true);
// 设置每个柱的颜色
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesPaint(1, Color.GREEN);
        renderer.setSeriesPaint(2, Color.RED);
// 设置每个地区所包含的平行柱的之间距离
        renderer.setItemMargin(0.05);
// 显示每个柱的数值，并修改该数值的字体属性
        renderer.setIncludeBaseInRange(true);
        renderer
                .setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
// 设置柱的透明度
        plot.setForegroundAlpha(1.0f);
// 给柱图添加呈现器
        plot.setRenderer(renderer);

// 没有数据的时候显示的内容
        plot.setNoDataMessage("找不到可用数据...");

        return chart;
    }

    public static JFreeChart createLineChart(String chartTitle, String chartXdesc, String chartYdesc,
                                             String periodType, int dateInterval, String timeFormat, XYDataset dataSet) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                chartTitle, chartXdesc, chartYdesc, dataSet, true, false, false
        );
        StandardChartTheme chartTheme = new StandardChartTheme("CN");
        ChartFactory.setChartTheme(chartTheme);
//设置标题
        chart.setTitle(new TextTitle(chartTitle, new Font(/*"黑体"*/fontsName, Font.ITALIC, 22)));
        LegendTitle legend = chart.getLegend();
        legend.setItemFont(new Font(fontsName, Font.BOLD, 12)); // 设置图例的字体，防止中文乱z码
        XYPlot xyplot = chart.getXYPlot();
//    xyplot.setBackgroundPaint(new Color(250, 250,250));
        xyplot.setBackgroundPaint(Color.white);
//    xyplot.setForegroundAlpha(0.65F); // 设置前景色透明度
        xyplot.setForegroundAlpha(1.0F); // 设置前景色透明度
        // 设置横虚线可见
        xyplot.setRangeGridlinesVisible(true);
        // 虚线色彩
        xyplot.setRangeGridlinePaint(Color.gray);
        xyplot.getRangeAxis().setLabelFont(new Font(fontsName, Font.BOLD, 12)); // Y轴设置字体，防止中文乱码
        xyplot.getDomainAxis().setLabelFont(new Font(fontsName, Font.BOLD, 12)); // X轴设置字体，防止中文乱码

        //边框 不显示
        xyplot.setOutlineVisible(false);

        //获得 renderer 注意这里是XYLineAndShapeRenderer ！！
        XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) xyplot.getRenderer();
        xylineandshaperenderer.setShapesVisible(true); //数据点可见
        xylineandshaperenderer.setShapesFilled(true); //数据点被填充即不是空心点
        xylineandshaperenderer.setSeriesFillPaint(0, Color.GREEN); //数据点填充为红色
        xylineandshaperenderer.setSeriesPaint(0, Color.GREEN); //折线为绿色


        xylineandshaperenderer.setUseFillPaint(true); //应用

        ////设置Y轴间隔
        NumberAxis numAxis = (NumberAxis) xyplot.getRangeAxis();
        numAxis.setTickUnit(new NumberTickUnit(1));

        //边框是否显示


        //设置X轴间隔
        DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();
        if (periodType.equalsIgnoreCase("MONTH")) {
            dateaxis.setTickUnit(new DateTickUnit(DateTickUnitType.MONTH, dateInterval));
        } else if (periodType.equalsIgnoreCase("DAY")) {
            dateaxis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, dateInterval));
        } else if (periodType.equalsIgnoreCase("HOUR")) {
            dateaxis.setTickUnit(new DateTickUnit(DateTickUnitType.HOUR, dateInterval));
        }
        dateaxis.setDateFormatOverride(new SimpleDateFormat(timeFormat));
        return chart;
    }

    public static boolean drawToOutputStream(String destPath,
                                             JFreeChart chart, int width, int heigth) {
        FileOutputStream fos = null;
        boolean returnValue = true;
        try {
            fos = new FileOutputStream(destPath);
            ChartUtilities.writeChartAsPNG(fos, // 指定目标输出流
                    chart, // 图表对象
                    width, // 宽
                    heigth, // 高
                    null); // ChartRenderingInfo信息
        } catch (IOException e) {
            e.printStackTrace();
            returnValue = false;
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return returnValue;
    }

    public static void testBar() {
//初始化数据
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(5000, "北京", "Corejava");
        dataset.setValue(3000, "上海", "Corejava");
        dataset.setValue(2000, "广州", "Corejava");

        dataset.setValue(10000, "北京", "JavaWeb");
        dataset.setValue(6000, "上海", "JavaWeb");
        dataset.setValue(4000, "广州", "JavaWeb");

        dataset.setValue(15000, "北京", "易用struts");
        dataset.setValue(5000, "上海", "易用struts");
        dataset.setValue(10000, "广州", "易用struts");

        dataset.setValue(20000, "北京", "精通JSF");
        dataset.setValue(10000, "上海", "精通JSF");
        dataset.setValue(10000, "广州", "精通JSF");
//生成图表
        JFreeChart chart = createBarChart(dataset, "柱状图", "书名", "销售数量", false);
//生成图片
        drawToOutputStream("C:\\Users\\zhaojiqiao\\Desktop\\柱状图.JPG", chart, 640, 480);
    }

    public static void testPie() {
//初始化数据
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue(" 市场前期", new Double(10));
        dataset.setValue(" 立项", new Double(15));
        dataset.setValue(" 计划", new Double(10));
        dataset.setValue(" 需求与设计", new Double(10));
        dataset.setValue(" 执行控制", new Double(35));
        dataset.setValue(" 收尾", new Double(10));
        dataset.setValue(" 运维", new Double(10));
//生成图表
        JFreeChart chart = createPieChart(dataset, "饼图", true);
//生成图片
        drawToOutputStream("D:\\ty120919\\demo\\demo6\\IMG\\饼图.JPG", chart, 640, 480);
    }

    public static void testLine_month() {
//初始化数据
        String chartTitle = "访问量统计图形";
        String chartSeriesDesc = "月访问量曲线";
        String chartXdesc = "时间";
        String chartYdesc = "访问量";
        String timeFormat = "MM";//yyyy-MM-dd
        String periodType = "MONTH";
        int dateInterval = 1;
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        TimeSeries monthseries = new TimeSeries(chartSeriesDesc, Month.class);
        monthseries.add(new Month(1, 2001), 100);
        monthseries.add(new Month(2, 2001), 120);
        monthseries.add(new Month(3, 2001), 70);
        monthseries.add(new Month(4, 2001), 680);
        monthseries.add(new Month(5, 2001), 345);
        monthseries.add(new Month(6, 2001), 430);
        monthseries.add(new Month(7, 2001), 300);
        monthseries.add(new Month(8, 2001), 200);
        monthseries.add(new Month(9, 2001), 190);
        monthseries.add(new Month(10, 2001), 300);
        monthseries.add(new Month(11, 2001), 200);
        monthseries.add(new Month(12, 2001), 240);
//存储至集合对象中
        dataset.addSeries(monthseries);
//生成图表
        JFreeChart chart = createLineChart(chartTitle, chartXdesc, chartYdesc,
                periodType, dateInterval, timeFormat, dataset);
//生成图片
        drawToOutputStream("D:\\曲线-月.JPG", chart, 400, 200);
    }

    public static void testLine_DAY() {
//初始化数据
        String chartTitle = "";//"近七日浏览量";
        String chartSeriesDesc = "近七日浏览量";//"日访问量曲线";
        String chartXdesc = "";//"日期";
        String chartYdesc = "";//"访问量";
        String timeFormat = "MM-dd";//yyyy-MM-dd
        String periodType = "DAY";
        int dateInterval = 1;
        TimeSeriesCollection dataset = new TimeSeriesCollection();
   /* TimeSeries dayseries = new TimeSeries(chartSeriesDesc, Day.class);
    dayseries.add(new Day(1, 1, 2010), 100);
    dayseries.add(new Day(2, 1, 2010), 200);
    dayseries.add(new Day(4, 1, 2010), 400);
    dayseries.add(new Day(5, 1, 2010), 600);
    dayseries.add(new Day(7, 1, 2010), 200);
    dayseries.add(new Day(8, 1, 2010), 400);
    dayseries.add(new Day(12, 1, 2010), 300);
    dayseries.add(new Day(23, 1, 2010), 500);
    dayseries.add(new Day(29, 1, 2010), 300);*/
        TimeSeries timeSeries = new TimeSeries("近七日浏览量", Day.class);
        Date now = new Date();
        for (int i = -6; i <= 0; i++) {
            timeSeries.add(new Day(DateUtils.addDays(now, i)), RandomUtils.nextInt(100000));
        }
        dataset.addSeries(timeSeries);
//生成图表
        JFreeChart chart = createLineChart(chartTitle, chartXdesc, chartYdesc,
                periodType, dateInterval, timeFormat, dataset);
//生成图片
        drawToOutputStream("c:/tmp/曲线-天.JPG", chart, 400, 200);
    }

    public static void testLine_Hour() {
//初始化数据
        String chartTitle = "";
        String chartSeriesDesc = "";
        String chartXdesc = "日期";
        String chartYdesc = "浏览量";
        String timeFormat = "hh";//yyyy-MM-dd
        String periodType = "HOUR";
        int dateInterval = 1;
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        TimeSeries hourseries = new TimeSeries(chartSeriesDesc, Hour.class);
        hourseries.add(new Hour(1, 1, 1, 2010), 100);
        hourseries.add(new Hour(2, 1, 1, 2010), 200);
        hourseries.add(new Hour(3, 1, 1, 2010), 400);
        hourseries.add(new Hour(4, 1, 1, 2010), 600);
        hourseries.add(new Hour(5, 1, 1, 2010), 200);
        hourseries.add(new Hour(6, 1, 1, 2010), 400);
        hourseries.add(new Hour(7, 1, 1, 2010), 300);
        hourseries.add(new Hour(8, 1, 1, 2010), 500);
        hourseries.add(new Hour(9, 1, 1, 2010), 300);
//存储至集合对象中
        dataset.addSeries(hourseries);
//生成图表
        JFreeChart chart = createLineChart(chartTitle, chartXdesc, chartYdesc,
                periodType, dateInterval, timeFormat, dataset);
//生成图片
        drawToOutputStream("/Users/zhouyangzhou/Downloads/曲线-小时.JPG", chart, 640, 480);
    }

    public static void main(String[] args) {
        testPie();
        testBar();
        testLine_month();
        testLine_DAY();
        testLine_Hour();
    }

}