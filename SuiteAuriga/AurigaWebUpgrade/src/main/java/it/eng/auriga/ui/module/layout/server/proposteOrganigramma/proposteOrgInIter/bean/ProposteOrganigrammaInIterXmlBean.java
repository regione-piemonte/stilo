/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

import java.util.Date;

public class ProposteOrganigrammaInIterXmlBean {
	
	@NumeroColonna(numero = "1")
	private String idProcedimento;
	@NumeroColonna(numero = "2")
	private String estremiProcedimento;
	@NumeroColonna(numero = "3")
	private String tipoProcedimento;
	@NumeroColonna(numero = "4")
	private String oggettoProcedimento;
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
	private String nominativiProcedimento;
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