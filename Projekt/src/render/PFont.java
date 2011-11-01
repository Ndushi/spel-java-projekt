
package render;
/**
 *
 * @author Johan Lindskogen
 */
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;


public class PFont {
    private BufferedImage Bimg;
    private String text;
    private ImageIcon img= new ImageIcon(getClass().getResource("/res/fonts/white.png"));
    
    PFont(String str){
	text = str;
	ImgString();
    }
    PFont(){}
    PFont(String str, Graphics g, int x, int y){
	text = str;
	ImgString();
	PrintAt(g, x, y);
    }
    void PrintAt(Graphics g, int x, int y){
	g.drawImage(Bimg, x, y, null);
    }
    void SetString(String str) {
	text = str;
	ImgString();
    }
    void SetColor(String str){
	if (str == "white" || str == "black")
	    img = new ImageIcon(getClass().getResource("/res/fonts/"+str+".png"));
	else
	    System.out.println("\""+str+"\" is not a valid color!");
    }
    /** @TODO hejsan 
     */
    @Override
    public String toString(){
	return text;
    }
    private BufferedImage ImgString(){
	Bimg = new BufferedImage(7*text.length(), 13, BufferedImage.TYPE_INT_ARGB);
	for (int i=0; i<text.length(); i++){
	    BufferedImage bi = new BufferedImage(7,13,BufferedImage.TYPE_INT_ARGB);
	    bi.createGraphics().drawImage(img.getImage(),-(text.codePointAt(i)-33)*7,0,null);
	    Bimg.createGraphics().drawImage(bi,i*7,0,null);
	}
	return Bimg;
    }
}
