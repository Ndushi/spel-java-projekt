package projekt;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
/**
 *
 * @author jmoreno
 */
public class tempVideo extends JFrame {
	public tempVideo(URL mediaURL){
		super("Video test");
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		MediaPanel mediaPanel = new MediaPanel( mediaURL );
		add( mediaPanel );
	}
	public static void main(String args[]){
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog( null );
		if ( result == JFileChooser.APPROVE_OPTION ){
			URL mediaURL = null;
			try{
				mediaURL = fileChooser.getSelectedFile().toURL();
			}
			catch ( MalformedURLException malformedURLException ){
				System.err.println( "Could not create URL for the file" );
			}
			if ( mediaURL != null ) {
				new tempVideo(mediaURL);
			}
		}
	}
}
