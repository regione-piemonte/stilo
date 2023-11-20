/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

/**
 * 
 * @author DANCRIST
 *
 */

public class VisureXmlBean {
	
	@NumeroColonna(numero = "1")
	private String idProcedimento;
//	@NumeroColonna(numero = "2")
//	private String estremiProcedimento;
//	@NumeroColonna(numero = "3")
//	private String tipoProcedimento;
//	@NumeroColonna(numero = "4")
//	private String oggettoProcedimento;
	@NumeroColonna(numero = "5")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
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
//	@NumeroColonna(numero = "12")
//	private String attoParereFinaleProcedimento;
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
//	@NumeroColonna(numero = "19")
//	private String nominativiProcedimento;
	@NumeroColonna(numero = "19")
	private String richiedente;
	@NumeroColonna(numero = "20")
	private String idUdFolder;
	@NumeroColonna(numero = "21")
	private String idUd;
	@NumeroColonna(numero = "29")
	private String avviatoDa;
	@NumeroColonna(numero = "30")
	private String prossimeAttivita;
//	@NumeroColonna(numero = "32")
//	private String assegnatarioProcedimento;
	@NumeroColonna(numero = "41")
	private String uriFile;
	@NumeroColonna(numero = "42")
	private String nomeFile;
	@NumeroColonna(numero = "51")
	private String respIstruttoria;
	@NumeroColonna(numero = "52")
	private String tipoRichiedente;
	@NumeroColonna(numero = "53")
	private String indirizzo;
	@NumeroColonna(numero = "54")
	private String attiRichiesti;
	@NumeroColonna(numero = "56")
	private String classificaAtti;
	@NumeroColonna(numero = "57")
	private String archiviCoinvolti;
	@NumeroColonna(numero = "58")
	private String udc;
	@NumeroColonna(numero = "59")
	@TipoData(tipo=Tipo.DATA)
	private Date appCittadella;
	@NumeroColonna(numero = "60")
	@TipoData(tipo=Tipo.DATA)
	private Date appBernina;
	@NumeroColonna(numero = "61")
	private String codPraticaSUE;
	@NumeroColonna(numero = "66")
	private String tipologiaRichiedenteSUE;
	@NumeroColonna(numero = "67")
	private String motivazioneVisuraSUE;
//	@NumeroColonna(numero = "68")
//	private String richiestoCartaceo;
	@NumeroColonna(numero = "69")
	private String richAttiDiFabbrica;
	@NumeroColonna(numero = "70")
	private String fabbricatoAttiCostrFino1996;
	@NumeroColonna(numero = "71")
	private String fabbricatoAttiCostrDa1997;
	@NumeroColonna(numero="95")
	private String ultimaAttivitaEsito;	
	@NumeroColonna(numero = "96")
	private String altUltimaAttivitaEsito;
	@NumeroColonna(numero="104")
	private String canaleArrivo;	
	@NumeroColonna(numero ="105")
	private String richModifiche;
	
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
	public void setIdProcedimento(String idProcedimento) {
		this.idProcedimento = idProcedimento;
	}
//	public String getEstremiProcedimento() {
//		return estremiProcedimento;
//	}
//	public void setEstremiProcedimento(String estremiProcedimento) {
//		this.estremiProcedimento = estremiProcedimento;
//	}
//	public String getTipoProcedimento() {
//		return tipoProcedimento;
//	}
//	public void setTipoProcedimento(String tipoProcedimento) {
//		this.tipoProcedimento = tipoProcedimento;
//	}
//	public String getOggettoProcedimento() {
//		return oggettoProcedimento;
//	}
//	public void setOggettoProcedimento(String oggettoProcedimento) {
//		this.oggettoProcedimento = oggettoProcedimento;
//	}
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
	public void setDocumentoInizialeProcedimento(
			String documentoInizialeProcedimento) {
		this.documentoInizialeProcedimento = documentoInizialeProcedimento;
	}
//	public String getAttoParereFinaleProcedimento() {
//		return attoParereFinaleProcedimento;
//	}
//	public void setAttoParereFinaleProcedimento(String attoParereFinaleProcedimento) {
//		this.attoParereFinaleProcedimento = attoParereFinaleProcedimento;
//	}
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
	public void setFlgScadenzaTermineProcedimento(
			String flgScadenzaTermineProcedimento) {
		this.flgScadenzaTermineProcedimento = flgScadenzaTermineProcedimento;
	}
	public String getUltimoTaskProcedimento() {
		return ultimoTaskProcedimento;
	}
	public void setUltimoTaskProcedimento(String ultimoTaskProcedimento) {
		this.ultimoTaskProcedimento = ultimoTaskProcedimento;
	}
	public String getMessaggioUltimoTaskProcedimento() {
		return messaggioUltimoTaskProcedimento;
	}
	public void setMessaggioUltimoTaskProcedimento(
			String messaggioUltimoTaskProcedimento) {
		this.messaggioUltimoTaskProcedimento = messaggioUltimoTaskProcedimento;
	}
	public String getNoteProcedimento() {
		return noteProcedimento;
	}
	public void setNoteProcedimento(String noteProcedimento) {
		this.noteProcedimento = noteProcedimento;
	}
//	public String getNominativiProcedimento() {
//		return nominativiProcedimento;
//	}
//	public void setNominativiProcedimento(String nominativiProcedimento) {
//		this.nominativiProcedimento = nominativiProcedimento;
//	}
	public String getIdUdFolder() {
		return idUdFolder;
	}
	public void setIdUdFolder(String idUdFolder) {
		this.idUdFolder = idUdFolder;
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
//	public String getAssegnatarioProcedimento() {
//		return assegnatarioProcedimento;
//	}
//	public void setAssegnatarioProcedimento(String assegnatarioProcedimento) {
//		this.assegnatarioProcedimento = assegnatarioProcedimento;
//	}
	public String getProssimeAttivita() {
		return prossimeAttivita;
	}
	public void setProssimeAttivita(String prossimeAttivita) {
		this.prossimeAttivita = prossimeAttivita;
	}
	public String getAvviatoDa() {
		return avviatoDa;
	}
	public void setAvviatoDa(String avviatoDa) {
		this.avviatoDa = avviatoDa;
	}
	public String getRichiedente() {
		return richiedente;
	}
	public void setRichiedente(String richiedente) {
		this.richiedente = richiedente;
	}
	public String getTipoRichiedente() {
		return tipoRichiedente;
	}
	public void setTipoRichiedente(String tipoRichiedente) {
		this.tipoRichiedente = tipoRichiedente;
	}
	public String getRespIstruttoria() {
		return respIstruttoria;
	}
	public void setRespIstruttoria(String respIstruttoria) {
		this.respIstruttoria = respIstruttoria;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getAttiRichiesti() {
		return attiRichiesti;
	}
	public void setAttiRichiesti(String attiRichiesti) {
		this.attiRichiesti = attiRichiesti;
	}
	public String getClassificaAtti() {
		return classificaAtti;
	}
	public void setClassificaAtti(String classificaAtti) {
		this.classificaAtti = classificaAtti;
	}
	public String getArchiviCoinvolti() {
		return archiviCoinvolti;
	}
	public void setArchiviCoinvolti(String archiviCoinvolti) {
		this.archiviCoinvolti = archiviCoinvolti;
	}
	public String getUdc() {
		return udc;
	}
	public void setUdc(String udc) {
		this.udc = udc;
	}
	public Date getAppCittadella() {
		return appCittadella;
	}
	public void setAppCittadella(Date appCittadella) {
		this.appCittadella = appCittadella;
	}
	public Date getAppBernina() {
		return appBernina;
	}
	public void setAppBernina(Date appBernina) {
		this.appBernina = appBernina;
	}
	public String getUriFile() {
		return uriFile;
	}
	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public String getCodPraticaSUE() {
		return codPraticaSUE;
	}
	public void setCodPraticaSUE(String codPraticaSUE) {
		this.codPraticaSUE = codPraticaSUE;
	}
	public String getIdUd() {
		return idUd;
	}
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	public String getTipologiaRichiedenteSUE() {
		return tipologiaRichiedenteSUE;
	}
	public void setTipologiaRichiedenteSUE(String tipologiaRichiedenteSUE) {
		this.tipologiaRichiedenteSUE = tipologiaRichiedenteSUE;
	}
	public String getMotivazioneVisuraSUE() {
		return motivazioneVisuraSUE;
	}
	public void setMotivazioneVisuraSUE(String motivazioneVisuraSUE) {
		this.motivazioneVisuraSUE = motivazioneVisuraSUE;
	}
	public String getRichAttiDiFabbrica() {
		return richAttiDiFabbrica;
	}
	public void setRichAttiDiFabbrica(String richAttiDiFabbrica) {
		this.richAttiDiFabbrica = richAttiDiFabbrica;
	}
	public String getFabbricatoAttiCostrFino1996() {
		return fabbricatoAttiCostrFino1996;
	}
	public void setFabbricatoAttiCostrFino1996(String fabbricatoAttiCostrFino1996) {
		this.fabbricatoAttiCostrFino1996 = fabbricatoAttiCostrFino1996;
	}
	public String getFabbricatoAttiCostrDa1997() {
		return fabbricatoAttiCostrDa1997;
	}
	public void setFabbricatoAttiCostrDa1997(String fabbricatoAttiCostrDa1997) {
		this.fabbricatoAttiCostrDa1997 = fabbricatoAttiCostrDa1997;
	}
//	public String getRichiestoCartaceo() {
//		return richiestoCartaceo;
//	}
//	public void setRichiestoCartaceo(String richiestoCartaceo) {
//		this.richiestoCartaceo = richiestoCartaceo;
//	}
	public String getUltimaAttivitaEsito() {
		return ultimaAttivitaEsito;
	}
	public void setUltimaAttivitaEsito(String ultimaAttivitaEsito) {
		this.ultimaAttivitaEsito = ultimaAttivitaEsito;
	}
	public String getAltUltimaAttivitaEsito() {
		return altUltimaAttivitaEsito;
	}
	public void setAltUltimaAttivitaEsito(String altUltimaAttivitaEsito) {
		this.altUltimaAttivitaEsito = altUltimaAttivitaEsito;
	}
	public String getCanaleArrivo() {
		return canaleArrivo;
	}
	public void setCanaleArrivo(String canaleArrivo) {
		this.canaleArrivo = canaleArrivo;
	}
	public String getRichModifiche() {
		return richModifiche;
	}
	public void setRichModifiche(String richModifiche) {
		this.richModifiche = richModifiche;
	}
}