/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author theki
 */
public class ShieldCardDeck extends CardDeck<Shield> {

    @Override
    protected void addCards() {
        
        for(int i = 0; i < 10; i++){
            
            super.addCard(new Shield(Dice.shieldPower(), Dice.usesLeft()));
        }
    }
    
}
