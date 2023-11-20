/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

/**
 * 
 * @author DANCRIST
 *
 */

public class VisureBean extends VisureFilterBean {
	
	private String idProcedimento;
//	private String estremiProcedimento;
//	private String tipoProcedimento;
//	private String oggettoProcedimento;
	private Date   dataAvvioProcedimento;
	private Date   decorrenzaProcedimento;
	private String processoPadreProcedimento;
	private String statoProcedimento;
	private String documentoInizialeProcedimento;
//	private String attoParereFinaleProcedimento;
	private String inFaseProcedimento;
	private Date   scadenzaTermineProcedimento;
	private String flgScadenzaTermineProcedimento;
	private String ultimoTaskProcedimento;
	private String messaggioUltimoTaskProcedimento;
	private String noteProcedimento;
//	private String nominativiProcedimento;
//	private String assegnatarioProcedimento;
	private String idUdFolder;
	private String flgCreatodame;				
	private String flgValido;
	private String recProtetto;
	private String idUtenteIns;
	private String descUtenteIns;
	private Date dataIns;
	private String idUtenteUltMod;
	private String descUtenteUltMod;
	private Date dataUltMod;
	private String prossimeAttivita;
	private String avviatoDa;
	private String richiedente;
	private String tipoRichiedente;
	private String respIstruttoria;
	private String indirizzo;
	private String attiRichiesti;
	private String classificaAtti;
	private String archiviCoinvolti;
	private String udc;
	private Date appCittadella;
	private Date appBernina;
	private String codPraticaSUE;
	private String idUd;
	private String tipologiaRichiedenteSUE;
	private String motivazioneVisuraSUE;
	private String richAttiDiFabbrica;
	private String fabbricatoAttiCostrFino1996;
	private String fabbricatoAttiCostrDa1997;
	//private String richiestoCartaceo;
	private String richModifiche;
	private String ultimaAttivitaEsito;
	private String altUltimaAttivitaEsito;
	private String canaleArrivo;
	
	/* FILE */
	private String uriFile;
	private String nomeFile;
	
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
	public String getIdUdFolder() {
		return idUdFolder;
	}
	public void setIdUdFolder(String idUdFolder) {
		this.idUdFolder = idUdFolder;
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
	public String getRichModifiche() {
		return richModifiche;
	}
	public void setRichModifiche(String richModifiche) {
		this.richModifiche = richModifiche;
	}
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
	
}