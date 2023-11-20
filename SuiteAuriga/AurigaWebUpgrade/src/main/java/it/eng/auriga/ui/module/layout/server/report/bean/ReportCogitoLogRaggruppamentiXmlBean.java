/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import java.io.Serializable;

public class ReportCogitoLogRaggruppamentiXmlBean implements Serializable{
	
	
	@XmlVariabile(nome="Periodo", tipo=TipoVariabile.SEMPLICE)
	private String raggruppaPeriodo;
	
	
	@XmlVariabile(nome="UO", tipo=TipoVariabile.SEMPLICE)
	private String raggruppaUo;
	
	@XmlVariabile(nome="User", tipo=TipoVariabile.SEMPLICE)
	private String raggruppaUtente;

	@XmlVariabile(nome="Classificazione", tipo=TipoVariabile.SEMPLICE)
	private String raggruppaClassificazione;

	@XmlVariabile(nome="Registrazione", tipo=TipoVariabile.SEMPLICE)
	private String raggruppaRegistrazione;
	
	public String getRaggruppaPeriodo() {
		return raggruppaPeriodo;
	}

	public void setRaggruppaPeriodo(String raggruppaPeriodo) {
		this.raggruppaPeriodo = raggruppaPeriodo;
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

	public String getRaggruppaClassificazione() {
		return raggruppaClassificazione;
	}

	public void setRaggruppaClassificazione(String raggruppaClassificazione) {
		this.raggruppaClassificazione = raggruppaClassificazione;
	}

	public String getRaggruppaRegistrazione() {
		return raggruppaRegistrazione;
	}

	public void setRaggruppaRegistrazione(String raggruppaRegistrazione) {
		this.raggruppaRegistrazione = raggruppaRegistrazione;
	}

	
}