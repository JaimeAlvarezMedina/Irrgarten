/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import irrgarten.Enumerados.Directions;
import java.util.ArrayList;

/**
 *
 * @author theki
 */
public class Player extends LabyrinthCharacter {
    
    private static final int MAX_WEAPONS = 2;
    private static final int MAX_SHIELDS = 3;
    private static final int INITIAL_HEALTH = 10;
    private static final int HITS2LOSE = 3;
    
    private char number;
    private int consecutiveHits = 0;
    
    private ArrayList<Weapon> weapons; 
    private ArrayList<Shield> shields;
            
    public Player(char number, float intelligence, float strength){
    
        this.number = number;
        super("Player#" + number, intelligence, strength, INITIAL_HEALTH);
    
        this.weapons = new ArrayList<>();
        this.shields = new ArrayList<>();
    }
    
    public Player(Player other){
    
        super(other); 
        this.weapons = new ArrayList<>(other.weapons);
        this.shields = new ArrayList<>(other.shields);
    }
    
    public FuzzyPlayer resurrect(){
        
        return new FuzzyPlayer(this);
    }

    public char getNumber() {
        return number;
    }
    
    public Directions move(Directions direction, ArrayList<Directions> validMoves){
        
        int size = validMoves.size();
        boolean contained = validMoves.contains(direction);
        
        if( (size > 0) && (!contained) ){
            
            return validMoves.get(0);   
        }else{
            
            return direction;
        }
    }
    
    public float attack(){
        return this.getStrength() + this.sumWeapons();
    }
    
    public boolean defend(float receivedAttack){
        return this.manageHit(receivedAttack);
    }
    
    public void receiveReward(){
        
        int wReward = Dice.weaponsReward();
        int sReward = Dice.shieldsReward();
        
        for(int i = 0; i < wReward; i++){
            
            Weapon wnew = newWeapon();
            receiveWeapon(wnew);
        }
        for(int i = 0; i < sReward; i++){
            
            Shield snew = newShield();
            receiveShield(snew);
        }
        
        int extraHealth = Dice.healthReward();
        this.setHealth(this.getHealth() + extraHealth);
    }
    
    public String toString(){
    
        return "Player"+super.toString();
    }
    
    private void receiveWeapon(Weapon w){
        
        for( int i = 0; i < this.weapons.size(); i++){
            
            Weapon wi = this.weapons.get(i);
            boolean discard = wi.discard();
            
            if(discard){
                this.weapons.remove(wi);
            }
        }
        
        int size = this.weapons.size();
        
        if(size < MAX_WEAPONS){
            this.weapons.add(w);
        }
        
    }
    
    private void receiveShield(Shield s){

        for( int i = 0; i < this.shields.size(); i++){
            
            Shield si = this.shields.get(i);
            boolean discard = si.discard();
            
            if(discard){
                this.shields.remove(si);
            }
        }
        
        int size = this.shields.size();
        
        if(size < MAX_SHIELDS){
            this.shields.add(s);
        }
        
    }
    
    private Weapon newWeapon(){
        return new Weapon(Dice.weaponPower(), Dice.usesLeft());
    }
    
    private Shield newShield(){
        return new Shield(Dice.shieldPower(),Dice.usesLeft());
    }
    
    protected float sumWeapons(){
    
        float sum = 0;
        
        for(int i = 0; i < this.weapons.size(); i++){
            
            sum += weapons.get(i).attack();
        }
        return sum;
    }
    
    protected float sumShields(){
    
        float sum = 0;
        
        for(int i = 0; i < this.shields.size(); i++){
            
            sum += shields.get(i).protect();
        }
        return sum;
    }
    
    protected float defensiveEnergy(){
        return this.getIntelligence() + this.sumShields();
    }
    
    private boolean manageHit(float receivedAttack){
        
        boolean lose;
        
        float defense = defensiveEnergy();
    
        if( defense < receivedAttack ){
            
            gotWounded();
            incConsecutiveHits();
            
        }else{
            
            resetHits();
        }
        if( ( consecutiveHits == HITS2LOSE ) || dead() ){
            
            resetHits();
            
            lose = true;
            
        }else{
            
            lose = false;
        }
        return lose;
    }
    
    private void resetHits(){
        this.consecutiveHits = 0;
    }
    
    private void incConsecutiveHits(){
        this.consecutiveHits++;
    }  
}











