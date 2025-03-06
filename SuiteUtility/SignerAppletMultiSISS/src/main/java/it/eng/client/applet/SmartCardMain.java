package it.eng.client.applet;

import it.eng.client.applet.i18N.MessageKeys;

import it.eng.client.applet.i18N.Messages;
import it.eng.client.applet.util.PreferenceKeys;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.common.LogWriter;

import java.awt.EventQueue;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class SmartCardMain {

	private JFrame frame;
	private JTextField textField;
	 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		final String[] props=args;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PreferenceManager.initConfig(props);
					
					try {
						Messages.setBundle(ResourceBundle.getBundle("messages"));
					} catch(Exception e){
						LogWriter.writeLog("Exception "+e+" loading resource bundle.\n" +
								"Also check you have a file to support Locale="+java.util.Locale.getDefault(),e);
					}
					
					SmartCardMain window = new SmartCardMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SmartCardMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame( Messages.getMessage( MessageKeys.MAIN_TITLE ) );
		int mainPanelWidth = PreferenceManager.getInt( PreferenceKeys.MAINPANEL_WIDTH, 400 );
		int mainPanelHeight = PreferenceManager.getInt( PreferenceKeys.MAINPANEL_HEIGHT, 400 );
		System.out.println("mainPanelWidth=" + mainPanelWidth + " mainPanelHeight=" + mainPanelHeight );
		frame.setSize(mainPanelWidth,mainPanelHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		SmartCard sc = new SmartCard(frame);
		sc.init();

	}
}

