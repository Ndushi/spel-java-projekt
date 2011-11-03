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
    Image dbImage;
    Graphics dbg;
    Canvas can=new Canvas();
    ImageIcon img;

    Menu(){
	setVisible(true);
	setSize(405,405); //400x400 - Default
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.add(can);
	System.out.println(getClass().getResource("/res/"));
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
	
	g.drawImage(img.getImage(), 10, 10, null);
	
	PFont pf = new PFont("START").SetColor("black").PrintAt(g,30, 160);
	pf.SetString("TUTORIAL").PrintAt(g, 30, 180);
	pf.SetString("OPTIONS").PrintAt(g,30,200);
	pf.SetString("QUIT").PrintAt(g,30,220);
	pf.SetString("FULLSCREEN").PrintAt(g,130,200);
	pf.SetString("CONTROLS").PrintAt(g,130,220);
	pf.SetString("LINE SPEED").PrintAt(g,130,240);
	g.setColor(new Color(0x000080));
	int x=20,y=60;
	g.fillPolygon(new int[]{x,x+5,x},new int[]{y,y+5,y+10},3);
	
	//Options
	
    }
}
