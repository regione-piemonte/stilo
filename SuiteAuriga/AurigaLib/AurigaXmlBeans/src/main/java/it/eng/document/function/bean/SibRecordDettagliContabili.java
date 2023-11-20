/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SibRecordDettagliContabili implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tipoDettaglio;
	private String tipo;
	private Short annoCompetenza;
	private Integer numeroDet;
	private Integer subNumero;
	private Integer varNumero;
	private Integer annoCrono;
	private Integer numeroCrono;
	private String descrizione;
	private String descrizionePadre;
	private Integer capitolo;
	private Integer articolo;
	private Integer numero;
	private String descrizioneCapitolo;
	private Integer pdcLivuno;
	private String tipoTitolo;
	private Integer pdcLivcin;
	private String descrizionePdc;
	private BigDecimal importo;
	private String descrizioneSoggetto;
	private String codfisSoggetto;
	private String cup;
	private String cig;
	private String codiceCatalogazione;
	private Integer codFnz;
	private String desFnz;
	private Date dataInizioCmp;
	private Date dataFineCmp;
	private String validato;
	private String soggettoDaPubblicare;
	private String codivaSoggetto;
	private Integer settore;
	private String descrizioneSettore;
	private Integer centroDiResp;
	private String descrizioneCentroDiResp;
	private Date dataScadenza;

	public String getTipoDettaglio() {
		return tipoDettaglio;
	}

	public void setTipoDettaglio(String tipoDettaglio) {
		this.tipoDettaglio = tipoDettaglio;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Short getAnnoCompetenza() {
		return annoCompetenza;
	}

	public void setAnnoCompetenza(Short annoCompetenza) {
		this.annoCompetenza = annoCompetenza;
	}

	public Integer getNumeroDet() {
		return numeroDet;
	}

	public void setNumeroDet(Integer numeroDet) {
		this.numeroDet = numeroDet;
	}

	public Integer getSubNumero() {
		return subNumero;
	}

	public void setSubNumero(Integer subNumero) {
		this.subNumero = subNumero;
	}

	public Integer getVarNumero() {
		return varNumero;
	}

	public void setVarNumero(Integer varNumero) {
		this.varNumero = varNumero;
	}

	public Integer getAnnoCrono() {
		return annoCrono;
	}

	public void setAnnoCrono(Integer annoCrono) {
		this.annoCrono = annoCrono;
	}

	public Integer getNumeroCrono() {
		return numeroCrono;
	}

	public void setNumeroCrono(Integer numeroCrono) {
		this.numeroCrono = numeroCrono;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizionePadre() {
		return descrizionePadre;
	}

	public void setDescrizionePadre(String descrizionePadre) {
		this.descrizionePadre = descrizionePadre;
	}

	public Integer getCapitolo() {
		return capitolo;
	}

	public void setCapitolo(Integer capitolo) {
		this.capitolo = capitolo;
	}

	public Integer getArticolo() {
		return articolo;
	}

	public void setArticolo(Integer articolo) {
		this.articolo = articolo;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getDescrizioneCapitolo() {
		return descrizioneCapitolo;
	}

	public void setDescrizioneCapitolo(String descrizioneCapitolo) {
		this.descrizioneCapitolo = descrizioneCapitolo;
	}

	public Integer getPdcLivuno() {
		return pdcLivuno;
	}

	public void setPdcLivuno(Integer pdcLivuno) {
		this.pdcLivuno = pdcLivuno;
	}

	public String getTipoTitolo() {
		return tipoTitolo;
	}

	public void setTipoTitolo(String tipoTitolo) {
		this.tipoTitolo = tipoTitolo;
	}

	public Integer getPdcLivcin() {
		return pdcLivcin;
	}

	public void setPdcLivcin(Integer pdcLivcin) {
		this.pdcLivcin = pdcLivcin;
	}

	public String getDescrizionePdc() {
		return descrizionePdc;
	}

	public void setDescrizionePdc(String descrizionePdc) {
		this.descrizionePdc = descrizionePdc;
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public String getDescrizioneSoggetto() {
		return descrizioneSoggetto;
	}

	public void setDescrizioneSoggetto(String descrizioneSoggetto) {
		this.descrizioneSoggetto = descrizioneSoggetto;
	}

	public String getCodfisSoggetto() {
		return codfisSoggetto;
	}

	public void setCodfisSoggetto(String codfisSoggetto) {
		this.codfisSoggetto = codfisSoggetto;
	}

	public String getCup() {
		return cup;
	}

	public void setCup(String cup) {
		this.cup = cup;
	}

	public String getCig() {
		return cig;
	}

	public void setCig(String cig) {
		this.cig = cig;
	}

	public String getCodiceCatalogazione() {
		return codiceCatalogazione;
	}

	public void setCodiceCatalogazione(String codiceCatalogazione) {
		this.codiceCatalogazione = codiceCatalogazione;
	}

	public Integer getCodFnz() {
		return codFnz;
	}

	public void setCodFnz(Integer codFnz) {
		this.codFnz = codFnz;
	}

	public String getDesFnz() {
		return desFnz;
	}

	public void setDesFnz(String desFnz) {
		this.desFnz = desFnz;
	}

	public Date getDataInizioCmp() {
		return dataInizioCmp;
	}

	public void setDataInizioCmp(Date dataInizioCmp) {
		this.dataInizioCmp = dataInizioCmp;
	}

	public Date getDataFineCmp() {
		return dataFineCmp;
	}

	public void setDataFineCmp(Date dataFineCmp) {
		this.dataFineCmp = dataFineCmp;
	}

	public String getValidato() {
		return validato;
	}

	public void setValidato(String validato) {
		this.validato = validato;
	}

	public String getSoggettoDaPubblicare() {
		return soggettoDaPubblicare;
	}

	public void setSoggettoDaPubblicare(String soggettoDaPubblicare) {
		this.soggettoDaPubblicare = soggettoDaPubblicare;
	}

	public String getCodivaSoggetto() {
		return codivaSoggetto;
	}

	public void setCodivaSoggetto(String codivaSoggetto) {
		this.codivaSoggetto = codivaSoggetto;
	}

	public Integer getSettore() {
		return settore;
	}

	public void setSettore(Integer settore) {
		this.settore = settore;
	}

	public String getDescrizioneSettore() {
		return descrizioneSettore;
	}

	public void setDescrizioneSettore(String descrizioneSettore) {
		this.descrizioneSettore = descrizioneSettore;
	}

	public Integer getCentroDiResp() {
		return centroDiResp;
	}

	public void setCentroDiResp(Integer centroDiResp) {
		this.centroDiResp = centroDiResp;
	}

	public String getDescrizioneCentroDiResp() {
		return descrizioneCentroDiResp;
	}

	public void setDescrizioneCentroDiResp(String descrizioneCentroDiResp) {
		this.descrizioneCentroDiResp = descrizioneCentroDiResp;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

}
