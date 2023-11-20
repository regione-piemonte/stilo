/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class AurigaMailConfig {

	private int numMaxLettureStato;
	private int numMaxSecondiAttesaLettura;
	
	public AurigaMailConfig() {
	}

	public int getNumMaxLettureStato() {
		return numMaxLettureStato;
	}
	public void setNumMaxLettureStato(int numMaxLettureStato) {
		this.numMaxLettureStato = numMaxLettureStato;
	}
	
	public int getNumMaxSecondiAttesaLettura() {
		return numMaxSecondiAttesaLettura;
	}
	public void setNumMaxSecondiAttesaLettura(int numMaxSecondiAttesaLettura) {
		this.numMaxSecondiAttesaLettura = numMaxSecondiAttesaLettura;
	}

	@Override
	public String toString() {
		return String.format("AurigaMailConfig [numMaxLettureStato=%s, numMaxSecondiAttesaLettura=%s]", numMaxLettureStato,
				numMaxSecondiAttesaLettura);
	}
	
}
