/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

public class ProcedimentiInIterBean extends ProcedimentiInIterFilterBean {
	
	private String idProcedimento;
	private String estremiProcedimento;
	private String tipoProcedimento;
	private String oggettoProcedimento;
	private Date   dataAvvioProcedimento;
	private Date   decorrenzaProcedimento;
	private String processoPadreProcedimento;
	private String statoProcedimento;
	private String documentoInizialeProcedimento;
	private String attoParereFinaleProcedimento;
	private String inFaseProcedimento;
	private Date   scadenzaTermineProcedimento;
	private String flgScadenzaTermineProcedimento;
	private String ultimaAttivita;
	private String ultimaAttivitaMessaggio;
	private String ultimaAttivitaEsito;
	private String noteProcedimento;
	
	private String idUdFolder;
	private Date dataPresentazione;
	private String avviatoDa;
	private String prossimeAttivita;
	private String assegnatarioProcedimento;
	private String tipiTributo;
	private String anniTributo;
	private String attiRiferimento;	
	private String tipoAttoRif;
	private String annoAttoRif;
	private String numeroAttoRif;
	private String registroAttoRif;
	private String esitoAttoRif;
	private String flgCreatodame;				
	private String flgValido;
	private String recProtetto;
	private String idUtenteIns;
	private String descUtenteIns;
	private Date dataIns;
	private String idUtenteUltMod;
	private String descUtenteUltMod;
	private Date dataUltMod;
	private String flgPresenzaProcedimenti;
	private String idDocRispostaCedAutotutele;
	private String uriRispostaCedAutotutele;
	private String displayFilenameRispostaCedAutotutele;
	private String idUdRispostaCedAutotutele;
	private String idDocTypeRispostaCedAutotutele;
	private String mimetypeRispostaCedAutotutele;
	private Boolean firmatoRispostaCedAutotutele;
	private Boolean convertibileRispostaCedAutotutele;
	private String flgGeneraFileUnionePerLibroFirma;
	private String activityName;
	private String flgPrevistaNumerazione;
	private String assegnatarioIstruttoria;
	private String assegnatarioIstruttoriaPreIstruttoria;
	private String codFiscalePivaRichiedente;
	private String prossimoTaskAppongoFirmaVisto;
	private String prossimoTaskRifiutoFirmaVisto; 
	
	
	public String getIdProcedimento() {
		return idProcedimento;
	}
	public void setIdProcedimento(String idProcedimento) {
		this.idProcedimento = idProcedimento;
	}
	public String getEstremiProcedimento() {
		return estremiProcedimento;
	}
	public void setEstremiProcedimento(String estremiProcedimento) {
		this.estremiProcedimento = estremiProcedimento;
	}
	public String getTipoProcedimento() {
		return tipoProcedimento;
	}
	public void setTipoProcedimento(String tipoProcedimento) {
		this.tipoProcedimento = tipoProcedimento;
	}
	public String getOggettoProcedimento() {
		return oggettoProcedimento;
	}
	public void setOggettoProcedimento(String oggettoProcedimento) {
		this.oggettoProcedimento = oggettoProcedimento;
	}
	public Date getDataAvvioProcedimento() {
		return dataAvvioProcedimento;
	}
	public void setDataAvvioProcedimento(Date dataAvvioProcedimento) {
		this.dataAvvioProcedimento = dataAvvioProcedimento;
	}
	public Date getDecorrenzaProcedimento() {
		return decorrenzaProcedimento;
	}
	public void setDecorrenzaProcedimento(Date decorrenzaProcedimento) {
		this.decorrenzaProcedimento = decorrenzaProcedimento;
	}
	public String getProcessoPadreProcedimento() {
		return processoPadreProcedimento;
	}
	public void setProcessoPadreProcedimento(String processoPadreProcedimento) {
		this.processoPadreProcedimento = processoPadreProcedimento;
	}
	public String getStatoProcedimento() {
		return statoProcedimento;
	}
	public void setStatoProcedimento(String statoProcedimento) {
		this.statoProcedimento = statoProcedimento;
	}
	public String getDocumentoInizialeProcedimento() {
		return documentoInizialeProcedimento;
	}
	public void setDocumentoInizialeProcedimento(String documentoInizialeProcedimento) {
		this.documentoInizialeProcedimento = documentoInizialeProcedimento;
	}
	public String getAttoParereFinaleProcedimento() {
		return attoParereFinaleProcedimento;
	}
	public void setAttoParereFinaleProcedimento(String attoParereFinaleProcedimento) {
		this.attoParereFinaleProcedimento = attoParereFinaleProcedimento;
	}
	public String getInFaseProcedimento() {
		return inFaseProcedimento;
	}
	public void setInFaseProcedimento(String inFaseProcedimento) {
		this.inFaseProcedimento = inFaseProcedimento;
	}
	public Date getScadenzaTermineProcedimento() {
		return scadenzaTermineProcedimento;
	}
	public void setScadenzaTermineProcedimento(Date scadenzaTermineProcedimento) {
		this.scadenzaTermineProcedimento = scadenzaTermineProcedimento;
	}
	public String getFlgScadenzaTermineProcedimento() {
		return flgScadenzaTermineProcedimento;
	}
	public void setFlgScadenzaTermineProcedimento(String flgScadenzaTermineProcedimento) {
		this.flgScadenzaTermineProcedimento = flgScadenzaTermineProcedimento;
	}
	public String getNoteProcedimento() {
		return noteProcedimento;
	}
	public void setNoteProcedimento(String noteProcedimento) {
		this.noteProcedimento = noteProcedimento;
	}
	
