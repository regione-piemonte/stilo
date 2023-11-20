/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

/**
 * 
 * @author ottavio passalacqua
 *
 */

public class ApplEstBean {

	private String idApplEsterna;
    private String codApplicazione;
	private String codIstanza;
	private String nome;
	private Boolean flgUsaCredenzialiDiverse;
	private Boolean valido;
	private Date dtCensimento;
	private String utenteCensimento;
	private Date dtUltimoAggiornamento;
	private String utenteUltimoAggiornamento;
	private Boolean flgSistema;
	
	public String getCodApplicazione() {
		return codApplicazione;
	}
	public void setCodApplicazione(String codApplicazione) {
		this.codApplicazione = codApplicazione;
	}
	public String getCodIstanza() {
		return codIstanza;
	}
	public void setCodIstanza(String codIstanza) {
		this.codIstanza = codIstanza;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDtCensimento() {
		return dtCensimento;
	}
	public void setDtCensimento(Date dtCensimento) {
		this.dtCensimento = dtCensimento;
	}
	public String getUtenteCensimento() {
		return utenteCensimento;
	}
	public void setUtenteCensimento(String utenteCensimento) {
		this.utenteCensimento = utenteCensimento;
	}
	public Date getDtUltimoAggiornamento() {
		return dtUltimoAggiornamento;
	}
	public void setDtUltimoAggiornamento(Date dtUltimoAggiornamento) {
		this.dtUltimoAggiornamento = dtUltimoAggiornamento;
	}
	public String getUtenteUltimoAggiornamento() {
		return utenteUltimoAggiornamento;
	}
	public void setUtenteUltimoAggiornamento(String utenteUltimoAggiornamento) {
		this.utenteUltimoAggiornamento = utenteUltimoAggiornamento;
	}
	public Boolean getFlgUsaCredenzialiDiverse() {
		return flgUsaCredenzialiDiverse;
	}
	public void setFlgUsaCredenzialiDiverse(Boolean flgUsaCredenzialiDiverse) {
		this.flgUsaCredenzialiDiverse = flgUsaCredenzialiDiverse;
	}
	public Boolean getValido() {
		return valido;
	}
	public void setValido(Boolean valido) {
		this.valido = valido;
	}
	public Boolean getFlgSistema() {
		return flgSistema;
	}
	public void setFlgSistema(Boolean flgSistema) {
		this.flgSistema = flgSistema;
	}
	public String getIdApplEsterna() {
		return idApplEsterna;
	}
	public void setIdApplEsterna(String idApplEsterna) {
		this.idApplEsterna = idApplEsterna;
	}
}
