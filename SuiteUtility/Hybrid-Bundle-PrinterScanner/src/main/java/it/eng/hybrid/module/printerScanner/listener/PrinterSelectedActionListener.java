package it.eng.hybrid.module.printerScanner.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

import org.apache.log4j.Logger;

import it.eng.hybrid.module.printerScanner.ui.PrinterScannerApplication;

public class PrinterSelectedActionListener implements ActionListener {

	private boolean setDefaultPrinter = true;

	public enum Comandi {
		SELEZIONA("Seleziona"), PRINTER("PrinterSelected");

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

	private PrinterScannerApplication printerAppl;
	private static Logger mLogger = Logger.getLogger(PrinterSelectedActionListener.class);

	public PrinterSelectedActionListener(PrinterScannerApplication printerAppl) {
		this.printerAppl = printerAppl;
	}

	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		String lStrAction = paramActionEvent.getActionCommand();
		mLogger.info("Ricevuto comando " + lStrAction);

		Comandi lComando = Comandi.getComando(lStrAction);
		switch (lComando) {
		case SELEZIONA:
			// Ho cliccato sul pulsante di selezione stampante
			if (!setDefaultPrinter) {
				mLogger.info("Impostata una nuova stampante rispetto a quanto presente in precedenza");
				// Si ï¿½ selezionata un'opzione quindi si deve memorizzare la nuova scelta
				printerAppl.seleziona();
			} else {
				// Altrimenti vuol dire che non si ï¿½ selezionato nulla e quindi l'opzione presente in precedenza ï¿½ ancora la preferita.
				mLogger.info("La stampante selezionata e' la stessa impostata in precedenza");
				
				
				/*
				 * Il successivo codice è stato aggiunto perchè, nel caso in cui si aggiunga una stampante
				 * alla voce "Altra stampante", poi si prema ok, si riavvii il modulo e si modifichi la stampante
				 * senza cliccare su nessuna delle option questa modifica non viene registrata e memorizzata
				 */
				mLogger.info("isAltraStampante in PrinterSelectedActionListener: " + printerAppl.isAltraStampante());
				if(printerAppl.isAltraStampante())
				{
					printerAppl.seleziona();
				}
				else
				{
					printerAppl.setCallbackAndClose();
				}
			}

			break;
		case PRINTER:
			// Si ï¿½ cliccato su una delle opzioni quindi non si deve impostare la stampante di default
			setDefaultPrinter = false;
			printerAppl.enableSeleziona(((JRadioButton) paramActionEvent.getSource()).getName());
		

			break;
		default:

			break;
		}
	}


}
