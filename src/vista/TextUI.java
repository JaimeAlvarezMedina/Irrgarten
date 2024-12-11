
package vista;

import irrgarten.Enumerados.Directions;
import irrgarten.GameState;
import java.util.Scanner;


public class TextUI implements UI{
    
    private static Scanner in = new Scanner(System.in);
    
    private char readChar() {
        String s = in.nextLine();     
        return s.charAt(0);
    }
    

    public Directions nextMove() {
        System.out.print("Where? ");
        
        Directions direction = Directions.DOWN;
        boolean gotInput = false;
        
        while (!gotInput) {
            char c = readChar();
            switch(c) {
                case 'w':
                    System.out.print(" UP\n");
                    direction = Directions.UP;
                    gotInput = true;
                    break;
                case 's':
                    System.out.print(" DOWN\n");
                    direction = Directions.DOWN;
                    gotInput = true;
                    break;
                case 'd':
                    System.out.print("RIGHT\n");
                    direction = Directions.RIGHT;
                    gotInput = true;
                    break;
                case 'a':
                    System.out.print(" LEFT\n");
                    direction = Directions.LEFT;
                    gotInput = true;    
                    break;
            }
        }    
        return direction;
    }
    
    public void showGame(GameState gameState) {   
        
        if(!gameState.isWinner()){
            
            String laberinto = gameState.getLabyrinth();
            int actualJugador = gameState.getCurrentPlayer() + 1;
            String log = gameState.getLog();
            
            
            System.out.println(laberinto);
            System.out.println(log);

            System.out.println("\nEl jugador actual es: " + actualJugador);
            
        }else{
            
            System.out.println("El ganador es: \n" + (gameState.getCurrentPlayer() + 1));
        }

    }
    
}
