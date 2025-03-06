package it.eng.utility.client.acta;

import java.net.SocketTimeoutException;

import it.eng.utility.data.Outcome;

public class UtilActa {

	public static final void aggiornaEsito(final Outcome output, Exception e) {
		final Throwable cause = e.getCause();
		output.setRispostaNonRicevuta(true);
		output.setTimeout(cause instanceof SocketTimeoutException);
		output.setMessaggio(cause != null ? cause.getLocalizedMessage() : e.getLocalizedMessage());
		output.setOk(false);
	}
	
	
	public static final boolean isNotBlank(String input) {
		return (input != null) && (!input.trim().isEmpty());
	}

}
