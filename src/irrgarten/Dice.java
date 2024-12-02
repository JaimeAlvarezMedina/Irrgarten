/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import irrgarten.Enumerados.Directions;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author theki
 */
public class Dice {
    
    private final static int    MAX_USES=5;          //(número máximo de usos de armas y escudos)
    private final static float  MAX_INTELLIGENCE=10; //(valor máximo para la inteligencia de jugadores y monstruos)
    private final static float  MAX_STRENGTH = 10;   //(valor máximo para la fuerza de jugadores y monstruos)
    private final static double RESURRECT_PROB = 0.3;//(probabilidad de que un jugador sea resucitado en cada turno) 
    private final static int    WEAPONS_REWARD = 2;  //(numero máximo de armas recibidas al ganar un combate)
    private final static int    SHIELDS_REWARD = 3;  //(numero máximo de escudos recibidos al ganar un combate)
    private final static int    HEALTH_REWARD = 5;   //(numero máximo de unidades de salud recibidas al ganar un combate)
    private final static int    MAX_ATTACK = 3;      //(máxima potencia de las armas)
    private final static int    MAX_SHIELD = 2;      //(máxima potencia de los escudos)

    private static Random generator = new Random();

    public static int randomPos(int max){
    
        return generator.nextInt(0, max);
    }
    
    public static int whoStarts( int nplayers){
        
        return generator.nextInt(0,nplayers);
    }
    
    public static float randomIntelligence(){
    
        return generator.nextFloat(0, MAX_INTELLIGENCE);
    }
    
    public static float randomStrength(){
    
        return generator.nextFloat(0, MAX_STRENGTH);
    }
    
    public static boolean resurrectPlayer(){
    
        boolean resucitado = false;
        
        if(generator.nextFloat(0,1)<RESURRECT_PROB){
            resucitado = true;
        }
        
        return resucitado;
    }
    
    public static int weaponsReward(){
        
        return generator.nextInt(0,WEAPONS_REWARD);
    }
    
    public static int shieldsReward(){
        
        return generator.nextInt(0,SHIELDS_REWARD);
    }

    public static int healthReward(){
        
        return generator.nextInt(0,HEALTH_REWARD);
    }
    
    public static float weaponPower(){
    
        return generator.nextFloat(0, MAX_ATTACK);
    }
    
    public static float shieldPower(){
    
        return generator.nextFloat(0, MAX_SHIELD);
    }
    
    public static int usesLeft(){
    
        return generator.nextInt(0, MAX_USES);
    }
    
    public static float intesity(float competence){
    
        return generator.nextFloat(0, competence);
    }
    
    public static boolean discardElement(int usesLeft){
        
        boolean discard = true;
        
        if(usesLeft > 0){
            final float PROBABILITY = (float) (1.0/usesLeft);
        
            if(generator.nextFloat(0,1) > PROBABILITY ){
                discard = false;
            }
        }
        return discard;
    }
    
    public static Directions nextStep(Directions preference, ArrayList<Directions> validMoves, float intelligence){
    
        Directions Dout;
        
        double probabilidad = generator.nextFloat(0,1);
        
        if(probabilidad < (intelligence/10)){
            
            Dout = preference;
            
        }else{
        
            Dout = validMoves.get(0);
        }
            
            
        return Dout;
        
    }
    
}
