/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;


public class ApplEstAccredOutBean {

	@NumeroColonna(numero="1")
	private String codiceApplEst;
	
    @NumeroColonna(numero="2")
	private String codiceIstAppl;
	
	@NumeroColonna(numero="3")
	private String denominazioneApplEst;
	
	@NumeroColonna(numero="4")
	private String idUtenteApplEst;
	
	@NumeroColonna(numero="5")
	private String usernameUtenteApplEst;
	
	@NumeroColonna(numero="6")
	private String passwordUtenteApplEst;
	
	@NumeroColonna(numero="7")
	private String  idUoApplEst;
	
	@NumeroColonna(numero="8")
	private String  nriLivelliApplEst;
	
	@NumeroColonna(numero="9")
	private String  denominazioneUoApplEst;
	
	@NumeroColonna(numero="10")
	private String flgUsaCredenzialiDiverseAuriga;

	public String getCodiceApplEst() {
		return codiceApplEst;
	}

	public void setCodiceApplEst(String codiceApplEst) {
		this.codiceApplEst = codiceApplEst;
	}

	public String getCodiceIstAppl() {
		return codiceIstAppl;
	}

	public void setCodiceIstAppl(String codiceIstAppl) {
		this.codiceIstAppl = codiceIstAppl;
	}

	public String getDenominazioneApplEst() {
		return denominazioneApplEst;
	}

	public void setDenominazioneApplEst(String denominazioneApplEst) {
		this.denominazioneApplEst = denominazioneApplEst;
	}

	public String getIdUtenteApplEst() {
		return idUtenteApplEst;
	}

	public void setIdUtenteApplEst(String idUtenteApplEst) {
		this.idUtenteApplEst = idUtenteApplEst;
	}

	public String getUsernameUtenteApplEst() {
		return usernameUtenteApplEst;
	}

	public void setUsernameUtenteApplEst(String usernameUtenteApplEst) {
		this.usernameUtenteApplEst = usernameUtenteApplEst;
	}

	public String getIdUoApplEst() {
		return idUoApplEst;
	}

	public void setIdUoApplEst(String idUoApplEst) {
		this.idUoApplEst = idUoApplEst;
	}

	public String getNriLivelliApplEst() {
		return nriLivelliApplEst;
	}

	public void setNriLivelliApplEst(String nriLivelliApplEst) {
		this.nriLivelliApplEst = nriLivelliApplEst;
	}

	public String getDenominazioneUoApplEst() {
		return denominazioneUoApplEst;
	}

	public void setDenominazioneUoApplEst(String denominazioneUoApplEst) {
		this.denominazioneUoApplEst = denominazioneUoApplEst;
	}

	public String getFlgUsaCredenzialiDiverseAuriga() {
		return flgUsaCredenzialiDiverseAuriga;
	}

	public void setFlgUsaCredenzialiDiverseAuriga(
			String flgUsaCredenzialiDiverseAuriga) {
		this.flgUsaCredenzialiDiverseAuriga = flgUsaCredenzialiDiverseAuriga;
	}

	public String getPasswordUtenteApplEst() {
		return passwordUtenteApplEst;
	}

	public void setPasswordUtenteApplEst(String passwordUtenteApplEst) {
		this.passwordUtenteApplEst = passwordUtenteApplEst;
	}

	
}
