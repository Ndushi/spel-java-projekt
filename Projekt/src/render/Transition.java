package render;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Johan Lindskogen
 */
public final class Transition {
    private int x=0, y=0, width=400, height=400;
    Graphics g;
    int index=0;
    int speed;
    boolean dirBool=false;
    String type=null;
    
    public Transition(String type){ //Del av skärmen
        this.type=type;
    }
    Transition setType(String type){
        this.type=type;
        return this;
    }
    void transition(Graphics g){
        this.g = g;
        if(type.equals("fadeOutIn"))
            fadeOutIn();
        else if (type.equals("blank"))
            blank();
        else if (type.equals("slideUpDown"))
            slideUpDown();
        else if (type.equals("stripes"))
            stripes(100);
    }
    private void slideUpDown(){
        g.setColor(Color.BLACK);
        speed = 10;
        g.fillRect(0,0,400,200-index);
        g.fillRect(0, 200+index, 400, 200-index);
        
        index+=speed;
        if(index>0)
            dirBool=true;
        if(index>=200){
            dirBool = false;
            index=0;
        }
    }
    private void stripes(int antal){
        g.setColor(Color.BLACK);
        speed = 20;
        int lineHeight = 400/antal;
        for (int i=0; i<antal; i++){
            if (i%2==0)
                g.fillRect(0,lineHeight*i,index,lineHeight);
            else
                g.fillRect(400-index,lineHeight*i,index,lineHeight);
        }
        
        index+=speed;
        if(index>0)
            dirBool=true;
        if(index>=400){
            dirBool = false;
            index=0;
        }
    }
    private void fadeOutIn(){
        speed = 6;
        if(!dirBool)
            index+=speed;
        else
            index-=speed;
        if(index==255-(255%speed)){
            index=255;
            g.setColor(new Color(0,0,0,index));
            dirBool=true;
        }
        else if(dirBool && index<0){
            dirBool = false;
            index=0;
        }
        else
            g.setColor(new Color(0,0,0,index));
        g.fillRect(x,y,width,height);
    }
    private void blank(){
        if(!dirBool)
            index+=speed;
        else
            index-=speed;
        if(index==255-(255%speed)){
            index=255;
            g.setColor(new Color(0,0,0));
            dirBool=true;
        }
        else if(dirBool && index<0){
            dirBool = false;
            index=0;
        }
        else
            g.setColor(new Color(0,0,0));
        g.fillRect(x,y,width,height);
    }
}