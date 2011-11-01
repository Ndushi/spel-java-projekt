/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package render;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JFrame;

import projekt.event.EventHandler;
import projekt.event.Keys;
/**
 *
 * @author Johan Lindskogen
 */
public class Battle extends JFrame {

    public static void main(String[] args) {
	new Battle(new character(0, 0, 16), new character(0, 0, 16));
    }
    
    private EventHandler eh = new EventHandler();
    
    Battle(character c1, character c2) {
	super("POKÉMANS");
	me = c1;
	you = c2;
	Canvas c = new Canvas();
	c.setSize(400, 400);
	add(c);
	c.setBackground(new Color(0xD0F0D0));
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setSize(405, 405);
	setVisible(true);
	setResizable(false);
	c.addKeyListener(eh);
	addKeyListener(eh);
	while (true)
	    Update(c.getGraphics());
    }
    Image dbImage;
    Graphics dbg;
    int menupos = 0;
    character me,you;
    
    private void Update(Graphics g) {
	if(eh.keys[Keys.up]){
	    if(menupos==2)
		menupos=0;
	    else if (menupos==3)
		menupos=1;
	}
	if(eh.keys[Keys.down]){
	    if(menupos==0)
		menupos=2;
	    else if (menupos==1)
		menupos=3;
	}
	if(eh.keys[Keys.left]){
	    if(menupos==1)
		menupos=0;
	    else if (menupos==3)
		menupos=2;
	}
	if(eh.keys[Keys.right]){
	    if(menupos==0)
		menupos=1;
	    else if (menupos==2)
		menupos=3;
	}
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
	Color blk = new Color(0x444444),
	    ylw = new Color(0xF8F8D8),
	    hltgrn = new Color(0x70F8A8),
	    hltylw = new Color(0xF8E038),
	    hltred = new Color(0xF85838);
	
	int x = 270,y = 200;
	double meRatio = (double)me.health/(double)me.maxHealth;
	double youRatio = (double)you.health/(double)you.maxHealth;
	Font normal = new Font("Arial",1,11),
	battle = new Font("Arial",1,15);
	
	g.setColor(blk);
        g.fillRoundRect(10, 10, 120, 34, 5, 5); //Motståndarens bakgrundsruta
	g.fillRoundRect(270, 200, 120, 44, 5, 5);	//Spelarens bakgrundsruta
        g.setColor(ylw);
        g.fillRoundRect(12, 12, 116, 30, 5, 5); //Motståndarens förgrundsruta
	g.fillRoundRect(x+2, y+2, 116, 40, 5, 5); //Spelarens förgrundsruta
	
	//Motståndarens healthbar
	g.setColor(blk);
	g.setFont(normal);
	g.drawString(you.name, 20, 25);
	g.drawString("Lv "+you.lvl, 97, 25);
	g.fillRect(20, 30, 104, 6);
	g.setColor((youRatio>0.5?hltgrn:(youRatio>0.1?hltylw:hltred)));
	g.fillRect(22, 32, (int)Math.round(youRatio*100), 2);
	
	//Spelarens healthbar
	g.setColor(blk);
	g.drawString(me.name, x+10, y+15);
	g.drawString("Lv "+me.lvl, x+87, y+15);
	g.fillRect(x+10, y+20, 104, 6);
	g.setColor((meRatio>0.5?hltgrn:(meRatio>0.1?hltylw:hltred)));
	g.fillRect(x+12, y+22, (int)Math.round(meRatio*100), 2);
	g.setColor(blk);
	String str= me.health+"/ "+me.maxHealth;
	g.drawString(str,(x+115)-g.getFontMetrics().stringWidth(str),y+37);
	
	//Ovaler, marken
	g.setColor(new Color(0x80E078));
	g.fillOval(175, 75, 200, 75);
	g.fillOval(20, 200, 200, 75);
	g.setColor(new Color(0xC0F890));
	g.fillOval(180, 80, 190, 65);
	g.fillOval(25, 205, 190, 65);
	
	
	//Statusmenu + Actionmenu
	g.setColor(blk);
	g.fillRect(0,250,400,150);
	g.setColor(new Color(0xC8A848));
	g.fillRoundRect(5, 254, 250, 120, 10, 10);
	g.setColor(new Color(0xFFFFFF));
	g.fillRoundRect(10, 259, 240, 110, 5, 5);
	g.setColor(new Color(0x285068));
	g.fillRoundRect(12, 261, 236, 106, 5, 5);
	g.setColor(new Color(0xFFFFFF));
	new PFont("What will "+me.name+" do?", g, 25, 280);
	//g.drawString("What will "+name.toUpperCase()+" do?", 25, 280);
	g.setColor(blk);
	g.fillRect(200, 250, 200, 150);
	g.setColor(new Color(0x706880));
	g.fillRoundRect(205, 255, 190, 120,5,5);
	g.setColor(new Color(0xFFFFFF));
	g.fillRoundRect(210, 260, 180, 110, 5, 5);
	
	//Menu items
	g.setColor(blk);
	g.setFont(battle);
	g.drawString("ATTACK", 225, 300);
	g.drawString("ITEMS", 330, 300);
	g.drawString("MAGIC", 225, 340);
	g.drawString("RUN", 330, 340);
	
	//Cursor position
	int[] curpos[] = {new int[]{215,285},new int[]{317,285},new int[]{215,325},new int[]{317,325}};
	g.fillPolygon(new int[]{curpos[menupos][0],curpos[menupos][0]+9,curpos[menupos][0]}, new int[]{curpos[menupos][1],curpos[menupos][1]+9,curpos[menupos][1]+18}, 3);
    }
}
