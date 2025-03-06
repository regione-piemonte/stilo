package it.eng.areas.hybrid.module.siss.thread;

import it.eng.areas.hybrid.module.siss.messages.MessageKeys;
import it.eng.areas.hybrid.module.siss.messages.Messages;
import it.eng.areas.hybrid.module.siss.ui.PanelSign;
import it.eng.areas.hybrid.module.siss.ui.SissApplication;

import javax.smartcardio.CardTerminals;
import javax.smartcardio.TerminalFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;

import org.apache.log4j.Logger;

public class ConfigurationThread extends Thread {

	public final static Logger logger = Logger.getLogger(ConfigurationThread.class);
	
	private JProgressBar bar;
	private SissApplication appl;
	private JPasswordField pin;
	private JButton button;
	private PanelSign sign;

	public ConfigurationThread(JProgressBar bar,SissApplication appl,JPasswordField pin,JButton button,PanelSign panelsign) {
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
//			Factory fact = new Factory();
//			fact.copyProvider( bar );
		
			String osname = System.getProperty("os.name");
			sign.setSO( osname );
			
			//Controllo se il lettore  è  attivo
			TerminalFactory factory = TerminalFactory.getDefault();
			CardTerminals terminale =  factory.terminals();
			
			CardPresentThread cardpresetnthread = new CardPresentThread(sign, terminale);
			cardpresetnthread.start();
		
			sign.setThreadControl(cardpresetnthread);

		} catch (Exception e) {
			logger.error("Errore", e);
			JOptionPane.showMessageDialog( appl.getJTabbedPane(), Messages.getMessage( MessageKeys.MSG_ERROR_PREFERENCELOADING, e.getMessage() ), 
					Messages.getMessage( MessageKeys.MSG_ERROR ), JOptionPane.ERROR_MESSAGE);
		}

		bar.setVisible(false);
		bar.setStringPainted(false);
		((SissApplication)appl).getJTabbedPane().setEnabled(true);
		button.setVisible(true);
		pin.setVisible(true);
	}	
	
}