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

public class RichiesteAutotuteleCedPersonaliXmlBean {

	@NumeroColonna(numero = "1")
	private String idProcedimento;
	@NumeroColonna(numero = "2")
	private String estremiProcedimento;
	@NumeroColonna(numero = "3")
	private String tipoProcedimento;
	@NumeroColonna(numero = "4")
	private String oggettoProcedimento;
	@NumeroColonna(numero = "5")
	@TipoData(tipo=Tipo.DATA)
	private Date   dataAvvioProcedimento;
	@NumeroColonna(numero = "6")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date   decorrenzaProcedimento;
	@NumeroColonna(numero = "7")
	private String processoPadreProcedimento;
	@NumeroColonna(numero = "10")
	private String statoProcedimento;
	@NumeroColonna(numero = "11")
	private String documentoInizialeProcedimento;
	@NumeroColonna(numero = "12")
	private String attoParereFinaleProcedimento;
	@NumeroColonna(numero = "13")
	private String inFaseProcedimento;
	@NumeroColonna(numero = "14")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date   scadenzaTermineProcedimento;
	@NumeroColonna(numero = "15")
	private String flgScadenzaTermineProcedimento;
	@NumeroColonna(numero = "16")
	private String ultimoTaskProcedimento;
	@NumeroColonna(numero = "17")
	private String messaggioUltimoTaskProcedimento;
	@NumeroColonna(numero = "18")
	private String noteProcedimento;
	@NumeroColonna(numero = "19")
	private String denominazioneRichiedente;
	@NumeroColonna(numero = "20")
	private String idUdFolder;
	@NumeroColonna(numero = "25")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date   dataPresentazione;
	@NumeroColonna(numero = "29")
	private String avviatoDa;
	@NumeroColonna(numero = "30")
	private String prossimeAttivita;
	@NumeroColonna(numero = "32")
	private String assegnatarioProcedimento;
	@NumeroColonna(numero = "62")
	private String tipiTributo;
	@NumeroColonna(numero = "63")
	private String anniTributo;
	@NumeroColonna(numero = "64")
	private String attiRiferimento;
	@NumeroColonna(numero = "65")
	private String flgPresenzaProcedimenti;
	@NumeroColonna(numero = "87")
	private String idDocRispostaCedAutotutele;
	@NumeroColonna(numero = "88")
	private String uriRispostaCedAutotutele;
	@NumeroColonna(numero = "89")
	private String displayFilenameRispostaCedAutotutele;
	@NumeroColonna(numero = "95")
	private String ultimaAttivitaEsito;
	@NumeroColonna(numero = "97")
	private String idUdRispostaCedAutotutele;
	@NumeroColonna(numero = "98")
	private String idDocTypeRispostaCedAutotutele;
	@NumeroColonna(numero = "99")
	private String mimetypeRispostaCedAutotutele;
	@NumeroColonna(numero = "100")
	private String flgFirmatoRispostaCedAutotutele;
	@NumeroColonna(numero = "118")
	private String flgPrevistaNumerazione;
	@NumeroColonna(numero = "133")
	private String codFiscalePivaRichiedente;
	@NumeroColonna(numero = "140")
	private String nrRichiestaAutotutela;

