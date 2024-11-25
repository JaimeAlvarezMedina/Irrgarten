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
public class FuzzyPlayer extends Player {
    
    public FuzzyPlayer(Player other){
        super(other.getNumber(), other.getIntelligence(), other.getStrength());
    }
    
    @Override
    public Directions move(Directions direction, ArrayList<Directions> validMoves){
    
        return Dice.nextStep(direction, validMoves, this.getIntelligence());
    }
    
    public float attack(){
        return this.sumWeapons() + Dice.intesity(this.getStrength());
    }
    
    protected float defensiveEnergy(){
        return this.sumShields() + Dice.intesity(this.getIntelligence());
    } 
    
    public String toString(){
        return "Fuzzy"+super.toString();
    }
    
}
