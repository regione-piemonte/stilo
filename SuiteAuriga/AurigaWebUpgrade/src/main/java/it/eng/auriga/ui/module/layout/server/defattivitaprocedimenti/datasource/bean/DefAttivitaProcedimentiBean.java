/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Antonio Magnocavallo
 *
 */

public class DefAttivitaProcedimentiBean {
	
	private BigDecimal idEventType;
	private String descrizione;
	private String categoria;
	private Boolean puntualeDurativa;//1 per durativo e 0 per puntuale
	private String docAss;
	private Boolean flgTuttiProcedimenti;
	private String note;
	private Boolean flgAnnLogico; 
	private String flgModTerminiProc;
	private Integer flgAssociatoTipoDoc;
	private String tipologiaDocAss;
	private Integer flgInclAnnullati;
	private BigDecimal durataMaxGiorni;
	private List<AttrAddXEvtDelTipoBean> listaAttributiAddXEventiDelTipo;
	private String codTipoDataRelDocInOut;
	private String rowid;

	// Attributi dinamici
	private Map<String, Object> valori;
	private Map<String, String> tipiValori;
		
	public BigDecimal getIdEventType() {
		return idEventType;
	}
	public void setIdEventType(BigDecimal idEventType) {
		this.idEventType = idEventType;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public Boolean getPuntualeDurativa() {
		return puntualeDurativa;
	}
	public void setPuntualeDurativa(Boolean puntualeDurativa) {
		this.puntualeDurativa = puntualeDurativa;
	}
	public String getDocAss() {
		return docAss;
	}
	public void setDocAss(String docAss) {
		this.docAss = docAss;
	}
	public Boolean getFlgTuttiProcedimenti() {
		return flgTuttiProcedimenti;
	}
	public void setFlgTuttiProcedimenti(Boolean flgTuttiProcedimenti) {
		this.flgTuttiProcedimenti = flgTuttiProcedimenti;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Boolean getFlgAnnLogico() {
		return flgAnnLogico;
	}
	public void setFlgAnnLogico(Boolean flgAnnLogico) {
		this.flgAnnLogico = flgAnnLogico;
	}
	public String getFlgModTerminiProc() {
		return flgModTerminiProc;
	}
	public void setFlgModTerminiProc(String flgModTerminiProc) {
		this.flgModTerminiProc = flgModTerminiProc;
	}
	public Integer getFlgAssociatoTipoDoc() {
		return flgAssociatoTipoDoc;
	}
	public void setFlgAssociatoTipoDoc(Integer flgAssociatoTipoDoc) {
		this.flgAssociatoTipoDoc = flgAssociatoTipoDoc;
	}
	public String getTipologiaDocAss() {
		return tipologiaDocAss;
	}
	public void setTipologiaDocAss(String tipologiaDocAss) {
		this.tipologiaDocAss = tipologiaDocAss;
	}
	public Integer getFlgInclAnnullati() {
		return flgInclAnnullati;
	}
	public void setFlgInclAnnullati(Integer flgInclAnnullati) {
		this.flgInclAnnullati = flgInclAnnullati;
	}
	public BigDecimal getDurataMaxGiorni() {
		return durataMaxGiorni;
	}
	public void setDurataMaxGiorni(BigDecimal durataMaxGiorni) {
		this.durataMaxGiorni = durataMaxGiorni;
	}
	public List<AttrAddXEvtDelTipoBean> getListaAttributiAddXEventiDelTipo() {
		return listaAttributiAddXEventiDelTipo;
	}
	public void setListaAttributiAddXEventiDelTipo(List<AttrAddXEvtDelTipoBean> listaAttributiAddXEventiDelTipo) {
		this.listaAttributiAddXEventiDelTipo = listaAttributiAddXEventiDelTipo;
	}
	public String getCodTipoDataRelDocInOut() {
		return codTipoDataRelDocInOut;
	}
	public void setCodTipoDataRelDocInOut(String codTipoDataRelDocInOut) {
		this.codTipoDataRelDocInOut = codTipoDataRelDocInOut;
	}
	public String getRowid() {
		return rowid;
	}
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	public Map<String, Object> getValori() {
		return valori;
	}
	public void setValori(Map<String, Object> valori) {
		this.valori = valori;
	}
	public Map<String, String> getTipiValori() {
		return tipiValori;
	}
	public void setTipiValori(Map<String, String> tipiValori) {
		this.tipiValori = tipiValori;
	}
	
}