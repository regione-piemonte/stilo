/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabilitaAdspGetListaDocumentiRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String codiceFiscaleOp;
	private String IdTipdoc;
	private Integer tipoRicerca;
	private String parteEs;
	private String anno;
	private String progSogg;
	private String codiceFiscale;
	private String partitaIva;
	private Integer tdocK;
	private Integer progTipmo;
	private Integer saldoDoc;
	private Date dataRegDa;
	private Date dataRegA;
	private Integer numeroRecords;
	private Integer recordPartenza;
	
	public String getCodiceFiscaleOp() {
		return codiceFiscaleOp;
	}
	
	public void setCodiceFiscaleOp(String codiceFiscaleOp) {
		this.codiceFiscaleOp = codiceFiscaleOp;
	}
	
	public String getIdTipdoc() {
		return IdTipdoc;
	}

	public void setIdTipdoc(String idTipdoc) {
		IdTipdoc = idTipdoc;
	}

	public Integer getTipoRicerca() {
		return tipoRicerca;
	}
	
	public void setTipoRicerca(Integer tipoRicerca) {
		this.tipoRicerca = tipoRicerca;
	}
	
	public String getParteEs() {
		return parteEs;
	}
	
	public void setParteEs(String parteEs) {
		this.parteEs = parteEs;
	}
	
	public String getAnno() {
		return anno;
	}
	
	public void setAnno(String anno) {
		this.anno = anno;
	}
	
	public String getProgSogg() {
		return progSogg;
	}
	
	public void setProgSogg(String progSogg) {
		this.progSogg = progSogg;
	}
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	public String getPartitaIva() {
		return partitaIva;
	}
	
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	
	public Integer getTdocK() {
		return tdocK;
	}
	
	public void setTdocK(Integer tdocK) {
		this.tdocK = tdocK;
	}
	
	public Integer getProgTipmo() {
		return progTipmo;
	}
	
	public void setProgTipmo(Integer progTipmo) {
		this.progTipmo = progTipmo;
	}
	
	public Integer getSaldoDoc() {
		return saldoDoc;
	}
	
	public void setSaldoDoc(Integer saldoDoc) {
		this.saldoDoc = saldoDoc;
	}
	
	public Date getDataRegDa() {
		return dataRegDa;
	}
	
	public void setDataRegDa(Date dataRegDa) {
		this.dataRegDa = dataRegDa;
	}
	
	public Date getDataRegA() {
		return dataRegA;
	}
	
	public void setDataRegA(Date dataRegA) {
		this.dataRegA = dataRegA;
	}
	
	public Integer getNumeroRecords() {
		return numeroRecords;
	}
	
	public void setNumeroRecords(Integer numeroRecords) {
		this.numeroRecords = numeroRecords;
	}
	
	public Integer getRecordPartenza() {
		return recordPartenza;
	}
	
	public void setRecordPartenza(Integer recordPartenza) {
		this.recordPartenza = recordPartenza;
	}

	@Override
	public String toString() {
		return "ContabilitaAdspGetListaDocumentiRequest [codiceFiscaleOp=" + codiceFiscaleOp + ", IdTipdoc=" + IdTipdoc
				+ ", tipoRicerca=" + tipoRicerca + ", parteEs=" + parteEs + ", anno=" + anno + ", progSogg=" + progSogg
				+ ", codiceFiscale=" + codiceFiscale + ", partitaIva=" + partitaIva + ", tdocK=" + tdocK
				+ ", progTipmo=" + progTipmo + ", saldoDoc=" + saldoDoc + ", dataRegDa=" + dataRegDa + ", dataRegA="
				+ dataRegA + ", numeroRecords=" + numeroRecords + ", recordPartenza=" + recordPartenza + "]";
	}
	
}
