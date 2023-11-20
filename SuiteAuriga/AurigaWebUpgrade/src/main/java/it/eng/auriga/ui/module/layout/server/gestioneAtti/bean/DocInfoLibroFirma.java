/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.AttProcBean;
import it.eng.document.NumeroColonna;

@XmlRootElement
public class DocInfoLibroFirma {

	@NumeroColonna(numero = "1")
	private String idUd;
	@NumeroColonna(numero = "2")
	private String idDocType;
	@NumeroColonna(numero = "3")
	private String idProcess;
	
	private String flgPrevistaNumerazione;
	private String flgGeneraFileUnionePerLibroFirma;
	private String activityName;
	private String prossimoTaskAppongoFirmaVisto;
	private String prossimoTaskRifiutoFirmaVisto;
	private String segnatura;
	
	/**
	 * Variabili appoggio per invio a libro firma massivo
	 */
	private AttProcBean attoProcedimento;
	private HashMap<String, String> errorMessages;
	private Boolean skipNumerazioneEGenerazioniDaModello;
	private Boolean esitoNumerazioneOk;
	private Boolean esitoGenerazioniDaModelloOk;
	
	public String getIdUd() {
		return idUd;
	}
	
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	
	public String getIdDocType() {
		return idDocType;
	}
	
	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}
	
	public String getIdProcess() {
		return idProcess;
	}
	
	public void setIdProcess(String idProcess) {
		this.idProcess = idProcess;
	}
	
	public String getFlgPrevistaNumerazione() {
		return flgPrevistaNumerazione;
	}
	
	public void setFlgPrevistaNumerazione(String flgPrevistaNumerazione) {
		this.flgPrevistaNumerazione = flgPrevistaNumerazione;
	}
	
	public String getFlgGeneraFileUnionePerLibroFirma() {
		return flgGeneraFileUnionePerLibroFirma;
	}
	
	public void setFlgGeneraFileUnionePerLibroFirma(String flgGeneraFileUnionePerLibroFirma) {
		this.flgGeneraFileUnionePerLibroFirma = flgGeneraFileUnionePerLibroFirma;
	}
	
	public String getActivityName() {
		return activityName;
	}
	
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	public String getProssimoTaskAppongoFirmaVisto() {
		return prossimoTaskAppongoFirmaVisto;
	}
	
	public void setProssimoTaskAppongoFirmaVisto(String prossimoTaskAppongoFirmaVisto) {
		this.prossimoTaskAppongoFirmaVisto = prossimoTaskAppongoFirmaVisto;
	}
	
	public String getProssimoTaskRifiutoFirmaVisto() {
		return prossimoTaskRifiutoFirmaVisto;
	}
	
	public void setProssimoTaskRifiutoFirmaVisto(String prossimoTaskRifiutoFirmaVisto) {
		this.prossimoTaskRifiutoFirmaVisto = prossimoTaskRifiutoFirmaVisto;
	}
	
	public String getSegnatura() {
		return segnatura;
	}

	public void setSegnatura(String segnatura) {
		this.segnatura = segnatura;
	}

	public AttProcBean getAttoProcedimento() {
		return attoProcedimento;
	}

	public void setAttoProcedimento(AttProcBean attoProcedimento) {
		this.attoProcedimento = attoProcedimento;
	}

	public HashMap<String, String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(HashMap<String, String> errorMessages) {
		this.errorMessages = errorMessages;
	}
	
	public Boolean getSkipNumerazioneEGenerazioniDaModello() {
		return skipNumerazioneEGenerazioniDaModello;
	}

	public void setSkipNumerazioneEGenerazioniDaModello(Boolean skipNumerazioneEGenerazioniDaModello) {
		this.skipNumerazioneEGenerazioniDaModello = skipNumerazioneEGenerazioniDaModello;
	}

	public Boolean getEsitoNumerazioneOk() {
		return esitoNumerazioneOk;
	}
	
	public void setEsitoNumerazioneOk(Boolean esitoNumerazioneOk) {
		this.esitoNumerazioneOk = esitoNumerazioneOk;
	}

	public Boolean getEsitoGenerazioniDaModelloOk() {
		return esitoGenerazioniDaModelloOk;
	}

	public void setEsitoGenerazioniDaModelloOk(Boolean esitoGenerazioniDaModelloOk) {
		this.esitoGenerazioniDaModelloOk = esitoGenerazioniDaModelloOk;
	}
	
}
