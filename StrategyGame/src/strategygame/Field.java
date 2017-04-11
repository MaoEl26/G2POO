package strategygame;

import java.io.Serializable;

public class Field implements Serializable {
    
    private int cols, rows;
    private Cell[][] playfield;
    
    /**
     *
     * @param pRows
     * @param pCols
     */
    public Field(int pRows, int pCols){
        this.rows = pRows;
        this.cols = pCols;
        
        // Se crea el arreglo doble. Es una matriz.
        
        this.playfield = new Cell[this.rows][this.cols];
        
        for (int x = 0; x < this.rows; x++)
        {
            for (int y = 0; y < this.cols; y++)
            {
                this.playfield[x][y] = new Cell();
            }
        }
    }
    
    public void setCols(int pCols){
        this.cols = pCols;
    }
    
    public void setRows(int pRows){
        this.rows = pRows;
    }
    
    // Agrega la unidad a la celda.
    
    public void setUnitInCell(int pRow, int pCol, Unit unidad){ 
        this.playfield[pRow][pCol].setArmy(unidad);
    }
    
    // Cambia el estado "passable" de la celda.
    
    public void setPassableCell(int pRow, int pCol, boolean passable){ 
        this.playfield[pRow][pCol].setPassable(passable);
    }
    
    public Unit getUnitInCell(int pRow, int pCol){
        return this.playfield[pRow][pCol].getArmy();
    }
    
    // Retorna el jugador al que pertenece la unidad en una celda.
    
    public String getPlayerUnitInCell(int pRow, int pCol){ 
        return this.playfield[pRow][pCol].getArmyPlayer();
    }
    
    public int getCols(){
        return cols;
    }
    
    public int getRows(){
        return rows;
    }
    
    // Verifica si la celda es franqueable.
    
    public boolean isPassable(int pRows, int pCols){ 
        return this.playfield[pRows][pCols].getPassable();
    }
        
    public void verArena()    // Ver la arena.
    {
        // Números de arriba.
        
        for (int z = 0; z < this.cols; z++)
        {
            System.out.print("   " + z + "");
        }
        
        System.out.println();
        
        // Resto del tablero.
        
        for (int x = 0; x < this.rows; x++)
        {
            System.out.print(x);
            
            for (int y = 0; y < this.cols; y++)
            {
                
                // Si hay una unidad.
                
                if (this.playfield[x][y].getPassable() == true)
                {
                    System.out.print("  " + this.playfield[x][y].getArmyType() + " ");
                }
                else
                {
                    System.out.print("  - ");
                }
            }
            
            System.out.println("");
        }
        System.out.printf("\n");
    }
}