	private String flgCreatodame;				
	private String flgValido;
	private String recProtetto;
	private String idUtenteIns;
	private String descUtenteIns;
	private Date dataIns;
	private String idUtenteUltMod;
	private String descUtenteUltMod;
	private Date dataUltMod;
	public String getIdProcedimento() {
		return idProcedimento;
	}
	public String getEstremiProcedimento() {
		return estremiProcedimento;
	}
	public String getTipoProcedimento() {
		return tipoProcedimento;
	}
	public String getOggettoProcedimento() {
		return oggettoProcedimento;
	}
	public Date getDataAvvioProcedimento() {
		return dataAvvioProcedimento;
	}
	public Date getDecorrenzaProcedimento() {
		return decorrenzaProcedimento;
	}
	public String getProcessoPadreProcedimento() {
		return processoPadreProcedimento;
	}
	public String getStatoProcedimento() {
		return statoProcedimento;
	}
	public String getDocumentoInizialeProcedimento() {
		return documentoInizialeProcedimento;
	}
	public String getAttoParereFinaleProcedimento() {
		return attoParereFinaleProcedimento;
	}
	public String getInFaseProcedimento() {
		return inFaseProcedimento;
	}
	public Date getScadenzaTermineProcedimento() {
		return scadenzaTermineProcedimento;
	}
	public String getFlgScadenzaTermineProcedimento() {
		return flgScadenzaTermineProcedimento;
	}
	public String getUltimoTaskProcedimento() {
		return ultimoTaskProcedimento;
	}
	public String getMessaggioUltimoTaskProcedimento() {
		return messaggioUltimoTaskProcedimento;
	}
	public String getNoteProcedimento() {
		return noteProcedimento;
	}
	public String getDenominazioneRichiedente() {
		return denominazioneRichiedente;
	}
	public String getIdUdFolder() {
		return idUdFolder;
	}
	public Date getDataPresentazione() {
		return dataPresentazione;
	}
	public String getAvviatoDa() {
		return avviatoDa;
	}
	public String getProssimeAttivita() {
		return prossimeAttivita;
	}
	public String getAssegnatarioProcedimento() {
		return assegnatarioProcedimento;
	}
	public String getTipiTributo() {
		return tipiTributo;
	}
	public String getAnniTributo() {
		return anniTributo;
	}
	public String getAttiRiferimento() {
		return attiRiferimento;
	}
	public String getFlgPresenzaProcedimenti() {
		return flgPresenzaProcedimenti;
	}
	public String getIdDocRispostaCedAutotutele() {
		return idDocRispostaCedAutotutele;
	}
	public String getUriRispostaCedAutotutele() {
		return uriRispostaCedAutotutele;
	}
	public String getDisplayFilenameRispostaCedAutotutele() {
		return displayFilenameRispostaCedAutotutele;
	}
	public String getUltimaAttivitaEsito() {
		return ultimaAttivitaEsito;
	}
	public String getIdUdRispostaCedAutotutele() {
		return idUdRispostaCedAutotutele;
	}
	public String getIdDocTypeRispostaCedAutotutele() {
		return idDocTypeRispostaCedAutotutele;
	}
	public String getMimetypeRispostaCedAutotutele() {
		return mimetypeRispostaCedAutotutele;
	}
	public String getFlgFirmatoRispostaCedAutotutele() {
		return flgFirmatoRispostaCedAutotutele;
	}
	public String getFlgPrevistaNumerazione() {
		return flgPrevistaNumerazione;
	}
	public String getCodFiscalePivaRichiedente() {
		return codFiscalePivaRichiedente;
	}
	public String getFlgCreatodame() {
		return flgCreatodame;
	}
	public String getFlgValido() {
		return flgValido;
	}
	public String getRecProtetto() {
		return recProtetto;
	}
	public String getIdUtenteIns() {
		return idUtenteIns;
	}
	public String getDescUtenteIns() {
		return descUtenteIns;
	}
	public Date getDataIns() {
		return dataIns;
	}
	public String getIdUtenteUltMod() {
		return idUtenteUltMod;
	}
	public String getDescUtenteUltMod() {
		return descUtenteUltMod;
	}
	public Date getDataUltMod() {
		return dataUltMod;
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
	public void setOggettoProcedimento(String oggettoProcedimento) {
		this.oggettoProcedimento = oggettoProcedimento;
	}
	public void setDataAvvioProcedimento(Date dataAvvioProcedimento) {
		this.dataAvvioProcedimento = dataAvvioProcedimento;
	}
	public void setDecorrenzaProcedimento(Date decorrenzaProcedimento) {
		this.decorrenzaProcedimento = decorrenzaProcedimento;
	}
	public void setProcessoPadreProcedimento(String processoPadreProcedimento) {
		this.processoPadreProcedimento = processoPadreProcedimento;
	}
	public void setStatoProcedimento(String statoProcedimento) {
		this.statoProcedimento = statoProcedimento;
	}
	public void setDocumentoInizialeProcedimento(String documentoInizialeProcedimento) {
		this.documentoInizialeProcedimento = documentoInizialeProcedimento;
	}
	public void setAttoParereFinaleProcedimento(String attoParereFinaleProcedimento) {
		this.attoParereFinaleProcedimento = attoParereFinaleProcedimento;
	}
	public void setInFaseProcedimento(String inFaseProcedimento) {
		this.inFaseProcedimento = inFaseProcedimento;
	}
	public void setScadenzaTermineProcedimento(Date scadenzaTermineProcedimento) {
		this.scadenzaTermineProcedimento = scadenzaTermineProcedimento;
	}
	public void setFlgScadenzaTermineProcedimento(String flgScadenzaTermineProcedimento) {
		this.flgScadenzaTermineProcedimento = flgScadenzaTermineProcedimento;
	}
	public void setUltimoTaskProcedimento(String ultimoTaskProcedimento) {
		this.ultimoTaskProcedimento = ultimoTaskProcedimento;
	}
	public void setMessaggioUltimoTaskProcedimento(String messaggioUltimoTaskProcedimento) {
		this.messaggioUltimoTaskProcedimento = messaggioUltimoTaskProcedimento;
	}
	public void setNoteProcedimento(String noteProcedimento) {
		this.noteProcedimento = noteProcedimento;
	}
	public void setDenominazioneRichiedente(String denominazioneRichiedente) {
		this.denominazioneRichiedente = denominazioneRichiedente;
	}
	public void setIdUdFolder(String idUdFolder) {
		this.idUdFolder = idUdFolder;
	}
	public void setDataPresentazione(Date dataPresentazione) {
		this.dataPresentazione = dataPresentazione;
	}
	public void setAvviatoDa(String avviatoDa) {
		this.avviatoDa = avviatoDa;
	}
	public void setProssimeAttivita(String prossimeAttivita) {
		this.prossimeAttivita = prossimeAttivita;
	}
	public void setAssegnatarioProcedimento(String assegnatarioProcedimento) {
		this.assegnatarioProcedimento = assegnatarioProcedimento;
	}
	public void setTipiTributo(String tipiTributo) {
		this.tipiTributo = tipiTributo;
	}
	public void setAnniTributo(String anniTributo) {
		this.anniTributo = anniTributo;
	}
	public void setAttiRiferimento(String attiRiferimento) {
		this.attiRiferimento = attiRiferimento;
	}
	public void setFlgPresenzaProcedimenti(String flgPresenzaProcedimenti) {
		this.flgPresenzaProcedimenti = flgPresenzaProcedimenti;
	}
	public void setIdDocRispostaCedAutotutele(String idDocRispostaCedAutotutele) {
		this.idDocRispostaCedAutotutele = idDocRispostaCedAutotutele;
	}
	public void setUriRispostaCedAutotutele(String uriRispostaCedAutotutele) {
		this.uriRispostaCedAutotutele = uriRispostaCedAutotutele;
	}
	public void setDisplayFilenameRispostaCedAutotutele(String displayFilenameRispostaCedAutotutele) {
		this.displayFilenameRispostaCedAutotutele = displayFilenameRispostaCedAutotutele;
	}
	public void setUltimaAttivitaEsito(String ultimaAttivitaEsito) {
		this.ultimaAttivitaEsito = ultimaAttivitaEsito;
	}
	public void setIdUdRispostaCedAutotutele(String idUdRispostaCedAutotutele) {
		this.idUdRispostaCedAutotutele = idUdRispostaCedAutotutele;
	}
	public void setIdDocTypeRispostaCedAutotutele(String idDocTypeRispostaCedAutotutele) {
		this.idDocTypeRispostaCedAutotutele = idDocTypeRispostaCedAutotutele;
	}
	public void setMimetypeRispostaCedAutotutele(String mimetypeRispostaCedAutotutele) {
		this.mimetypeRispostaCedAutotutele = mimetypeRispostaCedAutotutele;
	}
	public void setFlgFirmatoRispostaCedAutotutele(String flgFirmatoRispostaCedAutotutele) {
		this.flgFirmatoRispostaCedAutotutele = flgFirmatoRispostaCedAutotutele;
	}
	public void setFlgPrevistaNumerazione(String flgPrevistaNumerazione) {
		this.flgPrevistaNumerazione = flgPrevistaNumerazione;
	}
	public void setCodFiscalePivaRichiedente(String codFiscalePivaRichiedente) {
		this.codFiscalePivaRichiedente = codFiscalePivaRichiedente;
	}
	public void setFlgCreatodame(String flgCreatodame) {
		this.flgCreatodame = flgCreatodame;
	}
	public void setFlgValido(String flgValido) {
		this.flgValido = flgValido;
	}
	public void setRecProtetto(String recProtetto) {
		this.recProtetto = recProtetto;
	}
	public void setIdUtenteIns(String idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	public void setDescUtenteIns(String descUtenteIns) {
		this.descUtenteIns = descUtenteIns;
	}
	public void setDataIns(Date dataIns) {
		this.dataIns = dataIns;
	}
	public void setIdUtenteUltMod(String idUtenteUltMod) {
		this.idUtenteUltMod = idUtenteUltMod;
	}
	public void setDescUtenteUltMod(String descUtenteUltMod) {
		this.descUtenteUltMod = descUtenteUltMod;
	}
	public void setDataUltMod(Date dataUltMod) {
		this.dataUltMod = dataUltMod;
	}
	public String getNrRichiestaAutotutela() {
		return nrRichiestaAutotutela;
	}
	public void setNrRichiestaAutotutela(String nrRichiestaAutotutela) {
		this.nrRichiestaAutotutela = nrRichiestaAutotutela;
	}
	
}