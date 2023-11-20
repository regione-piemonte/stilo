/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

import java.util.List;

public class RegistroDocumentiBean {
	
	private String idUd;
	private String codDocumento;
	private String idDoc;
	private String nome;
	private String oggetto;
	private String stato;
	private String codiceSocieta;
	private String codiceCliente;
	private String codFiscPIVA;
	private String descrizioneCliente;
	private String codiceTipoDocumento;
	private String codiceProcesso;
	private String tipoServizio;
	private String dataDocumento;
	private String numeroDocumento;
	private String tipoFattura;
	private String codiceContratto;
	private String codiceFornitura;
	private String sezionale;
	private String codicePRD_POD;
	private String nroDocConFile;
	private String statoConservazione;
	
	private String nomeFile;
	private String uriFile;
	private MimeTypeFirmaBean infoFile;
	private Boolean remoteUri;	
	
	private List<VersioneDocBean> listaVersioni;
	
	public String getIdUd() {
		return idUd;
	}
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	public String getCodDocumento() {
		return codDocumento;
	}
	public void setCodDocumento(String codDocumento) {
		this.codDocumento = codDocumento;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getCodiceSocieta() {
		return codiceSocieta;
	}
	public void setCodiceSocieta(String codiceSocieta) {
		this.codiceSocieta = codiceSocieta;
	}
	public String getCodiceCliente() {
		return codiceCliente;
	}
	public void setCodiceCliente(String codiceCliente) {
		this.codiceCliente = codiceCliente;
	}
	public String getCodFiscPIVA() {
		return codFiscPIVA;
	}
	public void setCodFiscPIVA(String codFiscPIVA) {
		this.codFiscPIVA = codFiscPIVA;
	}
	public String getDescrizioneCliente() {
		return descrizioneCliente;
	}
	public void setDescrizioneCliente(String descrizioneCliente) {
		this.descrizioneCliente = descrizioneCliente;
	}
	public String getCodiceTipoDocumento() {
		return codiceTipoDocumento;
	}
	public void setCodiceTipoDocumento(String codiceTipoDocumento) {
		this.codiceTipoDocumento = codiceTipoDocumento;
	}
	public String getCodiceProcesso() {
		return codiceProcesso;
	}
	public void setCodiceProcesso(String codiceProcesso) {
		this.codiceProcesso = codiceProcesso;
	}
	public String getTipoServizio() {
		return tipoServizio;
	}
	public void setTipoServizio(String tipoServizio) {
		this.tipoServizio = tipoServizio;
	}
	public String getCodicePRD_POD() {
		return codicePRD_POD;
	}
	public void setCodicePRD_POD(String codicePRD_POD) {
		this.codicePRD_POD = codicePRD_POD;
	}
	public String getDataDocumento() {
		return dataDocumento;
	}
	public void setDataDocumento(String dataDocumento) {
		this.dataDocumento = dataDocumento;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getTipoFattura() {
		return tipoFattura;
	}
	public void setTipoFattura(String tipoFattura) {
		this.tipoFattura = tipoFattura;
	}
	public String getCodiceContratto() {
		return codiceContratto;
	}
	public void setCodiceContratto(String codiceContratto) {
		this.codiceContratto = codiceContratto;
	}
	public String getCodiceFornitura() {
		return codiceFornitura;
	}
	public void setCodiceFornitura(String codiceFornitura) {
		this.codiceFornitura = codiceFornitura;
	}
	public String getSezionale() {
		return sezionale;
	}
	public void setSezionale(String sezionale) {
		this.sezionale = sezionale;
	}
	public String getIdDoc() {
		return idDoc;
	}
	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}
	public String getUriFile() {
		return uriFile;
	}
	public void setUrifile(String uriFile) {
		this.uriFile = uriFile;
	}
	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}
	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public String getNroDocConFile() {
		return nroDocConFile;
	}
	public void setNroDocConFile(String nroDocConFile) {
		this.nroDocConFile = nroDocConFile;
	}
	public Boolean getRemoteUri() {
		return remoteUri;
	}
	public void setRemoteUri(Boolean remoteUri) {
		this.remoteUri = remoteUri;
	}
	public String getStatoConservazione() {
		return statoConservazione;
	}
	public void setStatoConservazione(String statoConservazione) {
		this.statoConservazione = statoConservazione;
	}
	public List<VersioneDocBean> getListaVersioni() {
		return listaVersioni;
	}
	public void setListaVersioni(List<VersioneDocBean> listaVersioni) {
		this.listaVersioni = listaVersioni;
	}
	
}
