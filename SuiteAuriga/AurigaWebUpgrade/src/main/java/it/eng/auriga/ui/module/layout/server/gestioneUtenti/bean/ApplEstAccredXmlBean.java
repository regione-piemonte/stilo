/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;


public class ApplEstAccredXmlBean {

	@NumeroColonna(numero="1")
	private String codiceApplEst;
	
    @NumeroColonna(numero="2")
	private String codiceIstAppl;
	
	@NumeroColonna(numero="3")
	private String idUtenteApplEst;
	
	@NumeroColonna(numero="4")
	private String usernameUtenteApplEst;
	
	@NumeroColonna(numero="5")
	private String passwordUtenteApplEst;
	
	@NumeroColonna(numero="6")
	private String flgRecDaEliminare;
	
	@NumeroColonna(numero="7")
	private String  idUoApplEst;

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

	public String getPasswordUtenteApplEst() {
		return passwordUtenteApplEst;
	}

	public void setPasswordUtenteApplEst(String passwordUtenteApplEst) {
		this.passwordUtenteApplEst = passwordUtenteApplEst;
	}

	public String getFlgRecDaEliminare() {
		return flgRecDaEliminare;
	}

	public void setFlgRecDaEliminare(String flgRecDaEliminare) {
		this.flgRecDaEliminare = flgRecDaEliminare;
	}

	public String getIdUoApplEst() {
		return idUoApplEst;
	}

	public void setIdUoApplEst(String idUoApplEst) {
		this.idUoApplEst = idUoApplEst;
	}
	
	
}
