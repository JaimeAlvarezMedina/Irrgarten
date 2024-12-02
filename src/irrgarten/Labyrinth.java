/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import irrgarten.Enumerados.Directions;
import irrgarten.Enumerados.Orientation;
import java.util.ArrayList;

/**
 *
 * @author theki
 */
public class Labyrinth {
    
    private static final char BLOCK_CHAR = 'X';
    private static final char EMPTY_CHAR = '-';
    private static final char MONSTER_CHAR = 'M';
    private static final char COMBAT_CHAR = 'C';
    private static final char EXIT_CHAR = 'E';
    private static final int ROW = 0;
    private static final int COL = 1;
    
    private int nRows;
    private int nCols;
    private int exitRow;
    private int exitCol;
    
    private Monster[][] laberintoMonstruos;
    private Player[][] laberintoJugadores;
    private char[][] laberinto;
    
    private ArrayList<Monster> monstruos;
    private ArrayList<Player> jugadores;
    
    
    public Labyrinth(int nRows, int nCols, int exitRow, int exitCol){
    
        this.nRows = nRows;
        this.nCols = nCols;
        this.exitRow = exitRow;
        this.exitCol = exitCol;
        this.laberintoMonstruos = new Monster[nRows][nCols];
        this.laberintoJugadores = new Player[nRows][nCols];
        this.laberinto = new char[nRows][nCols];
        this.monstruos = new ArrayList<>();
        this.jugadores = new ArrayList<>();
        
        for(int i = 0; i < nRows; i++){
        
            for(int j = 0; j < nCols; j++){
                
                this.laberinto[i][j] = EMPTY_CHAR;
                
                if(this.laberintoJugadores[i][j] != null){
                    this.laberinto[i][j] = this.laberintoJugadores[i][j].getNumber();
                }
                if(this.laberintoMonstruos[i][j] != null){
                    this.laberinto[i][j] = MONSTER_CHAR;
                }
            }
        }
        
        this.laberinto[this.exitRow][this.exitCol] = EXIT_CHAR;
    }
    
    public void spreadPlayers(ArrayList<Player> players){
        
        for(int i = 0; i < players.size(); i++){
        
            Player p = players.get(i);
            
            int [] pos = this.randomEmptyPos();
            putPlayer2D(0, 0, pos[ROW], pos[COL], p);
        }
        
    }
    
    /*
        Devuelve true si hay un jugador en la casilla de salida y false si no hay
        ninguno.
    */
    public boolean haveAWinner(){
        
        return (this.laberintoJugadores[this.exitRow][this.exitCol] != null);
    }
    
    /*
        Genera un representación del estado completo del laberinto en forma de 
        cadena de caracteres.
    */
    public String toString(){
    
        String cadena = "";
        
        for(int i = 0; i < nRows; i++){
        
            for(int j = 0; j < nCols; j++){
                
                cadena +="[";
                cadena += this.laberinto[i][j];
                cadena +="]";
            }
            cadena += "\n";
        }
    
        return cadena;
    }
    
    /*
        Si la posición suministrada está dentro del tablero y
        está vacía, anota en el laberinto la presencia de un monstruo, guarda la referencia del monstruo en el
        atributo contenedor adecuado e indica al monstruo cual es su posición actual
    */
    public void addMonster(int row, int col, Monster monster){
    
        if(posOK(row,col) && emptyPos(row, col)){
        
            this.laberinto[row][col] = MONSTER_CHAR;
            this.laberintoMonstruos[row][col] = monster;
            monster.setPos(row, col);
            monstruos.add(monster);
        
        }
    }
    
    public Monster putPlayer(Directions direction, Player player){
        
        int oldCol = player.getCol();
        int oldRow = player.getRow();
        
        int [] newPos = dir2Pos(oldRow, oldCol, direction);
        
        return putPlayer2D(oldRow, oldCol, newPos[ROW],
                                           newPos[COL], player);
        
    }
    
    public void addBlock(Orientation orientation, int startRow, int startCol, 
                        int length){
        int incRow;
        int incCol;
        
        if(orientation == Orientation.VERTICAL){
            incRow = 1;
            incCol = 0;
        }else{
            incRow = 0;
            incCol = 1;
        }
        
        int row = startRow;
        int col = startCol;
        
        while( (posOK(row, col)) && (emptyPos(row, col)) && (length > 0)){
            
            this.laberinto[row][col] = this.BLOCK_CHAR;
            
            length -= 1;
            row += incRow;
            col += incCol;
            
        }
    }
    
    public ArrayList<Directions> validMoves(int row, int col){
        
        ArrayList <Directions> output = new ArrayList<>();
        
        if(canStepOn( row + 1, col)){
            output.add(Directions.DOWN);
        }
        
        if(canStepOn( row - 1, col)){
            output.add(Directions.UP);
        }
        
        if(canStepOn( row, col + 1)){
            output.add(Directions.RIGHT);
        }
        
        if(canStepOn( row, col - 1)){
            output.add(Directions.LEFT);
        }
        
        return output;
    }
    
