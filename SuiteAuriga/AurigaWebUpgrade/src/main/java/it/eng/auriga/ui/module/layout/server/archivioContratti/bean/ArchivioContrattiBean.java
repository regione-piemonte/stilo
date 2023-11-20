/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.xpath.operations.Bool;

public class ArchivioContrattiBean {
	
	private BigDecimal idUd;
	
	private String nroContratto;
	private Date dataStipulaContratto;
	
	private List<ContraenteBean> listaContraenti;
	
	private String oggetto;
	private String nomeFilePrimario;
	private String uriFilePrimario;
	private String idAttachEmailPrimario;
	private Boolean remoteUriFilePrimario;	
	private String mimetypeVerPreFirma;
	private String uriFileVerPreFirma;
	private String nomeFileVerPreFirma;
	private BigDecimal idDocPrimario;
	private Boolean isDocPrimarioChanged;
	private MimeTypeFirmaBean infoFile;
	private String improntaVerPreFirma;	
	
	private List<AllegatoProtocolloBean> listaAllegati;
	
	private String siglaProtocollo;
	private String nroProtocollo;
	private String annoProtocollo;
	private Date dataProtocollo;	
	
	private String estremiProtocollo;	
	private String contraente;
	private String pIvaCfContraente;
	
	private String statoConservazione;
	private Date dataInvioInConservazione;
	private String erroreInInvio;
	
	private Boolean modificaDati;
	private Boolean invioInConservazione;
	
	public BigDecimal getIdUd() {
		return idUd;
	}
	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}
	public String getNroContratto() {
		return nroContratto;
	}
	public void setNroContratto(String nroContratto) {
		this.nroContratto = nroContratto;
	}
	public Date getDataStipulaContratto() {
		return dataStipulaContratto;
	}
	public void setDataStipulaContratto(Date dataStipulaContratto) {
		this.dataStipulaContratto = dataStipulaContratto;
	}
	public List<ContraenteBean> getListaContraenti() {
		return listaContraenti;
	}
	public void setListaContraenti(List<ContraenteBean> listaContraenti) {
		this.listaContraenti = listaContraenti;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getNomeFilePrimario() {
		return nomeFilePrimario;
	}
	public void setNomeFilePrimario(String nomeFilePrimario) {
		this.nomeFilePrimario = nomeFilePrimario;
	}
	public String getUriFilePrimario() {
		return uriFilePrimario;
	}
	public void setUriFilePrimario(String uriFilePrimario) {
		this.uriFilePrimario = uriFilePrimario;
	}
	public String getIdAttachEmailPrimario() {
		return idAttachEmailPrimario;
	}
	public void setIdAttachEmailPrimario(String idAttachEmailPrimario) {
		this.idAttachEmailPrimario = idAttachEmailPrimario;
	}
	public Boolean getRemoteUriFilePrimario() {
		return remoteUriFilePrimario;
	}
	public void setRemoteUriFilePrimario(Boolean remoteUriFilePrimario) {
		this.remoteUriFilePrimario = remoteUriFilePrimario;
	}
	public String getMimetypeVerPreFirma() {
		return mimetypeVerPreFirma;
	}
	public void setMimetypeVerPreFirma(String mimetypeVerPreFirma) {
		this.mimetypeVerPreFirma = mimetypeVerPreFirma;
	}
	public String getUriFileVerPreFirma() {
		return uriFileVerPreFirma;
	}
	public void setUriFileVerPreFirma(String uriFileVerPreFirma) {
		this.uriFileVerPreFirma = uriFileVerPreFirma;
	}
	public String getNomeFileVerPreFirma() {
		return nomeFileVerPreFirma;
	}
	public void setNomeFileVerPreFirma(String nomeFileVerPreFirma) {
		this.nomeFileVerPreFirma = nomeFileVerPreFirma;
	}
	public BigDecimal getIdDocPrimario() {
		return idDocPrimario;
	}
	public void setIdDocPrimario(BigDecimal idDocPrimario) {
		this.idDocPrimario = idDocPrimario;
	}
	public Boolean getIsDocPrimarioChanged() {
		return isDocPrimarioChanged;
	}
	public void setIsDocPrimarioChanged(Boolean isDocPrimarioChanged) {
		this.isDocPrimarioChanged = isDocPrimarioChanged;
	}
	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}
	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}
	public String getImprontaVerPreFirma() {
		return improntaVerPreFirma;
	}
	public void setImprontaVerPreFirma(String improntaVerPreFirma) {
		this.improntaVerPreFirma = improntaVerPreFirma;
	}
	public List<AllegatoProtocolloBean> getListaAllegati() {
		return listaAllegati;
	}
	public void setListaAllegati(List<AllegatoProtocolloBean> listaAllegati) {
		this.listaAllegati = listaAllegati;
	}
	public String getSiglaProtocollo() {
		return siglaProtocollo;
	}
	public void setSiglaProtocollo(String siglaProtocollo) {
		this.siglaProtocollo = siglaProtocollo;
	}
	public String getNroProtocollo() {
		return nroProtocollo;
	}
	public void setNroProtocollo(String nroProtocollo) {
		this.nroProtocollo = nroProtocollo;
	}
	public String getAnnoProtocollo() {
		return annoProtocollo;
	}
	public void setAnnoProtocollo(String annoProtocollo) {
		this.annoProtocollo = annoProtocollo;
	}
	public Date getDataProtocollo() {
		return dataProtocollo;
	}
	public void setDataProtocollo(Date dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}
	public String getEstremiProtocollo() {
		return estremiProtocollo;
	}
	public void setEstremiProtocollo(String estremiProtocollo) {
		this.estremiProtocollo = estremiProtocollo;
	}
	public String getContraente() {
		return contraente;
	}
	public void setContraente(String contraente) {
		this.contraente = contraente;
	}
	public String getpIvaCfContraente() {
		return pIvaCfContraente;
	}
	public void setpIvaCfContraente(String pIvaCfContraente) {
		this.pIvaCfContraente = pIvaCfContraente;
	}
	public String getStatoConservazione() {
		return statoConservazione;
	}
	public void setStatoConservazione(String statoConservazione) {
		this.statoConservazione = statoConservazione;
	}
	public Date getDataInvioInConservazione() {
		return dataInvioInConservazione;
	}
	public void setDataInvioInConservazione(Date dataInvioInConservazione) {
		this.dataInvioInConservazione = dataInvioInConservazione;
	}
	public String getErroreInInvio() {
		return erroreInInvio;
	}
	public void setErroreInInvio(String erroreInInvio) {
		this.erroreInInvio = erroreInInvio;
	}
	public Boolean getModificaDati() {
		return modificaDati;
	}
	public void setModificaDati(Boolean modificaDati) {
		this.modificaDati = modificaDati;
	}
	public Boolean getInvioInConservazione() {
		return invioInConservazione;
	}
	public void setInvioInConservazione(Boolean invioInConservazione) {
		this.invioInConservazione = invioInConservazione;
	}
	
}
