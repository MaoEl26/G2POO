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
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.mariuszgromada.math.mxparser.Function;

/**
 *
 * @author Mauricio Castillo
 */
public class FunctionVisualizer{//extends JFrame
    // define X range
    private int minX=-10;
    private int maxX=10;
    private XYSeries serieFuncion,lineaValorA,lineaValorB;
    private XYSeries serie0,serie1,serie2,serie3,serie4;
    private ArrayList<XYSeries> listaseries;
    private Function funcion;
    private double valorA,valorB,valorX ;
    private XYSeriesCollection dataset;
    private JFreeChart chart;
    private double[] puntosCentrales;       // Arreglo con la derivada de los puntos.
    /**
     *
     * @param funcion
     * @param valorA
     * @param valorB
     */
    public FunctionVisualizer(Function funcion,double valorA, double valorB){
        this.funcion = funcion;
        this.valorA = valorA;
        this.valorB = valorB;
        SerieFuncion();
        SerieValorAyB();
        dataSetIntegral();
    }
    
    /**
     *
     * @param funcion
     * @param valorX
     */
    public FunctionVisualizer(Function funcion,double valorX){
        this.funcion = funcion;
        this.valorX = valorX;
        SerieFuncion();
        SerieValorX();
        dataSetLimit();
    }
    
        // Constructor para el método de Diferencias Centrales.
    public FunctionVisualizer(Function pFuncion, double[] pPuntosCentrales){
        this.funcion = pFuncion;                    // Función dada por el usuario.
        this.puntosCentrales = pPuntosCentrales;    // Arreglo de puntos.
        SerieFuncion();                             // Grafica la función. 
        creaLista();
        SeriesDerivada();
        dataSetDerivate();
    }
    
    private void creaLista(){
        listaseries = new ArrayList<>();
        listaseries.add(serie0 = new XYSeries("Valor 1"));
        listaseries.add(serie1 = new XYSeries("Valor 2"));
        listaseries.add(serie2 = new XYSeries("Valor X"));
        listaseries.add(serie3 = new XYSeries("Valor 3"));
        listaseries.add(serie4 = new XYSeries("Valor 4"));
    }
    
    private void dataSetIntegral(){
        dataset = new XYSeriesCollection();
        dataset.addSeries(serieFuncion);
        dataset.addSeries(lineaValorA);
        dataset.addSeries(lineaValorB);
    }
    
    private void dataSetDerivate(){
        dataset = new XYSeriesCollection();
        dataset.addSeries(serieFuncion);
        dataset.addSeries(serie0);
        dataset.addSeries(serie1);
        dataset.addSeries(serie2);
        dataset.addSeries(serie3);
        dataset.addSeries(serie4);
    }
    
    private void dataSetLimit(){
        dataset = new XYSeriesCollection();
        dataset.addSeries(serieFuncion);
        dataset.addSeries(lineaValorA);
    }
    
    private void SeriesDerivada(){
        for(int x = 0;x<5;x++){
            double resultadoFuncion = funcion.calculate(valorA);
            for (double i = 0; i < resultadoFuncion ; i=i+0.5)
            {
                listaseries.get(x).add(1,2);
            }
        }
    }
    
    private void SerieValorX(){
        lineaValorA = new XYSeries("Valor X");
        for (double i = minX; i < maxX; i=i+0.5)
        {
            lineaValorA.add(valorX,i);
        }
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
    
    /**
     *
     * @return panel
     */
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
    }
}