    //Devuelve true si la posición proporcionada está dentro del laberinto.
    private boolean posOK(int row, int col){ 
        return (row < this.nRows && row >= 0 && 
                col < this.nCols && col >= 0 );
    }
    
    /*
        Devuelve true si la posición suministrada está vacía. Para ello debe 
        contener el carácter indicado anteriormente que representa ese hecho.
    */
    private boolean emptyPos(int row, int col){ 
        return (this.laberinto[row][col] == EMPTY_CHAR);
    }
    
    /*
        Devuelve true si la posición suministrada alberga exclusivamente un
        monstruo. Para ello debe contener el carácter indicado anteriormente 
        que representa ese hecho.
    */
    private boolean monsterPos(int row, int col){
        return (this.laberinto[row][col] == MONSTER_CHAR);
    }
    
    /*
        Devuelve true si la posición suministrada es la de salida.
    */
    private boolean exitPos(int row, int col){
        return (this.laberinto[row][col] == EXIT_CHAR);
    }
    
    /*
        Devuelve true si la posición suministrada contiene a la vez un
        monstruo y un jugador (carácter ‘C’).
    */
    private boolean combatPos(int row, int col){
        return (this.laberinto[row][col] == COMBAT_CHAR);
    }
    
    /*
        Indica si la posición suministrada está dentro del laberinto y se
        corresponde con una de estas tres opciones: casilla vacía, casilla donde 
        habita un monstruo o salida
    */
    private boolean canStepOn(int row, int col){
        return (posOK(row,col) && (emptyPos(row,col) || monsterPos(row,col) || 
                                    exitPos(row,col)));
    }
    
    /*
        Este método solo realiza su función si la posición suministrada está
        dentro del laberinto. Si es el caso, si en esa posición el laberinto 
        estaba indicando el estado de combate, el estado de esa casilla del 
        laberinto pasa a indicar que simplemente hay un monstruo. En otro caso,
        el estado de esa casilla del laberinto pasa a indicar que está vacía. 
        Este método es llamado cuando un jugador abandona una casilla y se 
        encarga de dejar la casilla que se abandona en el estado correcto.
    */
    private void updateOldPos(int row, int col){
    
        if(posOK(row, col)){
            if(combatPos(row, col)){
                this.laberinto[row][col] = MONSTER_CHAR;
            }else{
                this.laberinto[row][col] = EMPTY_CHAR;
            }
        }
    }
    /*
        Este método calcula la posición del laberinto a la que se llegaría si 
        desde la posición suministrada se avanza en la dirección pasada como
        parámetro. No es necesario realizar comprobaciones relativas a no 
        generar posiciones fuera del laberinto.

    */
    private int[] dir2Pos(int row, int col, Directions direction){
        
        int [] posicion = new int[2];
        
        switch(direction){
        
            case Directions.LEFT:
                posicion[0] =row;
                posicion[1] =col - 1;
                break;
                    
            case Directions.RIGHT:
                posicion[0] =row;
                posicion[1] =col + 1;
                break;
                            
            case Directions.UP:
                posicion[0] =row - 1;
                posicion[1] =col;
                break;
                    
            case Directions.DOWN:
                posicion[0] =row + 1;
                posicion[1] =col;
                break;
        }
        
        return posicion;
    }
    
    /*
        Utilizando el dado, genera una posición aleatoria en el laberinto 
        (fila y columna) asegurando que esta esté vacía. Genera internamente 
        posiciones hasta que se cumple esta restricción y una vez generada se 
        devuelve. Si no hay posiciones vacías se producirá un bucle infinito.
    */
    private int[] randomEmptyPos(){
        
        int [] posicion = new int[2];
        boolean vacio = false;
        
        while(!vacio){
        
            posicion[0] = Dice.randomPos(this.nRows);
            posicion[1] = Dice.randomPos(this.nCols);
        
            if(emptyPos(posicion[0],posicion[1])){
                vacio = true;
            }
        }
        
        return posicion;
    }
    
    private Monster putPlayer2D(int oldRow, int oldCol, int row, int col, 
                                Player player){
        
        Monster output = null;
        
        if(canStepOn(row,col)){
            if(posOK(row, col)){
                
                Player p = this.laberintoJugadores[oldRow][oldCol];
                
                if(p == player){
                    updateOldPos(oldRow, oldCol);
                    this.laberintoJugadores[oldRow][oldCol] = null;
                }
            }
            
            boolean monsterPos = monsterPos(row, col);
            
            if(monsterPos){
            
                this.laberinto[row][col] = COMBAT_CHAR;
                output = this.laberintoMonstruos[row][col];
                
            }else{
            
                char number = player.getNumber();
                this.laberinto[row][col] = number;
            }
            
            this.laberintoJugadores[row][col] = player;
            player.setPos(row, col);
        } 
            
        return output;
    }
    
}











