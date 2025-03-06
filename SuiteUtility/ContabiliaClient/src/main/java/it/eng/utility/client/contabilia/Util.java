package it.eng.utility.client.contabilia;

import java.net.SocketTimeoutException;

public class Util {
	
	public static final void aggiornaEsito(final Esito response, Exception e) {
		final Throwable cause = e.getCause();
		response.setRispostaNonRicevuta(true);
		response.setTimeout(cause instanceof SocketTimeoutException);
		response.setMessaggio(cause != null ? cause.getLocalizedMessage() : e.getLocalizedMessage());
		response.setOk(false);
	}
	
}
