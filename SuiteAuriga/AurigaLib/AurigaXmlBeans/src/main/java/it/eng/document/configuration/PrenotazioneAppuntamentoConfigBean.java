/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

/**
 * Permette di definire valori di configurazione necessari a invocare i servizi per la prenotazione appuntamento 
 * @author Mattia Zanetti
 *
 */

public class PrenotazioneAppuntamentoConfigBean {
	
	private String codIdConnectionToken;
	private Integer tipoDominio;
	private BigDecimal idDominio;
	private String idApplicazione;
	private String codApplicazione;
	private String schema;
	
	public String getCodIdConnectionToken() {
		return codIdConnectionToken;
	}
	
	public void setCodIdConnectionToken(String codIdConnectionToken) {
		this.codIdConnectionToken = codIdConnectionToken;
	}
	
	public Integer getTipoDominio() {
		return tipoDominio;
	}
	
	public void setTipoDominio(Integer tipoDominio) {
		this.tipoDominio = tipoDominio;
	}
	
	public BigDecimal getIdDominio() {
		return idDominio;
	}
	
	public void setIdDominio(BigDecimal idDominio) {
		this.idDominio = idDominio;
	}
	
	public String getIdApplicazione() {
		return idApplicazione;
	}
	
	public void setIdApplicazione(String idApplicazione) {
		this.idApplicazione = idApplicazione;
	}
	
	public String getCodApplicazione() {
		return codApplicazione;
	}
	
	public void setCodApplicazione(String codApplicazione) {
		this.codApplicazione = codApplicazione;
	}
	
	public String getSchema() {
		return schema;
	}
	
	public void setSchema(String schema) {
		this.schema = schema;
	}
	

}
