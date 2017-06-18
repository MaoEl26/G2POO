/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculo;

/**
 *
 * @author Mauricio Castillo
 */
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class FunctionVisualizer extends JFrame implements ActionListener {
    // define X range
    public int minX=1;
    public int maxX=100;

    JFreeChart chart;

    public FunctionVisualizer(String title){
        super(title);
        // Create a simple XY chart
        XYSeries series = new XYSeries("function");
        for (int i = minX; i < maxX; i++)
        {
            series.add(i,f(i));
        }

        // Add the series to your data set
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        // Generate the graph
        chart = ChartFactory.createXYLineChart(
            "f(x)",                       // Title
            "x",                         // x-axis Label
            "f(x)",                    // y-axis Label
            dataset,                   // Dataset
            PlotOrientation.VERTICAL,  // Plot Orientation
            true,                      // Show Legend
            true,                      // Use tooltips
            false                      // Configure chart to generate URLs?
            );

        ChartPanel panel = new ChartPanel(chart);

        JButton saveButton = new JButton("Save...");
        saveButton.addActionListener(this);

        getContentPane().setLayout(new FlowLayout());
        getContentPane().add(panel);
        getContentPane().add(saveButton);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);    
    }

    private double f(int x){
        int inf=Integer.MAX_VALUE;
        // define your function
        if (x==0) return inf;
        return (x*x);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fc=new JFileChooser("C:/");
        fc.setCurrentDirectory(new File("C:/"));
        fc.setSelectedFile(new File("C:/chart1.jpg"));
        int ret=fc.showSaveDialog(null);
        if(ret==fc.APPROVE_OPTION)
        {
            try {
                System.out.print("Saving"+ fc.getSelectedFile().getAbsolutePath()+"\n");
                ChartUtilities.saveChartAsJPEG(fc.getSelectedFile(), chart, 500, 300);
            } catch (IOException IOe) {
                System.err.println("Problemrred creating chart.");
                IOe.printStackTrace();     
            }
        }

    }
}
