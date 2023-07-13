

import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ifeng.ad.mutacenter.lianghua.JijinData;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;

public class Test2 {
    public static void testKKK() {
        setLanguageCN();
        //testBar();
        testLine();
        //testPie();
    }

    /**
     * 设置语言，防止中文乱码
     */
    public static void setLanguageCN() {
        // 创建主题样式
        StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
        // 设置标题字体
        standardChartTheme.setExtraLargeFont(new Font("宋书", Font.BOLD, 20));
        // 设置图例的字体
        standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 15));
        // 设置轴向的字体
        standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 15));
        // 应用主题样式
        ChartFactory.setChartTheme(standardChartTheme);
    }

    /**
     * 测试柱状图
     */
    public static void testBar() {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        dataSet.setValue(123, "a", "苹果");
        dataSet.setValue(321, "b", "葡萄");
        dataSet.setValue(456, "c", "香蕉");
        dataSet.setValue(654, "d", "西瓜");
        JFreeChart chart = ChartFactory.createBarChart("水果销量统计表", "水果种类", "销量", dataSet, PlotOrientation.HORIZONTAL,
                true, true, true);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("C:\\work\\java\\mutacenter\\testBar.jpg");
            ChartUtils.writeChartAsJPEG(out, 0.5f, chart, 1200, 900, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 测试时间序列的折线图
     */
    public static void testLine() {
        TimeSeries ts = new TimeSeries("测试例子");
        ts.add(new TimeSeriesDataItem(new Day(string2Date("2020-05-01")), new Double(Math.random() * 100)));
        ts.add(new TimeSeriesDataItem(new Day(string2Date("2020-05-02")), new Double(Math.random() * 100)));
        ts.add(new TimeSeriesDataItem(new Day(string2Date("2020-05-03")), new Double(Math.random() * 100)));
        ts.add(new TimeSeriesDataItem(new Day(string2Date("2020-05-04")), new Double(Math.random() * 100)));
        ts.add(new TimeSeriesDataItem(new Day(string2Date("2020-05-05")), new Double(Math.random() * 100)));
        ts.add(new TimeSeriesDataItem(new Day(string2Date("2020-05-06")), new Double(Math.random() * 100)));
        ts.add(new TimeSeriesDataItem(new Day(string2Date("2020-05-07")), new Double(Math.random() * 100)));
        ts.add(new TimeSeriesDataItem(new Day(string2Date("2020-05-08")), new Double(Math.random() * 100)));

        TimeSeries ts1 = new TimeSeries("测试例子1");
        ts1.add(new TimeSeriesDataItem(new Day(string2Date("2020-05-01")), new Double(Math.random() * 200)));
        ts1.add(new TimeSeriesDataItem(new Day(string2Date("2020-05-02")), new Double(Math.random() * 200)));
        ts1.add(new TimeSeriesDataItem(new Day(string2Date("2020-05-03")), new Double(Math.random() * 200)));
        ts1.add(new TimeSeriesDataItem(new Day(string2Date("2020-05-04")), new Double(Math.random() * 200)));
        ts1.add(new TimeSeriesDataItem(new Day(string2Date("2020-05-05")), new Double(Math.random() * 200)));
        ts1.add(new TimeSeriesDataItem(new Day(string2Date("2020-05-06")), new Double(Math.random() * 200)));
        ts1.add(new TimeSeriesDataItem(new Day(string2Date("2020-05-07")), new Double(Math.random() * 200)));
        ts1.add(new TimeSeriesDataItem(new Day(string2Date("2020-05-08")), new Double(Math.random() * 200)));


        TimeSeriesCollection dataSet = new TimeSeriesCollection();
        dataSet.addSeries(ts);
        dataSet.addSeries(ts1);
        JFreeChart chart = ChartFactory.createTimeSeriesChart("某软件用户数量变化", "日期", "用户数量", dataSet, true, true, false);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("C:\\work\\java\\mutacenter\\testLine.jpg");
            ChartUtils.writeChartAsJPEG(out, 0.5f, chart, 1200, 900, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void toLine(List<JijinData> ls,String dir) {
        String fundcode = ls.get(0).getFundcode();
        String name = ls.get(0).getName();
        TimeSeries jz = new TimeSeries("jz");
        TimeSeries d20jz = new TimeSeries("d20jz");
        TimeSeries grz = new TimeSeries("grz");
        TimeSeries glz = new TimeSeries("glz");
        for(JijinData d:ls){
            jz.add(new TimeSeriesDataItem(new Day(string2Date(d.getJzrq())), new Double(d.getDwjz())));
            d20jz.add(new TimeSeriesDataItem(new Day(string2Date(d.getJzrq())), new Double(d.getD20jz())));
            grz.add(new TimeSeriesDataItem(new Day(string2Date(d.getJzrq())), new Double(d.getGrz())));
            glz.add(new TimeSeriesDataItem(new Day(string2Date(d.getJzrq())), new Double(d.getGlz())));
        }

        TimeSeriesCollection dataSet = new TimeSeriesCollection();
        dataSet.addSeries(jz);
        dataSet.addSeries(d20jz);
        dataSet.addSeries(grz);
        dataSet.addSeries(glz);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(name, "Date", "Price", dataSet, true, true, false);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(String.format("C:\\work\\java\\mutacenter\\%s\\line_%s.jpg",dir,fundcode));
            ChartUtils.writeChartAsJPEG(out, 0.5f, chart, 1200, 900, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 测试饼状图
     */
    public static void testPie() {
        DefaultPieDataset dataSet = new DefaultPieDataset();
        dataSet.setValue("西瓜", 100);
        dataSet.setValue("苹果", 80);
        dataSet.setValue("梨子", 120);
        dataSet.setValue("香蕉", 300);
        JFreeChart chart = ChartFactory.createPieChart("水果销量统计表", dataSet, true, true, true);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("C:\\work\\java\\mutacenter\\testPie.jpg");
            ChartUtils.writeChartAsJPEG(out, 0.5f, chart, 1200, 900, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    /**
     * 将日期字符串转为Date对象
     *
     * @param date 日期字符串
     * @return Date对象
     */
    private static Date string2Date(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date day = null;
        try {
            day = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

}

