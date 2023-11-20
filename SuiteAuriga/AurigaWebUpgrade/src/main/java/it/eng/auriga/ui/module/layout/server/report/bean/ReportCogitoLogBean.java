/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

public class ReportCogitoLogBean {
	
	// filtri
	private String tipoReport;	
	private Date da;
	private Date a;
	
	
	private String idUO;
	private boolean flgIncluseSottoUO;
	private String idUtente;
	private String idClassificazioneSuggerita;
	private String idClassificazioneScelta;
	private String idEsito;
	private boolean errore;
		
	// raggruppamenti
	private String raggruppaPeriodo;
	private String raggruppaUo;
	private boolean raggruppaUtente;
	private boolean raggruppaClassificazione;
	private boolean raggruppaRegistrazione;
	
	
	public String getTipoReport() {
		return tipoReport;
	}
	public void setTipoReport(String tipoReport) {
		this.tipoReport = tipoReport;
	}
	public Date getDa() {
		return da;
	}
	public void setDa(Date da) {
		this.da = da;
	}
	public Date getA() {
		return a;
	}
	public void setA(Date a) {
		this.a = a;
	}
	public String getIdUO() {
		return idUO;
	}
	public void setIdUO(String idUO) {
		this.idUO = idUO;
	}
	public boolean isFlgIncluseSottoUO() {
		return flgIncluseSottoUO;
	}
	public void setFlgIncluseSottoUO(boolean flgIncluseSottoUO) {
		this.flgIncluseSottoUO = flgIncluseSottoUO;
	}
	public String getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}
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
	public boolean isRaggruppaUtente() {
		return raggruppaUtente;
	}
	public void setRaggruppaUtente(boolean raggruppaUtente) {
		this.raggruppaUtente = raggruppaUtente;
	}
	public String getIdClassificazioneSuggerita() {
		return idClassificazioneSuggerita;
	}
	public void setIdClassificazioneSuggerita(String idClassificazioneSuggerita) {
		this.idClassificazioneSuggerita = idClassificazioneSuggerita;
	}
	public String getIdClassificazioneScelta() {
		return idClassificazioneScelta;
	}
	public void setIdClassificazioneScelta(String idClassificazioneScelta) {
		this.idClassificazioneScelta = idClassificazioneScelta;
	}
	public boolean isRaggruppaClassificazione() {
		return raggruppaClassificazione;
	}
	public void setRaggruppaClassificazione(boolean raggruppaClassificazione) {
		this.raggruppaClassificazione = raggruppaClassificazione;
	}
	public String getIdEsito() {
		return idEsito;
	}
	public void setIdEsito(String idEsito) {
		this.idEsito = idEsito;
	}
	public boolean isErrore() {
		return errore;
	}
	public void setErrore(boolean errore) {
		this.errore = errore;
	}
	public boolean isRaggruppaRegistrazione() {
		return raggruppaRegistrazione;
	}
	public void setRaggruppaRegistrazione(boolean raggruppaRegistrazione) {
		this.raggruppaRegistrazione = raggruppaRegistrazione;
	}
	
		
}
