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

public final class UI implements Serializable{
    private Field Arena;
    private Player player1;
    private Player player2;
    private transient Scanner scanner;
    private static transient Scanner stScanner;
    public static UI juego;
    private String answer ;
    private int cantidadPartidas = 1;
    private static int cargado;
    private static String cargarArchivo;
    
    public UI() throws ClassNotFoundException{
        /*
        Constructor del juego
        Objeto que es serializado en el momento de guardar la partida
        Se crean los ejercitos y se ubican en la matriz de juego
        */
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
      
    private void game(){
        /*
        Controla el flujo del juego y el almacenamiento de partidas
        */
        scanner = new Scanner(System.in);
        while (player1.lose() == false || player2.lose() == false)
        {   
            System.out.print(player1.getName()+"\nDo you"
                    + " want to save the current progress? (Y/N)");
            answer = scanner.next().toUpperCase();
            
            if("Y".equals(answer)){
                guardarArchivos();
            }
            
            player1.play();
            System.out.println(player1.getName()+"\nDo you "
                    + "want to save the current progress? (Y/N)");
            answer = scanner.next().toUpperCase();
            
            if(answer.equals("Y")){
                guardarArchivos();
            }
            player2.play();
        }
}
     
    private void guardarArchivos(){
        /*
    controla el flujo de juego
    
    Almacena o carga las partidas
    
    el jugador puede guardar la partida en un archivo codificado
    
    cuando se inicia preguntar si partida nueva o juego
    
    3 partidas max
    */
        FileOutputStream file;
        ObjectOutputStream objeto;
        scanner = new Scanner(System.in);
        boolean flag = true;
        boolean overWrite = false;
        while(flag){
            File miFile = new File("StrategyGame "+cantidadPartidas);
            if(!miFile.exists() || overWrite != false){
                try{
                // Serializar un objeto de datos a un archivo
                    file = new FileOutputStream(miFile);
                    objeto = new ObjectOutputStream(file);
                    objeto.writeObject(juego);
                    objeto.close();
                    file.close();
                    flag = false;
                    System.out.println(miFile.getName()+" was stored");
                }
                catch(FileNotFoundException mesage){
                    System.out.println("1"+mesage.getMessage());
                }catch(IOException mesage){
                    System.out.println("2"+mesage);
                } 
            }else{
                cantidadPartidas++;
                if (cantidadPartidas>3){
                    System.out.println("Do you want to overwrite a game?(Y/N)");
                    String respuesta = scanner.next().toUpperCase();
                    if ("Y".equals(respuesta)){
                        System.out.println("Enter the number(1 - 2 - 3)");
                        cantidadPartidas = scanner.nextInt();
                        overWrite = true;
                    }else{
                        System.out.println("The game was not stored");
                        flag = false;
                    }
                }
            }
        } 
   }
    
    private static UI cargarArchivos() throws ClassNotFoundException{
        /*
        Carga una de las 3 partidas almacenadas
        Si se ingresa un numero no valido retorna una partida nula
        y se crea un juego nuevo
        */
        UI nuevoJuego = null;
        System.out.println("Enter the stored game number you want to load (1 - 2 - 3)");
        cargado = stScanner.nextInt();
        
        if ((cargado>=1)&&(cargado<=3)){
            File miFile = new File("StrategyGame "+cargado);
            System.out.println(miFile.getName());
            try{  
                FileInputStream archivoEntrada = new FileInputStream(miFile);
                ObjectInputStream objetoEntrada = new ObjectInputStream(archivoEntrada);
                nuevoJuego = (UI) objetoEntrada.readObject();
            }catch(FileNotFoundException mesage){
                System.out.println("1"+mesage.getMessage());
            }catch(IOException mesage){
                System.out.println("2"+mesage.getMessage());
            } 
        }else{
            System.out.println("The stored game doesn't exist");
        }
        return nuevoJuego; 
    }
    
    public static void main(String[] args) throws ClassNotFoundException {
        /*
        Da el mensaje de bienvenida
        Se selecciona si se desea cargar una partida guardada
        o crear un juego nuevo
        Valida si la partida es valida
        sino crea una nueva partida 
        */
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
        
        stScanner = new Scanner(System.in);
        System.out.println("Do you want to load a game? (Y/N)");
        cargarArchivo = stScanner.next().toUpperCase();
        if ("Y".equals(cargarArchivo)){
            juego = cargarArchivos();
        }else{
            juego = new UI();
        }
        if(juego != null){
            juego.game();
        }else{
           System.out.println("New game created");
           juego = new UI();
           juego.game();
        }
    }
}
    
