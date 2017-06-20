/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculo;

import org.mariuszgromada.math.mxparser.Function;
import java.util.ArrayList;
import java.text.DecimalFormat;

/**
 *
 * @author Mauricio Castillo
 */
public class MetodoAproximacion implements CalculoLimite{
    
    private Function funcion;
    private double valorX;
    private ArrayList<Double> tendenciaSuperior;
    private ArrayList<Double> tendenciaInferior;
    
    /**
     *
     * @param funcion
     */
    public MetodoAproximacion(Function funcion){
        this.funcion = funcion;
    }
    
    /**
     *
     * @param x
     * @return
     */
    @Override
    public Double LimiteEn(double x){
        /*
        Inicializa los valores 
        Calcula el limite superior e inferior de los valor ingresado
        si es igual devuelve el valor calculado
        */
        this.valorX = x;
        tendenciaSuperior = CalculoLimiteSuperior();
        tendenciaInferior = CalculoLimiteInferior();
        double valorInferior = tendenciaInferior.get(tendenciaInferior.size()-1);
        double valorSuperior = tendenciaSuperior.get(tendenciaSuperior.size()-1);
        if(valorInferior==valorSuperior){
           return valorInferior;
        }
        return null;
    }

    /**
     *
     * @return
     */
    public ArrayList<Double> getTendenciaSuperior(){
        return tendenciaSuperior;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<Double> getTendenciaInferior(){
        return tendenciaInferior;
    }
    
    private ArrayList<Double> CalculoLimiteSuperior(){
        /*
        Calcula el limite superior mediante un ciclo de 4 puntos
        y los almacena en un arreglo
        */
        double restaValor = 0.1;
        double valorSuperior = valorX+restaValor;
        DecimalFormat formato = new DecimalFormat("######.####");
        ArrayList<Double> resultado = new ArrayList<>();
        for(int i =0;i<=3;i++){
            String aux = formato.format(funcion.calculate(valorSuperior));
            resultado.add(Double.parseDouble(aux));
            restaValor=restaValor*0.1;
            restaValor = Double.parseDouble(formato.format(restaValor));
            valorSuperior=valorX+restaValor;
        }
        return resultado;
    }
    
    private ArrayList<Double> CalculoLimiteInferior(){
         /*
        Calcula el limite inferior mediante un ciclo de 4 puntos 
        y los almacena en un arreglo
        */
        double sumaValor = 0.1;
        double valorInferior = valorX-sumaValor;
        DecimalFormat formato = new DecimalFormat("######.####");
        ArrayList<Double> resultado = new ArrayList<>();
        for(int i =0;i<=3;i++){
            String aux = formato.format(funcion.calculate(valorInferior));
            resultado.add(Double.parseDouble(aux));
            sumaValor=sumaValor*0.1;
            sumaValor = Double.parseDouble(formato.format(sumaValor));
            valorInferior=valorX-sumaValor;
        }
        return resultado;
    }
}
