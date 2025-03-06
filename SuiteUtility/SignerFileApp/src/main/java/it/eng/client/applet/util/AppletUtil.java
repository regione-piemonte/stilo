package it.eng.client.applet.util;

import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

import com.nilo.plaf.nimrod.NimRODLookAndFeel;

public class AppletUtil {

	
	  public static ImageIcon loadRes( String fich) {
		    try {
		      return new ImageIcon( Toolkit.getDefaultToolkit().createImage( readStream( NimRODLookAndFeel.class.getResourceAsStream( fich))));
		    }
		    catch ( Exception ex) {
		      ex.printStackTrace();
		      return null;
		    }
		  }
	
	  static byte[] readStream( InputStream input) throws IOException {
		    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		    int read;
		    byte[] buffer = new byte[256];
		    
		    while ( (read = input.read( buffer, 0, 256)) != -1 ) {
		      bytes.write( buffer, 0, read);
		    }
		    
		    return bytes.toByteArray();
		  }
}
