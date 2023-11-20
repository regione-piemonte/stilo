/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

import it.eng.core.business.beans.AbstractBean;

public class DaoEsportazioneDocumentiZipBean extends AbstractBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal idSpaoo;
	private String companyId;
	private String idUser;
	private BigDecimal idUserLavoro;
	private String uri_xls_in;
	private String errorMessage;

	public BigDecimal getIdSpaoo() {
		return idSpaoo;
	}
	public void setIdSpaoo(BigDecimal idSpaoo) {
		this.idSpaoo = idSpaoo;
	}
	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	public String getIdUser() {
		return idUser;
	}
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	
	public BigDecimal getIdUserLavoro() {
		return idUserLavoro;
	}
	public void setIdUserLavoro(BigDecimal idUserLavoro) {
		this.idUserLavoro = idUserLavoro;
	}
	
	public String getUri_xls_in() {
		return uri_xls_in;
	}
	public void setUri_xls_in(String uri_xls_in) {
		this.uri_xls_in = uri_xls_in;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
