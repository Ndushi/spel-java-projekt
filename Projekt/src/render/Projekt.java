package render;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import projekt.event.Dialogs;
import projekt.event.Keys;

/**
 * Projekt är en subklass av Render vilket innebär att det är här all grafik egentligen skrivs ut på skärmen
 * @author johannes
 */
public class Projekt extends Render {

	/**
	 * världens nuvarande egenskaper sparas och ändras i denna variabel.
	 */
	public World world;
	/**
	 * focus innehåller information om var karaktären befinner sig och hur världen skall relatera sig till var karaktären är(görs ex med att sätta world.setOffset(int x,int y)
	 */
	public character focus;

	/**
	 * konstruktorn för spelet
	 */
	public Projekt() {
		focus = new character(14, 16, radius);
		String[] s = new String[4];
		for (int i = 0; i < 4; i++) {
			s[i] = "/res/CharMain/ts" + i + ".png";
		}
		focus.setChar("/res/CharMain/firehero.gif");
		this.world = new World(this.getClass().getResource("/res/worlds/world200").getPath());
		focus.setOnWalkCallback(new OnWalkCallback() {

			public boolean onWalk() {
				return true;
			}

			public boolean onStartWalk() {
				return false;
			}

			public boolean onEndWalk() {
				if (!Projekt.this.focus.action.isEmpty()) {
					Projekt.this.focus.freeze = false;
					Projekt.this.focus.action = "";
				}
				return true;
			}
		});
	}
	int delay = 0;
	private long millidelay;
	/**
	 * tick är medlem av Render och anropas varje loopintervall och här kollar vi då om det har tryckts ner någon tangent
	 * @param k boolean[]
	 */
	@Override
	public void tick(boolean[] k) {
		boolean left = (k[Keys.left] || k[KeyEvent.VK_A]) && !focus.freeze;
		boolean right = (k[Keys.right] || k[KeyEvent.VK_D]) && !focus.freeze;
		boolean up = (k[Keys.up] || k[KeyEvent.VK_W]) && !focus.freeze;
		boolean down = (k[Keys.down] || k[KeyEvent.VK_S]) && !focus.freeze;
		boolean a=(k[Keys.a]);
		boolean b=(k[Keys.b]);
		boolean select=(k[Keys.select]);
		if (k[Keys.esc] || this.exitCode == 1) {
			this.exitCode = 1;
			return;
		}
		
		if (!(left || right || up || down)) {
			//**** Delayen ****//
			delay++;
		} else {
			delay = 0;
		}
		if (delay >= radius) {
			this.focus.frame = 0;
		}
		//**** pickup ****//
		if (k[Keys.a]){
			if(Dialogs.endof&&this.pickup(false))
				millidelay = System.currentTimeMillis();
			else if (millidelay!=0&& System.currentTimeMillis() - millidelay > 100 ) {
				this.drawDialog(Dialogs.nextMessage());
				if(Dialogs.endof){
					this.focus.onWalkCallback.onEndWalk();
					this.pickup(true);
				}
				millidelay = 0;
			}
			millidelay = System.currentTimeMillis();
			
		}
		
		
		
		
		//X-axeln
		if (this.focus.y - this.focus.y2 / radius == 0 && !up && !down) {
			if ((left && !right) && (this.focus.x - this.focus.x2 / radius >= 0) && focus.x >= 1 && this.world.canGo(this.focus.x - 1, this.focus.y)) {
				focus.x--;
			}
			if ((right && !left) && (this.focus.x - this.focus.x2 / radius <= 0) && focus.x2 < world.width * radius - radius && this.world.canGo(this.focus.x + 1, this.focus.y)) {
				focus.x++;
			}
		}
		//Y-axeln
		if (this.focus.x - this.focus.x2 / radius == 0) {
			if ((up && !down) && (this.focus.y - this.focus.y2 / radius >= 0) && focus.y > 1 && this.world.canGo(this.focus.x, this.focus.y - 1)) {
				focus.y--;
			}
			if ((down && !up) && (this.focus.y - this.focus.y2 / radius <= 0) && focus.y >= 1 && focus.y2 < world.height * radius - radius && this.world.canGo(this.focus.x, this.focus.y + 1)) {
				focus.y++;
			}
		}
		/* Directions!!!*/
		if ((this.focus.x) * radius - (this.focus.x2) == 0) {
			this.focus.stand = false;
			if ((left && !right)) {
				focus.direciton = 1;
			}
			if ((right && !left)) {
				focus.direciton = 2;
			}
		}
		if ((this.focus.y) - (this.focus.y2) / radius == 0) {
			if ((up && !down)) {
				focus.direciton = 3;
			}
			if ((down && !up)) {
				focus.direciton = 0;
			}
		}
	}

