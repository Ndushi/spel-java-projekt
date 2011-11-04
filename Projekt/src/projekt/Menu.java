package projekt;
import javax.swing.JFrame;
import java.awt.*;
import javax.swing.ImageIcon;
import render.PFont;
/**
 *
 * @author Klaph
 */
public class Menu extends JFrame {
    //Play
	//Namn
	//SPELA
    //Tutorial där man visar spelets koncept
    //Options 
	//Texthastighet
	//Fullskärmsläge 
	//Kontroller
	//Backa till föregående meny
    //(Quit)
    private final int WIDTH=405,HEIGHT=405;
    Image dbImage;
    Graphics dbg;
    Canvas can=new Canvas();
    ImageIcon img;

    Menu(){
	setVisible(true);
	setSize(WIDTH,HEIGHT); //400x400 - Default
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setResizable(false);
	this.add(can);
	img=new ImageIcon(getClass().getResource("/res/pokemans.png").getPath());
	
	while(true)update(can.getGraphics());
    }
    public static void main(String[] args){
	new Menu();
    }
    public void update(Graphics g){
	if (dbImage == null || dbImage.getWidth(this) != this.getSize().width) {
	    dbImage = createImage(this.getSize().width, this.getSize().height);
	    dbg = dbImage.getGraphics();
	}
	dbg.setColor(getBackground());
	dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

	dbg.setColor(getForeground());
	paint(dbg);
	g.drawImage(dbImage, 0, 0, this);
    }
    @Override
    public void paint(Graphics g){
	g.setColor(Color.white);
	g.fillRoundRect(0, 0, 404, 404, 25, 25);
	
	g.setColor(new Color(0xFFFDD0));
	g.fillRoundRect(2, 2, 400, 400, 25, 25);
	
	g.drawImage(img.getImage(), 0, 0,WIDTH,
			(int)(img.getIconHeight()*((double)WIDTH/(double)img.getIconWidth())), null);
	int x=30,y=200;
	PFont pf = new PFont("START").SetColor("black").PrintAt(g,x, y);
	pf.SetString("TUTORIAL").PrintAt(g, x, y+20);
	pf.SetString("OPTIONS").PrintAt(g,x,y+40);
	pf.SetString("QUIT").PrintAt(g,x,y+60);
	pf.SetString("FULLSCREEN").PrintAt(g,x+100,y+60);
	pf.SetString("CONTROLS").PrintAt(g,x+100,y+80);
	pf.SetString("LINE SPEED").PrintAt(g,x+100,y+100);
	g.setColor(new Color(0x000080));
	//x=20;
	//y=60;
	int offX=20;
	g.fillPolygon(new int[]{x-offX,x-offX+5,x-offX},new int[]{y,y+5,y+10},3);
	
	//Options
	
    }
}
