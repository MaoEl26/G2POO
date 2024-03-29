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
public class FunctionVisualizer{
    
    private int minX=-10;//Valores minimos para el ciclo de la funcion(eje x)
    private int maxX=10;//Valores maximos para el calculo de la funcion(eje x)
    private XYSeries serieFuncion,lineaValorA,lineaValorB; //Valores de los 
        //puntos X Y de la funcion y puntos de la integral e indeterminación 
        //del limite
    private XYSeries serie0,serie1,serie2,serie3,serie4; // Puntos X Y para la 
        //graficacion de los puntos centrales de la derivada
    private ArrayList<XYSeries> listaseries; //Lista de puntos para la derivada
    private Function funcion; //Funcion matematica ingresada
    private double valorA,valorB,valorX ; //valores de los puntos a graficar
    private XYSeriesCollection dataset; //Conjunto de puntos para mostrar en la 
                                        //grafica
    private JFreeChart chart; //Conjunto de puntos graficados
    private double[] puntosCentrales;// Arreglo con la derivada de los puntos.
    /**
     *
     * @param funcion
     * @param valorA
     * @param valorB
     */
    public FunctionVisualizer(Function funcion,double valorA, double valorB){
        //Constructor para el calculo de la integral en la  gráfica
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
        //Constructor para la graficación del limite
        this.funcion = funcion;
        this.valorX = valorX;
        SerieFuncion();
        SerieValorX();
        dataSetLimit();
    }
    
        // Constructor para el método de Diferencias Centrales.

    /**
     *
     * @param pFuncion
     * @param pPuntosCentrales
     * @param pValorX
     */
public FunctionVisualizer(Function pFuncion, double[] pPuntosCentrales, 
            double pValorX){
        this.funcion = pFuncion;                    // Función dada por el usuario.
        this.puntosCentrales = pPuntosCentrales;    // Arreglo de puntos.
        this.valorX = pValorX;
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
        // Dos puntos hacia atrás.
        valorX = valorX - (2 * 0.1);
        for(int x = 0;x<5;x++){
            double resultadoFuncion = puntosCentrales[x];
            for (double i = 0; i <= resultadoFuncion ; i=i+0.5)
            {
                listaseries.get(x).add(valorX, i);
            }
            valorX = valorX + 0.1;     
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
