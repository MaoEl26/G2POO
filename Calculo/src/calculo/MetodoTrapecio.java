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
public class MetodoTrapecio implements CalculoIntegral{
        
    private double valorA,valorB,valorN,valorH;
    private Function funcion;
    
    /**
     *
     * @param funcion
     * @param valorN
     */
    public MetodoTrapecio(Function funcion, int valorN){
        this.funcion = funcion; 
        this.valorN = valorN;
    }
    
    /**
     *
     * @param a
     * @param b
     * @return
     */
    @Override
    public double calcularIntegralDefinida(double a,double b){
        this.valorA = a;
        this.valorB = b;
        calcularValorH();
        
    return evaluarTrapecioExtendido();
    }
      
    private double evaluarTrapecioExtendido(){
        double resultado = ((funcion.calculate(valorA)+funcion.calculate(valorB))/2);
        for (int i = 1; i<valorN;i++){
            resultado += funcion.calculate(valorA+i*valorH);
        }
        
        return resultado*valorH;
    }
    
    private void calcularValorH(){
        valorH = (valorB-valorA)/valorN;
    }
    
}
