package strategygame;

public class Player {
    private int maxCost;
    private Unit[] army;
    private Field playField;
    private String name;
    private int score;
    private int lastUnit; // Sirve para ir guardando las unidades en Unit.
    
    // createArmy 3 ints: cantidad de tropas y que no exceda el maxCost.
    
    // move: 2 ints: "x" y "y", ret false si no es franqueable la pos, 
    // de lo contrario asignar nuevas coords.
    
    // attack: 2 ints: "x" y "y", validar si están dentro del rango de ataque,
    // sino ret false; si ataque de A > a def de D restar puntos de vida a D
    // de lo contrario no sucede nada.
    
    // play: metodo de interacción con la lista de unidades del jugador, 
    // si no desea mover una ingresar el par especial (-1,-1).
    
    public Player(String name, Field pPlayfield){
        this.name = name;
        this.maxCost = 15; // x2 lanceros, x1 caballeria, x1 arquero
        this.army = new Unit[5];
        this.playField = pPlayfield;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }
    
    public int getMaxCost(){
        return this.maxCost;
    }
    
    public void createArmy(int numberOfLancer, int numberOfCavalry, int numberOfArcher){
        if (numberOfLancer * 3 + numberOfCavalry * 5 + numberOfArcher * 4 <= maxCost){
            for (int i = 0; i < numberOfLancer; i++){
                this.army[lastUnit] = new Unit(lastUnit, 'L', "Lancer", 20, 20, 10, 10, 1, 1, 0, 1, 3); // Agrega unidad Lancer
                this.lastUnit++;
            }
            
            for (int i = 0; i < numberOfCavalry; i++){
                this.army[lastUnit] = new Unit(lastUnit, 'C', "Cavalry", 25, 25, 15, 5, 1, 1, 0, 3, 5); // agrega unidad Cavalry
                this.lastUnit++;
            }
            
            for (int i = 0; i < numberOfArcher; i++){
                this.army[lastUnit] = new Unit(lastUnit, 'A', "Archer", 15, 15, 10, 5, 3, 1, 0, 1, 4); // Agrega unidad Archer
                this.lastUnit++;
            }
        }
        else{
            System.out.println("Max Cost reached");
        }  
    }
    
    // Verifica la flanqueabilidad de una celda y si la posición en la
    // fila y columna está entre 0 y el tamaño de la fila o columna - 1.    
    
    public boolean cellRestrictions(int posRow, int posCol){                                                      
        int maxPosRow = this.playField.getRows() - 1; // Máxima posición en la fila
        int maxPosCol = this.playField.getCols() - 1; // Máxima posición en la columna
        
        if (0 <= posRow && posRow <= maxPosRow && 0 <= posCol && posCol <= maxPosCol 
                && this.playField.isPassable(posRow, posCol) == false){
            return true;
        }
        else
        {
            return false;
        }
        
    }
    
    // O simplemente poner en createArmy la pos de cada bicho con i-
    // Agrega las primeras unidades en la primera fila del lado respectivo de cada jugador
    
    public void addFirstUnits(int id, int posCol){ 
        int posRow = 0;
        
        if (getName() == "P2"){ 
            posRow = this.playField.getRows() - 1;
        }

        if (0 <= id && id < lastUnit && cellRestrictions(posRow, posCol) == true){
            this.playField.setUnitInCell(posRow, posCol, this.army[id]);
                                                   //this.playField.actualPosCol(this.army[id])
//            this.playField.setPassableCell(posRow, posCol, false); // Quita la unidad de la posición actual para que pueda moverse
            this.playField.setPassableCell(posRow, posCol, true);
        }
        else if (!(0 <= id && id < lastUnit)){
            System.out.println("No existe una unidad con ese id");
        }
        else{
            System.out.println("Celda ocupada");
        }
    }
    
    // Verifica las restricciones de movimiento.
    
