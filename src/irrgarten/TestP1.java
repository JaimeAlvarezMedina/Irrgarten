/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package irrgarten;

import vista.GUI;
import controlador.Controller;
import vista.UI;

/**
 *
 * @author theki
 */
public class TestP1 {
    
    //Funcion Main
    public static void main(String[] args) {
        
        UI vista = new GUI();
        Game juego = new Game(1);
        Controller controlador = new Controller( juego, vista);
        
        controlador.play();
    }
    
}


















