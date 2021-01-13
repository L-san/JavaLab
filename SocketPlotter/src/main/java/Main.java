import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {
    private static final ArrayBlockingQueue<String> exchanger = new ArrayBlockingQueue<>(6);
    private final JFrame frame = new JFrame("Plotter");
    private final int width;
    private final int height;
    private JPanel panel = new JPanel();
    private XYSeries series = new XYSeries(" ");
    private XYSeries series2 = new XYSeries(" ");

    Main() {
        width = 1280;
        height = 720;
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(width, height));
        frame.setLocationRelativeTo(null);
        panel.add(repaintChart(series,series2));

        Timer t = new Timer(1, e -> {
            try {
                updateSeries();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });
        t.start();

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public ChartPanel repaintChart(XYSeries series, XYSeries series2) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(series2);
        JFreeChart chart = ChartFactory.createXYLineChart(null,
                "x",
                "y",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false);
        XYPlot plot = chart.getXYPlot();
        var renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRenderer(renderer);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(5 * width / 7, height - 200));
        chartPanel.setBackground(new Color(0xEEEEEE));
        return chartPanel;
    }

    public void updateSeries() throws InterruptedException {
        String[] inArray = exchanger.take().split(" ");
        Double[] outArray = new Double[inArray.length];
        for (int i = 0; i < inArray.length; i++) {
            outArray[i] = Double.parseDouble(inArray[i]);
        }

        series.add(series.getItemCount()+1, outArray[0]);
        series2.add(series.getItemCount()+1, outArray[3]);
        panel.removeAll();
        panel.revalidate();
        panel.add(repaintChart(series,series2));
    }

    public static void main(String[] args) {
        Thread server = new Server(exchanger);
        server.setDaemon(true);
        server.start();
        new Main();

    }
}