    public boolean moveRestrictions(Unit unidad, int posActualRow, int posRow, 
            int posActualCol, int posCol){ 
        
        // Guardan la suma o resta de la posición en la fila con el movimiento dependiendo del jugador
        // ya que el jugador 1 empieza en la fila 0 y el jugador 2 en la última fila
        
        int actualRow_Movement; 
        int actualCol_Movement;
        int actualRowMinusMovement;
        int actualColMinusMovement;
        
        if (getName() == "P1"){ // Como el jugador 1 está arriba entonces se le deben ir sumando las posiciones ya que debe ir "bajando"
            actualRow_Movement = posActualRow + unidad.getMovement(); 
            actualCol_Movement = posActualCol + unidad.getMovement();
            actualRowMinusMovement = posActualRow - unidad.getMovement();
            actualColMinusMovement = posActualCol - unidad.getMovement();
        }
        
        else{ // Como el jugador 2 está abajo entonces se le deben ir restando las posiciones ya que debe ir "subiendo"
            actualRow_Movement = posActualRow - unidad.getMovement();
            actualCol_Movement = posActualCol + unidad.getMovement();
            actualRowMinusMovement = posActualRow + unidad.getMovement();
            actualColMinusMovement = posActualCol - unidad.getMovement();
        }
        System.out.println("Cond 1 C: " + (posActualRow >= posRow && posRow >= actualRow_Movement));
        System.out.println("Cond 2 C: " + (actualRowMinusMovement <= posRow && posRow <= posActualRow));
        System.out.println("Cond 3 C: " + (posActualCol <= posCol && posCol <= actualCol_Movement));
        System.out.println("Cond 4 C: " + (actualColMinusMovement <= posCol && posCol <= posActualCol));
        if ((unidad.getType() == 'L' || unidad.getType() == 'A') // Restricciones de movimiento si la unidad es un Lancero o un Arquero
                    && ((actualRow_Movement == posRow || actualRowMinusMovement == posRow || posActualRow == posRow) 
                    && (actualCol_Movement == posCol || actualColMinusMovement == posCol 
                    || posActualCol == posCol))){ // Poner que solo columna se pueda mover a ambos lados pero que no pueda caminar para atras
            return true;
        }

        else if (unidad.getType() == 'C' // Restricciones de movimiento si la unidad es una Caballería
                && ((posActualRow >= posRow && posRow >= actualRow_Movement || actualRowMinusMovement >= posRow && posRow >= posActualRow
                || posActualRow <= posRow && posRow <= actualRow_Movement || actualRowMinusMovement <= posRow && posRow <= posActualRow) //|| posActualRow == posRow)
                && (posActualCol <= posCol && posCol <= actualCol_Movement || actualColMinusMovement <= posCol && posCol <= posActualCol))){
//                || posActualCol == posCol))){
            return true;
        }
        
        else{
            return false;
        }
    }
    
    public boolean move(int pId, int posRow, int posCol){
        if (0 <= pId && pId < this.lastUnit && cellRestrictions(posRow, posCol) == true){ // Se verifica si la franqueabilidad y posiciones son correctos
            Unit unidad = this.army[pId];
            
            int posActualRow = this.army[pId].getPosX(); // Posición actual en filas de la unidad
            int posActualCol = this.army[pId].getPosY(); // Posición actual en columnas de la unidad
            
            System.out.println("Unidad: " + unidad.getName());
            System.out.println("Id: " + pId);
            System.out.println("PosRow: " + posActualRow);
            System.out.println("PosCol: " + posActualCol);
            System.out.println("Jugador: " + getName());
            System.out.println("Restricciones de celda " + cellRestrictions(posRow, posCol));

            if (moveRestrictions(unidad, posActualRow, posRow, posActualCol, posCol) == true){ // Se verifica si el movimiento es correcto
                this.playField.setPassableCell(posActualRow, posActualCol, false);
                this.playField.setUnitInCell(posRow, posCol, unidad);
                this.playField.setPassableCell(posRow, posCol, true);
                
                return true;
            }
            
        }
        System.out.println("No se puede mover a esa posición"); // poner eso en el main si move retorna false
        return false;
    }
    
    public void seeArmy(){ // Ver información de cada unidad
        for (int i = 0; i < this.lastUnit; i++){
            System.out.println(this.army[i].toString());
        }
        System.out.printf("\n");
    }
    
    public void deadUnit(){ // Verifica si hay alguna unidad muerta
        for (int i = 0; i < this.lastUnit; i++){
            if (this.army[i].isDead() == true){
                for (int j = i; j < this.lastUnit; j++){
                this.army[j] = this.army[j + 1];
                }
                this.lastUnit--;
            }
        }
//        seeArmy();
    }
    
    
}
