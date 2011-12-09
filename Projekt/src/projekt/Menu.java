package projekt;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import projekt.event.*;
import render.PFont;
import render.Projekt;

/**
 *
 * @author Klaph
 */
public final class Menu extends JFrame {
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

	private final int WIDTH = 405, HEIGHT = 405;
	Image dbImage;
	Graphics dbg;
	Canvas can = new Canvas();
	Projekt proj = new Projekt();
	ImageIcon img;
	int step=0;
	int diff=0;
	int lvl=0;
	int innerStep=0;
	private int max=3;
	private int innerMax=0;
	int controll=-1;
	int incr=10;
	private EventHandler eHandle=new EventHandler();
	private int exit=0;
	Runner game=null;
	 
	boolean fullscreenError=false;
	Menu() throws IOException {
		super("Pokemans - Menu");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		proj.removeKeyListener(proj.eHandle);
		addKeyListener(eHandle);
		proj.addKeyListener(eHandle);
		Color col = new Color(0, 0, 255, 0);
		
		img = new ImageIcon(getClass().getResource("/res/pokemans.png").getPath());
		
		add(proj);
		setVisible(true);
		Dimension size = new Dimension(WIDTH , HEIGHT );
		setSize(size);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setLocationRelativeTo(null);
		pack();
		
		dbImage = createImage(this.getSize().width, this.getSize().height);
		dbg = dbImage.getGraphics();
		Graphics g=proj.getGraphics();
		while(exit!=1){
			event();
			proj.tick(proj.eHandle.keys);
			proj.update(dbg);
			paint(dbg);
			g.drawImage(dbImage, 0, 0, null);
			if(game!=null&&game.game.exitCode==1){
				game=null;
				setVisible(true);
			}
		}
		this.dispose();
	}
	
