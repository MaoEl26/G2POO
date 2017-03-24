
package strategygame;

/**
 *
 * @author Mauricio Castillo
 */
public class Player {
    private int maxCost;
    private Unit[] army;
    private Field playField;
    private String name;
    private int score;
    
    // createArmy 3 ints: cantidad de tropas y que no exceda el maxCost
    
    // move: 2 ints: "x" y "y", ret false si no es franqueable la pos, 
    // de lo contrario asignar nuevas coords;
    
    // attack: 2 ints: "x" y "y", validar si estan dentro del rango de ataque,
    // sino ret false; si ataque de A > a def de D restar puntos de vida a D
    // de lo contrario no sucede nada
    
    // play: metodo de interacion con la lista de unidades del jugador, 
    // si no desea mover una ingresar el par especial (-1,-1)
}
