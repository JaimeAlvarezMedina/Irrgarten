/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import irrgarten.Enumerados.Directions;
import irrgarten.GameState;

/**
 *
 * @author theki
 */
public interface UI {
    
    public Directions nextMove();
    
    public void showGame(GameState gameState);
    
}
