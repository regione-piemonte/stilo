/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SibInputAggiornaAtto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String siglaRegistroProposta;
	private Short annoProposta;
	private Long numeroProposta;
	private String evento;
	private String tipoProvvedimento;
	private String siglaRegistroDefinitiva;
	private Short annoDefinitiva;
	private Long numeroDefinitiva;
	private Date dataDefinitiva;
	private String oggetto;
	private String tipologiaProvvedimento;
	private String nominativoFirmatario;
	private Long idUoAdottante;
	private String codiceUoAdottante;
	private String denomUoAdottante;
	private Long idUoDirAdottante;
	private String codiceUoDirAdottante;
	private String denomUoDirAdottante;
	private Date dataVisto;
	private String nominativoVisto;
	private Date dataArchiviazione;

	public String getSiglaRegistroProposta() {
		return siglaRegistroProposta;
	}

	public void setSiglaRegistroProposta(String siglaRegistroProposta) {
		this.siglaRegistroProposta = siglaRegistroProposta;
	}

	public Short getAnnoProposta() {
		return annoProposta;
	}

	public void setAnnoProposta(Short annoProposta) {
		this.annoProposta = annoProposta;
	}

	public Long getNumeroProposta() {
		return numeroProposta;
	}

	public void setNumeroProposta(Long numeroProposta) {
		this.numeroProposta = numeroProposta;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}

	public String getTipoProvvedimento() {
		return tipoProvvedimento;
	}

	public void setTipoProvvedimento(String tipoProvvedimento) {
		this.tipoProvvedimento = tipoProvvedimento;
	}

	public String getSiglaRegistroDefinitiva() {
		return siglaRegistroDefinitiva;
	}

	public void setSiglaRegistroDefinitiva(String siglaRegistroDefinitiva) {
		this.siglaRegistroDefinitiva = siglaRegistroDefinitiva;
	}

	public Short getAnnoDefinitiva() {
		return annoDefinitiva;
	}

	public void setAnnoDefinitiva(Short annoDefinitiva) {
		this.annoDefinitiva = annoDefinitiva;
	}

	public Long getNumeroDefinitiva() {
		return numeroDefinitiva;
	}

	public void setNumeroDefinitiva(Long numeroDefinitiva) {
		this.numeroDefinitiva = numeroDefinitiva;
	}

	public Date getDataDefinitiva() {
		return dataDefinitiva;
	}

	public void setDataDefinitiva(Date dataDefinitiva) {
		this.dataDefinitiva = dataDefinitiva;
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

	public String getNominativoFirmatario() {
		return nominativoFirmatario;
	}

	public void setNominativoFirmatario(String nominativoFirmatario) {
		this.nominativoFirmatario = nominativoFirmatario;
	}

	public Long getIdUoAdottante() {
		return idUoAdottante;
	}

	public void setIdUoAdottante(Long idUoAdottante) {
		this.idUoAdottante = idUoAdottante;
	}

	public String getCodiceUoAdottante() {
		return codiceUoAdottante;
	}

	public void setCodiceUoAdottante(String codiceUoAdottante) {
		this.codiceUoAdottante = codiceUoAdottante;
	}

	public String getDenomUoAdottante() {
		return denomUoAdottante;
	}

	public void setDenomUoAdottante(String denomUoAdottante) {
		this.denomUoAdottante = denomUoAdottante;
	}

	public Long getIdUoDirAdottante() {
		return idUoDirAdottante;
	}

	public void setIdUoDirAdottante(Long idUoDirAdottante) {
		this.idUoDirAdottante = idUoDirAdottante;
	}

	public String getCodiceUoDirAdottante() {
		return codiceUoDirAdottante;
	}

	public void setCodiceUoDirAdottante(String codiceUoDirAdottante) {
		this.codiceUoDirAdottante = codiceUoDirAdottante;
	}

	public String getDenomUoDirAdottante() {
		return denomUoDirAdottante;
	}

	public void setDenomUoDirAdottante(String denomUoDirAdottante) {
		this.denomUoDirAdottante = denomUoDirAdottante;
	}

	public Date getDataVisto() {
		return dataVisto;
	}

	public void setDataVisto(Date dataVisto) {
		this.dataVisto = dataVisto;
	}

	public String getNominativoVisto() {
		return nominativoVisto;
	}

	public void setNominativoVisto(String nominativoVisto) {
		this.nominativoVisto = nominativoVisto;
	}

	public Date getDataArchiviazione() {
		return dataArchiviazione;
	}

	public void setDataArchiviazione(Date dataArchiviazione) {
		this.dataArchiviazione = dataArchiviazione;
	}

}