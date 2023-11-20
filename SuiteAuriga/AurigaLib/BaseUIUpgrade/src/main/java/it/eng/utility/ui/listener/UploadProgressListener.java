/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;
import org.apache.log4j.Logger;

public class UploadProgressListener implements ProgressListener {

	private double percentDone;
	private long megaBytes = -1;
	private static final Logger logger = Logger.getLogger(UploadProgressListener.class);
	private HttpSession session;
	
	public UploadProgressListener(HttpServletRequest request) {
		session = request.getSession();
		logger.debug("Setting progress listener attribute on session: "+session.getId());
    } 

	/*
	 * (non-Javadoc)
	 * @see org.apache.commons.fileupload.ProgressListener#update(long, long, int)
	 */
	@Override
	public void update(long pBytesRead, long pContentLength, int pItems) {	
		if (pBytesRead / pContentLength * 95 > 94.7) {
			logger.debug(50);
//			session.setAttribute("caricato", 50);
		}
		// calcolo i mega uplodati
		long mBytes = pBytesRead / 1000000;//1MB
		// se cambiano vado avanti altrimenti esco, nel sito si consiglia di utilizzarlo per velocizzare il processo
		// per questo motivo ho dovuto aggiungere alche il primo controllo altrimenti non avrei potuto controllare l'ultimo mega o i file inferiori
		if (megaBytes == mBytes) {
			return;
		}
		// mi salvo i mega caricati se cambiati dall'ultima volta
		megaBytes = mBytes;
		// Carico in sessione il numero del field corrispondente al file che si sta caricando
		session.setAttribute("numero", pItems);
		// -1 viene restituito quando non ci sono dati
		if (pContentLength == -1) {
			logger.debug(50);
//			session.setAttribute("caricato", 50);
		} else {
			//Calcolo la percentuale caricata rispetto al totale dei file e non rispetto al singolo
			double perc = (((double) pBytesRead) / ((double) pContentLength)) * 50;
			//La salvo in sessione
			logger.debug(perc);
//			session.setAttribute("caricato", ((int) (perc)));
		}
	}
	
	/**
	 * Get the percent done
	 * @return the percent done
	 */
	public double getPercentDone() {
		return percentDone;
	}
}