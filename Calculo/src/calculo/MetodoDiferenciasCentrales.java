/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculo;

import org.mariuszgromada.math.mxparser.*;

/**
 *
 * @author Mauricio Castillo
 */
public class MetodoDiferenciasCentrales implements CalculoDerivada {
    
    private Function Funcion;       // La función.
    private double valorH;          // El valor h.
    private double[] lista2;        // Centrada.
    
    
    /**
     *
     * @param pFuncion
     * @param h
     */
    public MetodoDiferenciasCentrales(Function pFuncion)
    {
        this.Funcion = pFuncion;
        this.valorH = 0.1;
        
        // Definimos el tamaño de las lista.
        this.lista2 = new double[5];
        
     }
    
    public double[] getListaCentrada(){
        return this.lista2;
    }
    
    /**
     *
     * @param x
     * @return valorF(x)
     */
    @Override
    public double calcularDerivadaEn(double x){
        
        // Formula centrada.
        double tresPuntosCentrada = x - (2 * valorH);
        double resCentrado;
        
        // Tres puntos centrada.
        for (int i = 0; i < 5; i++){
            double Fx0 = Funcion.calculate(tresPuntosCentrada - (2 * valorH));  // Fx0 - 2h.
            double Fx1 = 8 * Funcion.calculate(tresPuntosCentrada - valorH);    // 8 * (Fx - h).
            double Fx2 = 8 * Funcion.calculate(tresPuntosCentrada + valorH);    // 8 * (Fx + h).
            double Fx3 = Funcion.calculate(tresPuntosCentrada + (2 * valorH));  // Fx0 + 2h.
            
            // Incrementar el X.
            tresPuntosCentrada = tresPuntosCentrada + valorH;               
            
            // Resultado.
            resCentrado = (Fx0 - Fx1 + Fx2 - Fx3) / (12 * valorH);
            lista2[i] = resCentrado;
        }
        
        return lista2[2];
    }
}

