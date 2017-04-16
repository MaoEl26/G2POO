package strategygame;

import java.io.Serializable;

public class Unit implements Serializable {
    
    private String player;
    private int id;
    private char type;
    private String name;
    private int maxHitPoints;
    private int hitPoints;
    private int attack;
    private int defense; 
    private int range; 
    private int level;
    private int experience;
    private int movement;
    private int posX;
    private int posY;
    private static int cost;
    private static int idCount;
    
    // Método adicional desplegar estado. 
    
    public Unit(String player, int pId, char pType, String pName, int mHP, int hP, int atk, 
            int def, int r, int lvl, int xp, int mov, int pCost){
        this.player = player;
        this.id = pId;
        this.type = pType;
        this.name = pName;
        this.maxHitPoints = mHP;
        this.hitPoints = hP;
        this.attack = atk;
        this.defense = def;
        this.range = r;
        this.level = lvl;
        this.experience = xp;
        this.movement = mov;
        this.cost = pCost;
    }
    
    public void setId(int pId){
        this.id = pId;
    }
    
    public void setType(char pType){
        this.type = pType;
    }
    
    public void setName(String pName){
        this.name = pName;
    }
    
    public void setMaxHitPoints(int mHP){
        this.maxHitPoints = mHP;
    }
    
    public void setHitPoints(int hP){
        this.hitPoints = hP;
    }
    
    public void setAttack(int atk){
        this.attack = atk;
    }
    
    public void setDefense(int def){
        this.defense = def;
    }
    public void setRange(int range){
        this.range = range;
    }
    
    public void setLevel(int pLevel){
        this.level = pLevel;
    }
    
    public void setExperience(int pExperience){
        this.experience = pExperience;
    }
    
    public void setMovement(int pMovement){
        this.movement = pMovement;
    }
    
    public void setPosX(int pPosX){
        this.posX = pPosX;
    }
    
    public void setPosY(int pPosY){
        this.posY = pPosY;
    }
    
    public void setCost(int pCost){
        Unit.cost = pCost;
    } 
    
    public String getPlayer(){
        return player;
    }
    
    public int getId(){
        return id;
    }
    
    public char getType(){
        return type;
    }
    
    public String getName(){
        return name;
    }
    
    public int getMaxHitPoints(){
        return maxHitPoints;
    }
    
    public int getHitPoints(){
        return hitPoints;
    }
    
    public int getAttack(){
        return attack;
    }
    
    public int getDefense(){
        return defense;
    }
    
    public int getRange(){
        return range;
    }
    
    public int getLevel(){
        return level;
    }
    
    public int getExperience(){
        return experience;
    }
    
    public int getMovement(){
        return movement;
    }
    
    public int getPosX(){
        return posX;
    }
    
    public int getPosY(){
        return posY;
    }
    
    public int getCost(){
        return this.cost;
    }
    
    public void win(){
        this.experience++;
        if (this.experience == level * 2){
            this.maxHitPoints += maxHitPoints * 0.25; 
            this.hitPoints = maxHitPoints;
            this.attack += attack * 0.25;
            this.defense += defense * 0.25;
            this.level++;
            this.experience = 0;
        }
    }
    @Override  
    public String toString(){
        String mensaje = "";
        mensaje += "Id: " + getId() + " Unidad: " + getName() + " Max Hit Points: " +
                getMaxHitPoints() + " Hit Points: " + getHitPoints() + " Attack: " +
                getAttack() + " Defense: " + getDefense() + " Range: " + 
                getRange() + " Level: " + getLevel() + " Experiencie: " +
                getExperience() + " Movement: " + getMovement() + "Pos X: " +
                getPosX() + "Pos Y: " + getPosY() + "Cost: " + getCost();
        
        return mensaje;
                
    }
}
