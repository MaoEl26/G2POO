package strategygame;

public class Field {
    private int cols, rows;
    private Cell[][] playfield;
    
    public Field(int pRows, int pCols)
    {
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
    
    public int getCols(){
        return this.cols;
    }
    
    public int getRows(){
        return this.rows;
    }
    
    // Verifica si la celda es franqueable.
    
    public boolean isPassable(int pRows, int pCols){ 
        return this.playfield[pRows][pCols].getPassable();
    }
    
    // Retorna la fila actual de la unidad.
    
    public int actualPosRow(Unit unidad){ 
        int posX = 0;
        for (int x = 0; x < this.rows; x++){
            for (int y = 0; y < this.cols; y++){
                if (this.playfield[x][y].getPassable() == true){
                    if (this.playfield[x][y].getId() == unidad.getId()){
                        posX = x;
                        return posX;
                        
                    }
                }
            }
        }
        return 0;
    }
    
    // Retorna la columna actual de la unidad.
    
    public int actualPosCol(Unit unidad){ 
        int posY = 0;
        for (int x = 0; x < this.rows; x++){
            for (int y = 0; y < this.cols; y++){
                if (this.playfield[x][y].getPassable() == true){
                    if (this.playfield[x][y].getId() == unidad.getId()){
                        posY = y;
                        return posY;
                    }
                }
            }
        }
       
        return 0;
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
                    System.out.print("  " + this.playfield[x][y].getArmy() + " ");
                }
                else
                {
                    System.out.print("  - ");
                }
            }
            
            System.out.println("");
        }
    }
}