	public void event(){
		if(eHandle.release){
			if(lvl==0)
				diff=step;
			else diff=innerStep;
		}
		if(controll==-1){
			if(eHandle.keys[KeyEvent.VK_ESCAPE])
				if(lvl==0&&exit==0)
					exit=1;
				else{
					exit=2;
					lvl=0;
					innerStep=0;
				}
			else if(eHandle.keys[Keys.down]||eHandle.keys[KeyEvent.VK_S]){
				if(lvl==0&&diff==step)
					step++;
				else if(diff==innerStep) 
					innerStep++;
			}
			else if(eHandle.keys[Keys.up]||eHandle.keys[KeyEvent.VK_W]){
				if(lvl==0&&diff==step)
					step--;
				else if(diff==innerStep) 
					innerStep--;
			}
			else if(eHandle.keys[Keys.a]&&lvl==0){
				lvl+=step;
				if(lvl==0){
					//exit=1;
					eHandle.keys[Keys.a]=false;
					game=new Runner();
					game.game.focus.incr=(double)incr/10;
					this.setVisible(false);
					//game.game.exitCode==
				}
			}
			else if(eHandle.keys[Keys.b]){
				lvl=0;
				fullscreenError=false;
			}
		}
		if(step<0)
			step=max;
		else if(step>max)
			step=0;
		if(innerStep<0)
			innerStep=innerMax;
		else if(innerStep>innerMax)
			innerStep=0;
		if(eHandle.release)exit=0;
		
		if(innerStep==2&&diff==2&&lvl!=0) {
			if(eHandle.keys[Keys.left])
				incr--;
			if(eHandle.keys[Keys.right])
				incr++;
			eHandle.keys[Keys.right]=false;
			eHandle.keys[Keys.left]=false;
			if(incr>10)incr=10;
			if(incr<5)incr=5;
		}
		if(lvl!=0&&(eHandle.keys[Keys.a])||(controll>=0&&controll<=6)){
			if(innerStep==1||(controll>=0&&controll<=6)){
				if(eHandle.keys[Keys.a]||eHandle.keys[KeyEvent.VK_ESCAPE]){
					eHandle.previus=-1;
					eHandle.keys[Keys.a]=false;
					eHandle.keys[KeyEvent.VK_ESCAPE]=false;
					if(controll==-1)
						controll=0;
					else controll=-1;
				}
				if(eHandle.previus!=-1){
					switch(controll){
						case 0:				//up key
							Keys.up=eHandle.previus;
							controll++;
							break;
						case 1:				//Left key
							Keys.left=eHandle.previus;
							controll++;
							break;
						case 2:				//Down key
							Keys.down=eHandle.previus;
							controll++;
							break;
						case 3:				//Right key
							Keys.right=eHandle.previus;
							controll++;
							break;
						case 4:				//A key
							Keys.a=eHandle.previus;
							eHandle.keys[Keys.a]=false;
							controll++;
							break;
						case 5:				//B key
							Keys.b=eHandle.previus;
							eHandle.keys[Keys.b]=false;
							controll=-1;
							break;
					}
					eHandle.previus=-1;
				}
			}
			else if(innerStep==0&&diff==0)
				fullscreenError=true;
		}
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(new Color(0xFFFDD0));
		//g.fillRoundRect(2, 2, 400, 400, 25, 25);
		PFont pf = new PFont("The adventures of Dud");
		g.setColor(new Color(100,100,0,100));
		g.fillRect(215-4, 110-4, 165+8, 40+8);
		g.setColor(new Color(0xCCFF90));
		g.fillRect(215, 110, 165, 40);
		g.drawImage(img.getImage(), 0, 0, WIDTH,
				(int) (img.getIconHeight() * ((double) WIDTH / (double) img.getIconWidth())), null);
		pf.SetColor("green").PrintAt(g, 225, 120);
		int x = 30, y = 200;
		if(lvl==0){
			g.setColor(new Color(100,100,0,100));
			g.fillRect(x-25-4, y-20-4, 110+8, 120+8);
			g.setColor(new Color(0xFFFDD0));
			g.fillRect(x-25, y-20, 110, 120);
			pf.SetString("START").SetColor("black").PrintAt(g, x, y);
			pf.SetString("TUTORIAL").PrintAt(g, x, y + 20);
			pf.SetString("OPTIONS").PrintAt(g, x, y + 40);
			pf.SetString("QUIT").PrintAt(g, x, y + 60);
		}
		else if(lvl==1){
			g.setColor(new Color(100,100,0,100));
			g.fillRect(x-25-4, y-20-4, 340+8, 120+8);
			
			g.setColor(new Color(0xFFFDD0));
			g.fillRect(x-25, y-20, 340, 120);
			
			pf.SetString("TUTORIAL TEXT HERE!!!!").PrintAt(g, x, y);
		}
		else if(lvl==2){
			innerMax=2;
			
			if(controll!=-1&&innerStep==1){
				g.setColor(new Color(100,100,0,100));
				g.fillRect(x-25-4, y+3-4, 100+8, 40+8);
				
				g.setColor(new Color(0xFFFDD0));
				g.fillRect(x-25, y+3, 100, 40);
				
				String []contr=new String[]{"UP","LEFT","DOWN","RIGHT","A - key","B - key"};
				pf.SetString(contr[controll]).PrintAt(g, x , y + 20);
			}
			else{
				g.setColor(new Color(100,100,0,100));
				g.fillRect(x-25-4, y-4, 110+8, 90+8);
				g.setColor(new Color(0xFFFDD0));
				g.fillRect(x-25, y, 110, 90);
				if(fullscreenError)
					pf.SetColor("red").SetString("FULLSCREEN").PrintAt(g, x , y + 20);
				else
					pf.SetString("FULLSCREEN").PrintAt(g, x , y + 20);
				pf.SetColor("black");
				pf.SetString("CONTROLS").PrintAt(g, x , y + 40);
				pf.SetString("LINE SPEED").PrintAt(g, x , y + 60);
				if(innerStep==2&&diff==2){
					g.setColor(new Color(100,100,0,100));
					g.fillRect(x+85+4, y-4, 30, 90+8);
					g.setColor(new Color(0xFFFDD0));
					g.fillRect(x+85, y, 30, 90);
					pf.SetString(" - "+(double)incr/10).PrintAt(g, x+65, y+60);
				}
			}
		}
		else if(lvl==3)
			exit=1;
		g.setColor(new Color(0x000080));
		//x=20;
		//y=60;
		int offX = 20;
		int dist=19;
		if(lvl==0)
			g.fillPolygon(new int[]{x - offX, x - offX + 5, x - offX}, new int[]{y+step*dist, y + 5+step*dist, y + 10+step*dist}, 3);
		else if(lvl==2&&controll==-1)
			g.fillPolygon(new int[]{x - offX, x - offX + 5, x - offX}, new int[]{y+innerStep*dist+20, y + 5+innerStep*dist+20, y + 10+innerStep*dist+20}, 3);
		
		//Options

	}

	public static void main(String[] args) {
		try {
			new Menu();
		} catch (IOException ex) {
		}
	}
}
