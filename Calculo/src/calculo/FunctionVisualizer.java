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

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.mariuszgromada.math.mxparser.Function;

public class FunctionVisualizer{//extends JFrame
    // define X range
    private int minX=-100;
    private int maxX=100;
    private XYSeries serieFuncion;
    private XYSeries lineaValorA;
    private XYSeries lineaValorB;
    private Function funcion;
    private double valorA,valorB;
    private XYSeriesCollection dataset;
    private JFreeChart chart;

    public FunctionVisualizer(Function funcion,double valorA, double valorB){
        this.funcion = funcion;
        this.valorA = valorA;
        this.valorB = valorB;
        SerieFuncion();
        SerieValorAyB();
        dataSetIntegral();
    }
    
    private void dataSetIntegral(){
        dataset = new XYSeriesCollection();
        dataset.addSeries(serieFuncion);
        dataset.addSeries(lineaValorA);
        dataset.addSeries(lineaValorB);
    }
    
    private void SerieFuncion(){
        serieFuncion = new XYSeries("Función");
        for (double i = minX; i < maxX; i=i+0.5)
        {
            serieFuncion.add(i,funcion.calculate(i));
        }
    }
    
    private void SerieValorAyB(){
        lineaValorA = new XYSeries("Valor A");
        lineaValorB = new XYSeries("Valor B");
        
        double resultadoFuncion = funcion.calculate(valorA);
        for (double i = 0; i < resultadoFuncion ; i=i+0.5)
        {
            lineaValorA.add(valorA,i);
        }
        
        resultadoFuncion = funcion.calculate(valorB);
        for (double i = 0; i < resultadoFuncion ; i=i+0.5)
        {
            lineaValorB.add(valorB,i);
        }
    }
    
    public ChartPanel creacionGrafico(){
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
        return panel;
    
//        getContentPane().setLayout(new FlowLayout());
//        getContentPane().add(panel);
//
//        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//        pack();
//        setVisible(true);
    }
}
