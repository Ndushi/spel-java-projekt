package projekt;

import core.Vector;
import core.Vertex;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author johannes
 */
public class World {
    public double angle=75*Math.PI/180;
    public int width=0,height=0;
    public World(){
        Image image=new ImageIcon(getClass().getResource("/res/metal_box.jpg")).getImage();
        this.width=image.getWidth(null);
        this.height=image.getWidth(null);
        
    }
}
