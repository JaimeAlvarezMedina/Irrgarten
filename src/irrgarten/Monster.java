/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author theki
 */
public class Monster extends LabyrinthCharacter {
    
    private static final int INITIAL_HEALTH = 50;
    
    public Monster(String name, float intelligence, float strength){
        super(name, intelligence, strength, INITIAL_HEALTH);
    }
    
    public float attack(){
    
        return Dice.intesity(this.getStrength());
    }
    
    public boolean defend(float receivedAttack){
    
        boolean isDead = dead();
        
        if(!isDead){
        
            float defensiveEnergy = Dice.intesity(this.getIntelligence());
            
            if(defensiveEnergy < receivedAttack){
                
                gotWounded();
                isDead = dead();   
            }    
        }
        
        return isDead;
    }   
}
