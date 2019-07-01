/*
 * 
 * 
 * 
 */
package arduino_project;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Ryan Rusli 2201832446
 */
public class HumidityCharter extends JFrame implements Charter{
    
    public HumidityCharter(ArrayList<datum> values){
        
        super("Humidity Graph");    
        
        JPanel panel = createChartPanel(values);
        setSize(640, 480);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        panel.setVisible(true);
        setContentPane(panel);
        
        
    }
    
    @Override
    public JPanel createChartPanel(ArrayList<datum> values) {
        
        String chartTitle = "Humidity Readings";
        String xLabel = "Time";
        String yLabel = "Reading";
        
        XYDataset dataset = createDataset(values);
        JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, xLabel, yLabel, dataset);
        
        customizeChart(chart,values);
        File imageFile = new File("Humidity_Chart.png");
        int width = 640;
        int height = 480;
    
        try {
            ChartUtilities.saveChartAsPNG(imageFile, chart, width, height);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    return new ChartPanel(chart);
    
    }
    
        
    private XYDataset createDataset(ArrayList<datum> values) {   
        XYSeriesCollection dataset = new XYSeriesCollection();
        //XYSeries templine = new XYSeries("Temperature");
        XYSeries humline = new XYSeries("Humidity");
  
        System.out.println(values.size());
        for (int i = 0; i < values.size(); i++){
            System.out.println(values.get(i).getTemperature() + " " + values.get(i).getID());
            humline.add(i,values.get(i).getHumidity());

        }
        dataset.addSeries(humline);                
        return dataset;
    }

    private void customizeChart(JFreeChart chart, ArrayList<datum> values) {   
        //chart customization 
        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);
        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        NumberAxis range = (NumberAxis) plot.getRangeAxis();


        domain.setTickUnit(new NumberTickUnit(1));
        domain.setAttributedLabel("Time in Hours");
        range.setRange(0, 100);
        range.setTickUnit(new NumberTickUnit(5));
        
        // sets paint color for each series
        //renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(0, Color.BLUE);
        //renderer.setSeriesPaint(2, Color.YELLOW);
         
        // sets thickness for series (using strokes)
        //renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesStroke(0, new BasicStroke(3.0f));
        //renderer.setSeriesStroke(2, new BasicStroke(2.0f));

        // sets paint color for plot outlines
        plot.setOutlinePaint(Color.BLUE);
        plot.setOutlineStroke(new BasicStroke(2.0f));

        // sets renderer for lines
        plot.setRenderer(renderer);

        // sets plot background
        plot.setBackgroundPaint(Color.DARK_GRAY);

        // sets paint color for the grid lines
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);
        
        if (values.size() > 24){
            domain.setAttributedLabel("Time in days");
            customAxis hourAxis = new customAxis();
            chart.getXYPlot().setDomainAxis(hourAxis);
           
            
        }
        domain.setRange(0,values.size()-1);

    
    }   
    
}