	private boolean pickup(boolean pickNow) {
		int direction = this.focus.direciton;
		int x = 0, y = 0;
		if (direction == 1 || direction == 2) {
			x = (int) direction * 2 - 3;
		} else if (direction == 3 || direction == 0) {
			y = (-2 * direction) / 3 + 1;
		}
		Color temp = this.world.getRGBA((int) this.focus.x2 / radius + x, (int) this.focus.y2 / radius + y);
		if (temp.getRed()==255&&
			!(temp.equals(new Color(0xff000000)) || temp.equals(new Color(0xffffffff)))) {
			if(Dialogs.endof&&!pickNow){
				this.focus.action ="dialog";
				Dialogs.initDialog(Dialogs.Begin.sayHello);
				this.focus.freeze = true;
			}
			if(!pickNow)
				return true;
			Graphics2D ag = this.world.alpha.createGraphics();
			ag.setColor(Color.white);
			ag.fillRect((int) this.focus.x2 / radius + x, (int) this.focus.y2 / radius + y, 1, 1);

			ag = this.world.items.createGraphics();
			AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f);
			Composite c = ag.getComposite();
			ag.setComposite(composite);
			ag.setColor(new Color(0, 0, 0, 0));
			ag.fillRect(((int) this.focus.x2 / radius + x) * radius, ((int) this.focus.y2 / radius + y) * radius, radius, radius);
			ag.setComposite(c);
			int b = temp.getBlue();
			if (b != 0) {
				this.focus.addItem(b);
			}
			return true;
		}
		return false;
	}

	/**
	 *  Denna funktionen ritar allt i hela spelet (inklusive det world genererar) som en bild
	 * @param g Graphics
	 */
	@Override
	public void paint(Graphics g) {
		if ((focus.x * focus.radius != focus.x2)) {
			focus.slowMove(0);
		} else if (focus.y * focus.radius != focus.y2) {
			focus.slowMove(1);
		}
		int frx=this.focus.x;
		int fry=this.focus.y;
		if (world.isPoortal((int) (this.focus.x2 / radius - 0.1 + 1), (int) (this.focus.y2 / radius - 0.1 + 1))&&!this.focus.freeze) {
			/** @TODO switch worlds!!! */
			focus.action="world";
			focus.freeze = true;
			try {
				try {
					this.focus.addWorld(this.world.copy());
				} catch (CloneNotSupportedException ex) {
					//Logger.getLogger(Projekt.class.getName()).log(Level.SEVERE, null, ex);
				}
				String pa="";
				try{
					pa = getClass().getResource("/res/worlds/world" +world.pos[2]).getPath();
				}catch(NullPointerException e){
					this.exitCode=1;
					System.out.println("unable to locate the world: /res/worlds/world" +world.pos[2]);
				}
				int x=this.world.pos[0];
				int y=this.world.pos[1];
				if (this.focus.getWorldFromPath(pa) == -1) 
					this.world.setWorld(pa,this.focus.x,this.focus.y);
				else {
					this.world.pos[0]=-1;
					this.world.pos[1]=-1;
					this.world.pos[2]=-1;
					this.world = this.focus.getWorld(this.focus.getWorldFromPath(pa));
				}
				this.focus.transport(x,y);
				if (this.world.canGo(this.focus.x , this.focus.y+ 1)) 
					this.focus.y++;
				else if (this.world.canGo(this.focus.x , this.focus.y- 1)) 
					this.focus.y--;
				else if (this.world.canGo(this.focus.x - 1, this.focus.y )) 
					this.focus.x--;
				else if (this.world.canGo(this.focus.x + 1, this.focus.y )) 
					this.focus.x++;
			} catch (ArrayIndexOutOfBoundsException b) {
				ErrorHandler.CharacterBoundary.CharacterOutOfBoundary();
				ErrorHandler.CharacterBoundary.resetCharacterPositionAt(this.focus, frx-1,fry+1);
				this.focus.freeze=false;
			}
		}
		try{
			world.paint(g, (int) this.focus.x2, (int) this.focus.y2, this.getWidth(), this.getHeight());
		}		
		catch(ArrayIndexOutOfBoundsException e){
			ErrorHandler.CharacterBoundary.CharacterOutOfBoundary();
			ErrorHandler.CharacterBoundary.resetCharacterPositionAt(this.focus, frx-1,fry+1);
		}
		BufferedImage t = focus.c.getSubimage(radius * (((int) focus.frame) % 4), 20 * focus.direciton, radius, 20);
		g.drawImage(t, this.getWidth() / 2 - t.getWidth() / 2 - radius / 2 + radius, this.getHeight() / 2 - t.getHeight() - radius / 5 + radius, this);
		world.paintTop(g, (int) this.focus.x2, (int) this.focus.y2, this.getWidth(), this.getHeight());
		
		//this.drawShadowWithString("Version: \u03B1 0.2", 2, 12, Color.white, new Color(0x666666));
		//new PFont("Alpha - version 0.5",g,2,12);
		if (this.focus.action.equals("dialog")) {
			g.setColor(new Color(0x666666));
			//g.setFont(new Font("Lucida Typewriter Regular", Font.BOLD, 12));
			this.drawDialog(Dialogs.message[Dialogs.getIndex()]);
		}
	}

	public void drawDialog(String message) {
		int b = 5;
		int m = 8;
		int y = this.getHeight() / 4;
		this.dbg.fillRect(0, y * 3, this.getWidth(), y + this.getHeight() % 4);
		this.dbg.setColor(Color.black);
		this.dbg.drawRect(b, y * 3 + b, this.getWidth() - 2 * b, y + this.getHeight() % 4 - 2 * b);

		this.dbg.setFont(new Font(Font.DIALOG, Font.BOLD, 10));
		String[] lines = message.split("\n");
		/*for (int i = 0; i < lines.length; i++) {
			FontMetrics fm = this.getFontMetrics(this.dbg.getFont());
			int width = fm.stringWidth(lines[i]);
			if (width < this.getWidth() - (2 * b)) */
		new PFont(message,this.dbg,b + b, 3 * y + b +m );
		//}
	}

	public void drawShadowWithString(String string, int x, int y, Color c1, Color c2) {
		this.dbg.setColor(c2);
		this.dbg.drawString(string, x + 1, y);
		this.dbg.drawString(string, x - 1, y);
		this.dbg.drawString(string, x, y + 1);
		this.dbg.drawString(string, x, y - 1);
		this.dbg.setColor(c1);
		this.dbg.drawString(string, x, y);
	}
}
