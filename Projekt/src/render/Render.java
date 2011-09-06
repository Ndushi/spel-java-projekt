package render;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import projekt.event.EventHandler;
/**
 * Render funktionen är en indirekt använd klass (via Projekt) som gör tillgång till skällva Canvas på JFramen
 * @author johannes
 */
public class Render extends Canvas {
	/**
	 * om spelet vill stänga ner programmet så sätts denna till 1
	 */
	 public int exitCode=0;
	/**
	 * radius används för att bestämma hur stor den kvadratiska ruta är som karaktären kan stå i
	 */
	protected int radius = 20;
	/**
	 *  dbg används som en buffert för att förvara föregående renderade bild utav Canvaset
	 */
	protected Graphics dbg;
	/**
	 * dbImage lagrar dbg
	 */
	protected Image dbImage;
	/**
	 * eHandle används för tangentnedtryckningar
	 */
	public EventHandler eHandle;
	/**
	 *  tick används till att kolla om några nertryckningar av användaren är nere(game.tick(e);
	 * @param k boolean[]
	 */
	public void tick(boolean[] k){}
	/**
	 *  konstruktorn för våt canvasobjekt ställer in några standardvärden som förhindrar Exceptions
	 */
	public Render(){
		setBackground(Color.black);
		this.eHandle=new EventHandler();
		this.addKeyListener(this.eHandle);
	}
	/**
	 * render är bara en genväg till att måla om hela canvasytan(innehåller:this.repaint())
	 */
	public void render() {
		repaint();
	}
	/**
	 * en funktion som finns inbyggt i Canvas klassen som anropas när man anropar repaint() g är här den nuvarande målade arean
	 * @param g Graphics
	 */
    @Override
	public void update(Graphics g) {
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
	/**
	 *  Definieras i en annan subklass om vad som skall målas på detta canvas objekt
	 * @param g Graphics
	 */
    @Override
	public void paint(Graphics g) {}
}