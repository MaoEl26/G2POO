package strategygame;
import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner; 

public class Player implements Serializable {
    private int maxCost;
    private Unit[] army;
    private Field playField;
    private String name;
    private int score;
    private int lastUnit; // Sirve para ir guardando las unidades en Unit.
    private transient Scanner scan;
    private int maxCostAux;
    
    
    // createArmy 3 ints: cantidad de tropas y que no exceda el maxCost.
    
    // move: 2 ints: "x" y "y", ret false si no es franqueable la pos, 
    // de lo contrario asignar nuevas coords.
    
    // attack: 2 ints: "x" y "y", validar si estan dentro del rango de ataque,
    // sino ret false; si ataque de A > a def de D restar puntos de vida a D
    // de lo contrario no sucede nada.
    
    // play: metodo de interacion con la lista de unidades del jugador, 
    // si no desea mover una ingresar el par especial (-1,-1).
    
    public Player(String name, Field pPlayfield){
        this.name = name;
        this.maxCost = 15; // Por ejemplo: x2 lanceros, x1 caballeria, x1 arquero.
        this.army = new Unit[5];
        this.playField = pPlayfield;
        this.maxCostAux = this.maxCost;
        
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public int getMaxCost(){
        return this.maxCost;
    }
    
    public int calcularPuntaje(String tipo, int cantidad)
    {
        
        if ("1".equals(tipo))        // Lanceros.
        {
            for (int y = 0; y < cantidad; y++)
            {
                this.maxCostAux = this.maxCostAux - 3;
            }
        }
        else if ("2".equals(tipo))   // Caballería.
        {
            for (int y = 0; y < cantidad; y++)
            {
                this.maxCostAux = this.maxCostAux - 5;
            }
        }
        else                    // Arqueros.
        {
             for (int y = 0; y < cantidad; y++)
            {
                this.maxCostAux = this.maxCostAux - 4;
            }
        }
        
        return this.maxCostAux;
    }
    public boolean createArmy(int numberOfLancer, int numberOfCavalry, int numberOfArcher){
        if (numberOfLancer * 3 + numberOfCavalry * 5 + numberOfArcher * 4 <= maxCost){
            for (int i = 0; i < numberOfLancer; i++){
                this.army[this.lastUnit] = new Unit(getName()
                        , this.lastUnit, 'L', "Lancer", 
                        20, 20, 10, 10, 1, 1, 0, 1, 3); // Agrega unidad Lancer
                this.lastUnit++;
                
            }
            
            for (int i = 0; i < numberOfCavalry; i++){
                this.army[this.lastUnit] = new Unit(getName(), 
                        this.lastUnit, 'C', "Cavalry", 25, 25, 
                        15, 5, 1, 1, 0, 3, 5); // agrega unidad Cavalry
                this.lastUnit++;
                
            }
            
            for (int i = 0; i < numberOfArcher; i++){
                this.army[this.lastUnit] = new Unit(getName(), 
                        this.lastUnit, 'A', "Archer", 15, 15, 10, 5, 3, 
                        1, 0, 1, 4); // Agrega unidad Archer
                this.lastUnit++;
                
            }
            return true;
        }
        else{
            return false;
        }  
    }
    
    // Verifica la flanqueabilidad de una celda y si la posición en la
    // fila y columna está entre 0 y el tamaño de la fila o columna - 1.  
    // Pasa por parámetro true para verificar si la celda está ocupada (para el attack)
    // y false para verificar que no haya nadie en esa celda (para el move).
    
    public boolean cellRestrictions(int posRow, int posCol, boolean true_false){                                                      
        
        int maxPosRow = this.playField.getRows() - 1; // Máxima posición en la fila
        int maxPosCol = this.playField.getCols() - 1; // Máxima posición en la columna
        
        if (0 <= posRow && posRow <= maxPosRow && 0 <= posCol && posCol <= maxPosCol 
                && this.playField.isPassable(posRow, posCol) == true_false){
            return true;
        }
        return false;
    }
    
    // Agrega las primeras unidades en la primera fila del lado 
    // respectivo de cada jugador.
    
    public void placeUnits()
    {
        for (int y = 0; y < this.lastUnit; y++)
        {
            System.out.println("Set the position of your unit: " + this.army[y].getName() + 
                    "Id: " + this.army[y].getId());
            
            int pos;
            scan = new Scanner(System.in);
            do // Para poder verificar la entrada de los valores.
            {
                pos = scan.nextInt();
                if (addFirstUnits(y, pos) == true)
                {
                    System.out.println("Done.");
                    break;
                }
                else
                {
                    System.out.println("Cell occupied or doesn't exist.");
                    System.out.println("Choose another Cell.");
                }
            }
            while (addFirstUnits(y, pos) == false);
            
            System.out.println("Unit on Field");
            this.playField.verArena();
            
        }
    }
    public boolean addFirstUnits(int id, int posCol){ 
        
        int posRow = 0;
        
        if ("P2".equals(getName())){ 
            posRow = this.playField.getRows() - 1;
        }
      
        if (0 <= id && id < this.lastUnit && cellRestrictions(posRow, posCol ,false) == true 
                && posCol <= this.playField.getCols()){
            this.playField.setUnitInCell(posRow, posCol, this.army[id]);                                                    
            this.playField.setPassableCell(posRow, posCol, true); 
            this.army[id].setPosX(posRow);
            this.army[id].setPosY(posCol);
            return true;
        }
        else if (!(0 <= id && id < this.lastUnit)){
            System.out.println("Id doesn't exist.");
            return false;
            
        }
        else{
            
            return false;
            
        }
    }
    
    // Verifica las restricciones de movimiento.
    
    public boolean moveRestrictions(Unit unidad, int posActualRow, 
            int posRow, int posActualCol, int posCol){ 
        
        // Guardan la suma o resta de la posición en la fila con el movimiento 
        // dependiendo del jugador ya que el jugador 1 empieza en la fila 0 
        // y el jugador 2 en la última fila.
        
        int forwardMovement; // Movimiento hacia el frente.
        int backwardMovement; // Movimiento hacia atrás.
        int rightMovement = posActualCol + unidad.getMovement(); // Movimiento hacia la derecha.
        int leftMovement = posActualCol - unidad.getMovement(); // Movimiento hacia la izquierda.
        
        // Como el jugador 1 está arriba entonces se le deben ir sumando las 
        // posiciones ya que debe ir "bajando". 
        
        if ("P1".equals(getName())){ 
            forwardMovement = posActualRow + unidad.getMovement(); 
            backwardMovement = posActualRow - unidad.getMovement();
            
        }
        
        // Como el jugador 2 está abajo entonces se le deben ir restando las 
        // posiciones ya que debe ir "subiendo".
        
        else{ 
            forwardMovement = posActualRow - unidad.getMovement();
            backwardMovement = posActualRow + unidad.getMovement();
        }
        
        // Restricciones de movimiento si la unidad es un Lancero o un Arquero.
        
        if ((unidad.getType() == 'L' || unidad.getType() == 'A') 
                    && ((forwardMovement == posRow || backwardMovement == posRow || posActualRow == posRow) 
                    && (rightMovement == posCol || leftMovement == posCol 
                    || posActualCol == posCol))){ 
            return true;
        }
        
        // Restricciones de movimiento si la unidad es una Caballería.
        
        else if (unidad.getType() == 'C' 
                && ((posActualRow >= posRow && posRow >= forwardMovement || backwardMovement >= posRow && posRow >= posActualRow
                || posActualRow <= posRow && posRow <= forwardMovement || backwardMovement <= posRow && posRow <= posActualRow) 
                && (posActualCol <= posCol && posCol <= rightMovement || leftMovement <= posCol && posCol <= posActualCol))){
            return true;
        }
        
        else{
            return false;
        }
    }
    
    // Se verifica la franqueabilidad y si las posiciones son correctas.
    
    public boolean move(int id, int posRow, int posCol){
        if (0 <= id && id < this.lastUnit && cellRestrictions(posRow, posCol, false) == true){ 
            Unit unidad = this.army[id];
            int posActualRow = this.army[id].getPosX(); // Posición actual en filas de la unidad.
            int posActualCol = this.army[id].getPosY(); // Posición actual en columnas de la unidad.
            
            // Se verifica si el movimiento es correcto.
            
            if (moveRestrictions(unidad, posActualRow, posRow, posActualCol, posCol) == true){ 
                this.playField.setPassableCell(posActualRow, posActualCol, false);
                this.playField.setUnitInCell(posRow, posCol, unidad);
                this.playField.setPassableCell(posRow, posCol, true);
                this.army[id].setPosX(posRow);
                this.army[id].setPosY(posCol);
                return true;
            }
            
        }
        
        // Poner eso en el main si move retorna false.
        
        System.out.println("You can't move to that position."); 
        return false;
    }
        
    public boolean attackRestrictions(Unit unidad, int posActualRow, 
            int posRow, int posActualCol, int posCol){ 
        
        // Guardan la suma o resta de la posición en la fila con el movimiento 
        // dependiendo del jugador ya que el jugador 1 empieza en la fila 0 
        // y el jugador 2 en la última fila.
        
        int forwardAttack; // Movimiento hacia el frente.
        int backwardAttack; // Movimiento hacia atrás.
        int rightRange = posActualCol + unidad.getRange(); // Movimiento hacia la derecha.
        int leftRange = posActualCol - unidad.getRange(); // Movimiento hacia la izquierda.
        
        // Jugador al que pertenece la unidad en una celda.
        
        String playerUnitInCell = this.playField.getPlayerUnitInCell(posRow, posCol); 
        
        if ("P1".equals(getName())){ 
            forwardAttack = posActualRow + unidad.getRange(); 
            backwardAttack = posActualRow - unidad.getRange();
        }
        
        // Como el jugador 2 está abajo entonces se le deben ir restando las 
        // posiciones ya que debe ir "subiendo".
        
        else{ 
            forwardAttack = posActualRow - unidad.getRange();
            backwardAttack = posActualRow + unidad.getRange();
        }
                
        // Restricciones de movimiento si la unidad es un Lancero o un Arquero.
        
        if ((unidad.getType() == 'L' || unidad.getType() == 'C') 
                    && ((forwardAttack == posRow || backwardAttack == posRow || posActualRow == posRow) 
                    && (rightRange == posCol || leftRange == posCol 
                    || posActualCol == posCol)) && playerUnitInCell != getName()){ 
            return true;
        }
        
        // Restricciones de movimiento si la unidad es una Caballería.
        
        else if (unidad.getType() == 'A' 
                && ((posActualRow >= posRow && posRow >= forwardAttack || backwardAttack >= posRow && posRow >= posActualRow
                || posActualRow <= posRow && posRow <= forwardAttack || backwardAttack <= posRow && posRow <= posActualRow) 
                && (posActualCol <= posCol && posCol <= rightRange || leftRange <= posCol && posCol <= posActualCol)) && playerUnitInCell != getName()){
            
            return true;
        }
        
        else{
            return false;
        }
    }
    
   
    public boolean attack(int id, int posRow, int posCol){
        if (0 <= id && id < this.lastUnit && cellRestrictions(posRow, posCol, true) == true){ 
            
            // Unidad atacante.
            
            Unit unit = this.army[id];
            int unitAttk = unit.getAttack();
            
            // Unidad enemiga que va a ser atacada.
            
            Unit enemyUnit = this.playField.getUnitInCell(posRow, posCol);
            int enemyUnitLife = enemyUnit.getHitPoints(); // vida del enemigo.
            int enemyUnitDefense = enemyUnit.getDefense();
            
            System.out.println("Damage to be dealt: " + unitAttk);
            System.out.println("Enemy unit HitPoints: " + enemyUnitLife);
            System.out.println("Enemy unit Defense: " + enemyUnitDefense);
            int posActualRow = this.army[id].getPosX(); // Posición actual en filas de la unidad.
            int posActualCol = this.army[id].getPosY(); // Posición actual en columnas de la unidad.
            
            if (attackRestrictions(unit, posActualRow, posRow, posActualCol, posCol) == true){
                if (unitAttk >= enemyUnitDefense){
                    
                    // Si la venció.
                    
                    if ((enemyUnitLife + enemyUnitDefense - unitAttk) <= 0){
                        enemyUnit.setHitPoints(0); // Le baja los puntos de vida a la unidad atacada
                        removeDeadUnit(enemyUnit);
                    }
                    else{
                        System.out.println("Result: " + (enemyUnitLife + enemyUnitDefense - unitAttk));
                        enemyUnit.setHitPoints((enemyUnitLife + enemyUnitDefense - unitAttk)); // Le baja los puntos de vida a la unidad atacada
                        System.out.println("Enemy unit actual HitPoints: " + enemyUnit.getHitPoints());
                    }
                    return true;
                }
                else{
                    System.out.println("Damage wasn't enough.");
                }
            }
        } 
        return false;
    }
    
    public void chooseArmy()
    {
        while (this.lastUnit == 0){ // Eso quiere decir que no ha agregado unidades aun
            
            try{
                System.out.println("+------------------------------------+");
                System.out.println("|            Max cost: " + getMaxCost() +
                        "            |");
                System.out.println("+------------------------------------+");
                System.out.println("\nCreate your army:\n");
                System.out.print("Number of lancers(cost 3): ");
                
                // Input de los números.
                scan = new Scanner(System.in);
                int lancers = scan.nextInt();
                System.out.println("    Remaining points: " + calcularPuntaje("1", lancers) + "\n");
                System.out.print("Number of cavalries(cost 5): ");
                int cavalries = scan.nextInt();
                System.out.println("    Remaining points: " + calcularPuntaje("2", cavalries) + "\n");
                System.out.print("Number of archers(cost 4): ");
                int archers = scan.nextInt();
                
                if ((cavalries < 0) || (lancers < 0) || (archers < 0) 
                        || createArmy(lancers, cavalries, archers) == false)
                {
                    System.out.println("\nMax Cost reached\n"
                            + "Please try again");
                    this.maxCostAux = this.maxCost;
                }
                else{
                    System.out.println("\nYour army has been created");
                    scan.nextLine();
                }
            }
            catch(InputMismatchException e){
                System.out.println("\nOnly numbers can be in.");
            }    
        }
    }
    
    public void play(){
        System.out.println("-------------- new turn --------------\n"
                + "----------------- " + getName() + " -----------------");
               
        // Se recorren las unidades.
        scan = new Scanner(System.in); 
        
        for (int y = 0; y < this.lastUnit; y++)
        {
            if (this.army[y].getId() >= 0)
            {
                System.out.println("Unit: " + this.army[y].getName() + "(" +
                        this.army[y].getId() + ")");
                System.out.print("Move? ");
                String auxMove = scan.next();//Aqui marca el err
                
                if (auxMove.equals("y"))
                {
                    System.out.print("Pos x: ");
                    int posX = scan.nextInt();
                    
                    System.out.print("Pos y: ");
                    int posY = scan.nextInt();
                    
                    if (this.move(this.army[y].getId(), posX, posY) == false);
                }
                
                System.out.print("Attack? ");
                String auxAttack = scan.next();
                
                if (auxAttack.equals("y"))
                {
                    System.out.print("Pos x: ");
                    int posX = scan.nextInt();
                    
                    System.out.print("Pos y: ");
                    int posY = scan.nextInt();
                    
                    this.attack(this.army[y].getId(), posX, posY);
                }
                
                this.playField.verArena();
            }
        }
    }
    
    // Ver información de cada unidad
    
    public void seeArmy(){ 
        for (int i = 0; i < this.lastUnit; i++){
            if (this.army[i].getId() != -1){
                System.out.println(this.army[i].toString());
            }
            else{
                System.out.println("You lost the unit: " + this.army[i].getName()
                + " with Id: " + i);
            }
        }
        System.out.printf("\n");
    }
    
    // Remueve una unidad muerta sustituyendo si id con un -1
    
    public void removeDeadUnit(Unit enemyUnit){ 
        enemyUnit.setPosX(-1);
        enemyUnit.setPosY(-1);
        enemyUnit.setId(-1);
        
    }
    
    // Verifica si el jugador perdió
    
    public boolean lose(){ 
        for (int i = 0; i < this.lastUnit ; i++){
            if (this.army[i].getId() != -1){
                // System.out.println("I'm still alive");
                return false;
            }
        }
        System.out.println(getName() + "has been defeated.");
        return true;
    }
    
}

// setLastUnit con lasUnit de la clase unit