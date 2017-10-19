//Paquetes y Librerias
package memoramaz;

import assets.Asset;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Juego extends JFrame{
    // Constantes
    public static final int EASY = 0;
    public static final int REGULAR = 1;
    public static final int HARD = 2;
    
    public static final int FAIL = 0;
    public static final int RIGHT = 1;
    
    // Propiedades
    private int widthTablero;
    private int heightTablero;
    
    private final Carta[][] cartas;
    private final int[] paresCount;
    private int pares;
    
    public int fallas = 0;
    public int puntos = 0;
    public int intentos = 0;
    
    // Elementos del lado derecho del Tablero (STATS)
    private JLabel puntosImage;
    private JLabel intentosImage;
    private JLabel fallasImage;
    
    // Main.java Frame
    private final JFrame parentFrame;
    
    // Carta temporal del primer turno
    public static Carta cartaTurno;
    
    public Juego(int dificultad, JFrame parentFrame){
        switch(dificultad){
            case 0: widthTablero = 4; heightTablero = 3; pares =  6; break;
            case 1: widthTablero = 4; heightTablero = 4; pares =  8; break;
            case 2: widthTablero = 5; heightTablero = 4; pares = 10; break;
            
            default: break;
        }
        
        this.parentFrame = parentFrame;
        
        cartas = new Carta[this.heightTablero][this.widthTablero];
        paresCount = new int[pares];
        
        rellenarTablero();
        init();
    }
    
    private void init(){
        Container panel = this.getContentPane();
        
        this.setTitle("Tablero");
        this.setSize((this.widthTablero * Carta.IMAGE_SIZE) + 150, (this.heightTablero * Carta.IMAGE_SIZE) + 30);
        this.setLocation(50, 50);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        panel.setLayout(null);
        panel.setBackground(new Color(255, 255, 255));
        
        // Elementos del lado derecho del Tablero (STATS)
        puntosImage = new JLabel("" + puntos);
        puntosImage.setSize(145,100);
        puntosImage.setLocation((this.widthTablero * Carta.IMAGE_SIZE) + 5, 10);
        puntosImage.setIcon(new ImageIcon(getClass().getClassLoader().getResource("assets/puntos.jpg")));
        puntosImage.setHorizontalTextPosition(JLabel.CENTER);
        puntosImage.setVerticalTextPosition(JLabel.BOTTOM);
        puntosImage.setFont(new Font("Triforce", Font.PLAIN, 58));
        puntosImage.setForeground(new Color(175, 161, 151));
        
        intentosImage = new JLabel("" + intentos);
        intentosImage.setSize(145,100);
        intentosImage.setLocation((this.widthTablero * Carta.IMAGE_SIZE) + 5, 120);
        intentosImage.setIcon(new ImageIcon(getClass().getClassLoader().getResource("assets/intentos.jpg")));
        intentosImage.setHorizontalTextPosition(JLabel.CENTER);
        intentosImage.setVerticalTextPosition(JLabel.BOTTOM);
        intentosImage.setFont(new Font("Triforce", Font.PLAIN, 58));
        intentosImage.setForeground(new Color(175, 161, 151));
        
        fallasImage = new JLabel("" + fallas);
        fallasImage.setSize(145,100);
        fallasImage.setLocation((this.widthTablero * Carta.IMAGE_SIZE) + 5, 240);
        fallasImage.setIcon(new ImageIcon(getClass().getClassLoader().getResource("assets/fallas.jpg")));
        fallasImage.setHorizontalTextPosition(JLabel.CENTER);
        fallasImage.setVerticalTextPosition(JLabel.BOTTOM);
        fallasImage.setFont(new Font("Triforce", Font.PLAIN, 58));
        fallasImage.setForeground(new Color(175, 161, 151));
        
        // Diagramar Cartas al tablero
        for (int x = 0; x < this.heightTablero; x++)
          for (int y = 0; y < this.widthTablero; y++)
            panel.add(this.cartas[x][y]);
        
        panel.add(puntosImage);
        panel.add(intentosImage);
        panel.add(fallasImage);
        
        parentFrame.setVisible(false);
        setVisible(true);
    }
    
    private void rellenarTablero(){
        for (int x = 0; x < this.heightTablero; x++)
          for (int y = 0; y < this.widthTablero; y++)
            this.cartas[x][y] = getRandomCarta(x,y);
    }

    private Carta getRandomCarta(int x, int y) {
        return new Carta(x,y, randomCartaType(), this);
    }
    
    private int randomCartaType(){
        int r = new Random().nextInt(this.pares);
        if(paresCount[r] < 2){
            paresCount[r]++;
            return r;
        }else{
            return randomCartaType();
        }
    }
    
    @Override
    protected void processWindowEvent(final WindowEvent e) {
        super.processWindowEvent(e);
        
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            this.parentFrame.setVisible(true);
            dispose();
        }
    }
    
    public void setTurno(int type){
        intentos++;
        
        if(Juego.FAIL == type){
            fallas++;
            
            if(puntos > 0)
                puntos -= 5;
        }else{
            puntos += 10;
            pares--;
        }
        
        puntosImage.setText("" + puntos);
        intentosImage.setText("" + intentos);
        fallasImage.setText("" + fallas);
        
        if(pares == 0){
            if(puntos > Main.getPuntos()){
                String nombre = (String) JOptionPane.showInputDialog(null, "¿Cual es su nombre?", "Nuevo Record", JOptionPane.PLAIN_MESSAGE, new ImageIcon(Asset.class.getResource("record.jpg")), null, null);

                if(nombre == null || "".equals(nombre))
                    nombre = "Anonimo";
            
                Main.setRecord(new Record(nombre, puntos, intentos, fallas));
                System.out.println("[ NUEVO RECORD ] : " + nombre + " | " + puntos + " Puntos | " + intentos + " Intentos | " + fallas + " Fallas");
            }else{
                System.out.println("[ PARTIDA GANADA ] : " + puntos + " Puntos | " + intentos + " Intentos | " + fallas + " Fallas");
            }
            
            parentFrame.setVisible(true);
            dispose();
        }
    }
}