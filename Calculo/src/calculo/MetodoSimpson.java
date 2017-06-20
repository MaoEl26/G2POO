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

import org.mariuszgromada.math.mxparser.*;

/**
 *
 * @author Mauricio Castillo
 */
public class MetodoSimpson implements CalculoIntegral {
    private double valorA,valorB,valorN,valorH;
    private Function funcion;
    
    /**
     *
     * @param funcion
     * @param valorN
     */
    public MetodoSimpson(Function funcion,int valorN){
        //Inicializa los valores del metodo
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
        //Setea los valores de los puntos a,b y 
        //realiza los calculos necesarios
        this.valorA = a;
        this.valorB = b;
        calcularValorH();
        return evaluarSimpsonExtendido();
    }
    
    private double evaluarSimpsonExtendido(){
        /*
        Realiza los calculos de la sumatoria por medio del metodo de Simpson
        */
        double resultado = funcion.calculate(valorA);
        double resultadosPares = 0,resultadosImpares = 0;
        for(int i=1; i<valorN;i++){
            if((i%2)!=0){
               resultadosImpares += funcion.calculate(valorA+i*valorH); 
            }else{
            resultadosPares += funcion.calculate(valorA+i*valorH);
            }
        }
        resultado += (2*resultadosPares)+(4*resultadosImpares);
        resultado += funcion.calculate(valorB);
        resultado *= valorH/3;
        return resultado;
    }
    
    private void calcularValorH(){
        //Calcula el ancho del segmento de la integral
        valorH = (valorB-valorA)/valorN;
    }
}
