package it.eng.client.applet.thread;

import it.eng.client.applet.SmartCard;
import it.eng.client.applet.i18N.MessageKeys;
import it.eng.client.applet.i18N.Messages;
import it.eng.client.applet.operation.Factory;
import it.eng.client.applet.panel.PanelSign;
import it.eng.common.LogWriter;

import javax.smartcardio.CardTerminals;
import javax.smartcardio.TerminalFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;

public class ConfigurationThread extends Thread {

	private JProgressBar bar;
	private SmartCard card;
	private JPasswordField pin;
	private JButton button;
	private PanelSign sign;

	public ConfigurationThread(JProgressBar bar,SmartCard card,JPasswordField pin,JButton button,PanelSign panelsign) {
		this.bar = bar; 
		this.card = card;
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
			LogWriter.writeLog("Errore", e);
			JOptionPane.showMessageDialog( card.getJTabbedPane(), Messages.getMessage( MessageKeys.MSG_ERROR_PREFERENCELOADING, e.getMessage() ), 
					Messages.getMessage( MessageKeys.MSG_ERROR ), JOptionPane.ERROR_MESSAGE);
		}

		bar.setVisible(false);
		bar.setStringPainted(false);
		((SmartCard)card).getJTabbedPane().setEnabled(true);
		button.setVisible(true);
		pin.setVisible(true);
	}	
	
}