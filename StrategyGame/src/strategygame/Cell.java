package strategygame;

public class Cell {
    private boolean passable;
    private Unit army;
    
    // Método de impresión de la celda, si es franqueable y
    // y si tiene una tropa.
    
    public Cell()
    {
        this.passable = false;
        this.army = null;       // Puesto que todavía no hay nadie.       
    }
    
    public boolean getPassable()
    {
        return this.passable;
    }
    
    // Para poder cambiar el valor.
    
    public void setPassable(boolean value)   
    {
        this.passable = value;
    }
    
    public char getArmy()
    {
        return this.army.getType();
    }
    
    // Información de la celda, franqueabilidad y estado de la unidad.
    
    public void setArmy(Unit army)
    {
        this.army = army;
    }
    
    public String toString()
    {
        String msj = "";
        msj = msj + "Franqueable: " + getPassable() + "\n";
        msj = msj + "Unidad: " + getArmy() + "\n";
        
        if (this.passable == true)
        {
            msj = msj + "Tipo: " + this.army.getType() + "\n";
        }
        
        return msj;
    }
}
