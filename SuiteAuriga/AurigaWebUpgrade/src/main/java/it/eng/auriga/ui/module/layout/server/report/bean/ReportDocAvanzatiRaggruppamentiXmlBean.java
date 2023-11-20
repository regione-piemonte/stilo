/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import java.io.Serializable;

public class ReportDocAvanzatiRaggruppamentiXmlBean implements Serializable{
	
	
	@XmlVariabile(nome="Periodo", tipo=TipoVariabile.SEMPLICE)
	private String raggruppaPeriodo;
	
	@XmlVariabile(nome="TipoRegistrazione", tipo=TipoVariabile.SEMPLICE)
	private String raggruppaTipoRegistrazione;
	
	@XmlVariabile(nome="CategoriaRegistrazione", tipo=TipoVariabile.SEMPLICE)
	private String raggruppaCategoriaRegistrazione;
	
	@XmlVariabile(nome="Registro", tipo=TipoVariabile.SEMPLICE)
	private String raggruppaRegistroNumerazione;
	
	@XmlVariabile(nome="RegAnnullate", tipo=TipoVariabile.SEMPLICE)
	private String raggruppaRegValideAnnullate;
	
	@XmlVariabile(nome="SpAOO", tipo=TipoVariabile.SEMPLICE)
	private String raggruppaEnteAoo;
	
	@XmlVariabile(nome="UO", tipo=TipoVariabile.SEMPLICE)
	private String raggruppaUo;
	
	@XmlVariabile(nome="User", tipo=TipoVariabile.SEMPLICE)
	private String raggruppaUtente;

	@XmlVariabile(nome="ApplicazioneReg", tipo=TipoVariabile.SEMPLICE)
	private String raggruppaApplicazioniEsterne;
		
	@XmlVariabile(nome="SupportoOriginale", tipo=TipoVariabile.SEMPLICE)
	private String raggruppaSupporto;

	@XmlVariabile(nome="PresenzaFile", tipo=TipoVariabile.SEMPLICE)
	private String raggruppaPresenzaFile;
	
	@XmlVariabile(nome="Canale", tipo=TipoVariabile.SEMPLICE)
	private String raggruppaMezzoTrasmissione;
	
	@XmlVariabile(nome="Riservatezza", tipo=TipoVariabile.SEMPLICE)
	private String raggruppaLivelliRiservatezza;

	public String getRaggruppaPeriodo() {
		return raggruppaPeriodo;
	}

	public void setRaggruppaPeriodo(String raggruppaPeriodo) {
		this.raggruppaPeriodo = raggruppaPeriodo;
	}

	public String getRaggruppaTipoRegistrazione() {
		return raggruppaTipoRegistrazione;
	}

	public void setRaggruppaTipoRegistrazione(String raggruppaTipoRegistrazione) {
		this.raggruppaTipoRegistrazione = raggruppaTipoRegistrazione;
	}

	public String getRaggruppaCategoriaRegistrazione() {
		return raggruppaCategoriaRegistrazione;
	}

	public void setRaggruppaCategoriaRegistrazione(
			String raggruppaCategoriaRegistrazione) {
		this.raggruppaCategoriaRegistrazione = raggruppaCategoriaRegistrazione;
	}

	public String getRaggruppaRegistroNumerazione() {
		return raggruppaRegistroNumerazione;
	}

	public void setRaggruppaRegistroNumerazione(String raggruppaRegistroNumerazione) {
		this.raggruppaRegistroNumerazione = raggruppaRegistroNumerazione;
	}
	
	public String getRaggruppaRegValideAnnullate() {
		return raggruppaRegValideAnnullate;
	}
	public void setRaggruppaRegValideAnnullate(String raggruppaRegValideAnnullate) {
		this.raggruppaRegValideAnnullate = raggruppaRegValideAnnullate;
	}

	public String getRaggruppaEnteAoo() {
		return raggruppaEnteAoo;
	}

	public void setRaggruppaEnteAoo(String raggruppaEnteAoo) {
		this.raggruppaEnteAoo = raggruppaEnteAoo;
	}

	public String getRaggruppaUo() {
		return raggruppaUo;
	}

	public void setRaggruppaUo(String raggruppaUo) {
		this.raggruppaUo = raggruppaUo;
	}

	public String getRaggruppaUtente() {
		return raggruppaUtente;
	}

	public void setRaggruppaUtente(String raggruppaUtente) {
		this.raggruppaUtente = raggruppaUtente;
	}

	public String getRaggruppaApplicazioniEsterne() {
		return raggruppaApplicazioniEsterne;
	}

	public void setRaggruppaApplicazioniEsterne(String raggruppaApplicazioniEsterne) {
		this.raggruppaApplicazioniEsterne = raggruppaApplicazioniEsterne;
	}

	public String getRaggruppaSupporto() {
		return raggruppaSupporto;
	}

	public void setRaggruppaSupporto(String raggruppaSupporto) {
		this.raggruppaSupporto = raggruppaSupporto;
	}

	public String getRaggruppaPresenzaFile() {
		return raggruppaPresenzaFile;
	}

	public void setRaggruppaPresenzaFile(String raggruppaPresenzaFile) {
		this.raggruppaPresenzaFile = raggruppaPresenzaFile;
	}

	public String getRaggruppaMezzoTrasmissione() {
		return raggruppaMezzoTrasmissione;
	}

	public void setRaggruppaMezzoTrasmissione(String raggruppaMezzoTrasmissione) {
		this.raggruppaMezzoTrasmissione = raggruppaMezzoTrasmissione;
	}

	public String getRaggruppaLivelliRiservatezza() {
		return raggruppaLivelliRiservatezza;
	}

	public void setRaggruppaLivelliRiservatezza(String raggruppaLivelliRiservatezza) {
		this.raggruppaLivelliRiservatezza = raggruppaLivelliRiservatezza;
	}

}