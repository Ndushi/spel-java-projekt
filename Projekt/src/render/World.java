package render;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.ImageIcon;
/**
 *  World har koll på all grafik och saker som skall vara utlagda på nuvarande bana denna klassen skall ha subklasser utformade effter vad
 *  man vill ha för värld
 * @author johannes
 */
public class World implements Cloneable {
	int pos[]=new int[2];
	String errors="";
	int radius=16;
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
	ImageIcon overlay;
	BufferedImage grafik;
	BufferedImage alpha;
	BufferedImage items;
	/**
	 * kostruktorn försöker öppna forldern med alla biler och bestämma dess width och height
	 */
	World(String i) {
		if(i==null)throw new RuntimeException("The World is not defined");
		this.path=i;
		sound.playSound("opening.mp3");
		File dir = new File(this.path);
		File[] data = dir.listFiles();
		if (data == null) 
			throw new RuntimeException("World,"+this.path+", does not exist!");
		System.out.println("Loading...");
		
		this.loadWorld();
	}
	World copy() throws CloneNotSupportedException {
		return (World)super.clone();
	}
	private void loadWorld(){
		ImageIcon temp=null;
		Graphics gr=null;
		System.out.println(this.path+"layout loaded.");
		this.layout= new ImageIcon(this.path+"/layout.png");

		//System.out.println("overlay loaded.");
		this.overlay = new ImageIcon(this.path+"/overlay.png");

		//System.out.println("graphics loaded.");
		temp = new ImageIcon(this.path+"/graphics.png");
		this.grafik = new BufferedImage(temp.getIconWidth(),temp.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
		this.grafik.getGraphics().drawImage(temp.getImage(), 0, 0, null);

		System.out.println("alpha loaded.");
		temp = new ImageIcon(this.path+"/alpha.png");
		this.alpha = new BufferedImage(temp.getIconWidth(),temp.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
		gr= this.alpha.getGraphics();
		gr.drawImage(temp.getImage(), 0, 0, null);

		this.items = new BufferedImage(this.alpha.getWidth()*radius,this.alpha.getHeight()*radius,BufferedImage.TYPE_INT_ARGB);
		gr = this.items.getGraphics();
		for(int ix=0;ix<this.alpha.getWidth();ix++){
			for(int iy=0;iy<this.alpha.getHeight();iy++){
				int ixy = this.alpha.getRGB(ix, iy);
				if(!(ixy==0xff000000||ixy==0xffffffff)&&(!this.isEqual(ixy)&&!this.isPoortal(ix, iy))){
					gr.drawImage(
						this.grafik.getSubimage(
						   ((ixy>>> 16) & 0xff)*radius,
						   ((ixy>>> 8) & 0xff )*radius,
						    radius, radius),
						ix*radius, iy*radius, null
					);
				}
			}
		}
		this.width=this.alpha.getWidth();
		this.height=this.alpha.getHeight();
	}
	public void setWorldFromColor(String path){
		if(this.pos[0]!=-1&&this.pos[1]!=-1){
			Color c=this.getRGBA(this.pos[0], this.pos[1]);
			if(this.isPoortal(this.pos[0],this.pos[1])){
				this.path=getClass().getResource(path+"/world"+c.getRed()).getPath();
				ImageIcon t= new ImageIcon(this.path+"/alpha.png");
				if(t.getIconWidth()>this.pos[0]&&t.getIconHeight()>this.pos[1])
					this.loadWorld();
				this.pos[0]=-1;
				this.pos[1]=-1;
			}
		}
		else {
			/** @TODO add and thow new ExceptionClass*/
		}
	}
	public void setWorld(String path){

	}
	public boolean isEqual(int rgb){
		return (((rgb>>> 16) & 0xff)+((rgb>>> 8) & 0xff)+(rgb & 0xff))/3 ==((rgb >>> 16) & 0xff);
	}
	/**
	 * @param x positionen på x
	 * @param y positionen på y
	 * @return true om karaktären kan gå till punkten x,y annars returnerar den false
	 */
	public boolean canGo(int x, int y) {
		int argb=this.getRGBA(x, y).getRGB();
		return (argb!=0xff000000&&this.getRGBA(x, y).getBlue()==0xff&&this.isEqual(argb))||this.isPoortal(x, y);
	}
	public boolean isPoortal(int x,int y){
		boolean t=!(this.getRGBA(x, y).getRGB()==0xffffffff||this.getRGBA(x, y).getRGB()==0xff000000)
			&&this.isEqual(this.getRGBA(x, y).getRGB());
		if(t){
			this.pos[0]=x;
			this.pos[1]=y;
		}
		return t;
	}
	private BufferedImage isSolid(int x, int y, int w, int h){
		BufferedImage b = new BufferedImage(this.layout.getIconWidth(),this.layout.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
		b.getGraphics().drawImage(this.layout.getImage(), 0, 0, null);
		int[] pixelCol=new int[Math.max(w,h)];
		
		b.getRGB(x, y,w,h,pixelCol,0,0);
		int i = 1;
		while(pixelCol!=null&&pixelCol.length-1>i&&pixelCol[i]!=pixelCol[i-1]){
			//s+=", "+Integer.toString(pixelCol[i]);
			i++;
		}
		if(i>=pixelCol.length-1)
			return new BufferedImage(radius,radius,BufferedImage.TYPE_INT_BGR);

		return b.getSubimage(x, y, w, h);
	}
	public Color getRGBA(int x,int y){
		int pixelCol = this.alpha.getRGB(x, y);
		return new Color(
				(pixelCol >>> 16) & 0xff,
				(pixelCol >>> 8) & 0xff,
				pixelCol & 0xff,
				(pixelCol >>> 24) & 0xff
		);
	}
	void paint(Graphics g,int x2,int y2,int width,int height) {
		if(this.getRGBA(x2/radius, y2/radius).equals(new Color(0xfbc815))){
			Graphics ag =this.alpha.createGraphics();
			//Graphics ag=this.alpha.getImage().getGraphics().create();
			ag.setColor(Color.white);
			ag.fillRect(x2/radius, y2/radius, 1, 1);
			
			//BufferedImage im=(BufferedImage)this.alpha.getImage().getSource();//.getGraphics().fillRect(x2/radius, y2/radius, 1, 1);
		}
		//g.drawImage(this.alpha, -x2+width/2, -y2 + height/2,this.alpha.getWidth()*radius,this.alpha.getHeight()*radius, null);
		g.drawImage(this.layout.getImage(), -x2+width/2, -y2 + height/2, null);
		g.drawImage(this.items, -x2+width/2, -y2 + height/2, null);
		//g.drawImage(this.isSolid(x2, y2, radius, radius), 0, 20, null);
		//g.setColor(new Color(this.isSolid(x2/radius, y2/radius, radius, radius)));
		//g.fillReFlashigare namn på bilder! Dolda ändringar som involverar utvecklingen av items.ct( width/2-2*radius, height/2-radius-radius/5+radius,radius,radius);
		if(!this.errors.equals(""))
			g.drawString(errors, 3, 41);
	}
	void paintTop(Graphics g,int x2, int y2,int width,int height){
		g.drawImage(this.overlay.getImage(), -x2+width/2, -y2 + height/2, null);
	}
}
