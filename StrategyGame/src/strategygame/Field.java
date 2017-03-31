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
                    System.out.print(this.playfield[x][y].getArmy());
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
