/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

/**
 * 
 * @author dbe4235
 *
 */

public class TSOXmlBean {
	
	@NumeroColonna(numero = "1")
	private String idProcedimento;
	@NumeroColonna(numero = "2")
	private String estremiProcedimento;
	@NumeroColonna(numero = "3")
	private String tipoProcedimento;
	@NumeroColonna(numero = "11")
	private String documentoInizialeProcedimento;
	@NumeroColonna(numero = "13")
	private String inFaseProcedimento;
	@NumeroColonna(numero = "16")
	private String ultimoTaskProcedimento;
	@NumeroColonna(numero = "17")
	private String messaggioUltimoPasso;
	@NumeroColonna(numero = "20")
	private String idUdFolder;
	@NumeroColonna(numero = "23")
	private String documentoInizialeProcedimentoXOrd;
	@NumeroColonna(numero = "25")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dataPresentazione;
	@NumeroColonna(numero = "30")
	private String prossimeAttivita;
	@NumeroColonna(numero = "32")
	private String assegnatarioProcedimento;
	@NumeroColonna(numero = "41")
	private String uriFile;
	@NumeroColonna(numero = "42")
	private String nomeFile;
	@NumeroColonna(numero = "95")
	private String esitoUltimaAttivita;
	@NumeroColonna(numero = "96")
	private String esitoEstesoUltimaAttivita;
	@NumeroColonna(numero = "122")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dataCertificato;
	@NumeroColonna(numero = "123")
	private String cognomeMedico;
	@NumeroColonna(numero = "124")
	private String nomeMedico;
	@NumeroColonna(numero = "125")
	private String contattiMedico;
	@NumeroColonna(numero = "126")
	private String cognomePaziente;
	@NumeroColonna(numero = "127")
	private String nomePaziente;
	@NumeroColonna(numero = "128")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date   dataNascitaPaziente;
	@NumeroColonna(numero = "129")
	private String minorePaziente;
	@NumeroColonna(numero = "130")
	private String luogoNascitaPaziente;
	
	public String getIdProcedimento() {
		return idProcedimento;
	}
	public String getEstremiProcedimento() {
		return estremiProcedimento;
	}
	public String getTipoProcedimento() {
		return tipoProcedimento;
	}
	public String getDocumentoInizialeProcedimento() {
		return documentoInizialeProcedimento;
	}
	public String getInFaseProcedimento() {
		return inFaseProcedimento;
	}
	public String getUltimoTaskProcedimento() {
		return ultimoTaskProcedimento;
	}
	public String getMessaggioUltimoPasso() {
		return messaggioUltimoPasso;
	}
	public String getIdUdFolder() {
		return idUdFolder;
	}
	public String getDocumentoInizialeProcedimentoXOrd() {
		return documentoInizialeProcedimentoXOrd;
	}
	public Date getDataPresentazione() {
		return dataPresentazione;
	}
	public String getProssimeAttivita() {
		return prossimeAttivita;
	}
	public String getAssegnatarioProcedimento() {
		return assegnatarioProcedimento;
	}
	public String getUriFile() {
		return uriFile;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public String getEsitoUltimaAttivita() {
		return esitoUltimaAttivita;
	}
	public String getEsitoEstesoUltimaAttivita() {
		return esitoEstesoUltimaAttivita;
	}
	public Date getDataCertificato() {
		return dataCertificato;
	}
	public String getCognomeMedico() {
		return cognomeMedico;
	}
	public String getNomeMedico() {
		return nomeMedico;
	}
	public String getContattiMedico() {
		return contattiMedico;
	}
	public String getCognomePaziente() {
		return cognomePaziente;
	}
	public String getNomePaziente() {
		return nomePaziente;
	}
	public Date getDataNascitaPaziente() {
		return dataNascitaPaziente;
	}
	public String getMinorePaziente() {
		return minorePaziente;
	}
	public String getLuogoNascitaPaziente() {
		return luogoNascitaPaziente;
	}
	public void setIdProcedimento(String idProcedimento) {
		this.idProcedimento = idProcedimento;
	}
	public void setEstremiProcedimento(String estremiProcedimento) {
		this.estremiProcedimento = estremiProcedimento;
	}
	public void setTipoProcedimento(String tipoProcedimento) {
		this.tipoProcedimento = tipoProcedimento;
	}
	public void setDocumentoInizialeProcedimento(String documentoInizialeProcedimento) {
		this.documentoInizialeProcedimento = documentoInizialeProcedimento;
	}
	public void setInFaseProcedimento(String inFaseProcedimento) {
		this.inFaseProcedimento = inFaseProcedimento;
	}
	public void setUltimoTaskProcedimento(String ultimoTaskProcedimento) {
		this.ultimoTaskProcedimento = ultimoTaskProcedimento;
	}
	public void setMessaggioUltimoPasso(String messaggioUltimoPasso) {
		this.messaggioUltimoPasso = messaggioUltimoPasso;
	}
	public void setIdUdFolder(String idUdFolder) {
		this.idUdFolder = idUdFolder;
	}
	public void setDocumentoInizialeProcedimentoXOrd(String documentoInizialeProcedimentoXOrd) {
		this.documentoInizialeProcedimentoXOrd = documentoInizialeProcedimentoXOrd;
	}
	public void setDataPresentazione(Date dataPresentazione) {
		this.dataPresentazione = dataPresentazione;
	}
	public void setProssimeAttivita(String prossimeAttivita) {
		this.prossimeAttivita = prossimeAttivita;
	}
	public void setAssegnatarioProcedimento(String assegnatarioProcedimento) {
		this.assegnatarioProcedimento = assegnatarioProcedimento;
	}
	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public void setEsitoUltimaAttivita(String esitoUltimaAttivita) {
		this.esitoUltimaAttivita = esitoUltimaAttivita;
	}
	public void setEsitoEstesoUltimaAttivita(String esitoEstesoUltimaAttivita) {
		this.esitoEstesoUltimaAttivita = esitoEstesoUltimaAttivita;
	}
	public void setDataCertificato(Date dataCertificato) {
		this.dataCertificato = dataCertificato;
	}
	public void setCognomeMedico(String cognomeMedico) {
		this.cognomeMedico = cognomeMedico;
	}
	public void setNomeMedico(String nomeMedico) {
		this.nomeMedico = nomeMedico;
	}
	public void setContattiMedico(String contattiMedico) {
		this.contattiMedico = contattiMedico;
	}
	public void setCognomePaziente(String cognomePaziente) {
		this.cognomePaziente = cognomePaziente;
	}
	public void setNomePaziente(String nomePaziente) {
		this.nomePaziente = nomePaziente;
	}
	public void setDataNascitaPaziente(Date dataNascitaPaziente) {
		this.dataNascitaPaziente = dataNascitaPaziente;
	}
	public void setMinorePaziente(String minorePaziente) {
		this.minorePaziente = minorePaziente;
	}
	public void setLuogoNascitaPaziente(String luogoNascitaPaziente) {
		this.luogoNascitaPaziente = luogoNascitaPaziente;
	}

}