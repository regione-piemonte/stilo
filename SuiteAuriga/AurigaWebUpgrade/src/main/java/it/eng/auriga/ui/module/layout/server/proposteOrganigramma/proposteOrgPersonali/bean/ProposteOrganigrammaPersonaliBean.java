/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;


public class ProposteOrganigrammaPersonaliBean extends ProposteOrganigrammaPersonaliFilterBean {
	
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
	private String ultimoTaskProcedimento;
	private String messaggioUltimoTaskProcedimento;
	private String noteProcedimento;
	private String nominativiProcedimento;
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
	public String getUltimoTaskProcedimento() {
		return ultimoTaskProcedimento;
	}
	public void setUltimoTaskProcedimento(String ultimoTaskProcedimento) {
		this.ultimoTaskProcedimento = ultimoTaskProcedimento;
	}
	public String getMessaggioUltimoTaskProcedimento() {
		return messaggioUltimoTaskProcedimento;
	}
	public void setMessaggioUltimoTaskProcedimento(String messaggioUltimoTaskProcedimento) {
		this.messaggioUltimoTaskProcedimento = messaggioUltimoTaskProcedimento;
	}
	public String getNoteProcedimento() {
		return noteProcedimento;
	}
	public void setNoteProcedimento(String noteProcedimento) {
		this.noteProcedimento = noteProcedimento;
	}
	public String getNominativiProcedimento() {
		return nominativiProcedimento;
	}
	public void setNominativiProcedimento(String nominativiProcedimento) {
		this.nominativiProcedimento = nominativiProcedimento;
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
	
}