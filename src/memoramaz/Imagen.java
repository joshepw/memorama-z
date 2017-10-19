package memoramaz;

import assets.Asset;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Imagen extends JLabel{
    
    private final ImageIcon image;
    
    public Imagen(int x, int y, String url){
        super("");
        this.image = new ImageIcon(Asset.class.getResource(url));
        init(x,y);
    }
       
    private void init(int x, int y){
        this.setSize(this.image.getIconWidth(), this.image.getIconHeight());
        this.setLocation(x, y);
        this.setIcon(this.image);
    }
}
