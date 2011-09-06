/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package render;

import java.io.File;
import java.net.URI;
import projekt.Main;

/**
 *
 * @author johannes
 */
public class WorldController{
	static World worlds[];
	static String path="/res/testWorld/";
	static void loadWorlds(){
		WorldController.loadWorlds(Main.class.getResource(WorldController.path).getPath());
	}
	static void loadWorlds(String path){
		File dir = new File(path);
		File[] data = dir.listFiles();
		if (data == null){
			throw new RuntimeException(path+", worlds liborary does not exist!");
		}
		for(int im = 0,c = 0;im < data.length; im++){
			if(data[im].isFile()){
				if(data[im].isDirectory()){
					WorldController.worlds[c]= new World(data[im].getPath());
					c++;
					if(c==255)
						throw new RuntimeException("too manny worlds in this folder!");
				}
			}
		}
	}
}
