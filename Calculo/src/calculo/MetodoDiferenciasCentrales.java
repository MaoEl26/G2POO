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
    private double[] lista1;        // Regresiva.
    private double[] lista2;        // Centrada.
    private double[] lista3;        // Progresiva.
    
    public MetodoDiferenciasCentrales(Function pFuncion, double h)
    {
        this.Funcion = pFuncion;
        this.valorH = h;
        
        // Definimos el tamaño de las listas.
        this.lista1 = new double[4];
        this.lista2 = new double[6];
        this.lista3 = new double[4];
     }
    
    public double[] getListaRegresiva(){
        return this.lista1; 
    }
    
    public double[] getListaCentrada(){
        return this.lista2;
    }
    
    public double[] getListaProgresiva(){
        return this.lista3;
    }
    
    @Override
    public double calcularDerivadaEn(double x){
        
        // Formula regresiva.
        double tresPuntosAtras = x;
        double resRegresivo;
        
        System.out.println("Diferenciacion Regresiva: ");

        // Tres puntos atrás.
        for (int i = 0; i <= 3; i++){
            double Fx0 = Funcion.calculate(tresPuntosAtras - 2 * valorH);   // Fx0 - 2h.
            double Fx1 = - 4 * Funcion.calculate(tresPuntosAtras - valorH); // 4 * (Fx0 - h).
            double Fx2 = 3 * Funcion.calculate(tresPuntosAtras);            // 3 * Fx0
            
            // Restar al X.
            tresPuntosAtras = tresPuntosAtras - valorH;
            
            // Resultado.
            resRegresivo = (Fx0 + Fx1 + Fx2) / (2 * valorH);
            lista1[i] = resRegresivo;
            
            System.out.println(i + " Punto(s) atrás");
            System.out.println("Valor: " + resRegresivo);
        }
        
        // Formula centrada.
        double tresPuntosCentrada = x - (2 * valorH);
        double resCentrado;
        
        System.out.println("-----------------------------");
        System.out.println("Diferenciación centrada: ");
        
        // Tres puntos centrada.
        for (int i = 0; i <= 5; i++){
            double Fx0 = Funcion.calculate(tresPuntosCentrada - (2 * valorH));  // Fx0 - 2h.
            double Fx1 = 8 * Funcion.calculate(tresPuntosCentrada - valorH);    // 8 * (Fx - h).
            double Fx2 = 8 * Funcion.calculate(tresPuntosCentrada + valorH);    // 8 * (Fx + h).
            double Fx3 = Funcion.calculate(tresPuntosCentrada + (2 * valorH));  // Fx0 + 2h.
            
            // Incrementar el X.
            tresPuntosCentrada = tresPuntosCentrada + valorH;               
            
            // Resultado.
            resCentrado = (Fx0 - Fx1 + Fx2 - Fx3) / (12 * valorH);
            lista2[i] = resCentrado;
            
            System.out.println(i + " Punto(s) atrás");
            System.out.println("Valor: " + resCentrado);
        }
        
        System.out.println("---------------------------");
        System.out.println("Diferenciación Progresiva.");
        
        // Formula progresiva.
        double tresPuntosProgresivo = x;
        double resProgresivo;
        
        // Tres puntos centrados.
        for (int i = 0; i <= 3; i++){
            double Fx0 = 3 * Funcion.calculate(tresPuntosProgresivo);            // 3 * Fx0.
            double Fx1 = 4 * Funcion.calculate(tresPuntosProgresivo + valorH);   // 4 * (Fx0 + h).
            double Fx2 = Funcion.calculate(tresPuntosProgresivo + (2 * valorH)); // Fx0 + 2h.
            
            // Incrementar el X.
            tresPuntosProgresivo = tresPuntosProgresivo + valorH;
            
            // Resultado.
            resProgresivo = ((-Fx0) + Fx1 - Fx2) / (2 * valorH);
            lista3[i] = resProgresivo;
            
            System.out.println(i + " Punto(s) atrás");
            System.out.println("Valor: " + resProgresivo);
        }
        
        return x;
    }
}
