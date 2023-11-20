/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SibInputCreaProposta implements Serializable {

	private static final long serialVersionUID = 1L;

	private String siglaRegistroProposta;
	private short annoProposta;
	private long numeroProposta;
	private Date dataProposta;
	private Long idUoCaricaProposta;
	private String codiceUoCaricaProposta;
	private String denomUoCaricaProposta;
	private Long idUoProponente;
	private String codiceUoProponente;
	private String denomUoProponente;
	private String oggetto;
	private String tipologiaProvvedimento;

	public String getSiglaRegistroProposta() {
		return siglaRegistroProposta;
	}

	public void setSiglaRegistroProposta(String siglaRegistroProposta) {
		this.siglaRegistroProposta = siglaRegistroProposta;
	}

	public short getAnnoProposta() {
		return annoProposta;
	}

	public void setAnnoProposta(short annoProposta) {
		this.annoProposta = annoProposta;
	}

	public long getNumeroProposta() {
		return numeroProposta;
	}

	public void setNumeroProposta(long numeroProposta) {
		this.numeroProposta = numeroProposta;
	}

	public Date getDataProposta() {
		return dataProposta;
	}

	public void setDataProposta(Date dataProposta) {
		this.dataProposta = dataProposta;
	}

	public Long getIdUoCaricaProposta() {
		return idUoCaricaProposta;
	}

	public void setIdUoCaricaProposta(Long idUoCaricaProposta) {
		this.idUoCaricaProposta = idUoCaricaProposta;
	}

	public String getCodiceUoCaricaProposta() {
		return codiceUoCaricaProposta;
	}

	public void setCodiceUoCaricaProposta(String codiceUoCaricaProposta) {
		this.codiceUoCaricaProposta = codiceUoCaricaProposta;
	}

	public String getDenomUoCaricaProposta() {
		return denomUoCaricaProposta;
	}

	public void setDenomUoCaricaProposta(String denomUoCaricaProposta) {
		this.denomUoCaricaProposta = denomUoCaricaProposta;
	}

	public Long getIdUoProponente() {
		return idUoProponente;
	}

	public void setIdUoProponente(Long idUoProponente) {
		this.idUoProponente = idUoProponente;
	}

	public String getCodiceUoProponente() {
		return codiceUoProponente;
	}

	public void setCodiceUoProponente(String codiceUoProponente) {
		this.codiceUoProponente = codiceUoProponente;
	}

	public String getDenomUoProponente() {
		return denomUoProponente;
	}

	public void setDenomUoProponente(String denomUoProponente) {
		this.denomUoProponente = denomUoProponente;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getTipologiaProvvedimento() {
		return tipologiaProvvedimento;
	}

	public void setTipologiaProvvedimento(String tipologiaProvvedimento) {
		this.tipologiaProvvedimento = tipologiaProvvedimento;
	}

}