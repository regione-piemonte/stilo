package it.eng.client.applet.thread;

import it.eng.client.applet.SelectFirmatari;

import it.eng.client.applet.i18N.MessageKeys;
import it.eng.client.applet.i18N.Messages;
import it.eng.client.applet.panel.PanelCertificati;
import it.eng.common.LogWriter;

import javax.smartcardio.CardTerminals;
import javax.smartcardio.TerminalFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;

public class ConfigurationThread extends Thread {

	private JProgressBar bar;
	private SelectFirmatari card;
	private JPasswordField pin;
	private JButton button;
	private PanelCertificati panelCert;

	public ConfigurationThread(JProgressBar bar,SelectFirmatari card, 
			JPasswordField pin, JButton button, PanelCertificati panelCert) {
		this.bar = bar; 
		this.card = card;
		this.pin = pin;
		this.button = button;
		this.panelCert = panelCert;
	}
	
	@Override
	public void run() {
		
		bar.setVisible(true);
		bar.setStringPainted(true);
		try {
			
			String osname = System.getProperty("os.name");
			panelCert.setSO( osname );
			
			//Controllo se il lettore  è  attivo
			TerminalFactory factory = TerminalFactory.getDefault();
			CardTerminals terminale =  factory.terminals();
			
			CardPresentThread cardpresetnthread = new CardPresentThread(panelCert, terminale);
			cardpresetnthread.start();
		
			panelCert.setThreadControl(cardpresetnthread);

		} catch (Exception e) {
			LogWriter.writeLog("Errore", e);
			JOptionPane.showMessageDialog( card.getJTabbedPane(), Messages.getMessage( MessageKeys.MSG_ERROR_PREFERENCELOADING, e.getMessage() ), 
					Messages.getMessage( MessageKeys.MSG_ERROR ), JOptionPane.ERROR_MESSAGE);
		}

		bar.setVisible(false);
		bar.setStringPainted(false);
		((SelectFirmatari)card).getJTabbedPane().setEnabled(true);
		button.setVisible(true);
		pin.setVisible(true);
	}	
	
}