	public String getIdUdFolder() {
		return idUdFolder;
	}
	public void setIdUdFolder(String idUdFolder) {
		this.idUdFolder = idUdFolder;
	}
	public Date getDataPresentazione() {
		return dataPresentazione;
	}
	public void setDataPresentazione(Date dataPresentazione) {
		this.dataPresentazione = dataPresentazione;
	}
	public String getAvviatoDa() {
		return avviatoDa;
	}
	public void setAvviatoDa(String avviatoDa) {
		this.avviatoDa = avviatoDa;
	}
	public String getProssimeAttivita() {
		return prossimeAttivita;
	}
	public void setProssimeAttivita(String prossimeAttivita) {
		this.prossimeAttivita = prossimeAttivita;
	}
	public String getAssegnatarioProcedimento() {
		return assegnatarioProcedimento;
	}
	public void setAssegnatarioProcedimento(String assegnatarioProcedimento) {
		this.assegnatarioProcedimento = assegnatarioProcedimento;
	}
	public String getTipiTributo() {
		return tipiTributo;
	}
	public void setTipiTributo(String tipiTributo) {
		this.tipiTributo = tipiTributo;
	}
	public String getAnniTributo() {
		return anniTributo;
	}
	public void setAnniTributo(String anniTributo) {
		this.anniTributo = anniTributo;
	}
	public String getAttiRiferimento() {
		return attiRiferimento;
	}
	public void setAttiRiferimento(String attiRiferimento) {
		this.attiRiferimento = attiRiferimento;
	}
	public String getTipoAttoRif() {
		return tipoAttoRif;
	}
	public void setTipoAttoRif(String tipoAttoRif) {
		this.tipoAttoRif = tipoAttoRif;
	}
	public String getAnnoAttoRif() {
		return annoAttoRif;
	}
	public void setAnnoAttoRif(String annoAttoRif) {
		this.annoAttoRif = annoAttoRif;
	}
	public String getNumeroAttoRif() {
		return numeroAttoRif;
	}
	public void setNumeroAttoRif(String numeroAttoRif) {
		this.numeroAttoRif = numeroAttoRif;
	}
	public String getRegistroAttoRif() {
		return registroAttoRif;
	}
	public void setRegistroAttoRif(String registroAttoRif) {
		this.registroAttoRif = registroAttoRif;
	}
	public String getEsitoAttoRif() {
		return esitoAttoRif;
	}
	public void setEsitoAttoRif(String esitoAttoRif) {
		this.esitoAttoRif = esitoAttoRif;
	}
	public String getFlgCreatodame() {
		return flgCreatodame;
	}
	public void setFlgCreatodame(String flgCreatodame) {
		this.flgCreatodame = flgCreatodame;
	}
	public String getFlgValido() {
		return flgValido;
	}
	public void setFlgValido(String flgValido) {
		this.flgValido = flgValido;
	}
	public String getRecProtetto() {
		return recProtetto;
	}
	public void setRecProtetto(String recProtetto) {
		this.recProtetto = recProtetto;
	}
	public String getIdUtenteIns() {
		return idUtenteIns;
	}
	public void setIdUtenteIns(String idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	public String getDescUtenteIns() {
		return descUtenteIns;
	}
	public void setDescUtenteIns(String descUtenteIns) {
		this.descUtenteIns = descUtenteIns;
	}
	public Date getDataIns() {
		return dataIns;
	}
	public void setDataIns(Date dataIns) {
		this.dataIns = dataIns;
	}
	public String getIdUtenteUltMod() {
		return idUtenteUltMod;
	}
	public void setIdUtenteUltMod(String idUtenteUltMod) {
		this.idUtenteUltMod = idUtenteUltMod;
	}
	public String getDescUtenteUltMod() {
		return descUtenteUltMod;
	}
	public void setDescUtenteUltMod(String descUtenteUltMod) {
		this.descUtenteUltMod = descUtenteUltMod;
	}
	public Date getDataUltMod() {
		return dataUltMod;
	}
	public void setDataUltMod(Date dataUltMod) {
		this.dataUltMod = dataUltMod;
	}
	public String getFlgPresenzaProcedimenti() {
		return flgPresenzaProcedimenti;
	}
	public void setFlgPresenzaProcedimenti(String flgPresenzaProcedimenti) {
		this.flgPresenzaProcedimenti = flgPresenzaProcedimenti;
	}
	public String getIdDocRispostaCedAutotutele() {
		return idDocRispostaCedAutotutele;
	}
	public void setIdDocRispostaCedAutotutele(String idDocRispostaCedAutotutele) {
		this.idDocRispostaCedAutotutele = idDocRispostaCedAutotutele;
	}
	public String getUriRispostaCedAutotutele() {
		return uriRispostaCedAutotutele;
	}
	public void setUriRispostaCedAutotutele(String uriRispostaCedAutotutele) {
		this.uriRispostaCedAutotutele = uriRispostaCedAutotutele;
	}
	public String getDisplayFilenameRispostaCedAutotutele() {
		return displayFilenameRispostaCedAutotutele;
	}
	public void setDisplayFilenameRispostaCedAutotutele(String displayFilenameRispostaCedAutotutele) {
		this.displayFilenameRispostaCedAutotutele = displayFilenameRispostaCedAutotutele;
	}
	public String getIdUdRispostaCedAutotutele() {
		return idUdRispostaCedAutotutele;
	}
	public void setIdUdRispostaCedAutotutele(String idUdRispostaCedAutotutele) {
		this.idUdRispostaCedAutotutele = idUdRispostaCedAutotutele;
	}
	public String getIdDocTypeRispostaCedAutotutele() {
		return idDocTypeRispostaCedAutotutele;
	}
	public void setIdDocTypeRispostaCedAutotutele(String idDocTypeRispostaCedAutotutele) {
		this.idDocTypeRispostaCedAutotutele = idDocTypeRispostaCedAutotutele;
	}
	public String getMimetypeRispostaCedAutotutele() {
		return mimetypeRispostaCedAutotutele;
	}
	public void setMimetypeRispostaCedAutotutele(String mimetypeRispostaCedAutotutele) {
		this.mimetypeRispostaCedAutotutele = mimetypeRispostaCedAutotutele;
	}
	public Boolean getFirmatoRispostaCedAutotutele() {
		return firmatoRispostaCedAutotutele;
	}
	public void setFirmatoRispostaCedAutotutele(Boolean firmatoRispostaCedAutotutele) {
		this.firmatoRispostaCedAutotutele = firmatoRispostaCedAutotutele;
	}
	public Boolean getConvertibileRispostaCedAutotutele() {
		return convertibileRispostaCedAutotutele;
	}	
	public void setConvertibileRispostaCedAutotutele(Boolean convertibileRispostaCedAutotutele) {
		this.convertibileRispostaCedAutotutele = convertibileRispostaCedAutotutele;
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
	public String getFlgPrevistaNumerazione() {
		return flgPrevistaNumerazione;
	}
	public void setFlgPrevistaNumerazione(String flgPrevistaNumerazione) {
		this.flgPrevistaNumerazione = flgPrevistaNumerazione;
	}
	public String getUltimaAttivita() {
		return ultimaAttivita;
	}
	public void setUltimaAttivita(String ultimaAttivita) {
		this.ultimaAttivita = ultimaAttivita;
	}
	public String getUltimaAttivitaMessaggio() {
		return ultimaAttivitaMessaggio;
	}
	public void setUltimaAttivitaMessaggio(String ultimaAttivitaMessaggio) {
		this.ultimaAttivitaMessaggio = ultimaAttivitaMessaggio;
	}
	public String getUltimaAttivitaEsito() {
		return ultimaAttivitaEsito;
	}
	public void setUltimaAttivitaEsito(String ultimaAttivitaEsito) {
		this.ultimaAttivitaEsito = ultimaAttivitaEsito;
	}
	public String getAssegnatarioIstruttoria() {
		return assegnatarioIstruttoria;
	}
	public void setAssegnatarioIstruttoria(String assegnatarioIstruttoria) {
		this.assegnatarioIstruttoria = assegnatarioIstruttoria;
	}
	public String getAssegnatarioIstruttoriaPreIstruttoria() {
		return assegnatarioIstruttoriaPreIstruttoria;
	}
	public void setAssegnatarioIstruttoriaPreIstruttoria(String assegnatarioIstruttoriaPreIstruttoria) {
		this.assegnatarioIstruttoriaPreIstruttoria = assegnatarioIstruttoriaPreIstruttoria;
	}
	public String getCodFiscalePivaRichiedente() {
		return codFiscalePivaRichiedente;
	}
	public void setCodFiscalePivaRichiedente(String codFiscalePivaRichiedente) {
		this.codFiscalePivaRichiedente = codFiscalePivaRichiedente;
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
}