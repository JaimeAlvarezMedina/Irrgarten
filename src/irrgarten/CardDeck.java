/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author theki
 */
public abstract class CardDeck<T extends CombatElement> {
    
    private ArrayList<T> cardDeck;
    
    public CardDeck(){
        this.cardDeck = new ArrayList<>();
    }
    
    protected abstract void addCards();
    
    protected void addCard(T card){
        this.cardDeck.add(card);
    }
    
    public T nextCard(){
        
        if(this.cardDeck.isEmpty()){
            addCards();
            Collections.shuffle(this.cardDeck);
        }
        
        T carta = this.cardDeck.getFirst();
        this.cardDeck.removeFirst();
        
        return carta;
    }
    
}
