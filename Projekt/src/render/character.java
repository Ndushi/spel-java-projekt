package render;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
/**
 * denna funktionen konrollerar karaktärens rörelser och data ex Grafik på hur karaktären skall se ut i olika vinlklar osv
 * @author johannes
 */
public class character {
	/**
	 * karaktärens punkt som den skall gå till i x
	 */
	public int x=0;
	/**
	 * karaktärens punkt som den skall gå till i y
	 */
	public int y=0;

	/**
	 * karaktärens punkt som den nuvarnade befinner sig på i x led
	 */
	protected float x2=0;
	/**
	 * karaktärens punkt som den nuvarnade befinner sig på i y led
	 */
	protected float y2=0;
	/**
	 * definierar hur stor area karaktären tar upp (kvadratisk)
	 */
	protected int radius=10;
	/**
	 * en array av alla items karaktären har plockat upp
	 */
	public int[] items;
	/**
	 * direction säger åt vilket håll som karaktären är påväg emot
	 */
	public int direciton;
	/**
	 * c innehåller alla bilder på karaktären
	 */
	public BufferedImage c;
	/**
	 * avgör hur snabb karaktären förflyttarsig mellan ruta till ruta
	 */
	private double incr=1;
	/**
	 * blir 1 eller 2 om det händer någonting med förflyttningen av gubben
	 * @deprecated 
	 */
        private int bug =-1;
	/**
	 * animationen på vilken bild som skall visas
	 */
	public boolean stand=true;
        public float frame=1;
	/**
	 * konstruktorn för character har 3 arg x är start positionen i x led och arg y är startpositionen i y led medans arg r är stroleken på karaktärens ruta
	 * @param x int
	 * @param y int
	 * @param r int
	 */
	public character(int x,int y,int r){
		this.x=x;
		this.y=y;
		this.x2=x*r;
		this.y2=y*r;
		this.radius=r;
	}
	/**
	 * initialiserar all Grafik på karaktären med hjäl utav en string som innehåller alla path:er till bilderna
	 * @param img array av strings
	 */
	public void setChar(String img){
            ImageIcon t= new ImageIcon(getClass().getResource(img));
            c = new BufferedImage(t.getIconWidth(),t.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
            c.getGraphics().drawImage(t.getImage(), 0, 0, null);
	}
	public void setChar(String img,int width,int height){
		ImageIcon t= new ImageIcon(getClass().getResource(img));
		c = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		c.getGraphics().drawImage(t.getImage(), 0, 0, null);
	}
	/**
	 * clear avrundar karaktärens nuvarande position så att det blir ett heltal
	 */
	public void clear(){
		this.x2=(int)(this.x2);
		this.y2=(int)(this.y2);
	}
	/**
	 * avrundar s till närmsta heltal
	 * @param s talet
	 * @return det avrundade talet
	 */
        public int round(double s){
            if(s%1<0.5)
                return (int)(s+0.5);
            return (int)(s);
        }
	/**
	 * Clearar karaktärens nuvarande position på en specifik axel s=0 x-axen s=1 y-axen s=-1 inget annars båda
	 * @see #clear()
	 * @param s avgör vilken axel du skall cleara karaktären på
	 */
	public void clear(int s){
		if(s==0)
			this.x2=this.round(this.x2);
		else if(s==1)
			this.y2=this.round(this.y2);
		else{
			this.x2=this.round(this.x2);
			this.y2=this.round(this.y2);
		}
	}
	/**
	 *  rör sakta karaktären mot this.x och this.y
	 * @param res avgör vilken axel du skall flytta karaktären på 
	 */
	public void slowMove(int res){
                //System.out.println(this.x*radius+","+(int)this.x2+";"+this.y*radius+","+(int)this.y2);
		if((this.x*radius==(int)this.x2&&this.y*radius==(int)this.y2)){
			this.clear();
			this.frame=0;
                        return;
		}
		double in=0.09;
		if(res==-1)return;
		else if(res==0){
                    if(this.x*radius>this.x2){
                        this.x2+=incr;
                        this.direciton=2;
                            frame+=in;
                    }
                    else if(this.x*radius<this.x2){
                        this.x2-=incr;
                        this.direciton=1;
                            frame+=in;
                    }
		}
		else if(res==1){
			if(this.y*radius>this.y2){
				this.y2+=incr;
				this.direciton=0;
				frame+=in;
			}
			else if(this.y*radius<this.y2){
				this.y2-=incr;
				this.direciton=3;
				frame+=in;
			}
		}
		else{
			if(this.x*radius>this.x2){
				this.x2+=incr;
				//this.direciton=2;
			}
			else if(this.x*radius<this.x2){
				this.x2-=incr;
				//this.direciton=0;
			}
			if(this.y*radius>this.y2){
				this.y2+=incr;
				//this.direciton=3;
			}
			else if(this.y*radius<this.y2){
				this.y2-=incr;
				//this.direciton=1;
			}
		}
	}
	/**
	 * sätter positionen av karaktären utan smothness
	 * @param res avgör vilken axel du skall flytta karaktären på
	 */
	public void staticMove(int res){
		if(this.x*radius==(int)(this.x2)&&this.y*radius==(int)(this.y2)){this.clear();return;}
		if(res==-1)return;
		else if(res==0){
			if(this.x*radius>this.x2)		this.x2+=radius;
			else if(this.x*radius<this.x2)	this.x2-=radius;
		}
		else if(res==1){
			if(this.y*radius>this.y2)		this.y2+=radius;
			else if(this.y*radius<this.y2)	this.y2-=radius;
		}
		else{
			if(this.x*radius>this.x2)		this.x2+=radius;
			else if(this.x*radius<this.x2)	this.x2-=radius;
			if(this.y*radius>this.y2)		this.y2+=radius;
			else if(this.y*radius<this.y2)	this.y2-=radius;
		}
		this.clear(res);
	}
	/**
	 * @deprecated
	 * @return
	 */
	public float getX2(){return this.x2;}
	/**
	 * @deprecated 
	 * @return
	 */
	public float getY2(){return this.y2;}
	/**
	 * @deprecated 
	 * @param x positionen på x
	 */
	public void setX2(float x){this.x2=x;}
	/**
	 * @deprecated 
	 * @param y positionen på y
	 */
	public void setY2(float y){this.y2=y;}
}