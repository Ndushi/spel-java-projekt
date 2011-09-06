package render;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.ImageIcon;

/**
 *  World har koll på all grafik och saker som skall vara utlaggda på nuvarande bana denna klassen skall ha subklasser utformade effter vad
 *  man vill ha för värld
 * @author johannes
 */
public class World {
	/**
	 * lutingen på spelplanen beskrivs med angle i radianer
	 */
	public double angle=75*Math.PI/180;
	/**
	 * berdden på världens layout bild
	 */
	public int width=0;
	/**
	 * höjden på världens layout bild
	 */
	public int height=0;
	/**
	 *path är en string till foldern där alla bilder och grafik är förvarade för nuvarande värld
	 */
	String path;
	/**
	 * vis består av all Grafik i denna världen
	 */
	ImageIcon layout;
	ImageIcon grafik;
	ImageIcon alpha;
	/**
	 * kostruktorn försöker öppna forldern med alla biler och bestämma dess width och height
	 */
	World(String i) {
		if(i==null)throw new RuntimeException("The World is not defined");
		this.path=i;
		sound.playSound("opening.mp3");
		File dir = new File(getClass().getResource(this.path).getPath());
		File[] data = dir.listFiles();
		if (data == null) 
			throw new RuntimeException("World does not exist!");
		for(int im = 0;im < data.length; im++){
			if(data[im].isFile()){
				if(data[im].getName().equals("metal_box.jpg")){
					this.layout= new ImageIcon(data[im].getPath());
				}
				if(data[im].getName().equals("alpha.png")){
					this.alpha= new ImageIcon(data[im].getPath());
				}
			}
		}
		this.width=this.alpha.getIconWidth();//*20;
		this.height=this.alpha.getIconHeight();//*20;
	}
	/*public Graphics paint(Graphics g){
		g.drawImage(hics, width, width, hics);
		return g;
	}*/
	/**
	 * @param x positionen på x
	 * @param y positionen på y
	 * @return true om karaktären kan gå till punkten x,y annars returnerar den false
	 */
	public boolean canGo(int x, int y) {
		return !Integer.toHexString(this.getRGBA(x, y).getRGB()).equals("ff000000");
	}
	public boolean isPoortal(int x,int y){
		return  Integer.toHexString(this.getRGBA(x, y).getRGB()).equals("ffaaaaaa");
	}
	public Color getRGBA(int x,int y){
		BufferedImage b=new BufferedImage(this.alpha.getIconWidth(),this.alpha.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
		b.getGraphics().drawImage(this.alpha.getImage(), 0, 0, null);
		int pixelCol = b.getRGB(x, y);
		return new Color(
				(pixelCol >>> 16) & 0xff,
				(pixelCol >>> 8) & 0xff,
				pixelCol & 0xff,
				(pixelCol >>> 24) & 0xff
		);
	}
}
