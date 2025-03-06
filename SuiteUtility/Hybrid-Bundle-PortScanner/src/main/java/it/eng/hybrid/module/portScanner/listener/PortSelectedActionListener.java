package it.eng.hybrid.module.portScanner.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

import org.apache.log4j.Logger;

import it.eng.hybrid.module.portScanner.ui.PortScannerApplication;

public class PortSelectedActionListener implements ActionListener {

	private boolean setDefaultPort = true;

	public enum Comandi {
		SELEZIONA("Seleziona"), PORT("PortSelected");

		private String value;

		public String getValue() {
			return value;
		}

		private Comandi(String pStrComando) {
			value = pStrComando;
		}

		public static Comandi getComando(String value) {
			for (Comandi lComandi : Comandi.values()) {
				if (lComandi.getValue().equals(value))
					return lComandi;
			}
			return null;
		}
	}

	private PortScannerApplication portAppl;
	private static Logger mLogger = Logger.getLogger(PortScannerApplication.class);

	public PortSelectedActionListener(PortScannerApplication portAppl) {
		this.portAppl = portAppl;
	}

	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		String lStrAction = paramActionEvent.getActionCommand();
		mLogger.info("Ricevuto comando " + lStrAction);

		Comandi lComando = Comandi.getComando(lStrAction);
		switch (lComando) {
		case SELEZIONA:
			// Ho cliccato sul pulsante di selezione porta
			if (!setDefaultPort) {
				mLogger.info("Impostata una nuova porta rispetto a quanto presente in precedenza");
				// Si � selezionata un'opzione quindi si deve memorizzare la nuova scelta
				portAppl.seleziona();
			} else {
				// Altrimenti vuol dire che non si è selezionato nulla e quindi l'opzione presente in precedenza è ancora la preferita.
				mLogger.info("La porta selezionata e' la stessa impostata in precedenza");

				/*
				 * Il successivo codice è stato aggiunto perch�, nel caso in cui si aggiunga una porta alla voce "Altra porta", poi si prema ok, si
				 * riavvii il modulo e si modifichi la porta senza cliccare su nessuna delle option questa modifica non viene registrata e memorizzata
				 */
				mLogger.info("isAltraPorta in PortSelectedActionListener: " + portAppl.isAltraPorta());
				if (portAppl.isAltraPorta()) {
					portAppl.seleziona();
				} else {
					portAppl.setCallbackAndClose();
				}
			}
			break;
		case PORT:
			// Si � cliccato su una delle opzioni quindi non si deve impostare la porta di default
			setDefaultPort = false;
			portAppl.enableSeleziona(((JRadioButton) paramActionEvent.getSource()).getName());
			break;
		default:
			break;
		}
	}


}
