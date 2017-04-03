package strategygame;

public class UI {
    
    public static void main(String[] args) {
        
        Field Arena = new Field(5, 5);
        
        Player player1 = new Player("P1", Arena);
        // Player player2 = new Player("P2", Arena);
        
        System.out.println("Player 1 army:");
        player1.createArmy(2, 1, 1);
        player1.seeArmy();
        
        /*
        System.out.println("Player 2 army:");
        player2.createArmy(0, 1, 2);
        player2.seeArmy();
        */
        
// Jugador 1 ingresa sus primeras tropas en su lado de la arena
        
        player1.addFirstUnits(0, 0);
        player1.addFirstUnits(1, 1);
        player1.addFirstUnits(2, 2);
        player1.addFirstUnits(3, 3);

        // Jugador 2 ingresa sus primeras tropas en su lado de la arena
        /*
        player2.addFirstUnits(0, 0);
        player2.addFirstUnits(1, 1);
        player2.addFirstUnits(2, 2);
        
        */
        // player2.addFirstUnits(3, 3);
        // player2.addFirstUnits(4, 3);
        Arena.verArena();
        
        // Jugador 2 hace un movimiento
        
        player1.move(0, 1, 0);
        player1.move(1, 1, 1);
        // player2.move(0, 3, 2);
        
        Arena.verArena();
        
//        player1.move(2, 3, 0);
//        player2.move(0, 4, 3);
//        Arena.verArena();
//        
//        player2.move(2, 4, 3);
//        Arena.verArena();
        
          
          
    }  
    /*
    controla el flujo de juego
    
    Almacena o carga las partidas
    
    el jugador puede guardar la partida en un archivo codificado
    
    cuando se inicia preguntar si partida nueva o juego
    
    3 partidas max
    */
}
