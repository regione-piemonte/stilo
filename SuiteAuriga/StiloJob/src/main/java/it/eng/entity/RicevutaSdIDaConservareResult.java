/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

@Entity
@SqlResultSetMapping(
	name = "RicevutaSdIDaConservareResult", 
	entities = { 
		@EntityResult(
			entityClass = RicevutaSdIDaConservareResult.class, 
			fields = {
					@FieldResult(name = "idSpAoo",              column = "ID_SP_AOO"), 
					@FieldResult(name = "codApplOwner",         column = "COD_APPL_OWNER"), 
					@FieldResult(name = "codIstApplOwner",      column = "COD_IST_APPL_OWNER"), 
					@FieldResult(name = "idDocFattura",         column = "ID_DOC_FATTURA"),
					@FieldResult(name = "intitolazioneFattura", column = "INTITOLAZIONE_FATTURA"),
					@FieldResult(name = "flgAttivaPassiva",     column = "FLG_ATTIVA_PASSIVA"), 
					@FieldResult(name = "nroFattura",           column = "NRO_FATTURA"), 
					@FieldResult(name = "fattData",             column = "FATT_DATA"), 
					@FieldResult(name = "fattIdFiscaleDest",    column = "FATT_ID_FISC_DEST"), 
					@FieldResult(name = "fattDenomDest",        column = "FATT_DENOM_DEST"), 
					@FieldResult(name = "progrTrasmSdi",        column = "PROGR_TRASM_SDI"), 
					@FieldResult(name = "idUd",                 column = "ID_UD"), 
					@FieldResult(name = "idDoc",                column = "ID_DOC"),
					@FieldResult(name = "tipoRicevuta",         column = "TIPO_RICEVUTA"),
					@FieldResult(name = "tsArrivo",             column = "TS_ARRIVO"),
					@FieldResult(name = "uri",                  column = "URI"),
					@FieldResult(name = "displayFilename",      column = "DISPLAY_FILENAME"),
					@FieldResult(name = "mimetype",             column = "MIMETYPE"),
					@FieldResult(name = "statoLavorazione",     column = "STATO_LAVORAZIONE"),
					@FieldResult(name = "tsUltimoTentativo",    column = "TS_ULTIMO_TENTATIVO"),
					@FieldResult(name = "idPdv",    			column = "ID_PDV")
			}
		)
	}
)
public class RicevutaSdIDaConservareResult implements java.io.Serializable {

	private static final long serialVersionUID = 1304167215739148358L;
	
	private BigDecimal idSpAoo;
	private String codApplOwner;
	private String codIstApplOwner;
	private BigDecimal idDocFattura;
	private String intitolazioneFattura;
	private String flgAttivaPassiva;
	private String nroFattura;
	private Date fattData;
	private String fattIdFiscaleDest;
	private String fattDenomDest;
	private String progrTrasmSdi;
	private BigDecimal idUd;
	@Id
	private BigDecimal idDoc;
	private String tipoRicevuta;
	private Date tsArrivo;
	private String uri;
	private String displayFilename;
	private String mimetype;
	private Boolean statoLavorazione;
	private Date tsUltimoTentativo;
	private BigDecimal idPdv;


	public RicevutaSdIDaConservareResult() {
	}

	public RicevutaSdIDaConservareResult(BigDecimal idSpAoo, String codApplOwner, String codIstApplOwner,
			BigDecimal idDocFattura, String intitolazioneFattura, String flgAttivaPassiva, String nroFattura,
			Date fattData, String fattIdFiscaleDest, String fattDenomDest, String progrTrasmSdi, BigDecimal idUd, BigDecimal idDoc,
			String tipoRicevuta, Date tsArrivo, String uri, String displayFilename, String mimetype,
			Boolean statoLavorazione, Date tsUltimoTentativo, BigDecimal idPdv) {
		this.idSpAoo = idSpAoo;
		this.codApplOwner = codApplOwner;
		this.codIstApplOwner = codIstApplOwner;
		this.idDocFattura = idDocFattura;
		this.intitolazioneFattura = intitolazioneFattura;
		this.flgAttivaPassiva = flgAttivaPassiva;
		this.nroFattura = nroFattura;
		this.fattData = fattData;
		this.fattIdFiscaleDest = fattIdFiscaleDest;
		this.fattDenomDest = fattDenomDest;
		this.progrTrasmSdi = progrTrasmSdi;
		this.idUd = idUd;
		this.idDoc = idDoc;
		this.tipoRicevuta = tipoRicevuta;
		this.tsArrivo = tsArrivo;
		this.uri = uri;
		this.displayFilename = displayFilename;
		this.mimetype = mimetype;
		this.statoLavorazione = statoLavorazione;
		this.tsUltimoTentativo = tsUltimoTentativo;
		this.idPdv = idPdv;
	}

