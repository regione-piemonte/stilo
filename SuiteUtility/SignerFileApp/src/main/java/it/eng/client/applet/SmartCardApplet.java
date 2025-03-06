package it.eng.client.applet;

import java.util.ResourceBundle;

import it.eng.client.applet.i18N.Messages;
import it.eng.client.applet.util.PreferenceKeys;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.common.LogWriter;

import javax.swing.JApplet;

public class SmartCardApplet extends JApplet {

	private static final long serialVersionUID = 5549888919540596926L;

	@Override
	public void destroy() {
		super.destroy();
		System.exit(0);
	}

	public void init() {
		
		//init della config
		PreferenceManager.initConfig(this);

		try{
			Messages.setBundle(ResourceBundle.getBundle("messages"));
		} catch(Exception e){
			LogWriter.writeLog("Exception "+e+" loading resource bundle.\n" +
					"Also check you have a file to support Locale="+java.util.Locale.getDefault(),e);
		}
		
		int mainPanelWidth = PreferenceManager.getInt( PreferenceKeys.MAINPANEL_WIDTH, 400 );
		int mainPanelHeight = PreferenceManager.getInt( PreferenceKeys.MAINPANEL_HEIGHT, 400 );
		System.out.println("mainPanelWidth=" + mainPanelWidth + " mainPanelHeight=" + mainPanelHeight );
		this.setSize( mainPanelWidth, mainPanelHeight );
		
		final SmartCard sc = new SmartCard( this );
		sc.init();
		
	}
	
}
