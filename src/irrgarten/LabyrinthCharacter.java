/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author theki
 */
public abstract class LabyrinthCharacter {
    
    private String name;
    private float  intelligence;
    private float  strength;
    private float  health;
    private int    row;
    private int    col;

    public LabyrinthCharacter(String name, float intelligence, float strength, float health) {
        this.name = name;
        this.intelligence = Dice.randomIntelligence();
        this.strength = strength;
        this.health = health;
        this.row = -1;
        this.col = -1;
    }
    
    public LabyrinthCharacter(LabyrinthCharacter other){
        
        this.name = other.name;
        this.intelligence = other.intelligence;
        this.strength = other.strength;
        this.health = other.health;
        this.row = other.row;
        this.col = other.col;
    }
    
    public boolean dead(){
        return (this.health==0) ?true :false;
    }
    
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    protected float getIntelligence() {
        return intelligence;
    }

    protected float getStrength() {
        return strength;
    }

    protected float getHealth() {
        return health;
    }


    protected void setHealth(float health) {
        this.health = health;
    }
    
    public void setPos(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return "{" + "name=" + name + ", intelligence=" + intelligence + ", strength=" + strength + ", health=" + health + ", row=" + row + ", col=" + col + '}';
    }
    
    protected void gotWounded(){
        this.health--;
    }
    
    public abstract float attack();
    
    public abstract boolean defend(float attack);
    
}
