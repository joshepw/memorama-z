//Paquetes y Librerias
package memoramaz;

import assets.Asset;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public final class MenuBtn extends JLabel{
    
    private ImageIcon normal;
    private ImageIcon hover;
    
    public MenuBtn(int x, int y, String icon){
        super("");
        init(x,y,icon);
    }
    
    public void init(int x, int y, String icon){
        normal = new ImageIcon(Asset.class.getResource(icon + ".jpg"));
        hover = new ImageIcon(Asset.class.getResource(icon + "_hover.jpg"));
        
        this.setSize(200, 66);
        this.setLocation(x, y);
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
        });
    }
    
    public void setHover(){
        this.setIcon(hover);
    }

    public void setNormal(){
        this.setIcon(normal);
    }
}
