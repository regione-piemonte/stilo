/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;


public class ApplicazioniEsterneBean {

	@NumeroColonna(numero="1")
	private String codiceApplIstEst;
	
	@NumeroColonna(numero="2")
	private String denominazioneApplEst;
	
	@NumeroColonna(numero="3")
	private boolean flgUsaCredenzialiDiverseAuriga;

	public String getCodiceApplIstEst() {
		return codiceApplIstEst;
	}

	public void setCodiceApplIstEst(String codiceApplIstEst) {
		this.codiceApplIstEst = codiceApplIstEst;
	}

	public String getDenominazioneApplEst() {
		return denominazioneApplEst;
	}

	public void setDenominazioneApplEst(String denominazioneApplEst) {
		this.denominazioneApplEst = denominazioneApplEst;
	}

	public boolean isFlgUsaCredenzialiDiverseAuriga() {
		return flgUsaCredenzialiDiverseAuriga;
	}

	public void setFlgUsaCredenzialiDiverseAuriga(
			boolean flgUsaCredenzialiDiverseAuriga) {
		this.flgUsaCredenzialiDiverseAuriga = flgUsaCredenzialiDiverseAuriga;
	}
	

		

	
}
