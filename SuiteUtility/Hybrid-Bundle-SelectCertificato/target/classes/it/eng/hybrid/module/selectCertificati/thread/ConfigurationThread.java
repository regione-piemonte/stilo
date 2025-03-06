package it.eng.hybrid.module.selectCertificati.thread;

import javax.smartcardio.CardTerminals;
import javax.smartcardio.TerminalFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;

import org.apache.log4j.Logger;

import it.eng.hybrid.module.selectCertificati.messages.MessageKeys;
import it.eng.hybrid.module.selectCertificati.messages.Messages;
import it.eng.hybrid.module.selectCertificati.ui.PanelCertificati;
import it.eng.hybrid.module.selectCertificati.ui.SelectCertificatiApplication;

public class ConfigurationThread extends Thread {

	public final static Logger logger = Logger.getLogger(ConfigurationThread.class);
	
	private JProgressBar bar;
	private SelectCertificatiApplication card;
	private JPasswordField pin;
	private JButton button;
	private PanelCertificati panelCert;

	public ConfigurationThread(JProgressBar bar, SelectCertificatiApplication card, JPasswordField pin, JButton button, PanelCertificati panelCert) {
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
			CardTerminals terminale = factory.terminals();
			
			// Resetto le carte
			try {
				panelCert.getJcombocardreader().removeAllItems();
			} catch (Exception e) {
			}
			
			CardPresentThread cardpresetnthread = new CardPresentThread(panelCert, terminale);
			cardpresetnthread.start();
		
			panelCert.setThreadControl(cardpresetnthread);

		} catch (Exception e) {
			logger.info("Errore", e);
			JOptionPane.showMessageDialog( card.getJTabbedPane(), Messages.getMessage( MessageKeys.MSG_ERROR_PREFERENCELOADING, e.getMessage() ), 
					Messages.getMessage( MessageKeys.MSG_ERROR ), JOptionPane.ERROR_MESSAGE);
		}

		bar.setVisible(false);
		bar.setStringPainted(false);
		((SelectCertificatiApplication) card).getJTabbedPane().setEnabled(true);
		button.setVisible(true);
		pin.setVisible(true);
	}	
	
}