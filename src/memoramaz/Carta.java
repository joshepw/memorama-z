//Paquetes y Librerias
package memoramaz;

import assets.Asset;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public final class Carta extends JLabel{
    // Constantes
    public static final int IMAGE_SIZE = 120;
    public static final int OCULTA = 0;
    public static final int MOSTRADA = 1;
    public static final int ENCONTRADA = 2;
    
    // Propiedades
    private final int x;
    private final int y;
    private final int type;
    private int status;
    private static Juego game;
    
    // Iconos por estado
    private ImageIcon normal;
    private ImageIcon hover;
    private ImageIcon item;
    
    public Carta(int x, int y, int type, Juego game){
        super("");
        
        this.x = x;
        this.y = y;
        this.type = type;
        this.status = Carta.OCULTA;
        this.game = game;
        
        init();
    }
    
    public void init(){
        normal = new ImageIcon(Asset.class.getResource("item_x.jpg"));
        hover = new ImageIcon(Asset.class.getResource("item_hover.jpg"));
        item = new ImageIcon(Asset.class.getResource("item_" + this.type + ".jpg"));
                
        this.setSize(Carta.IMAGE_SIZE, Carta.IMAGE_SIZE);
        this.setLocation(this.y * Carta.IMAGE_SIZE, this.x * Carta.IMAGE_SIZE);
        this.setIcon(normal);
        
        // Eventos
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
              setHover();
            }

            @Override
            public void mouseExited(MouseEvent e) {
              setNormal();
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
              setItem();
            }
        });
    }
    
    public void setHover(){
        if (this.status == Carta.OCULTA)
            this.setIcon(hover);
    }

    public void setNormal(){
        if(this.status == Carta.OCULTA)
            this.setIcon(normal);
    }
    
    // Verifica el estado de la Carta al hacer click
    public void setItem(){
        if(this.status == Carta.OCULTA){
            this.setIcon(item);
            
            if(game.cartaTurno == null){
                game.cartaTurno = this;
                this.status = Carta.MOSTRADA;
            }else{
                if(game.cartaTurno.type == this.type){
                    game.cartaTurno.status = Carta.ENCONTRADA;
                    this.status = Carta.ENCONTRADA;
                    
                    game.setTurno(Juego.RIGHT);
                }else{
                    game.cartaTurno.resetItem();
                    this.resetItem();
                    
                    game.setTurno(Juego.FAIL);
                }
                game.cartaTurno = null;
            }
        }
    }
    
    // Reinicia el estado de la Carta
    private void resetItem(){
        if(this.status == Carta.MOSTRADA){
            this.setIcon(normal);
            this.status = Carta.OCULTA;
        }
    }
}
