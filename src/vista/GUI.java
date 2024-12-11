/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import irrgarten.Enumerados;
import irrgarten.GameState;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author theki
 */
public class GUI extends javax.swing.JFrame implements UI {

    private Cursors cursor;
    
    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        setVisible(true);
        cursor=new Cursors(this, true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tablero = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Irrgarten");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel1.setText("Turno del jugador:");

        Tablero.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(Tablero);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap(86, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    @Override
    public Enumerados.Directions nextMove() {

        return cursor.getDirection();

    }

    @Override
    public void showGame(GameState gameState) {
        
        if(!gameState.isWinner()){
            
            String laberinto = gameState.getLabyrinth();
            
            String []  filas = laberinto.split("\n");
            String [][] celdas = new String [filas.length][];
            
            for(int i = 0; i < filas.length; i++){
            
                celdas[i]=filas[i].split(" ");
            
            }
            
            DefaultTableModel model = new DefaultTableModel(celdas, new String[filas[0].split(" ").length]);
            
            Tablero.setModel(model);            
            
            Tablero.getTableHeader().setVisible(false);
            
            Tablero.setRowHeight(254/filas.length);
            
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setFont(new Font("Arial", Font.BOLD, 20));
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);  // Alineación horizontal al centro
            centerRenderer.setVerticalAlignment(SwingConstants.CENTER);    // Alineación vertical al centro

        // Aplicar el renderer a todas las columnas de la tabla
        for (int i = 0; i < Tablero.getColumnCount(); i++) {
            Tablero.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
            
            int actualJugador = gameState.getCurrentPlayer() + 1;
            String log = gameState.getLog();
            
            //jTextArea1.setText(laberinto + "\n\n" + log);
            
            jTextArea1.setText(log);
            
            jLabel1.setText("Turno del jugador: " + actualJugador);
            
        }else{
            
            jLabel1.setText("El ganador es el jugador: " + (gameState.getCurrentPlayer() + 1));
        }   
        
        repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tablero;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}