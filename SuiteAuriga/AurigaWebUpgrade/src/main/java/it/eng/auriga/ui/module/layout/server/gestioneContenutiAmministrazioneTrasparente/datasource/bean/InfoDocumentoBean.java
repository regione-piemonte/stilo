/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DocumentBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.MittenteProtBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.OpzioniTimbroDocBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class InfoDocumentoBean {
		
	private BigDecimal idUd;
	private String segnatura;
	private String segnaturaXOrd;
	private String statoDocumento;
	private String tipo;
	private String nomeTipo;
	private String tipoRegNum;
	private String siglaRegNum;
	private String annoRegNum;
	private String nroRegNum;
	private String idDoc;
	private List<FileDaSelezionareBean> listaAllegati;
	
	public BigDecimal getIdUd() {
		return idUd;
	}
	public String getSegnatura() {
		return segnatura;
	}
	public String getSegnaturaXOrd() {
		return segnaturaXOrd;
	}
	public String getTipo() {
		return tipo;
	}
	public String getNomeTipo() {
		return nomeTipo;
	}
	public String getTipoRegNum() {
		return tipoRegNum;
	}
	public String getSiglaRegNum() {
		return siglaRegNum;
	}
	public String getAnnoRegNum() {
		return annoRegNum;
	}
	public String getNroRegNum() {
		return nroRegNum;
	}
	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}
	public void setSegnatura(String segnatura) {
		this.segnatura = segnatura;
	}
	public void setSegnaturaXOrd(String segnaturaXOrd) {
		this.segnaturaXOrd = segnaturaXOrd;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public void setNomeTipo(String nomeTipo) {
		this.nomeTipo = nomeTipo;
	}
	public void setTipoRegNum(String tipoRegNum) {
		this.tipoRegNum = tipoRegNum;
	}
	public void setSiglaRegNum(String siglaRegNum) {
		this.siglaRegNum = siglaRegNum;
	}
	public void setAnnoRegNum(String annoRegNum) {
		this.annoRegNum = annoRegNum;
	}
	public void setNroRegNum(String nroRegNum) {
		this.nroRegNum = nroRegNum;
	}
	public String getStatoDocumento() {
		return statoDocumento;
	}
	public void setStatoDocumento(String statoDocumento) {
		this.statoDocumento = statoDocumento;
	}
	public String getIdDoc() {
		return idDoc;
	}
	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}
	public List<FileDaSelezionareBean> getListaAllegati() {
		return listaAllegati;
	}
	public void setListaAllegati(List<FileDaSelezionareBean> listaAllegati) {
		this.listaAllegati = listaAllegati;
	}
	
	
}