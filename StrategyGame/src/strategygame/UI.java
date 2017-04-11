package strategygame;

import java.io.File;
import java.io.Serializable;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class UI implements Serializable{
    private Field Arena;
    private Player player1;
    private Player player2;
    private transient Scanner scanner;
    public static UI juego;
    
    public UI() throws ClassNotFoundException{
    System.out.println(""
                + 
"                  ۞ \n" +
"              ╘╗_۩_╔╛ \n" +
"                ]█▓[ \n" +
"                ]█▓[ \n" +
"                ]█▓[ \n" +
"                ]█▓[ \n" +
"                ]█▓[ \n" +
"                ]█▓[ \n" +
"     ╘╗______╔╝█▓╚╗______╔╛ \n" +
"    ۞]▓▓▓║Strategy game║▓▓▓[۞ \n" +
"     ┌╜‾‾‾‾‾‾ ╚╗░▒╔╝ ‾‾‾‾‾‾╙┐ \n" +
"                ║░▒║ \n" +
"                ║░▒║ \n" +
"                ║░▒║ \n" +
"                ║░▒║ \n" +
"                ║░▒║ \n" +
"                ║░▒║ \n" +
"                ║░▒║ \n" +
"                ║░▒║ \n" +
"                ║░▒║ \n" +
"                ║░▒║ \n" +
"                ║░▒║ \n" +
"                ║░▒║ \n" +
"                ║░▒║ \n" +
"                 ║▒  \n" +
"                  ▼   \n"  + 
"       Welcome to Strategy Game");
        
        // Aquí debería de ir, las opciones de jugar.
        // Ya sea reanudar una partida o iniciar otra.
        
        if (false){
            cargarArchivos();
        }else{
        Arena = new Field(5, 5);
        player1 = new Player("P1", Arena);
        player2 = new Player("P2", Arena);
        
        // Momento donde se inician las tropas y 
        // se escogen sus posiciones.
        
        System.out.println("\nSetup phase.");
        System.out.println("Player 1: Choose your army:");
        
        player1.chooseArmy();
        
        System.out.println("Player 2: Choose your army:");
        player2.chooseArmy();
        
        System.out.println("\n");
        System.out.println("This will be the arena: ");
        Arena.verArena();
        
        System.out.println("Player 1: choose your formation.");
        System.out.println("You can only pick columns." + "\n");
        player1.placeUnits();
        
        System.out.println("Player 2: choose your formation.");
        System.out.println("You can only pick columns." + "\n");
        player2.placeUnits();
        
        System.out.println("Let the battle begin!");
        System.out.println("\\,,/ O.O \\,,/");
        System.out.println("\n");
        }
    }       
public void game(){
        int answer;
        while (player1.lose() == false || player2.lose() == false)
        {   
            System.out.print("Desea Guardar el progreso actual? (Y/N)");
//            answer = scanner.nextInt();

            if(true){
                guardarArchivos();
            }
            player1.play();
            System.out.println("Desea Guardar el progreso actual? (Y/N)");
            //answer = scan.next().toUpperCase();
            
            //if(answer.equals("Y")){
              //  guardarArchivos();
            //}
            player2.play();
        }
}
    
    public static void main(String[] args) throws ClassNotFoundException {
        
        juego = new UI();
        juego.Arena.verArena();
        juego.game();
    }
    public void guardarArchivos(){
        File miFile = new File("prueba");
        FileOutputStream file;
        ObjectOutputStream objeto;
        try{
        // Serializar un objeto de datos a un archivo
            file = new FileOutputStream(miFile);
            objeto = new ObjectOutputStream(file);
            objeto.writeObject(juego);
            objeto.close();
        }
        catch(FileNotFoundException mesage){
            System.out.println("1"+mesage.getMessage());
        }catch(IOException mesage){
            System.out.println("2"+mesage);
        }            
    
    /*
    controla el flujo de juego
    
    Almacena o carga las partidas
    
    el jugador puede guardar la partida en un archivo codificado
    
    cuando se inicia preguntar si partida nueva o juego
    
    3 partidas max
    */
   }
    
    public UI cargarArchivos() throws ClassNotFoundException{
    try{  
      FileInputStream archivoEntrada = new FileInputStream("prueba.txt");
      ObjectInputStream objetoEntrada = new ObjectInputStream(archivoEntrada);
      juego = (UI)objetoEntrada.readObject();
    }catch(FileNotFoundException mesage){
            System.out.println("1"+mesage.getMessage());
        }catch(IOException mesage){
            System.out.println("2"+mesage.getMessage());
        }
      return juego; 
    }
}
    
