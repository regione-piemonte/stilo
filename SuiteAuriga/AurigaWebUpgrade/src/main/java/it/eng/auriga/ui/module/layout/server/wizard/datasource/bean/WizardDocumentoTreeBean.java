/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

import java.math.BigDecimal;
import java.util.Date;

public class WizardDocumentoTreeBean {

	private String id;
	private Integer idUd;
	private String nome;
	private boolean flagObbligatorio;
	private BigDecimal maxNroDoc;
	private String idDocumento;
	private String dimensioneAllegato;
	private String mimetype;
	private String quadro;
	private String algoritmoImpronta;
	private String encodingImpronta;
	private Date dataOraInserimentoFile;
	private String uri;
	private MimeTypeFirmaBean info;
	private Boolean remoteUri;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public boolean isFlagObbligatorio() {
		return flagObbligatorio;
	}
	public void setFlagObbligatorio(boolean flagObbligatorio) {
		this.flagObbligatorio = flagObbligatorio;
	}
	public BigDecimal getMaxNroDoc() {
		return maxNroDoc;
	}
	public void setMaxNroDoc(BigDecimal maxNroDoc) {
		this.maxNroDoc = maxNroDoc;
	}
	public String getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}
	public String getDimensioneAllegato() {
		return dimensioneAllegato;
	}
	public void setDimensioneAllegato(String dimensioneAllegato) {
		this.dimensioneAllegato = dimensioneAllegato;
	}
	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	public String getQuadro() {
		return quadro;
	}
	public void setQuadro(String quadro) {
		this.quadro = quadro;
	}
	public String getAlgoritmoImpronta() {
		return algoritmoImpronta;
	}
	public void setAlgoritmoImpronta(String algoritmoImpronta) {
		this.algoritmoImpronta = algoritmoImpronta;
	}
	public String getEncodingImpronta() {
		return encodingImpronta;
	}
	public void setEncodingImpronta(String encodingImpronta) {
		this.encodingImpronta = encodingImpronta;
	}
	public Date getDataOraInserimentoFile() {
		return dataOraInserimentoFile;
	}
	public void setDataOraInserimentoFile(Date dataOraInserimentoFile) {
		this.dataOraInserimentoFile = dataOraInserimentoFile;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public MimeTypeFirmaBean getInfo() {
		return info;
	}
	public void setInfo(MimeTypeFirmaBean info) {
		this.info = info;
	}
	public Boolean getRemoteUri() {
		return remoteUri;
	}
	public void setRemoteUri(Boolean remoteUri) {
		this.remoteUri = remoteUri;
	}
	public Integer getIdUd() {
		return idUd;
	}
	public void setIdUd(Integer idUd) {
		this.idUd = idUd;
	}

	//OLD 
//	private String nome;
//	private String uri;
//	private String tipo = "Tipo documento";
//	private String idDocumento;
//	
	
}