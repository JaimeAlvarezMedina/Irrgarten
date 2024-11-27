/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import irrgarten.Enumerados.Directions;
import irrgarten.Enumerados.GameCharacter;
import java.util.ArrayList;

/**
 *
 * @author theki
 */
public class Game {

    private static final int MAX_ROUNDS = 10;
    private int currentPlayerIndex;
    private String log;
    
    private ArrayList<Player> jugadores;
    private ArrayList<Monster> monstruos;
    
    private Labyrinth laberinto;
    private final int NROW = 5;
    private final int NCOL = 5;
    
    private final int nmonsters = 1;
    
    public Game(int nplayers){
            
        jugadores = new ArrayList<>();
        monstruos = new ArrayList<>();
        laberinto = new Labyrinth(this.NROW,this.NCOL,Dice.randomPos(5),
                                    Dice.randomPos(5));
        this.log = "";
        this.currentPlayerIndex = Dice.whoStarts(nplayers);
        
        for(int i = 1; i <= nplayers; i++){
            Player jugador = new Player((char)(48 + i), Dice.randomIntelligence(), 
                                        Dice.randomStrength());
            
            jugadores.add(jugador);
        }
        
        configureLabyrinth();
        
        laberinto.spreadPlayers(jugadores);
    }
    
    /*
        Delega en el método del laberinto que indica si hay un ganador.
    */
    public boolean finished(){
        return laberinto.haveAWinner();
    }
    
    public boolean nextStep(Directions preferredDirection){
        
        this.log = "";
        Player currentPlayer = this.jugadores.get(this.currentPlayerIndex);
        boolean dead = currentPlayer.dead();
        
        if(!dead){
            
            Directions direction = actualDirection(preferredDirection);
            
            if(direction != preferredDirection){
                logPlayerNoOrders();
            }
            Monster monster = this.laberinto.putPlayer(direction, currentPlayer);
            
            if(monster == null){
            
                logNoMonster();
            }else{
            
                GameCharacter winner = combat(monster);
                manageReward(winner);
            }
        }else{
        
            manageResurrection();
        }
        boolean endGame = finished();
        
        if(!endGame){
            
            nextPlayer();
        }
        return endGame;
    }
    
    /*
        Genera una instancia de GameState integrando toda la información del
        estado del juego.
    */
    public GameState getGameState(){
        
        String lista_jugadores = "";
        String lista_monstruos = "";
        
        for(int i = 0; i < this.jugadores.size(); i++){
            lista_jugadores = this.jugadores.get(i).toString();
        }
        
        for(int i = 0; i < this.monstruos.size(); i++){
            lista_monstruos = this.monstruos.get(i).toString();
        }
        
        
        return new GameState(this.laberinto.toString(), lista_jugadores, lista_monstruos, 
                                this.currentPlayerIndex, finished(), this.log);
    }
    
    /*
        Configura el laberinto añadiendo bloques de obstáculos y monstruos.
        Los monstruos, además de en el laberinto son guardados en el contenedor 
        propio de esta clase para este tipo de objetos.
    */
    private void configureLabyrinth(){
        
        for(int i = 1; i <= nmonsters; i++){

            Monster monstruo = new Monster("Monster" + i, 
                                            Dice.randomIntelligence(), 
                                            Dice.randomStrength());

            monstruos.add(monstruo);
            laberinto.addMonster(Dice.randomPos(this.NCOL), 
                                Dice.randomPos(this.NROW), monstruo);
        }

        for(int i = 0; i < 3; i++){

            if( (i % 2) == 0){
                laberinto.addBlock(Enumerados.Orientation.HORIZONTAL, 
                                Dice.randomPos(NROW), Dice.randomPos(NCOL),
                                2);
            }else{
                laberinto.addBlock(Enumerados.Orientation.VERTICAL, 
                                Dice.randomPos(NROW), Dice.randomPos(NCOL),
                                2);
            }
        }
    }
    
    /*
        Actualiza los dos atributos que indican el jugador (current*) con el 
        turno pasando al siguiente jugador
    */
    private void nextPlayer(){
        this.currentPlayerIndex = (this.currentPlayerIndex + 1) % jugadores.size();
    }

    private Directions actualDirection(Directions preferredDirection){

        int row = this.jugadores.get(this.currentPlayerIndex).getRow();
        int col = this.jugadores.get(this.currentPlayerIndex).getCol();
        ArrayList<Directions> validMoves = this.laberinto.validMoves(row, col);
        
        return this.jugadores.get(this.currentPlayerIndex).move(preferredDirection, validMoves);
    }
    
    private GameCharacter combat(Monster monster){
        
        int rounds = 0;
        GameCharacter winner = GameCharacter.PLAYER;
        Player currentPlayer = this.jugadores.get(this.currentPlayerIndex);
        
        float playerAttack = currentPlayer.attack();
        boolean lose = monster.defend(playerAttack);

        while((!lose) && (rounds < MAX_ROUNDS)){
            
            winner = GameCharacter.MONSTER;
            rounds ++;
            
            float monsterAttack = monster.attack();
            lose = currentPlayer.defend(monsterAttack);
            
            if(!lose){
                
                playerAttack = currentPlayer.attack();
                winner = GameCharacter.PLAYER;
                lose = monster.defend(playerAttack);
            }
        }
        
        logRounds(rounds, this.MAX_ROUNDS);
        
        return winner;
    }
    
    private void manageReward(GameCharacter winner){
        
        if(winner == GameCharacter.PLAYER){
        
            this.jugadores.get(this.currentPlayerIndex).receiveReward();
            logPlayerWon();
        
        }else{
            
            logMonsterWon();
        }
        
    }
    
    private void manageResurrection(){
        
        boolean resurrect = Dice.resurrectPlayer();
        
        if(resurrect){
            
            Player currentPlayer = this.jugadores.get(this.currentPlayerIndex);
            
            FuzzyPlayer fuzzyPlayer = new FuzzyPlayer(currentPlayer);
            
            this.jugadores.set(this.currentPlayerIndex, fuzzyPlayer);
            logResurrected();
            
        }else{
            
            logPlayerSkipTurn();
        }
    }
    
    /*
        Añade al final del atributo log (concatena cadena al final) el mensaje
        necesario para describir cada apartado del juego. También añade el
        indicador de nueva línea al final.
    */
    private void logPlayerWon(){
        this.log += "El jugador gana el combate \n";
    }
    
    private void logMonsterWon(){
        this.log += "El monstruo gana el combate \n";
    }
    
    private void logResurrected(){
        this.log += "El jugador ha resucitado \n";
    }
    
    private void logPlayerSkipTurn(){
        this.log += "El jugador perdio su turno porque esta muerto \n";
    }
    
    private void logPlayerNoOrders(){
        this.log += "La accion no podido ser realizada por el personaje \n";
    }
    
    private void logNoMonster(){
        this.log += "El jugador no se movio o se movio a una celda vacia \n";
    }
    
    private void logRounds(int rounds, int max){
        this.log += "El combate finalizo: " + rounds + "/" + max + "\n";
    }
    
    
    
    
}








