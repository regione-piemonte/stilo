package it.eng.utility.client.sib;

import java.net.SocketTimeoutException;

public class Util {

	public static final void aggiornaEsito(final Esito output, Exception e) {
		final Throwable cause = e.getCause();
		output.setRispostaNonRicevuta(true);
		output.setTimeout(cause instanceof SocketTimeoutException);
		output.setMessaggio(cause != null ? cause.getLocalizedMessage() : e.getLocalizedMessage());
		output.setOk(false);
	}

}
