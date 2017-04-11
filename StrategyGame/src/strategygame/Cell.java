package strategygame;

import java.io.Serializable;

public class Cell implements Serializable{
    
    private boolean passable;
    private Unit army;
    
    // Método de impresión de la celda, si es franqueable y
    // y si tiene una tropa.
    
    public Cell()
    {
        this.passable = false;
        this.army = null;       // Puesto que todavía no hay nadie.       
    }
    
    // Para poder cambiar el valor.
    
    public void setPassable(boolean value)   
    {
        this.passable = value;
    }
    
    public void setArmy(Unit army)
    {
        this.army = army;
    }
    
    public boolean getPassable()
    {
        return this.passable;
    }
        
    public int getId(){
        return this.army.getId();
    }
    
    public char getArmyType()
    {
        return this.army.getType();
    }
    
    public Unit getArmy(){
        return army;
    }
    
    public String getArmyPlayer(){ // Retorna el jugador al que pertenece la unidad
        return this.army.getPlayer();
    }
    
    // Información de la celda, franqueabilidad y estado de la unidad.
    

    @Override
    public String toString()
    {
        String msj = "";
        msj = msj + "Franqueable: " + getPassable() + "\n";
        msj = msj + "Unidad: " + getArmyType() + "\n";
        
        if (this.passable == true)
        {
            msj = msj + "Tipo: " + this.army.getType() + "\n";
        }
        
        return msj;
    }
}
