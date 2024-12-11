/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package irrgarten;

import vista.TextUI;
import controlador.Controller;
import vista.UI;

/**
 *
 * @author theki
 */
public class TestP1 {
    
    //Funcion Main
    public static void main(String[] args) {
        
        UI vista = new TextUI();
        Game juego = new Game(1);
        Controller controlador = new Controller( juego, (TextUI) vista);
        
        controlador.play();
    }
    
}


















