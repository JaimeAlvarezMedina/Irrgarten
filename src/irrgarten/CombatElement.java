/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author theki
 */
public abstract class CombatElement {
    private float effect;
    private int uses;
    
    public CombatElement(float effect, int uses){
        
        this.effect = effect;
        this.uses = uses;
    }
    
    protected float produceEffect(){
        
        float efecto = 0;
        
        if(this.uses > 0){
            
            efecto = this.effect;
            this.uses--;
        }
        
        return efecto;
    }
    
    public boolean discard(){
        return Dice.discardElement(this.uses);
    }

    @Override
    public String toString() {
        return "{" + "effect=" + effect + ", uses=" + uses + '}';
    }
    
    
    
}
