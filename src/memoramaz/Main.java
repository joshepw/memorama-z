//Paquetes y Librerias
package memoramaz;

import assets.Asset;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public final class Main extends JFrame{
    
    private Imagen sideImage;
    private Imagen titleImage;

    // Record Assets
    private static Imagen recordImage;
    private static JLabel recordName;
    private static JLabel recordStats;
    
    // Botones de dificultad    
    private MenuBtn facilBtn;
    private MenuBtn normalBtn;
    private MenuBtn dificilBtn;
    
    // Objetos principales
    static Record record;
    private Juego juego;
    
    public Main(){
        record = getRecord();
        init();
    }
    
    public void init(){
        Container panel = this.getContentPane();
        
        // Propiedades del Frame
        this.setTitle("Memorama Z");
        this.setSize(820, 525);
        this.setLocation(50, 50);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        
        panel.setLayout(null);
        panel.setBackground(new Color(253, 250, 243));


        recordName = new JLabel(record.getName());
        recordName.setSize(400, 56);
        recordName.setLocation(476, 200);
        recordName.setForeground(new Color(149, 144, 130));
        recordName.setFont(new Font("Triforce", Font.PLAIN, 32));
        
        recordStats = new JLabel(record.toString());
        recordStats.setSize(400, 26);
        recordStats.setLocation(476, 244);
        recordStats.setForeground(new Color(149, 144, 130));
        recordStats.setFont(new Font("Triforce", Font.PLAIN, 18));
        
        // Imagenes
        sideImage = new Imagen(0, 0, "side.jpg");
        titleImage = new Imagen(370, 10, "title.jpg");
        recordImage = new Imagen(410, 210, "master.jpg");
        
        // Botones de Dificultad
        facilBtn = new MenuBtn(460, 280, "facil");
        normalBtn = new MenuBtn(460, 350, "normal");
        dificilBtn = new MenuBtn(460, 420, "dificil");
        
        panel.add(sideImage);
        panel.add(titleImage);
        
        panel.add(facilBtn);
        panel.add(normalBtn);
        panel.add(dificilBtn);
        
        panel.add(recordImage);
        panel.add(recordName);
        panel.add(recordStats);
        
        // Eventos
        facilBtn.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            showGame(Juego.EASY);
          }
        });
        
        normalBtn.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            showGame(Juego.REGULAR);
          }
        });
        
        dificilBtn.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            showGame(Juego.HARD);
          }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        // Edicion de estilos globales
        UIManager uiManager = new UIManager();
        
        try {
            // Sustituyo System.out.print() a log.txt
            System.setOut(new PrintStream(new FileOutputStream("log.txt", true)));
            
            // Importación de la fuente Triforce
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, Asset.class.getResourceAsStream("Triforce.ttf")));
        } catch (IOException | FontFormatException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Estilos Globales (Dialogos, Botones del Dialogo y TextField del Dialogo)
        UIManager.put("OptionPane.background",new Color(253, 250, 243));
        UIManager.put("Panel.background",new Color(253, 250, 243));
        
        UIManager.put("Button.opaque", true);
        UIManager.put("Button.border", new EmptyBorder(6,0,6,0));
        UIManager.put("Button.background",new Color(253, 250, 243));
        UIManager.put("Button.foreground",new Color(187, 0, 20));

        UIManager.put("TextField.opaque", true);
        UIManager.put("TextField.border", BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(254, 161, 0)), new EmptyBorder(4,0,4,0)));
        UIManager.put("TextField.foreground",new Color(75, 33, 13));
        UIManager.put("TextField.background",new Color(253, 250, 243));
        UIManager.put("TextField.font", new Font("Triforce", Font.BOLD, 28));

        // Fecha Actual
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        
        System.out.println("========= INICIO DEL JUEGO =========");
        System.out.println("[ FECHA ] : " + dateFormat.format(date));
        
        // Mostrar el Frame
        Main menu = new Main();
        menu.setVisible(true);
    }
    
    // Crea una nueva partida
    private void showGame(int type){
        juego = new Juego(type, this);
    }
    
    
    public static void setRecord(Record record){
        Main.record.saveOldRecord();
        Main.record = record;
        Main.recordName.setText(record.getName());
        Main.recordStats.setText(record.toString());
        
        FileOutputStream fout;
        try {
            fout = new FileOutputStream("record.z");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(record);
        } catch (IOException ex) {
            System.out.println("[ ERROR ] : Error al salvar record en archivo");
        }
    }
    
    public static int getPuntos(){
        return record.getPuntos();
    }

    private Record getRecord() {
        File recordFile = new File("record.z");
        
        if(recordFile.exists()){
            try {
                FileInputStream streamIn = new FileInputStream("record.z");
                ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
                return (Record) objectinputstream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                return new Record("Sin Record", 0, 0, 0);
            }
        }else{
            return new Record("Sin Record", 0, 0, 0);
        }
    }
}
