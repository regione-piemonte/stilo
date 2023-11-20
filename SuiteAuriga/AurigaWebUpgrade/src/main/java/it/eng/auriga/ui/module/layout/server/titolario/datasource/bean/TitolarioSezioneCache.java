/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.TipoData;
import it.eng.document.XmlVariabile;

import java.util.Date;

public class TitolarioSezioneCache {
	
	@XmlVariabile(nome="IdPianoClassificazione", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String idPianoClassificazione;
	@XmlVariabile(nome="FlgSoloClassifAttive", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String flgSoloAttive;
	@XmlVariabile(nome="DataInizioValiditaDa", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	@TipoData(tipo=TipoData.Tipo.DATA_SENZA_ORA)
	private Date tsInizioVldDa; 
	@XmlVariabile(nome="DataInizioValiditaA", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	@TipoData(tipo=TipoData.Tipo.DATA_SENZA_ORA)
	private Date tsInizioVldA; 
	@XmlVariabile(nome="DataFineValiditaDa", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	@TipoData(tipo=TipoData.Tipo.DATA_SENZA_ORA)
	private Date tsFineVldDa; 
	@XmlVariabile(nome="DataFineValiditaA", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	@TipoData(tipo=TipoData.Tipo.DATA_SENZA_ORA)
	private Date tsFineVldA;
	@XmlVariabile(nome="FlgStatoAbil", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String flgStatoAbil;
	@XmlVariabile(nome="FlgTpDestAbil", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String flgTpDestAbil;
	@XmlVariabile(nome="IdDestAbil", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
	private String idDestAbil;
	
	
	public String getIdPianoClassificazione() {
		return idPianoClassificazione;
	}
	public void setIdPianoClassificazione(String idPianoClassificazione) {
		this.idPianoClassificazione = idPianoClassificazione;
	}
	public String getFlgSoloAttive() {
		return flgSoloAttive;
	}
	public void setFlgSoloAttive(String flgSoloAttive) {
		this.flgSoloAttive = flgSoloAttive;
	}
	public Date getTsInizioVldDa() {
		return tsInizioVldDa;
	}
	public void setTsInizioVldDa(Date tsInizioVldDa) {
		this.tsInizioVldDa = tsInizioVldDa;
	}
	public Date getTsInizioVldA() {
		return tsInizioVldA;
	}
	public void setTsInizioVldA(Date tsInizioVldA) {
		this.tsInizioVldA = tsInizioVldA;
	}
	public Date getTsFineVldDa() {
		return tsFineVldDa;
	}
	public void setTsFineVldDa(Date tsFineVldDa) {
		this.tsFineVldDa = tsFineVldDa;
	}
	public Date getTsFineVldA() {
		return tsFineVldA;
	}
	public void setTsFineVldA(Date tsFineVldA) {
		this.tsFineVldA = tsFineVldA;
	}
	public String getFlgStatoAbil() {
		return flgStatoAbil;
	}
	public void setFlgStatoAbil(String flgStatoAbil) {
		this.flgStatoAbil = flgStatoAbil;
	}
	public String getFlgTpDestAbil() {
		return flgTpDestAbil;
	}
	public void setFlgTpDestAbil(String flgTpDestAbil) {
		this.flgTpDestAbil = flgTpDestAbil;
	}
	public String getIdDestAbil() {
		return idDestAbil;
	}
	public void setIdDestAbil(String idDestAbil) {
		this.idDestAbil = idDestAbil;
	}
	
	
	
}