	public BigDecimal getIdSpAoo() {
		return idSpAoo;
	}

	public void setIdSpAoo(BigDecimal idSpAoo) {
		this.idSpAoo = idSpAoo;
	}

	public String getCodApplOwner() {
		return codApplOwner;
	}

	public void setCodApplOwner(String codApplOwner) {
		this.codApplOwner = codApplOwner;
	}

	public String getCodIstApplOwner() {
		return codIstApplOwner;
	}

	public void setCodIstApplOwner(String codIstApplOwner) {
		this.codIstApplOwner = codIstApplOwner;
	}

	public BigDecimal getIdDocFattura() {
		return idDocFattura;
	}

	public void setIdDocFattura(BigDecimal idDocFattura) {
		this.idDocFattura = idDocFattura;
	}

	public String getIntitolazioneFattura() {
		return intitolazioneFattura;
	}

	public void setIntitolazioneFattura(String intitolazioneFattura) {
		this.intitolazioneFattura = intitolazioneFattura;
	}

	public String getFlgAttivaPassiva() {
		return flgAttivaPassiva;
	}

	public void setFlgAttivaPassiva(String flgAttivaPassiva) {
		this.flgAttivaPassiva = flgAttivaPassiva;
	}

	public String getNroFattura() {
		return nroFattura;
	}

	public void setNroFattura(String nroFattura) {
		this.nroFattura = nroFattura;
	}

	public Date getFattData() {
		return fattData;
	}

	public void setFattData(Date fattData) {
		this.fattData = fattData;
	}

	public String getFattIdFiscaleDest() {
		return fattIdFiscaleDest;
	}

	public void setFattIdFiscaleDest(String fattIdFiscaleDest) {
		this.fattIdFiscaleDest = fattIdFiscaleDest;
	}

	public String getFattDenomDest() {
		return fattDenomDest;
	}

	public void setFattDenomDest(String fattDenomDest) {
		this.fattDenomDest = fattDenomDest;
	}

	public String getProgrTrasmSdi() {
		return progrTrasmSdi;
	}
	
	public void setProgrTrasmSdi(String progrTrasmSdi) {
		this.progrTrasmSdi = progrTrasmSdi;
	}

	public BigDecimal getIdUd() {
		return idUd;
	}

	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}

	public BigDecimal getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(BigDecimal idDoc) {
		this.idDoc = idDoc;
	}

	public String getTipoRicevuta() {
		return tipoRicevuta;
	}

	public void setTipoRicevuta(String tipoRicevuta) {
		this.tipoRicevuta = tipoRicevuta;
	}

	public Date getTsArrivo() {
		return tsArrivo;
	}

	public void setTsArrivo(Date tsArrivo) {
		this.tsArrivo = tsArrivo;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getDisplayFilename() {
		return displayFilename;
	}

	public void setDisplayFilename(String displayFilename) {
		this.displayFilename = displayFilename;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public Boolean getStatoLavorazione() {
		return statoLavorazione;
	}

	public void setStatoLavorazione(Boolean statoLavorazione) {
		this.statoLavorazione = statoLavorazione;
	}

	public Date getTsUltimoTentativo() {
		return tsUltimoTentativo;
	}

	public void setTsUltimoTentativo(Date tsUltimoTentativo) {
		this.tsUltimoTentativo = tsUltimoTentativo;
	}

	
	public BigDecimal getIdPdv() {
		return idPdv;
	}

	
	public void setIdPdv(BigDecimal idPdv) {
		this.idPdv = idPdv;
	}

	@Override
	public String toString() {
		return String.format(
				"RicevutaSdIDaConservareResult [idSpAoo=%s, codApplOwner=%s, codIstApplOwner=%s, idDocFattura=%s, intitolazioneFattura=%s, flgAttivaPassiva=%s, nroFattura=%s, fattData=%s, fattIdFiscaleDest=%s, fattDenomDest=%s, progrTrasmSdi=%s, idUd=%s, idDoc=%s, tipoRicevuta=%s, tsArrivo=%s, uri=%s, displayFilename=%s, mimetype=%s, statoLavorazione=%s, tsUltimoTentativo=%s, idPdv=%s]",
				idSpAoo, codApplOwner, codIstApplOwner, idDocFattura, intitolazioneFattura, flgAttivaPassiva,
				nroFattura, fattData, fattIdFiscaleDest, fattDenomDest, progrTrasmSdi, idUd, idDoc, tipoRicevuta,
				tsArrivo, uri, displayFilename, mimetype, statoLavorazione, tsUltimoTentativo, idPdv);
	}
}
