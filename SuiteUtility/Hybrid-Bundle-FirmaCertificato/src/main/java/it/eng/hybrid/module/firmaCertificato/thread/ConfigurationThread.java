package it.eng.hybrid.module.firmaCertificato.thread;


import javax.smartcardio.CardTerminals;
import javax.smartcardio.TerminalFactory;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;

import org.apache.log4j.Logger;

import it.eng.hybrid.module.firmaCertificato.tool.Factory;
import it.eng.hybrid.module.firmaCertificato.ui.FirmaCertificatoApplication;
import it.eng.hybrid.module.firmaCertificato.ui.PanelSign;

public class ConfigurationThread extends Thread {

	public final static Logger logger = Logger.getLogger(ConfigurationThread.class);
	
	private JProgressBar bar;
	private FirmaCertificatoApplication appl;
	private JPasswordField pin;
	private JButton button;
	private PanelSign sign;

	public ConfigurationThread(JProgressBar bar, FirmaCertificatoApplication appl, JPasswordField pin, JButton button, PanelSign panelsign) {
		this.bar = bar; 
		this.appl = appl;
		this.pin = pin;
		this.button = button;
		this.sign = panelsign;
	}
	
	@Override
	public void run() {
		
		bar.setVisible(true);
		bar.setStringPainted(true);
		try {
			Factory fact = new Factory();
			fact.copyProvider(bar);
		
			String osname = System.getProperty("os.name");
			sign.setSO(osname);
			
			//Controllo se il lettore  e'  attivo
			TerminalFactory factory = TerminalFactory.getDefault();
			CardTerminals terminale = factory.terminals();
			
			// Resetto le carte
			try {
				sign.getJcombocardreader().removeAllItems();
			} catch (Exception e) {
			}
			
			CardPresentThread cardpresentthread = new CardPresentThread(sign, terminale);
			cardpresentthread.start();
		
			sign.setThreadControl(cardpresentthread);

		} catch (Exception e) {
			logger.info("Errore", e);
//			JOptionPane.showMessageDialog( card.getJTabbedPane(), Messages.getMessage( MessageKeys.MSG_ERROR_PREFERENCELOADING, e.getMessage() ), 
//					Messages.getMessage( MessageKeys.MSG_ERROR ), JOptionPane.ERROR_MESSAGE);
		}

		bar.setVisible(false);
		bar.setStringPainted(false);
		((FirmaCertificatoApplication) appl).getJTabbedPane().setEnabled(true);
		button.setVisible(true);
		pin.setVisible(true);
	}	
	
}